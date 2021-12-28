package chess

class Square(val x: Char, val y: Char) {
    // representation: B - black pawn, W - white pawn, " " - no pawn
    override fun toString(): String = pawn?.color?.toString() ?: " "
    var pawn: Pawn? = null

    val yAsInt: Int
    get() {
        return y.toString().toInt()
    }

    fun putPawn(color: Char) {
        pawn = Pawn(color, x, y)
    }

    fun movePawn(player: Player, dest: Square): Boolean {
        return if (pawn == null || pawn?.color != player.pawnColor) {
            val team = if (player.pawnColor == 'W') "white" else "black"
            println("No $team pawn at $x$y")
            false
        } else if (pawn!!.isValid(dest)) {
            pawn?.moveTo(dest.x, dest.y)
            dest.pawn = pawn
            pawn = null
            true
        } else {
            println("Invalid Input")
            false
        }
    }

    fun canDoEnPassant(other: Square): Boolean {
        return (pawn?.hasPosForEP() ?: false) && other.pawn?.moves == 1
        // TODO: 28-Dec-21
    }

    fun canCapture(other: Square): Boolean = when (pawn?.color) {
        'W' -> { pawn != null
            && (other.pawn != null)
                && (pawn?.yAsInt == other.pawn?.yAsInt?.plus(1))
                    && (other.pawn?.x in listOf(pawn?.x?.plus(1), pawn?.x?.minus(1)))
        }
        'B' -> { TODO() }
        else -> false
    }
}