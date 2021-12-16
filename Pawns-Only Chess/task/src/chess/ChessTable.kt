package chess

class PawnsTable(private val size:Int) {
    // Horizontal line
    private val line: String = "  +${"---+".repeat(size)}\n"
    private val filesList = listOf(' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
    private val files: String = filesList.joinToString("   ")
    private val rows = size downTo 1
    val grid = mutableMapOf<Int, MutableMap<Char, Square>>()

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

    fun moved(p: Player, coordinates: String): Boolean {
        val (x1, y1, x2, y2) = coordinates.toCharArray()
        val startSquare = grid[y1.toString().toInt()]!![x1]!!
        val destSquare = grid[y2.toString().toInt()]!![x2]!!
        return startSquare.movePawn(p, destSquare)
    }
}