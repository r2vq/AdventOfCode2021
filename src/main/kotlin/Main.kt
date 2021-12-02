import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")
    val input = Scanner(System.`in`)
    var horizontal = 0
    var vertical = 0
    var aim = 0
    while (input.hasNext()) {
        when (input.next()) {
            "forward" -> {
                val distance = input.nextInt()
                horizontal += distance
                vertical += aim * distance
            }
            "down" -> aim += input.nextInt()
            "up" -> aim -= input.nextInt()
        }
    }
    println(horizontal * vertical)
}