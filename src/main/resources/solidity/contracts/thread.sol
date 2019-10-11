pragma solidity ^0.5.11;

contract Thread{
    string public participants;
    address public contractAddress;
    string[] public messages;

    event sendMessage(
     string participants,
     string message,
     string messageSender
    );

    event sendContractAddress(
     string participants,
     address contractAddress
    );

    constructor() public{
    }

    function startThread(string memory _participants, address _contractAddress) public {
        participants = _participants;
        contractAddress = _contractAddress;
        emit sendContractAddress(participants,contractAddress);
    }

    function sendMessageToThread(string memory _reply,string memory _messageSender) public {
        messages.push(_reply);
        emit sendMessage(participants,_reply,_messageSender);
    }
}