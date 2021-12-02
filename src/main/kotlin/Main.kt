import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val input = Scanner(System.`in`)
    var isFirst = true
    var count = 0
    var previous = 0
    while (input.hasNext()) {
        val current = input.nextInt()
        if (!isFirst && current > previous) {
            count += 1
        }
        isFirst = false
        previous = current
    }
    println(count)
}