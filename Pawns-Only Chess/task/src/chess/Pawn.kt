package chess

class Pawn(val color: Char, private var x: Char, private var y: Char) {
    private var hasFirstMove = true

    fun isValid(destination: Square): Boolean {
        // Checks first move and if the pawn will remain on same col
        val subtraction = destination.y.toString().toInt() - y.toString().toInt()
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
        hasFirstMove = false
    }
}