package com.sample.quorum.connection;

import org.springframework.stereotype.Component;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Quorum;

@Component
public class QuorumConnection {

    private String node;
    private String nodeAddress;
    private String nodeKey;
    private Admin admin;
    private Quorum quorum;

    public QuorumConnection() throws Exception {
        node = "node1";

        //Represents an account created in this Quorum node
        nodeAddress = "0xed9d02e382b34818e88b88a309c7fe71e65f419d";

        //Represents public key of the account
        nodeKey = "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=";

        //This node is running on the address passed as a parameter below
        admin = Admin.build(new HttpService("http://localhost:22000"));
        quorum = Quorum.build(new HttpService("http://localhost:22000"));
    }

    public String getNode() {
        return node;
    }

    public String getNodeAddress() {
        return nodeAddress;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Quorum getQuorum() {
        return quorum;
    }

}
