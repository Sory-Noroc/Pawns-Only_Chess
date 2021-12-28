package chess

const val TITLE = "Pawns-Only Chess"

data class Player(val name: String, val pawnColor: Char)

fun main() {

    val table = PawnsTable(size = 8)
    println(TITLE)

    println("First Player's name:")
    val player1 = Player(readLine()!!, 'W')
    println("Second Player's name:")
    val player2 = Player(readLine()!!, 'B')

    val mediator = Mediator(player1, player2)
    println(table.table)

    while (true) {
        println(mediator.currentPrompt())
        val input = readLine()!!
        
        if (input == "exit") break
        else if (mediator.hasValidInput(input)) {
            val isGoodResponse = table.moved(mediator.turner, input)
            if (isGoodResponse) {
                mediator.changeTurn()
                println(table.table)
            }
        }
    }

    println("Bye!")
}