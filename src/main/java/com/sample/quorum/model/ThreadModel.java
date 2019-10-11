package com.sample.quorum.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ThreadModel {

    String contractAddress;
    String threadParticipants;
    List<Map<String,String>> threadMessages;

    public ThreadModel(String contractAddress, String threadParticipants) {

        this.contractAddress = contractAddress;
        this.threadParticipants = threadParticipants;
        this.threadMessages = new ArrayList<>();
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getThreadParticipants() {
        return threadParticipants;
    }

    public void setThreadParticipants(String threadParticipants) {
        this.threadParticipants = threadParticipants;
    }

    public List<Map<String,String>> getThreadMessages() {
        return threadMessages;
    }

    public void updateThreadMessages(Map<String,String> threadMessage) {
        this.threadMessages.add(threadMessage);
    }
}
