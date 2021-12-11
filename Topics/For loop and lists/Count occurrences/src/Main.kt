fun main() {
    // write your code here
    val size = readLine()!!.toInt()
    val ls = mutableListOf<Int>()
    var count = 0
    for (i in 1..size) {
        ls.add(readLine()!!.toInt())
    }
    val search = readLine()!!.toInt()
    for (i in ls) {
        if (i == search) count++
    }
    println(count)
}
