package chess

class Square(val x: Char, val y: Char) {
    // representation: B - black pawn, W - white pawn, " " - no pawn
    override fun toString(): String = pawn?.color?.toString() ?: " "
    var pawn: Pawn? = null

    val Y: Int
    get() {
        return y.digitToInt()
    }

    fun putPawn(color: Char) {
        pawn = Pawn(color, x, y)
    }

    fun movePawn(player: Player, dest: Square): String? {
        return if (pawn == null || pawn?.color != player.pawnColor) {
            val team = if (player.pawnColor == 'W') "white" else "black"
            "No $team pawn at $x$y"
        } else if (pawn!!.isValid(dest)) {
            pawn?.moveTo(dest.x, dest.y)
            dest.pawn = pawn
            pawn = null
            null
        } else {
            "Invalid Input"
        }
    }

    private fun canCapture(other: Square): Boolean {
        return pawn?.checkCapture(other.pawn) ?: false
        }

    fun captureIfPossible(other: Square): String? {
        return if (canCapture(other)) {
            other.pawn = pawn
            pawn = null
            null
        } else {
            "Invalid Input"
        }
    }

    fun canDoEP(other: Square): Boolean {
        return (pawn?.hasPosForEP(other.pawn) ?: false) && other.pawn?.moves == 1
        // TODO: 28-Dec-21
    }
}
