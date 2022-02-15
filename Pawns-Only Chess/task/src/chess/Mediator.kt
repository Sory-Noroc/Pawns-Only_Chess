package chess

class Mediator(private val player1: Player, private val player2: Player) {
    private val prompt = "'s turn:"
    var turner = player1
        private set

    fun currentPrompt(): String {
        /**
         * Returns the message to be displayed for the player that turns now
         */
        return turner.name + prompt
    }

    fun changeTurn() {
        turner = if (turner == player1) player2 else player1
    }

    fun hasValidInput(input: String): Boolean {
        /**
         * Check is the start and destination squares are actually on the board,
         * otherwise it means an invalid input
         */
        return if (input.matches(Regex("^[a-h][1-8][a-h][1-8]$"))) true else {
            println("Invalid Input")
            false
        }
    }
}