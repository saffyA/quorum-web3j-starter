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
- **Accounts** are clients of a node and are defined by public private key pairs.
- **Contracts** define certain methods and rules for interacting with the data pool. 
- **Transactions** are how accounts perform operations in a Blockchain network - be it sending data to other accounts or adding contracts
- **Blocks** are how transactions move in a Blockchain network. Blocks are cryptographically secured and **chained** to each other, so each block contains the header of the next block.

**Quorum:**
- JP Morgan built Quorum on top of the [Go implementation](https://github.com/ethereum/go-ethereum) of Ethereum called go-ethereum
- Only **permissioned nodes** can be a part of the Quorum network.
- It allows for creation of **encrypted transactions and contracts** to enable private communication between accounts
- **Supports public state** accessible by all accounts to enable the distributed ledger provision of a Blockchain based network
- Private contracts and transactions cannot modify public state of the Quorum

## web3j-quorum
[web3j-quorum](https://github.com/web3j/quorum) is an extension to Web3j to connect a Java application to a Quorum network

## web3j-maven-plugin
In Ethereum, smart contracts are written in [Solidity](https://solidity.readthedocs.io/en/develop/index.html). To harness the power of smart contracts, the [web3j-maven-plugin](https://github.com/web3j/web3j-maven-plugin) generates Java wrapper classes that can be used in our application as actual contracts.
