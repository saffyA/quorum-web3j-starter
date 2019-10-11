package com.sample.quorum.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class Thread extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50610797806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80630d80fefd1461005c5780636c4470fb146100ee578063a743427f146100f6578063d62116df14610225578063f6b4dfb4146102d6575b600080fd5b6100796004803603602081101561007257600080fd5b50356102fa565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100b357818101518382015260200161009b565b50505050905090810190601f1680156100e05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100796103a0565b6102236004803603604081101561010c57600080fd5b81019060208101813564010000000081111561012757600080fd5b82018360208201111561013957600080fd5b8035906020019184600183028401116401000000008311171561015b57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156101ae57600080fd5b8201836020820111156101c057600080fd5b803590602001918460018302840111640100000000831117156101e257600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506103fb945050505050565b005b6102236004803603604081101561023b57600080fd5b81019060208101813564010000000081111561025657600080fd5b82018360208201111561026857600080fd5b8035906020019184600183028401116401000000008311171561028a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550505090356001600160a01b031691506105c39050565b6102de6106b8565b604080516001600160a01b039092168252519081900360200190f35b6002818154811061030757fe5b600091825260209182902001805460408051601f60026000196101006001871615020190941693909304928301859004850281018501909152818152935090918301828280156103985780601f1061036d57610100808354040283529160200191610398565b820191906000526020600020905b81548152906001019060200180831161037b57829003601f168201915b505050505081565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103985780601f1061036d57610100808354040283529160200191610398565b6002805460018101808355600092909252835161043f917f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace019060208601906106c7565b505060408051606080825260008054600260001961010060018416150201909116049183018290527f0eabeffe119b8ffbb23292e86677821e520cbaeb5401f69cb0d565b69fae8e6f93909286928692829160208301918301906080840190889080156104ed5780601f106104c2576101008083540402835291602001916104ed565b820191906000526020600020905b8154815290600101906020018083116104d057829003601f168201915b5050848103835286518152865160209182019188019080838360005b83811015610521578181015183820152602001610509565b50505050905090810190601f16801561054e5780820380516001836020036101000a031916815260200191505b50848103825285518152855160209182019187019080838360005b83811015610581578181015183820152602001610569565b50505050905090810190601f1680156105ae5780820380516001836020036101000a031916815260200191505b50965050505050505060405180910390a15050565b81516105d69060009060208501906106c7565b50600180546001600160a01b0319166001600160a01b038381169190911780835560408051919092166020820181905282825260008054600261010096821615969096026000190116949094049282018390527f4cf8037dff8f2e4212332ce6a37f5353c431bfc409fe36d824e7553dbaf66b86939290919081906060820190859080156106a55780601f1061067a576101008083540402835291602001916106a5565b820191906000526020600020905b81548152906001019060200180831161068857829003601f168201915b5050935050505060405180910390a15050565b6001546001600160a01b031681565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061070857805160ff1916838001178555610735565b82800160010185558215610735579182015b8281111561073557825182559160200191906001019061071a565b50610741929150610745565b5090565b61075f91905b80821115610741576000815560010161074b565b9056fea265627a7a72315820172d2ef06d60aba7717012dcb92d227b0b7a8804ab76971f6a7856ac6c95542c64736f6c634300050b0032";

    public static final String FUNC_MESSAGES = "messages";

    public static final String FUNC_PARTICIPANTS = "participants";

    public static final String FUNC_SENDMESSAGETOTHREAD = "sendMessageToThread";

    public static final String FUNC_STARTTHREAD = "startThread";

    public static final String FUNC_CONTRACTADDRESS = "contractAddress";

    public static final Event SENDMESSAGE_EVENT = new Event("sendMessage", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event SENDCONTRACTADDRESS_EVENT = new Event("sendContractAddress", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected Thread(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Thread(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Thread(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Thread(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> messages(BigInteger param0) {
        final Function function = new Function(FUNC_MESSAGES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> participants() {
        final Function function = new Function(FUNC_PARTICIPANTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> sendMessageToThread(String _reply, String _messageSender) {
        final Function function = new Function(
                FUNC_SENDMESSAGETOTHREAD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_reply), 
                new org.web3j.abi.datatypes.Utf8String(_messageSender)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> startThread(String _participants, String _contractAddress) {
        final Function function = new Function(
                FUNC_STARTTHREAD, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_participants), 
                new org.web3j.abi.datatypes.Address(_contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> contractAddress() {
        final Function function = new Function(FUNC_CONTRACTADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<SendMessageEventResponse> getSendMessageEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SENDMESSAGE_EVENT, transactionReceipt);
        ArrayList<SendMessageEventResponse> responses = new ArrayList<SendMessageEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SendMessageEventResponse typedResponse = new SendMessageEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.participants = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.message = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.messageSender = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SendMessageEventResponse> sendMessageEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, SendMessageEventResponse>() {
            @Override
            public SendMessageEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SENDMESSAGE_EVENT, log);
                SendMessageEventResponse typedResponse = new SendMessageEventResponse();
                typedResponse.log = log;
                typedResponse.participants = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.message = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.messageSender = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SendMessageEventResponse> sendMessageEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SENDMESSAGE_EVENT));
        return sendMessageEventFlowable(filter);
    }

    public List<SendContractAddressEventResponse> getSendContractAddressEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(SENDCONTRACTADDRESS_EVENT, transactionReceipt);
        ArrayList<SendContractAddressEventResponse> responses = new ArrayList<SendContractAddressEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SendContractAddressEventResponse typedResponse = new SendContractAddressEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.participants = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.contractAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<SendContractAddressEventResponse> sendContractAddressEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, SendContractAddressEventResponse>() {
            @Override
            public SendContractAddressEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(SENDCONTRACTADDRESS_EVENT, log);
                SendContractAddressEventResponse typedResponse = new SendContractAddressEventResponse();
                typedResponse.log = log;
                typedResponse.participants = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.contractAddress = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<SendContractAddressEventResponse> sendContractAddressEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SENDCONTRACTADDRESS_EVENT));
        return sendContractAddressEventFlowable(filter);
    }

    @Deprecated
    public static Thread load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Thread(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Thread load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Thread(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Thread load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Thread(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Thread load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Thread(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Thread> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Thread.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Thread> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Thread.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Thread> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Thread.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Thread> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Thread.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class SendMessageEventResponse {
        public Log log;

        public String participants;

        public String message;

        public String messageSender;
    }

    public static class SendContractAddressEventResponse {
        public Log log;

        public String participants;

        public String contractAddress;
    }
}
