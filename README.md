# quorum-web3j-starter
Starter blockchain application built using Spring Boot-Web3j-Quorum

This repository is a very basic example of how you can use Web3j to build a Quorum based distributed application in Java Spring Boot. Key things to understand:

## Web3j
Web3j is a lightweight Java library that provides an interface to connect with nodes in an Ethereum based Blockchain, such as Quorum. It allows to work with smart contracts as well.

## Quorum
[Quorum](https://github.com/jpmorganchase/quorum) is a permissioned implementation of [Ethereum](https://github.com/ethereum), which is a Blockchain based distributed ledger protocol. 

Very simply put:

**Ethereum:**
- **Network of nodes** that can together maintain a common data pool or ledger.
- **Accounts** are clients of a node and are defined by public private key pairs. Accounts communicate with each other using their public keys
- **Contracts** define certain methods and rules for interacting with the data pool. They can also contain local data.
- **Transactions** are how accounts perform operations in a Blockchain network - be it sending data to other accounts or adding contracts
- **Blocks** are how transactions move in a Blockchain network. Blocks are cryptographically secured and **chained** to each other, so each block contains the header of the next block.

**Quorum:**
- JP Morgan built Quorum on top of the [Go implementation](https://github.com/ethereum/go-ethereum) of Ethereum called go-ethereum
- Only **permissioned nodes** can be a part of the Quorum network.
- It provides a transaction manager to create **encrypted transactions and contracts** to enable private communication between accounts
- **Supports public state** accessible by all accounts to enable the distributed ledger provision of a Blockchain based network
- Private contracts and transactions cannot modify public state of the Quorum

## web3j-quorum
[web3j-quorum](https://github.com/web3j/quorum) is an extension to Web3j to connect a Java application to a Quorum network

## web3j-maven-plugin
In Ethereum, smart contracts are written in [Solidity](https://solidity.readthedocs.io/en/develop/index.html). To harness the power of smart contracts, the [web3j-maven-plugin](https://github.com/web3j/web3j-maven-plugin) generates Java wrapper classes that can be used in our application as actual contracts.

## What does this application actually do?
We have implemented a very basic messaging functionality between accounts in a Quorum network. A message thread can exist between 2 or more accounts. Every time a new thread is started, a new contract is generated which is private to the participating accounts. Thereafter, every message sent to this thread by any participant, updates the existing contract. Since it is a private contract, messages sent to it are only visible to the thread participants.

## Run this application:

- **Step 1:** Set up a Quorum network

A sample Quorum network of 7 nodes (having 1 account each already created) can be setup as instructed in [this example](https://github.com/jpmorganchase/quorum-examples). For running this application, we used the Docker version of the setup.

- **Step 2:** Download this repository (Java Spring Boot application)

This application was created in IntelliJ using the Spring Initializr. Its based on maven and includes Spring Web and Thymeleaf dependancies.

- **Step 3:** Build and run the application

Run the Spring Boot application and access it at http://localhpst:8080/threads in the browser

- **Step 4:** Extending the application for other nodes

This application is distributed over the 7 nodes, which means we can run upto 7 instances of the application. Each instance can connect to a node using its IP address and the account created on the node. This instance in this repository connects to Node1 of the Quorum. Configurations To run an instance for another node - for example - node7:

1. Download the repository into a separate folder

2. Change the following parameters in QuorumConnection.java
  ```
  node = "node7";
  nodeKey = "ROAZBWtSacxXQrOe3FGAqJDyJjFePR5ce4TSIzmJ0Bc=";
  nodeAddress = "0xcc71c7546429a13796cf1bf9228bff213e7ae9cc"
  admin = Admin.build(new HttpService("http://localhost:22006"));
  quorum = Quorum.build(new HttpService("http://localhost:22006"));
  ```
   
3. Specify a separate server port in src/resources/application.properties to run the application on
  ```
  server.port=8081
  ```
  
4. Build and run the application

5. Access the node7 instance of the application on the browser at http://localhost:8081/threads

## Docker issues

1. Fixes for common Docker issues can be found in the Troubleshooting Docker section [here](https://github.com/jpmorganchase/quorum-examples)

2. If timeouts occur frequently, check the status of docker containers using docker ps and ensure they are all healthy. Else, tear the Quorum network down using docker-compose down -v (Its a good idea to remove volumes as well). Spin it up back again using docker-compose up -d
