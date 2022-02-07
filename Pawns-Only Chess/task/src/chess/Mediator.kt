package chess

class Mediator(private val player1: Player, private val player2: Player) {
    private val prompt = "'s turn:"
    var turner = player1
        private set

    fun currentPrompt(): String {
        return turner.name + prompt
    }

    fun changeTurn() {
        turner = if (turner == player1) player2 else player1
    }

    fun getByColor(color: Char): Player {
        return if (player1.pawnColor == color) player1 else player2
    }

    fun hasValidInput(input: String): Boolean {

        return if (input.matches(Regex("^[a-h][1-8][a-h][1-8]$"))) true else {
            println("Invalid Input")
            false
        }
    }

    fun getOtherPlayer(): Player {
        return if (turner.pawnColor == 'W') getByColor('B') else turner
    }
}