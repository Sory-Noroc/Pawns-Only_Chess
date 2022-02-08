package chess

import java.lang.IllegalArgumentException
import javax.management.InvalidAttributeValueException
import kotlin.math.abs

class PawnsTable(private val size:Int) {
    // Horizontal line
    private val line: String = "  +${"---+".repeat(size)}\n"
    private val filesList = listOf(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
    private val files: String = filesList.joinToString("   ")
    private val rows = size downTo 1
    private val grid = mutableMapOf<Int, MutableList<Square>>()

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

    private fun getColumn(a: Char): Int {
        return when (a) {
            'a' -> 0
            'b' -> 1
            'c' -> 2
            'd' -> 3
            'e' -> 4
            'f' -> 5
            'g' -> 6
            'h' -> 7
            else -> throw IllegalArgumentException("Provide correct column Char!")
        }
    }

    private fun makeEmptyGrid() {
        /**
         * Prepares all the rows and columns of the table that will be populated with pieces later
         */
        for (i in rows) {
            val filesList = mutableListOf<Square>()
            for (j in 0..7) {
                filesList.add(Square(j, i))
            }
            grid[i] = filesList
        }
    }

    private fun populateGrid() {
        /**
         * Adds the pawns to the table, as these are the only pieces we use in this type of game
         * B -> Black team
         * W -> White team
         */
        grid[size-1]?.forEach { it.putPawn('B') }
        grid[2]?.forEach { it.putPawn('W') }
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
            tempList.add(buildRow(k, v.map { it.toString() }) + '\n')
        } // When accessing 'table' it will return this line
        return tempList.joinToString(line, prefix=line, postfix=line) + files
    }

    private fun accessSquares(pos: String): Pair<Square, Square> {
        /**
         * Decomposes a string with coordinates like "a5b6" to the actual squares at these positions,
         * returned as a pair
         */
        val (x1, y1, x2, y2) = pos.toCharArray()
        val startSquare = grid[y1.digitToInt()]!![getColumn(x1)]
        val destSquare = grid[y2.digitToInt()]!![getColumn(x2)]
        return Pair(startSquare, destSquare)
    }

    private fun checkForEP(player: Player): Boolean {
        val vertPos = when (player.pawnColor) {
            'W' -> 5
            'B' -> 4
            else -> throw IllegalArgumentException("No such Pawn Color!")
        }
        grid[vertPos]?.forEach {
            if (it.pawn?.hasPosForEP(grid[vertPos]!!) == true) {
                return true
            }
        }
        return false
    }

    private fun checkEP(start: Square, dest: Square, player: Player): Boolean {
        val victim: Square? = if (dest.y - start.y == 1 && abs(dest.x - start.x) == 1)
            grid[start.y]!![dest.x] else null
        // Makes the p Player able to make an En Passant in the next turn if the conditions are met
        return start.canDoEP(victim) && player.notMissedEP
    }

    private fun doEP(start: Square, dest: Square, player: Player) {
        /**
         * If the current player is able to do an En Passant, the pawn moves, then the capability of
         * the player to do another EP is lost, else we output that there is an invalid input
         */
//        player.canDoEP = checkEP(start, dest, player)
//        return if (player.canDoEP) {
        val victim: Square = grid[start.y]!![dest.x]
//        start.pawn?.moveTo(dest.x, dest.y)
//        dest.pawn = start.pawn
//        victim?.pawn = null
//        start.pawn = null
//        player.canDoEP = false
//        player.notMissedEP = false
        start.doEP(dest, victim, player)
//            null
//        } else { "Invalid Input" }
    }

    fun checkAndMove(p: Player, coordinates: String): String {
        /**
         * Moves/Captures pawns if possible, else it returns the response of the error (if no errors: null)
         */
        val (startSq, destSq) = accessSquares(coordinates)
//        var error = "Invalid Input"
        p.canDoEP = checkForEP(p)

        if (!startSq.isCorrectPawn(p)) return startSq.noPawn(p)

        when {
            // Only one branch gets executed, or we have an invalid input
            startSq.canMove(destSq) -> startSq.movePawn(p, destSq)
            startSq.canCapture(destSq) -> startSq.capturePawn(p, destSq)
            checkEP(startSq, destSq, p) -> doEP(startSq, destSq, p)
            else -> return "Invalid Input"
        }
        // If we arrived here, it means we skipped the else so the pawn had moved
        return "Well"

//        if (startSq.x == destSq.x) {
//            val movedResponse: String? = startSq.movePawn(p, destSq)
//            if (movedResponse == null) return null else error = movedResponse
//        } else {
//            val captureResponse: String? = startSq.tryCapture(p, destSq)
//            if (captureResponse == null) return null
//            val doEPResponse: String? = tryEP(startSq, destSq, p)
//            if (doEPResponse == null) return null
//        }
//        return error
    }

    private fun checkStalemate(p: Player): Char? {
        /**
         * Checks if the current player can't move any pawn, which means it's a stalemate
         * For each of his pawns we check if it can move, capture or do En Passant(EP)
         * Return: 'S' if there is stalemate else null
         */
        grid.forEach { (y, squares) ->
            squares.filter { i -> i.pawn?.color == p.pawnColor }.forEach {
                // it -> squares with pawns of our player
                if (canMoveAnywhere(it, p) || canCaptureAny(it, p) || canDoEPAnywhere(it, p)) {
                    return null
                }
            }
        }
        return 'S'
    }

    private fun canMoveAnywhere(sq: Square, p: Player): Boolean {
        /**
         * Checks if the square(pawn from it) of the current player can move
         * Returns true if
         */
        return when (p.pawnColor) {
            'B' -> sq.canMove(grid[sq.y - 2]?.get(sq.x)) || sq.canMove(grid[sq.y - 1]?.get(sq.x))
            'W' -> sq.canMove(grid[sq.y + 2]?.get(sq.x)) || sq.canMove(grid[sq.y + 1]?.get(sq.x))
            else -> false
        }
    }

    private fun canCaptureAny(sq: Square, p: Player): Boolean {
        val rank = sq.getCapturingRank(p)
        val left = sq.x - 1
        val right = sq.x + 1
        val canCaptureLeft = if (left in 0..7) sq.canCapture(grid[rank]!![left]) else false
        val canCaptureRight = if (right in 0..7) sq.canCapture(grid[rank]!![right]) else false
        return canCaptureLeft || canCaptureRight
    }

    private fun canDoEPAnywhere(sq: Square, p: Player): Boolean {
        val rank = sq.getCapturingRank(p)
        val left = sq.x - 1
        val right = sq.x + 1
        val checkLeft = if (left in 0..7) checkEP(sq, grid[rank]!![left], p) else false
        val checkRight = if (right in 0..7) checkEP(sq, grid[rank]!![right], p) else false
        return checkLeft || checkRight
    }

    private fun noPawns(m: Mediator): Char? {
        /**
         * Checks if out client has 0 pawns, then returns color of opposite player
         */
        var c = 0
        grid.values.forEach { list ->
            list.forEach { sq ->
                if (sq.pawn?.color == m.turner.pawnColor) c++
            }
        }
        // If anyone has 0 pawns, it means the opposite player won
        return if (c == 0) m.getOtherPlayer().pawnColor else null
    }

    private fun onLastRank(): Char? {
        /**
         * Check if reached opposite side
         * Return the color of the player that has reached the end
         */
        val check = mapOf(1 to 'B', 8 to 'W')

        check.forEach { (rank, color) ->
            grid[rank]?.onEach {
                // Check if there is any pawn on last rank, and return winner
                if (it.pawn != null) {
                    return color
                }
            }
        }
        return null
    }

    fun isGameOver(m: Mediator): Boolean {
        /**
         * After each turn, check if there is any pawn on end, current player
         * has 0 pawns or can't move
         */
        val results: List<Char?> = listOf(
            onLastRank(),
            noPawns(m),
            checkStalemate(m.turner)
        )
        if (results.any { it is Char }) {
            // Gets the winner from results and prints him
            printResultMsg(results.filterIsInstance<Char>()[0])
        }
        return results.any { it is Char }
    }

    private fun printResultMsg(output: Char) {
        println(
            when(output) {
                'B' -> "Black Wins!"
                'W' -> "White Wins!"
                'S' -> "Stalemate!"
                else -> throw IllegalArgumentException("No such output! B/W/S needed")
            }
        )
    }
}
