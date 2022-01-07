package chess

import kotlin.math.abs

class PawnsTable(private val size:Int) {
    // Horizontal line
    private val line: String = "  +${"---+".repeat(size)}\n"
    private val filesList = listOf(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
    private val files: String = filesList.joinToString("   ")
    private val rows = size downTo 1
    private val grid = mutableMapOf<Int, MutableMap<Char, Square>>()

    override fun toString(): String {
        /**
         * Gets used when we print the table. Converts the grid map to an actual string representation of a chess table
         */
        return assembleTable()
    }

    init {
        assert(size >= 5)
        makeEmptyGrid()
        populateGrid()
    }

    private fun makeEmptyGrid() {
        /**
         * Prepares all the rows and columns of the table that will be populated with pieces later
         */
        for (i in rows) {
            val filesMap = mutableMapOf<Char, Square>()
            for (j in 'a'..'h') {
                filesMap[j] = Square(j, i.toString()[0])
            }
            grid[i] = filesMap
        }
    }

    private fun populateGrid() {
        /**
         * Adds the pawns to the table, as these are the only pieces we use in this type of game
         * B -> Black team
         * W -> White team
         */
        grid[size-1]?.forEach { it.value.putPawn('B') }
        grid[2]?.forEach { it.value.putPawn('W') }
    }

    private fun buildRow(rank: Int, row: List<String>): String {
        /**
         * Creates the string representation of each row of the table
         */
        return "$rank " + row.joinToString(" | ", prefix="| ", postfix=" | ")
    }

    private fun assembleTable(): String {
        val tempList = mutableListOf<String>()
        grid.forEach { (k, v) ->
            tempList.add(buildRow(k, v.values.map { it.toString() }) + '\n')
        } // When accessing 'table' it will return this line
        return tempList.joinToString(line, prefix=line, postfix=line) + files
    }

    private fun accessSquares(pos: String): Pair<Square, Square> {
        /**
         * Decomposes a string with coordinates like "a5b6" to the actual squares at these positions,
         * returned as a pair
         */
        val (x1, y1, x2, y2) = pos.toCharArray()
        val startSquare = grid[y1.digitToInt()]!![x1]!!
        val destSquare = grid[y2.digitToInt()]!![x2]!!
        return Pair(startSquare, destSquare)
    }

    private fun checkForEP(player: Player): Boolean {
        when(player.pawnColor) {
            'W' -> {
                grid[5]?.forEach {
                    if (it.value.pawn?.hasPosForEP(grid[5]!!) == true) {
                        return true
                    }
                }
                return false
            }
            'B' -> {
                grid[4]?.forEach{
                    if (it.value.pawn?.hasPosForEP(grid[4]!!) == true) {
                        return true
                    }
                }
                return false
            }
            else -> return false
        }
    }

    private fun tryEP(start: Square, dest: Square, player: Player): String? {
        /**
         * If the current player is able to do an En Passant, the pawn moves, then the capability of
         * the player to do another EP is lost, else we output that there is an invalid input
         */
        val victim: Square? = if (dest.y - start.y == 1 && abs(dest.x - start.x) == 1)
            grid[start.y.digitToInt()]!![dest.x]!! else null
        // Makes the p Player able to make an En Passant in the next turn if the conditions are met
        player.canDoEP = start.canDoEP(victim) && player.notMissedEP == true
        return if (player.canDoEP) {
            start.pawn?.moveTo(dest.x, dest.y)
            dest.pawn = start.pawn
            victim?.pawn = null
            start.pawn = null
            player.canDoEP = false
            player.notMissedEP = false
            null
        } else { "Invalid Input" }
    }

    fun moved(p: Player, coordinates: String): String? {
        /**
         * Moves/Captures pawns if possible, else it returns the response of the error (if no errors: null)
         */
        val (startSq, destSq) = accessSquares(coordinates)
        var error = "Invalid Input"
        p.canDoEP = checkForEP(p)

        if (startSq.x == destSq.x) {
            val movedResponse: String? = startSq.movePawn(p, destSq)
            if (movedResponse == null) return null else error = movedResponse
        } else {
            val captureResponse: String? = startSq.tryCapture(p, destSq)
            if (captureResponse == null) return null
            val doEPResponse: String? = tryEP(startSq, destSq, p)
            if (doEPResponse == null) return null
        }
        return error
    }
}
