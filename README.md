# Decentralized Voting System - Kotlin

## Summary

This is a Kotlin-based implementation of a Decentralized Voting System using blockchain technology. The system allows voters to cast their votes for candidates in a decentralized and secure manner. Each vote is recorded as a transaction in a block, and blocks are linked together using cryptographic hashing, creating a chain of blocks (blockchain). The system implements Proof of Work (PoW) to mine blocks and secure the network from potential tampering.

## Tech Stack

- **Kotlin:** The programming language used for the implementation.
- **Blockchain:** The voting system is built on the principles of blockchain technology, ensuring data immutability and integrity.
- **SHA-256:** The cryptographic hashing algorithm used to calculate the hash of blocks.
- **Proof of Work (PoW):** Implemented to mine blocks and maintain the integrity of the blockchain.
- **Data Structures:** Kotlin data classes and collections are used to manage blocks, transactions, and voting records.

## How to Use

1. When the program is executed, the Decentralized Voting System will start.
2. The user will be presented with a list of available candidates to vote for.
3. The user needs to enter their Voter ID and select their desired candidate choice.
4. The system will validate the vote, ensuring that a voter can only vote once and that the candidate choice is valid.
5. If the vote is valid, a new block containing the voter's transaction will be added to the blockchain, and the vote will be recorded to prevent multiple votes from the same voter.
6. The user can continue voting for additional voters until they choose to stop.
7. After all votes are recorded, the system will verify the votes and display the voting results for each candidate.

Note: The current implementation always returns true in the `verifyVotes()` function for the sake of example simplicity. In a real-world implementation, additional validations can be performed to ensure the integrity of the voting results.

## Running the Code

To run the Decentralized Voting System, follow these steps:

1. Clone this repository to your local machine.
2. Ensure you have Kotlin installed on your system.
3. Open a terminal and navigate to the project directory.
4. Run the following command to compile and execute the code:
   ```
   kotlinc main.kt -include-runtime -d VotingSystem.jar && java -jar VotingSystem.jar
   ```

Please note that this is a basic example of a decentralized voting system, and in a real-world scenario, additional security measures, user authentication, and network communication protocols would be required to make the system robust and secure.