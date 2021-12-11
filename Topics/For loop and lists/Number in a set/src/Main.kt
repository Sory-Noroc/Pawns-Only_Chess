fun main() {
    // write your code here
    val size = readLine()!!.toInt()
    val mutList = mutableListOf<Int>()
    for (i in 0 until size) {
        mutList.add(readLine()!!.toInt())
    }
    val find = readLine()!!.toInt()
    println(if (find in mutList) "YES" else "NO")
}
