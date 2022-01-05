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
            if (player.canDoEP) {
                // Adding to the history of the player that he did not do an EP recently
                player.history.add(false)
            }
            null
        } else {
            "Invalid Input"
        }
    }

    private fun canCapture(other: Square): Boolean {
        return pawn?.checkCapture(other.pawn) ?: false
        }

    fun tryCapture(player: Player, other: Square): String? {
        return if (canCapture(other)) {
            other.pawn = pawn
            pawn?.moveTo(other.x, other.y)
            pawn = null
            if (player.canDoEP) {
                player.history.add(false)
            }
            null
        } else {
            "Invalid Input"
        }
    }

    fun canDoEP(victim: Square?): Boolean {
        return pawn?.hasPosForEP(victim?.pawn) == true && victim?.pawn?.moves == 1
    }
}
