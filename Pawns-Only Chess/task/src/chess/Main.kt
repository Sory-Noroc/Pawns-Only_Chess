package chess

const val TITLE = "Pawns-Only Chess"

data class Player(val name: String, val pawnColor: Char, var canDoEP: Boolean = false,
var notMissedEP: Boolean = true)

fun main() {

    val table = PawnsTable(size = 8)
    println(TITLE)

    println("First Player's name:")
    val player1 = Player((readLine() ?: "P1"), 'W')
    println("Second Player's name:")
    val player2 = Player((readLine() ?: "P2"), 'B')

    val mediator = Mediator(player1, player2)
    println(table)

    while (true) {
        println(mediator.currentPrompt())
        val input = readLine() ?: ""
        
        if (input == "exit") {
            break
        }
        else if (mediator.hasValidInput(input)) {
            val response = table.checkAndMove(mediator.turner, input)
            if (response == "Well") {
                println(table)
                mediator.changeTurn()
                if (table.isGameOver(mediator)) {
                    break
                }
            } else {
                println(response)
            }
        }
    }

    println("Bye!")
}
