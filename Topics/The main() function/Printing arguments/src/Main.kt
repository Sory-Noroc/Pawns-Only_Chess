fun main(args: Array<String>) {
    if (args.size == 3) {
        for (i in args.indices) {
            println("Argument ${ i + 1 }: ${args[i]}. It has ${args[i].length} characters")
        }
    } else {
        println("Invalid number of program arguments")
    }
}
