package chess

class Pawn(val color: Char, private var x: Int, private var y: Int) {
    private val hasFirstMove: Boolean
        get() {
        return moves == 0
    }
    var moves: Int = 0
    private set


    fun isValidMove(destination: Square): Boolean {
        /**
         * Check the pawn is moving for the first time, being capable of moving
         * 2 squares, or 1 square forward in any other case
         */
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
        /**
         * Checks if we are trying to capture a valid pawn, which sits diagonally
         */
        return if (other != null && other.x in listOf(x + 1, x - 1)) {
            when (color) {
                'W' -> y + 1 == other.y
                'B' -> y - 1 == other.y
                else -> false
            }
        } else { false }
    }

    fun checkCapture(other: Pawn?): Boolean {
        /**
         * Check the destination of the pawn we want to take, and if it is
         * of the opposite color
         */
        return when (color) {
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