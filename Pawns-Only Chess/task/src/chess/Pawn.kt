package chess

import kotlin.math.abs


class Pawn(val color: Char, var x: Char, var y: Char) {
    private var hasFirstMove = true

    fun isValid(l: Char, i: Char): Boolean {
        // Checks first move and if the pawn will remain on same col
        val difference = abs(i.toString().toInt() - y.toString().toInt())
        return if (x == l) {
            if (hasFirstMove) difference <= 2 else difference == 1
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