package chess

class PawnsTable(private val size:Int) {
    // Horizontal line
    private val line: String = "  +${"---+".repeat(size)}\n"
    private val filesList = listOf(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
    private val files: String = filesList.joinToString("   ")
    private val rows = size downTo 1
    private val grid = mutableMapOf<Int, MutableMap<Char, Square>>()

    override fun toString(): String {
        return assembleTable()
    }

    init {
        assert(size >= 5)
        makeEmptyGrid()
        populateGrid()
    }

    private fun makeEmptyGrid() {
        for (i in rows) {
            val filesMap = mutableMapOf<Char, Square>()
            for (j in 'a'..'h') {
                filesMap[j] = Square(j, i.toString()[0])
            }
            grid[i] = filesMap
        }
    }

    private fun populateGrid() {
        grid[size-1]?.forEach { it.value.putPawn('B') }
        grid[2]?.forEach { it.value.putPawn('W') }
    }

    private fun buildRow(rank: Int, row: List<String>): String {
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
        val (x1, y1, x2, y2) = pos.toCharArray()
        val startSquare = grid[y1.digitToInt()]!![x1]!!
        val destSquare = grid[y2.digitToInt()]!![x2]!!
        return Pair(startSquare, destSquare)
    }

    fun checkEP(pos: String): Boolean {
        val (startSq, destSq) = accessSquares(pos)
        return startSq.canDoEP(destSq)
    }

    private fun doEPIfPossible(start: Square, dest: Square, player: Player): String? {
        return if (player.canDoEP) {
            val victim: Square = grid[start.x.digitToInt()]!![dest.y]!!
            dest.pawn = start.pawn
            victim.pawn = null
            start.pawn = null
            player.canDoEP = false
            null
        } else { "Invalid Input" }
    }

    fun moved(p: Player, coordinates: String): String? {
        val (startSq, destSq) = accessSquares(coordinates)
        val movedResponse: String? = startSq.movePawn(p, destSq)
        val captureResponse: String? = startSq.captureIfPossible(destSq)
        val doEPResponse: String? = doEPIfPossible(startSq, destSq, p)
        val responses = listOf(movedResponse, captureResponse, doEPResponse)
        return if (responses.any { it == null}) null else movedResponse
    }
}