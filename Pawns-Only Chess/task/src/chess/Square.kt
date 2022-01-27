package chess

class Square(val x: Int, val y: Int) {
    // representation: B - black pawn, W - white pawn, " " - no pawn
    override fun toString(): String = pawn?.color?.toString() ?: " "
    var pawn: Pawn? = null
    fun putPawn(color: Char) {
        pawn = Pawn(color, x, y)
    }

    fun isCorrectPawn(player: Player): Boolean {
        /** Check if the pawn we currently want to move is actually there,
         * and matches the color of the current player
         */
        return pawn != null || pawn?.color == player.pawnColor
//            val team = if (player.pawnColor == 'W') "white" else "black"
//            "No $team pawn at $x$y"
    }

    fun noPawn(player: Player): String {
        val team = if (player.pawnColor == 'W') "white" else "black"
        return "No $team pawn at $x$y"
    }

    fun canMove(dest: Square): Boolean {
        return x == dest.x && pawn!!.isValidMove(dest)
    }

    fun movePawn(player: Player, dest: Square) {
//        if (pawn!!.isValidMove(dest)) {
        pawn?.moveTo(dest.x, dest.y)
        dest.pawn = pawn
        pawn = null
        if (player.canDoEP) {
            // Adding to the history of the player that he did not do an EP recently
            player.notMissedEP = false
        }
//        } else {
//            "Invalid Input"
//        }
    }

    fun canCapture(other: Square): Boolean {
        return pawn?.checkCapture(other.pawn) ?: false
        }

    fun capturePawn(player: Player, other: Square) {
//        return if (canCapture(other)) {
        other.pawn = pawn
        pawn?.moveTo(other.x, other.y)
        pawn = null
        if (player.canDoEP) {
            player.notMissedEP = false
        }
//        null
//        } else {
//            "Invalid Input"
//        }
    }

    fun canDoEP(victim: Square?): Boolean {
        return pawn?.hasPosForEP(victim?.pawn) == true && victim?.pawn?.moves == 1
    }

    fun doEP(dest: Square, victim: Square, p: Player) {
        pawn?.moveTo(dest.x, dest.y)
        dest.pawn = pawn
        victim.pawn = null
        pawn = null
        p.canDoEP = false
        p.notMissedEP = false
    }
}
