package chess

class Pawn(val color: Char, var x: Int, var y: Int) {
    private val hasFirstMove: Boolean
        get() {
        return moves == 0
    }
    var moves: Int = 0
    private set


    fun isValidMove(destination: Square): Boolean {
        // Checks first move and if the pawn will remain on same col
        val subtraction = destination.y - y
        val difference = if (color == 'W') subtraction else -subtraction
        return if (x == destination.x && destination.pawn == null) {
            if (hasFirstMove) difference in 1..2 else difference == 1
        } else {
            false
        }
    }

    fun moveTo(l: Int, i: Int) {
        x = l
        y = i
        moves++
    }

    private fun checkCaptureDistance(other: Pawn?): Boolean {
        return if (other != null && other.x in listOf(x + 1, x - 1)) {
            when (color) {
                'W' -> y + 1 == other.y
                'B' -> y - 1 == other.y
                else -> false
            }
        } else { false }
    }

    fun checkCapture(other: Pawn?): Boolean {
        return when (color) {
            // Check the other sq has a pawn, and also of the opp color
            'W' -> {
                checkCaptureDistance(other) && other?.color == 'B'
            }
            'B' -> {
                checkCaptureDistance(other) && other?.color == 'W'
            }
            else -> false
        }
    }

    fun hasPosForEP(other: Pawn?): Boolean {
        return ((color == 'W' && y == 5) || (color == 'B' && y == 4)) && other?.y == y
    }

    fun hasPosForEP(row: MutableList<Square>): Boolean {
        return (x - 1 >= 0) && row[x - 1].pawn?.color == (if (color == 'W') 'B' else 'W')
                || (x + 1 <= 7) && row[x + 1].pawn?.color == (if (color == 'W') 'B' else 'W')
    }
}