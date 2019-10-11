package com.sample.quorum.controller;

import com.sample.quorum.connection.QuorumConnection;
import com.sample.quorum.contracts.Thread;
import com.sample.quorum.model.ThreadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.web3j.abi.EventValues;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.tx.ClientTransactionManager;
import java.math.BigInteger;
import java.util.*;
import static org.web3j.tx.Contract.staticExtractEventParameters;

@Controller
public class ThreadController {

    //QuorumConnection defines all necessary parameters for application to connect with its respective node in Quorum
    @Autowired
    QuorumConnection quorumConnection;

    //Create map of all nodes names on Quorum along to the public keys of their respective accounts
    private Map<String,String> allNodeNamesToPublicKeysMap = new HashMap<String, String>(){{
        put("node1","BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=");
        put("node2","QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc=");
        put("node7","ROAZBWtSacxXQrOe3FGAqJDyJjFePR5ce4TSIzmJ0Bc=");
    }};

    //Map to hold thread participants string with corresponding ThreadModel instance - holds all thread data
    private Map<String,ThreadModel> allThreads;

    /*Display all threads that this node is a participant in:
    1. Subscribe to and extract all new thread events from Quorum (event sendContractAddress in thread contract)
    2. Create ThreadModel instances to persist extracted event parameters in application - contract address and participants
    3. Subscribe to and extract all send message events from Quorum (event sendMessage in thread contract)
    4. Persist event parameters in ThreadModel instances. ThreadModel object is identified based on participants returned by sendMessage event
    */
    @GetMapping("/threads")
    public String showThreads(Model model) {

        allThreads = new HashMap<String, ThreadModel>();

        //Keccak hash of sendContractAddress event signature defined in thread contract
        String sendContractAddressEventTopic = "0x4cf8037dff8f2e4212332ce6a37f5353c431bfc409fe36d824e7553dbaf66b86";

        //Create filter to extract out sendContractAddress events from all Thread contracts
        //Keccak hash of event signature is provided as a topic to filter out specifically sendContractAddress events only i.e new threads
        EthFilter filterToExtractNewThreads = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,Collections.emptyList()).addSingleTopic(sendContractAddressEventTopic);


        //Subscribe to all sendContractAddress events based on filter created above and read log of each event
        quorumConnection.getAdmin().ethLogFlowable(filterToExtractNewThreads).subscribe(messageLog -> {
            //          System.out.println(log.getData());
            //          System.out.println(log.getTransactionHash());
            //          System.out.println(log.getTopics());
            //          System.out.println(log.getBlockNumber());
            //          System.out.println(messageLog.toString());

            //Extract sendContractAddress event parameters defined in Thread contract
            EventValues sendContractAddressEventValues = staticExtractEventParameters(Thread.SENDCONTRACTADDRESS_EVENT, messageLog);

            //Create sendContractAddress event response object to store individual parameter values
            Thread.SendContractAddressEventResponse sendContractAddressEventResponse = new Thread.SendContractAddressEventResponse();
            sendContractAddressEventResponse.participants = (String)sendContractAddressEventValues.getNonIndexedValues().get(0).getValue();
            sendContractAddressEventResponse.contractAddress = (String)sendContractAddressEventValues.getNonIndexedValues().get(1).getValue();


            //Create new ThreadModel instance to save new thread details - contract address, participants
            ThreadModel threadModel = new ThreadModel(sendContractAddressEventResponse.contractAddress,sendContractAddressEventResponse.participants);
            allThreads.put(sendContractAddressEventResponse.participants,threadModel);

            System.out.println("contractaddress " + sendContractAddressEventResponse.contractAddress);
            System.out.println("participants " + sendContractAddressEventResponse.participants);
        });


        //Keccak hash of sendMessage event signature defined in thread contract
        String sendMessageEventTopic = "0x0eabeffe119b8ffbb23292e86677821e520cbaeb5401f69cb0d565b69fae8e6f";

        //Create filter to extract out sendMessage events from all Thread contracts
        //Keccak hash of event signature is provided as a topic to filter out specifically sendMessage events only
        EthFilter filterToExtractNewMessagesFromExistingThreads = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,Collections.emptyList()).addSingleTopic(sendMessageEventTopic);

        //Subscribe to all sendMessage events based on filter created above and read log of each event
        quorumConnection.getAdmin().ethLogFlowable(filterToExtractNewMessagesFromExistingThreads).subscribe(messageLog -> {
            //          System.out.println(log.getData());
            //          System.out.println(log.getTransactionHash());
            //          System.out.println(log.getTopics());
            //          System.out.println(log.getBlockNumber());
            //          System.out.println(messageLog.toString());

            //Extract sendMessage event parameters defined in Thread contract
            EventValues messageEventValues = staticExtractEventParameters(Thread.SENDMESSAGE_EVENT, messageLog);

            //Create sendMessage event response object to store individual parameter values
            Thread.SendMessageEventResponse messageTypedResponse = new Thread.SendMessageEventResponse();
            messageTypedResponse.participants= (String) messageEventValues.getNonIndexedValues().get(0).getValue();
            messageTypedResponse.message = (String) messageEventValues.getNonIndexedValues().get(1).getValue();
            messageTypedResponse.messageSender = (String) messageEventValues.getNonIndexedValues().get(2).getValue();
            System.out.println("participants" + messageTypedResponse.participants);
            System.out.println("message " + messageTypedResponse.message);
            System.out.println("messageSender " + messageTypedResponse.messageSender);

            //Store map of sender->message in ThreadModel Instance in allThreads map - identified by thread participants
            Map<String,String> messageSenderMap = new HashMap<String, String>();
            messageSenderMap.put(messageTypedResponse.messageSender,messageTypedResponse.message);
            allThreads.get(messageTypedResponse.participants).updateThreadMessages(messageSenderMap);
        });

        model.addAttribute("threadModels", allThreads);
        return "threads";
    }

    /*Handle new thread creation request:
    1. Data received from start thread form (threads.html)
        1.a List of thread participants
        1.b message
    2. Validations
        2.a Check if node has included itself in participant list
        2.b Check if message is empty
        2.c Check if thread already exists

    3. Deploy a fresh thread contract to create the new thread
        3.a Create list of thread participants public keys to be used as privateFor parameter. Only these participants will get logs of this contract
        3.b Sort the list so thread participants
        3.c Create comma separated string of participants from sorted list - this uniquely identifies a thread in the application ( participants field in ThreadModel )
        3.d Create ClientTransactionManager object by passing QuorumConnection parameters and privateFor - this will handle privacy requirements
        3.e Deploy the new thread contract. This returns a thread contract object
        3.f Extract contract address from thread contract object obtained in 2.d
        3.g Call the sendContractAddress event in thread contract to inform participants of new thread. Parameters sent - contract address, participants
        3.h Call the sendMessage event in thread contract to inform participants of new message in this thread. Parameters sent - participants, message, sender
    */
    @PostMapping("/threads")
    public String createNewThread(@RequestParam("message") String message,@RequestParam("threadParticipants") ArrayList<String> threadParticipants, Model model) throws Exception {


        //Check if node has included itself in participant list
        if(!threadParticipants.contains(quorumConnection.getNode()))
        {
            model.addAttribute("Error","Please include " + quorumConnection.getNode() + " in participants!");
            return showThreads(model);
        }

        //Check if message is empty
        if(message.trim().equals("") || (message == null))
        {
            model.addAttribute("Error","Please enter a message!");
            return showThreads(model);
        }

        //Create list of thread participants public keys to be used as privateFor parameter. Only these participants will get logs of this contract
        List<String> privateFor = new ArrayList<String>();
        for(String threadParticipant : threadParticipants)
            privateFor.add(allNodeNamesToPublicKeysMap.get(threadParticipant));

        //Sort the list so thread participants
        Collections.sort(threadParticipants);

        //Create comma separated string of participants from sorted list - this uniquely identifies a thread in the application ( participants field in ThreadModel )
        String threadParticipantsString = String.join(",",threadParticipants);

        //Check if thread already exists
        if(allThreads.get(threadParticipantsString)!=null)
        {
            model.addAttribute("Error","Thread already exists!");
            return showThreads(model);
        }

        //Create ClientTransactionManager object by passing QuorumConnection parameters and privateFor - this will handle privacy requirements
        ClientTransactionManager clientTransactionManager = new ClientTransactionManager(quorumConnection.getQuorum(),quorumConnection.getNodeAddress(),quorumConnection.getNodeKey(),privateFor, 100,1000);

        //Deploy the new thread contract. This returns a thread contract object
        Thread threadContract = Thread.deploy(quorumConnection.getQuorum(),clientTransactionManager,BigInteger.valueOf(0), BigInteger.valueOf(100000000)).send();

        //Extract contract address from thread contract object obtained in 2.d
        String newThreadContractAddress = threadContract.getContractAddress();

        //Call the sendContractAddress event in thread contract to inform participants of new thread. Parameters sent - contract address, participants
        TransactionReceipt startThreadTransactionReceipt = threadContract.startThread(threadParticipantsString,newThreadContractAddress).send();

        //Call the sendMessage event in thread contract to inform participants of new message in this thread. Parameters sent - participants, message, sender
        TransactionReceipt sendMessageTransactionReceipt = threadContract.sendMessageToThread(message,quorumConnection.getNode()).send();

        return showThreads(model);
    }

    /*Handle update thread request
    1. Data received from update thread form (threads.html)
        1.a New message
        1.b Thread participants
        1.c Contract Address of thread
    2. Validation
        2.a Check if message is empty
    3. Create a sendMessage event to be sent to all thread participants
        3.a Create list of thread participants public keys to be used as privateFor parameter.
        3.b Create ClientTransactionManager object by passing QuorumConnection parameters and privateFor - this will handle privacy requirements
        3.c Load the thread contract based on contract address
        3.d Call the sendMessage event in the contract to inform participants of new message in this thread. Parameters sent - participants, message, sender
     */
    @PostMapping("/updateThreads")
    public String sendNewMessageToExistingThread(@RequestParam("newMessage") String newMessage, @RequestParam("contractAddress") String contractAddress, @RequestParam("threadParticipants") String threadParticipants, Model model) throws Exception {

        //Check if message is empty
        if(newMessage.trim().equals("") || (newMessage == null))
        {
            model.addAttribute("Error","Please enter a message!");
            return showThreads(model);
        }

        //Create list of thread participants public keys to be used as privateFor parameter.
        List<String> privateFor = new ArrayList<String>();
        for(String threadParticipant : Arrays.asList(threadParticipants.split(",")))
            privateFor.add(allNodeNamesToPublicKeysMap.get(threadParticipant));

        //Create ClientTransactionManager object by passing QuorumConnection parameters and privateFor - this will handle privacy requirements
        ClientTransactionManager clientTransactionManager = new ClientTransactionManager(quorumConnection.getQuorum(),quorumConnection.getNodeAddress(),quorumConnection.getNodeKey(),privateFor, 100,1000);

        //Load the thread contract based on contract address
        Thread threadContract = Thread.load(contractAddress,quorumConnection.getQuorum(),clientTransactionManager,BigInteger.valueOf(0), BigInteger.valueOf(100000000));

        //Call the sendMessage event in the contract to inform participants of new message in this thread. Parameters sent - participants, message, sender
        TransactionReceipt sendMessageTransactionReceipt = threadContract.sendMessageToThread(newMessage,quorumConnection.getNode()).send();

        return showThreads(model);
    }
}