import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    val input = Scanner(System.`in`)

    var count = 0
    var first: Int? = null
    var second: Int? = null
    var third: Int? = null
    while (input.hasNext()) {
        val current = input.nextInt()
        when {
            first == null -> first = current
            second == null -> second = current
            third == null -> third = current
            else -> {
                if (current > first) count += 1
                first = second
                second = third
                third = current
            }
        }
    }
    println(count)
}