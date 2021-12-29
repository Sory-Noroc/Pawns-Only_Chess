package chess

class Pawn(val color: Char, var x: Char, var y: Char) {
    private val hasFirstMove: Boolean
        get() {
        return moves == 0
    }
    var moves: Int = 0
    private set

    private val yAsInt: Int
        get() {
            return y.toString().toInt()
        }

    fun isValid(destination: Square): Boolean {
        // Checks first move and if the pawn will remain on same col
        val subtraction = destination.yAsInt - yAsInt
        val difference = if (color == 'W') subtraction else -subtraction
        return if (x == destination.x && destination.pawn == null) {
            if (hasFirstMove) difference in 1..2 else difference == 1
        } else {
            false
        }
    }

    fun moveTo(l: Char, i: Char) {
        x = l
        y = i
        moves++
    }

    fun checkCaptureDistance(other: Pawn?): Boolean {
        return if (other != null && other.x in listOf(x + 1, x - 1)) {
            when (color) {
                'W' -> yAsInt + 1 == other.yAsInt
                'B' -> yAsInt - 1 == other.yAsInt
                else -> false
            }
        } else { false }
    }

    fun checkCapture(other: Pawn?): Boolean {
        return when (color) {
            'W' -> {
                checkCaptureDistance(other)
            }
            'B' -> {
                TODO()
            }
            else -> false
        }
    }

    fun hasPosForEP(): Boolean {
        return color == 'W' && y == '5' || color == 'B' && y == '4'
        // TODO: 28-Dec-21  
    }
}