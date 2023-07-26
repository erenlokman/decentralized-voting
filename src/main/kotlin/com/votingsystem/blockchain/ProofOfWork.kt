// ProofOfWork.kt

package com.votingsystem.blockchain

class ProofOfWork(private val difficulty: Int = 4) {
    // The difficulty determines the number of leading zeros required in the block's hash.
    // Higher difficulty means more computational work is required to mine a new block.

    fun mineBlock(block: Block) {
        val targetPrefix = "0".repeat(difficulty)
        while (!block.hash.startsWith(targetPrefix)) {
            // Keep incrementing the nonce until the block's hash satisfies the difficulty requirement.
            block.nonce++
            block.hash = block.calculateHash()
        }
    }
}