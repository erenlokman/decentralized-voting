import com.votingsystem.blockchain.Block
import com.votingsystem.blockchain.Blockchain

fun main() {
    val blockchain = Blockchain()

    if (blockchain.getChain().isNotEmpty()) {
        // Create a new block with transactions and other data.
        val newBlock = Block(
            index = blockchain.getChain().last().index + 1,
            timestamp = System.currentTimeMillis(),
            transactions = listOf(/* Add transactions here */),
            previousHash = blockchain.getChain().last().hash,
            hash = "", // The initial hash will be updated during mining (Proof of Work)
            nonce = 0 // Initialize the nonce to 0
        )

        // Calculate the hash of the new block.
        newBlock.hash = newBlock.calculateHash()

        // Add the new block to the blockchain.
        blockchain.addBlock(newBlock)

        // Verify that the new block is added to the blockchain.
        println("Blockchain:")
        blockchain.getChain().forEach { block ->
            println("Index: ${block.index}, Hash: ${block.hash}")
        }
    } else {
        println("Blockchain is empty. Cannot create a new block.")
    }
}
