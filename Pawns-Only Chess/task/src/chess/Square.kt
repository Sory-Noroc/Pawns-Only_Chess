package chess

class Square(private var representation: Char, val x: Char, val y: Char) {
    // representation: B - black pawn, W - white pawn, " " - no pawn
    override fun toString(): String = representation.toString()
    var pawn: Pawn? = null

    fun putPawn(color: Char) {
        pawn = Pawn(color, x, y)
        representation = color
    }

    fun movePawn(player: Player, t: PawnsTable, l: Char, i: Char): Boolean {
        return if (pawn == null || pawn?.color != player.pawnColor) {
            val team = if (player.pawnColor == 'W') "white" else "black"
            println("No $team pawn at $x$y")
            false
        } else if (pawn!!.isValid(l, i)) {
            pawn?.moveTo(l, i)
            t.grid[i.toString().toInt()]?.get(l)?.pawn = pawn
            pawn = null
            true
        } else {
            println("Invalid Input")
            false
        }
    }
}