// Blockchain.kt

package com.votingsystem.blockchain

data class Transaction(
    val voterId: String,
    val candidate: String // Replace "String" with an appropriate data type for candidate information
)


data class Block(
    val index: Int,
    val timestamp: Long,
    val transactions: List<Transaction>,
    val previousHash: String,
    val hash: String
)

class Blockchain {
    private val chain: MutableList<Block> = mutableListOf()

    init {
        // Create the genesis block (the first block in the chain)
        createGenesisBlock()
    }

    private fun createGenesisBlock() {
        // You can define the initial block data here, such as an initial transaction.
        // For a real-world project, you might want to set up a network and use a consensus mechanism to create the genesis block.
        val genesisBlock = Block(
            index = 0,
            timestamp = System.currentTimeMillis(),
            transactions = emptyList(),
            previousHash = "0",
            hash = "genesisHash" // Replace with the actual hash of the genesis block
        )
        chain.add(genesisBlock)
    }

    fun addBlock(block: Block) {
        // Add a new block to the chain
        chain.add(block)
    }

    fun isValidChain(): Boolean {
        // Implement the validation logic to check if the blockchain is valid.
        // You need to verify the hashes, timestamps, and other criteria to ensure the integrity of the chain.
        // Return true if the blockchain is valid; otherwise, return false.
        // For simplicity, we are not implementing full validation here, and you can extend it as needed.
        return true
    }
}
