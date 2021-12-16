package chess

class Square(val x: Char, val y: Char) {
    // representation: B - black pawn, W - white pawn, " " - no pawn
    override fun toString(): String = pawn?.color?.toString() ?: " "
    var pawn: Pawn? = null

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
}