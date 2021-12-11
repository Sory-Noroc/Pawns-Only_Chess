fun main() {
    val numbers = readLine()!!.split(' ').map{it.toInt()}.toMutableList()
    // do not touch the lines above
    // write your code here   
    numbers.add(0, numbers.sum())
    numbers.removeAt(numbers.size - 2)



    // do not touch the lines below
    println(numbers.joinToString(" "))
}
