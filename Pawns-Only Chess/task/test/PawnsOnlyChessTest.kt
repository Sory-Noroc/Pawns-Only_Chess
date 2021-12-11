import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram

val pawnsWhite = List<Pair<Int, Int>>(8) {index -> Pair(1,index) }
val pawnsBlack = List<Pair<Int, Int>>(8) {index -> Pair(6,index) }

class PawnsOnlyChessTest : StageTest<Any>() {
    @DynamicTest
    fun test1(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()

        var position = checkOutput(outputString.toLowerCase(), 0, "pawns-only chess")
        if ( position  == -1 ) return CheckResult(false, "Program title is expected.")
        position = checkOutput(outputString.toLowerCase(), position, "first player's name:")
        if ( position  == -1 ) return CheckResult(false, "Player 1 name prompt is expected.")

        outputString = main.execute("John").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "second player's name:")
        if ( position  == -1 ) return CheckResult(false, "Player 2 name prompt is expected.")

        outputString = main.execute("Amelia").trim()
        position = checkChessboard(outputString, 0, pawnsWhite, pawnsBlack)
        if ( position  == -1 ) return CheckResult(false, "Wrong chessboard printout")
        position = checkOutput(outputString.toLowerCase(), position, "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Player 1 prompt to play is expected.")

        outputString = main.execute("a1h8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "amelia's turn:")
        if ( position  == -1 ) return CheckResult(false, "Player 2 prompt to play is expected.")

        outputString = main.execute("h1a8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Player 1 prompt to play is expected.")

        outputString = main.execute("e2e4").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "amelia's turn:")
        if ( position  == -1 ) return CheckResult(false, "Player 2 prompt to play is expected.")

        outputString = main.execute("e7e5").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Player 1 prompt to play is expected.")

        outputString = main.execute("exit").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "bye")
        if ( position  == -1 ) return CheckResult(false, "Exit message is expected.")

        return CheckResult.correct()
    }

    @DynamicTest
    fun test2(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()

        var position = checkOutput(outputString.toLowerCase(), 0, "pawns-only chess")
        if ( position  == -1 ) return CheckResult(false, "Program title is expected.")
        position = checkOutput(outputString.toLowerCase(), position, "first player's name:")
        if ( position  == -1 ) return CheckResult(false, "Player 1 name prompt is expected.")

        outputString = main.execute("John").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "second player's name:")
        if ( position  == -1 ) return CheckResult(false, "Player 2 name prompt is expected.")

        outputString = main.execute("Amelia").trim()
        position = checkChessboard(outputString, 0, pawnsWhite, pawnsBlack)
        if ( position  == -1 ) return CheckResult(false, "Wrong chessboard printout")
        position = checkOutput(outputString.toLowerCase(), position, "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Player 1 prompt to play is expected.")

        outputString = main.execute("a0h8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a1h9").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a1i8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("e2e4").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "amelia's turn:")
        if ( position  == -1 ) return CheckResult(false, "Player 2 prompt to play is expected.")

        outputString = main.execute("a2a3a").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "amelia's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("exit").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "bye")
        if ( position  == -1 ) return CheckResult(false, "Exit message is expected.")

        return CheckResult.correct()
    }

    @DynamicTest
    fun testAdd1(): CheckResult {
        val main = TestedProgram()
        var outputString = main.start().trim()

        var position = checkOutput(outputString.toLowerCase(), 0, "pawns-only chess")
        if ( position  == -1 ) return CheckResult(false, "Program title is expected.")
        position = checkOutput(outputString.toLowerCase(), position, "first player's name:")
        if ( position  == -1 ) return CheckResult(false, "Player 1 name prompt is expected.")

        outputString = main.execute("John").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "second player's name:")
        if ( position  == -1 ) return CheckResult(false, "Player 2 name prompt is expected.")

        outputString = main.execute("Amelia").trim()
        position = checkChessboard(outputString, 0, pawnsWhite, pawnsBlack)
        if ( position  == -1 ) return CheckResult(false, "Wrong chessboard printout")
        position = checkOutput(outputString.toLowerCase(), position, "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Player 1 prompt to play is expected.")

        outputString = main.execute("j1h8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("m1h8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a1n8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a1z8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a0a8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a7a9").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a10a8").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a1a18").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a2 a4").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("a2\ta4").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "invalid input", "john's turn:")
        if ( position  == -1 ) return CheckResult(false, "Incorrect output after an invalid command.")

        outputString = main.execute("exit").trim()
        position = checkOutput(outputString.toLowerCase(), 0, "bye")
        if ( position  == -1 ) return CheckResult(false, "Exit message is expected.")

        return CheckResult.correct()
    }

}

fun checkChessboard(outputString: String, searchPos: Int, pawnsWhite: List<Pair<Int, Int>>, pawnsBlack: List<Pair<Int, Int>>): Int {
    fun createChessboardStringList(pawnsWhite: List<Pair<Int, Int>>, pawnsBlack: List<Pair<Int, Int>>): List<String> {
        var chessboard = "  +---+---+---+---+---+---+---+---+\n"
        for (i in 7 downTo 0) {
            chessboard += "${i + 1} |"
            for (j in 0..7) {
                val square = when {
                    pawnsWhite.contains(Pair(i, j)) -> 'W'
                    pawnsBlack.contains(Pair(i, j)) -> 'B'
                    else -> ' '
                }
                chessboard += " $square |"
            }
            chessboard += "\n  +---+---+---+---+---+---+---+---+\n"
        }
        chessboard += "    a   b   c   d   e   f   g   h\n"
        return chessboard.trim().split("\n").map { it.trim() }
    }
    val chessboardStringList = createChessboardStringList(pawnsWhite, pawnsBlack)
    return checkOutput(outputString, searchPos, * chessboardStringList.toTypedArray())
}

fun checkOutput(outputString: String, searchPos: Int, vararg checkStr: String): Int {
    var searchPosition = searchPos
    for (str in checkStr) {
        val findPosition = outputString.indexOf(str, searchPosition)
        if (findPosition == -1) return -1
        if ( outputString.substring(searchPosition until findPosition).isNotBlank() ) return -1
        searchPosition = findPosition + str.length
    }
    return searchPosition
}


