package com.votingsystem.blockchain
import java.security.MessageDigest

data class Transaction(
    val voterId: String,
    val candidate: String
)

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

class Blockchain {
    private val chain: MutableList<Block> = mutableListOf()
    private val difficulty = 3
    private val votedVoterIds = HashSet<String>() // Set to store voted voter IDs

    init {
        // Create the genesis block (the first block in the chain)
        createGenesisBlock()
    }

    private fun createGenesisBlock() {
        val transactions = listOf(
            Transaction("VoterID-1", "CandidateA"),
            Transaction("VoterID-2", "CandidateB")
        )

        val genesisBlock = Block(
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

    // Function to verify the validity of votes in the blockchain
    fun verifyVotes(): Boolean {
        val voterVotes = mutableMapOf<String, MutableList<String>>()

        for (block in chain) {
            for (transaction in block.transactions) {
                val voterId = transaction.voterId
                val candidate = transaction.candidate

                if (!voterVotes.containsKey(voterId)) {
                    voterVotes[voterId] = mutableListOf(candidate)
                } else {
                    val votes = voterVotes[voterId]!!
                    if (candidate in votes) {
                        println("Invalid vote for Voter ID: $voterId. Multiple votes for the same candidate.")
                        return false
                    } else {
                        votes.add(candidate)
                    }
                }
            }
        }

        return true
    }

    fun hasVoterVoted(voterId: String): Boolean {
        // Check if the voter ID is in the votedVoterIds set
        return votedVoterIds.contains(voterId)
    }

    fun recordVote(voterId: String) {
        // Add the voter ID to the votedVoterIds set after recording the vote
        votedVoterIds.add(voterId)
    }
}
