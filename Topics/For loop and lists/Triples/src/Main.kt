fun main() {
    // write your code here
    val size = readLine()!!.toInt()
    val ls = mutableListOf<Int>()
    var triples = 0
    for (i in 1..size) {
        ls.add(readLine()!!.toInt())
    }
    for (i in 2..ls.lastIndex) {
        if (ls[i - 2] + 2 == ls[i - 1] + 1 && ls[i - 1] + 1 == ls[i]) triples++
    }
    println(triples)
}
