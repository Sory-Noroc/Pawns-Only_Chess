fun main() {  
    val beyondTheWall = readLine()!!.split(", ").map { it }.toMutableList()
    val backFromTheWall = readLine()!!.split(", ").map { it }.toMutableList()   
    // do not touch the lines above
    // write your code here
    println((beyondTheWall - backFromTheWall).isEmpty())

}