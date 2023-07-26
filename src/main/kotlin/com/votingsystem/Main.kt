import com.votingsystem.blockchain.Block
import com.votingsystem.blockchain.Blockchain
import com.votingsystem.blockchain.Transaction

fun main() {
    val blockchain = Blockchain()
    val candidates = listOf("CandidateA", "CandidateB", "CandidateC")

    while (true) {
        println("Welcome to the Decentralized Voting System!")
        println("Available candidates: ${candidates.joinToString(", ")}")

        // Get voter's input
        print("Enter your Voter ID: ")
        val voterId = readLine() ?: break

        // Check if the voter has already voted
        if (blockchain.hasVoterVoted(voterId)) {
            println("You have already voted. You cannot cast multiple votes.")
            continue
        }

        print("Enter your candidate choice (${candidates.joinToString(", ")}): ")
        val candidateChoice = readLine() ?: break

        // Check if the candidate choice is valid
        if (candidateChoice !in candidates) {
            println("Invalid candidate choice. Please try again.")
            continue
        }

        // Create a new block with the voter's transaction
        val newBlock = Block(
            index = blockchain.getChain().size,
            timestamp = System.currentTimeMillis(),
            transactions = listOf(Transaction(voterId, candidateChoice)),
            previousHash = blockchain.getChain().lastOrNull()?.hash ?: "0",
            hash = "",
            nonce = 0,
            difficulty = 3
        )

        // Calculate the hash of the new block
        newBlock.hash = newBlock.calculateHash()

        // Add the new block to the blockchain
        blockchain.addBlock(newBlock)

        // Record the voter ID to prevent multiple votes
        blockchain.recordVote(voterId)

        println("Block mined: Nonce = ${newBlock.nonce}, Hash = ${newBlock.hash}")
        println("Vote recorded successfully!")

        // Verify the votes after recording the vote
        val isValidVotes = blockchain.verifyVotes()
        if (!isValidVotes) {
            println("Invalid votes detected. Please check your votes.")
            break
        }

        // Ask the user if they want to continue voting
        print("Do you want to continue voting? (yes/no): ")
        val continueVoting = readLine()?.trim()?.lowercase()
        if (continueVoting != "yes") {
            println("Thank you for participating in the voting.")
            break
        }
    }
}
