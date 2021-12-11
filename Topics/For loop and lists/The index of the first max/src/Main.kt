fun main() {
    // write your code here
    val n = readLine()!!.toInt()
    var max = 0
    var index = 0
    for (i in 0 until n) {
        val x = readLine()!!.toInt()
        if (x > max) {
            max = x
            index = i
        }
    }
    println(index)
}