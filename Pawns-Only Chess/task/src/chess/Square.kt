package chess

import javax.management.InvalidAttributeValueException

class Square(val x: Int, val y: Int) {
    override fun toString(): String = pawn?.color?.toString() ?: " "
    // representation: B - black pawn, W - white pawn, null - no pawn
    var pawn: Pawn? = null
    fun putPawn(color: Char) {
        pawn = Pawn(color, x, y)
    }

    fun isCorrectPawn(player: Player): Boolean {
        /**
         * Check if the pawn we currently want to move is actually there,
         * and matches the color of the current player
         */
        return pawn != null || pawn?.color == player.pawnColor
    }

    fun noPawn(player: Player): String {
        /**
         * Returns the output in case of a player trying to move a pawn that
         * is not his own, or it's square is empty
         */
        val team = if (player.pawnColor == 'W') "white" else "black"
        return "No $team pawn at $x$y"
    }

    fun canMove(dest: Square?): Boolean {
        /**
         * Check if current square is on the same rank(x)
         */
        return x == dest?.x && pawn!!.isValidMove(dest)
    }

    fun movePawn(player: Player, dest: Square) {
        /**
         * Takes the player that is currently moving, and his intended destination,
         * and moves the pawn from the current square to the new square, and if the
         * player was able to do an En Passant, it means that with this turn he missed
         * his opportunity...
         */
        pawn?.moveTo(dest.x, dest.y)
        dest.pawn = pawn
        pawn = null
        if (player.canDoEP) {
            // Adding to the history of the player that he did not do an EP recently
            player.notMissedEP = false
        }
    }

    fun getCapturingRank(p: Player): Int {
        /**
         * Returns the diagonal files from the current square, where a capture
         * could be possible
         */
        return when (p.pawnColor) {
            'B' -> y-1
            'W' -> y+1
            else -> throw InvalidAttributeValueException("No such value for color!")
        }
    }

    fun canCapture(other: Square): Boolean {
        /**
         * Checks if current square's pawn can capture
         */
        return pawn?.checkCapture(other.pawn) ?: false
        }

    fun capturePawn(player: Player, other: Square) {
        other.pawn = pawn
        pawn?.moveTo(other.x, other.y)
        pawn = null
        if (player.canDoEP) {
            player.notMissedEP = false
        }
    }

    fun canDoEP(victim: Square?): Boolean {
        return pawn?.hasPosForEP(victim?.pawn) == true && victim?.pawn?.moves == 1
    }

    fun doEP(dest: Square, victim: Square, p: Player) {
        /**
         * Does En Passant. Clears the possibility to do another EP in the next turn
         */
        pawn?.moveTo(dest.x, dest.y)
        dest.pawn = pawn
        victim.pawn = null
        pawn = null
        p.canDoEP = false
        p.notMissedEP = false
    }
}
