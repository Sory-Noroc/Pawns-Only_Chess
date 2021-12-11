fun main() {
    // write your code here
    val n = readLine()!!.toInt()
    val list = mutableListOf<Int>()
    for (i in 0 until n) {
        list.add(readLine()!!.toInt())
    }
    val (p, m) = readLine()!!.split(" ").map { it.toInt() }

    println(
        if (p in list && m in list) "YES" else "NO"
    )
}