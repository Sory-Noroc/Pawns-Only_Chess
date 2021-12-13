package chess


class ChessTable(private val size:Int) {
    // Horizontal line
    private val line: String = "  +${"---+".repeat(size)}\n"
    private val filesList = listOf(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
    private val files: String = filesList.joinToString("   ")
    private val rows = size downTo 1
    private val grid = mutableMapOf<Int, MutableMap<Char, Char>>()

    var table: String = ""
    get() {
        return assembleTable()
    }
    private set

    init {
        assert(size >= 5)
        makeEmptyGrid()
        populateGrid()
    }

    private fun makeEmptyGrid() {
        for (i in rows) {
            val filesMap = mutableMapOf<Char, Char>()
            for (j in 'a'..'h') {
                filesMap[j] = ' '
            }
            grid[i] = filesMap
        }
    }

    private fun populateGrid() {
        grid[size-1]?.forEach { grid[size-1]?.set(it.key, 'B') }
        grid[2]?.forEach { grid[2]?.set(it.key, 'W') }
    }

    private fun buildRow(rank: Int, row: List<Char>): String {
        return "$rank " + row.joinToString(" | ", prefix="| ", postfix=" | ")
    }

    private fun assembleTable(): String {
        val tempList = mutableListOf<String>()
        grid.forEach { (k, v) ->
            tempList.add(buildRow(k, v.values.toList()) + '\n')
        } // When accessing 'table' it will return this line
        return tempList.joinToString(line, prefix=line, postfix=line) + files
    }

    private fun move() {
        TODO()
    }
}

class Mediator(private val player1: Player, private val player2: Player,
              private val table: ChessTable) {
    private val prompt = "'s turn:"
    private var turner = player1

    fun currentPrompt(): String {
        return turner.name + prompt
    }

    fun changeTurn() {
        turner = if (turner == player1) player2 else player1
    }

    fun isInputValid(input: String): Boolean {
        
        return if (input.matches(Regex("^[a-h][1-8][a-h][1-8]$"))) true else {
            println("Invalid Input")
            false
            }
        /* return try {
            assert(input.length == 4)
            assert(table.filesList
                .containsAll(setOf(input[0], input[2])))
            assert(table.rows.map { it.toString()[0] }
                .containsAll(setOf(input[1], input[3])))
            true
        } catch (e: Exception) {
            println("Invalid Input")
            false */
    }
}

data class Player(val name: String, val pawnColor: String)

fun main() {

    val title = "Pawns-Only Chess"
    val pawnsTable = ChessTable(size = 8)

    println(title)
    println("First Player's name:")
    val player1 = Player(readLine()!!, "W")
    println("Second Player's name:")
    val player2 = Player(readLine()!!, "B")
    val mediator = Mediator(player1, player2, pawnsTable)
    println(pawnsTable.table)
    while (true) {
        println(mediator.currentPrompt())
        val input = readLine()!!
        
        if (input == "exit") break
        else if (mediator.isInputValid(input)) {
            mediator.changeTurn()
        }
    } 
    println("Bye!")

}