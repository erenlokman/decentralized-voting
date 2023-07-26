package com.votingsystem.blockchain

import java.security.MessageDigest

data class Transaction(val voterId: String, val candidate: String)

data class Block(
        val index: Int,
        val timestamp: Long,
        val transactions: List<Transaction>,
        val previousHash: String,
        var hash: String,
        var nonce: Long = 0,
        val difficulty: Int // Add the difficulty parameter to the constructor
) {
    // Calculate the hash of the block using the SHA-256 hashing algorithm.
    fun calculateHash(): String {
        val message = "$index$timestamp$transactions$previousHash$nonce"
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(message.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    // Perform Proof of Work to mine the block
    fun mineBlock() {
        val targetPrefix = "0".repeat(difficulty)
        while (!hash.startsWith(targetPrefix)) {
            nonce++
            hash = calculateHash()
        }
        println("Block mined: Nonce = $nonce, Hash = $hash")
    }
}

class Blockchain(private val difficulty: Int, private val candidates: List<String>) {
    private val chain: MutableList<Block> = mutableListOf()
    private val votedVoterIds = HashSet<String>() // Set to store voted voter IDs

    init {
        // Create the genesis block (the first block in the chain)
        createGenesisBlock()
    }

    private fun createGenesisBlock() {
        val transactions =
                listOf(
                        Transaction("VoterID-1", "CandidateA"),
                        Transaction("VoterID-2", "CandidateB")
                )

        val genesisBlock =
                Block(
                        index = 0,
                        timestamp = System.currentTimeMillis(),
                        transactions = transactions,
                        previousHash = "0", // Genesis block has no previous hash
                        hash = "", // The initial hash will be updated during mining (Proof of Work)
                        nonce = 0, // Initialize the nonce to 0
                        difficulty = 4 // Pass the difficulty level here
                )
        genesisBlock.mineBlock() // Mine the genesis block
        chain.add(genesisBlock)
    }

    fun viewResults() {
        val votingResults = mutableMapOf<String, Int>()
    
        // Initialize the votingResults map with candidate names and zero votes
        candidates.forEach { candidate ->
            votingResults[candidate] = 0
        }
    
        // Count the votes for each candidate
        recordedVotes.forEach { (_, candidate) ->
            votingResults[candidate] = votingResults[candidate]?.plus(1) ?: 1
        }
    
        // Display the voting results
        println("Voting Results:")
        votingResults.forEach { (candidate, votes) ->
            println("$candidate: $votes votes")
        }
    }
    

    fun getChain(): List<Block> {
        // Expose the chain as an unmodifiable list to prevent direct modification
        return chain.toList()
    }

    fun getDifficulty(): Int {
        return difficulty
    }

    fun addBlock(block: Block) {
        // Mine the block before adding it to the chain
        block.mineBlock()
        chain.add(block)
    }

    fun isValidChain(): Boolean {
        // Implement the validation logic as before
        return true
    }

    fun resolveConflicts(chains: List<List<Block>>) {
        val longestChain = chains.maxByOrNull { it.size }
        if (longestChain != null && isValidChain()) {
            chain.clear()
            chain.addAll(longestChain)
            println("Chain updated by consensus.")
        } else {
            println("Current chain is already the authoritative chain.")
        }
    }

    fun hasVoterVoted(voterId: String): Boolean {
        // Check if the voter ID is in the votedVoterIds set
        return votedVoterIds.contains(voterId)
    }

    private val voters = mutableSetOf<String>()

    fun hasVoted(voterId: String): Boolean {
        return voterId in voters
    }

    private fun mineBlock(block: Block) {
        val target = "0".repeat(difficulty)
        while (!block.hash.startsWith(target)) {
            block.nonce++
            block.hash = block.calculateHash()
        }
        println("Block mined: Nonce = ${block.nonce}, Hash = ${block.hash}")
    }

    val recordedVotes: MutableSet<Pair<String, String>> = mutableSetOf()

    // Modify the recordVote function in the Blockchain class
    fun recordVote(voterId: String, candidate: String, candidates: List<String>): Boolean {
        // Check if the voter has already voted for the same candidate
        if (Pair(voterId, candidate) in recordedVotes) {
            println(
                    "You have already voted for $candidate. You cannot cast multiple votes for the same candidate."
            )
            return false
        }

        // Check if the candidate choice is valid
        if (candidate !in candidates) {
            println("Invalid candidate choice. Please try again.")
            return false
        }

        // Create a new block with the voter's transaction
        val newBlock =
                Block(
                        index = chain.size,
                        timestamp = System.currentTimeMillis(),
                        transactions = listOf(Transaction(voterId, candidate)),
                        previousHash = chain.lastOrNull()?.hash ?: "0",
                        hash = "",
                        nonce = 0,
                        difficulty = 3
                )

        // Calculate the hash of the new block
        newBlock.hash = newBlock.calculateHash()

        // Add the new block to the blockchain
        addBlock(newBlock)

        // Record the voter ID and candidate choice to prevent multiple votes
        recordedVotes.add(Pair(voterId, candidate))

        return true
    }

    // Modify the verifyVotes function in the Blockchain class
    fun verifyVotes(): Boolean {
        val candidateVotes = mutableMapOf<String, Int>().withDefault { 0 }
        for (block in chain) {
            for (transaction in block.transactions) {
                val candidate = transaction.candidate
                candidateVotes[candidate] = candidateVotes.getValue(candidate) + 1
            }
        }
        return true // Always return true for this example. In a real implementation, you can
        // perform additional validations.
    }
}
