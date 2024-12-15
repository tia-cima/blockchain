# Blockchain Miner and Node

This program demonstrates a simplified blockchain system with a **node** for managing the blockchain and a **miner** for mining blocks. The implementation includes features such as transaction handling, mempool management, Proof-of-Work (PoW) mining, and reward allocation for miners.

---

## Table of Contents
1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Features](#features)
4. [Setup and Installation](#setup-and-installation)
5. [Node API Endpoints](#node-api-endpoints)
6. [Miner Logic](#miner-logic)
7. [Dynamic Difficulty Adjustment](#dynamic-difficulty-adjustment)
8. [Example Workflow](#example-workflow)
9. [Future Enhancements](#future-enhancements)

---

## Overview

This project simulates a basic blockchain system with the following components:
- **Node**: A Spring Boot application responsible for managing the blockchain, validating blocks, and maintaining the mempool (pending transactions).
- **Miner**: A Python script that fetches transactions from the node, mines new blocks by solving a PoW puzzle, and submits valid blocks to the node.

---

## Architecture

- **Node**:
    - Built using Spring Boot.
    - Manages the blockchain (a list of blocks) and the mempool (list of unconfirmed transactions).

- **Miner**:
    - Written in Python.
    - Fetches transactions and the latest block from the node.
    - Performs PoW by brute-forcing a nonce to create a valid block hash.
    - Submits the mined block to the node.

---

## Features

### Node:
1. **Blockchain Management**:
    - Stores a list of blocks with immutability guaranteed by hash chaining.
2. **Mempool Management**:
    - Maintains a list of unconfirmed transactions.
    - Removes transactions from the mempool after block validation.
3. **Block Validation**:
    - Ensures blocks meet the PoW difficulty.
    - Validates transactions and block hash integrity.
4. **Reward System**:
    - Awards miners with credits for each successfully mined and submitted block.

### Miner:
1. **PoW Mining**:
    - Solves the PoW puzzle by finding a nonce that satisfies the difficulty requirement.
2. **Mempool Fetching**:
    - Periodically fetches unconfirmed transactions from the node.
3. **Block Submission**:
    - Submits valid blocks to the node for validation and inclusion in the blockchain.
4. **Reward Accumulation**:
    - Tracks the miner's total earned credits.

---

## Setup and Installation

### Prerequisites
- **Node**:
    - Java 21+
    - Spring Boot
    - Maven

- **Miner**:
    - Python 3.7+
    - Required libraries:
      ```bash
      pip install requests
      ```

### Running the Node
1. Clone the repository:
   ```bash
   git clone https://github.com/tia-cima/blockchain.git
   cd blockchain
   ```
2. Build and run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
3. The node will be available at `http://localhost:8080`.

### Running the Miner
1. Run the miner script:
   ```bash
   python main.py
   ```

---

## Node API Endpoints

### 1. **Get Mempool**
- **Endpoint**: `GET /transactions/get`
- **Description**: Returns the list of unconfirmed transactions in the mempool.

### 2. **Add Transaction**
- **Endpoint**: `POST /transactions/add`
- **Description**: Adds a new transaction to the mempool.
- **Request Body**:
  ```json
  {
    "sender": "",
    "recipient": "",
    "amount": 0,
    "data": "",
    "timestamp": ""
  }
  ```

### 3. **Get Latest Block**
- **Endpoint**: `GET /blockchain/get/latest`
- **Description**: Fetches the latest block in the blockchain.

### 4. **Add Block**
- **Endpoint**: `POST /blockchain/add`
- **Description**: Validates and adds a mined block to the blockchain.
- **Request Body**:
  ```json
  {
    "index": 1,
    "timestamp": "2024-12-15T10:10:00Z",
    "transactions": [
        { "sender": "", "recipient": "", "amount": 0, "data": "", "timestamp": "" }
    ],
    "previousHash": "abcd1234",
    "hash": "0000abcd5678",
    "nonce": 12345,
    "difficulty": 4
  }
  ```
- **Response**:
    - `200 OK` with reward as the response body.
    - `403 Forbidden` if the block is invalid.

---

## Miner Logic

### Overview
1. Fetch transactions from the mempool.
2. Retrieve the latest block from the blockchain.
3. Solve the PoW puzzle by brute-forcing the nonce.
4. Submit the mined block to the node.
5. Accumulate credits for successfully mined blocks.

### PoW Mining
- **Accepted hashes**:
    - The block hash must start with a certain number of leading zeros (difficulty).


## Example Workflow
1. **Add Transactions**:
    - Use the `/transactions/add` endpoint to populate the mempool.
2. **Start Mining**:
    - Run the miner to fetch transactions, mine a block, and submit it to the node.
3. **Validate Block**:
    - The node validates the block, removes included transactions from the mempool, and rewards the miner.
4. **Repeat**:
    - New transactions are added to the mempool, and the process continues.

---

## Future Enhancements
- **Consensus Algorithm**:
    - Implement a mechanism for resolving forks (e.g., longest chain rule).
- **Transaction Fees**:
    - Add optional fees for transactions to incentivize miners.
- **Persistent Storage**:
    - Use a database to store the blockchain and mempool.
- **Improved Security**:
    - Enhance validation rules and prevent double-spending.

---
