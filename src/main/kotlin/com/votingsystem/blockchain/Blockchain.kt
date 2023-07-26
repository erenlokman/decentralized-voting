// Blockchain.kt

package com.votingsystem.blockchain
import java.security.MessageDigest

data class Transaction(
    val voterId: String,
    val candidate: String // Replace "String" with an appropriate data type for candidate information
)


data class Block(
    val index: Int,
    val timestamp: Long,
    val transactions: List<Transaction>,
    val previousHash: String,
    var hash: String, // Make 'hash' mutable to allow changing during mining (Proof of Work)
    var nonce: Long = 0 // Nonce is used in the Proof of Work mechanism
) {
    // Calculate the hash of the block using the SHA-256 hashing algorithm.
    fun calculateHash(): String {
        val message = "$index$timestamp$transactions$previousHash$nonce"
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(message.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}

class Blockchain {
    private val chain: MutableList<Block> = mutableListOf()

    init {
        // Create the genesis block (the first block in the chain)
        createGenesisBlock()
    }

    private fun createGenesisBlock() {
        // Create the genesis block as before
    }

    fun getChain(): List<Block> {
        // Expose the chain as an unmodifiable list to prevent direct modification
        return chain.toList()
    }

    fun addBlock(block: Block) {
        // Add a new block to the chain
        chain.add(block)
    }

    fun isValidChain(): Boolean {
        // Implement the validation logic as before
        return true
    }
}

