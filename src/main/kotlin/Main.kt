import java.util.*

fun main(args: Array<String>) {
    println("Hello World!")
    val input = Scanner(System.`in`)
    var horizontal = 0
    var vertical = 0
    while (input.hasNext()) {
        when (input.next()) {
            "forward" -> horizontal += input.nextInt()
            "down" -> vertical += input.nextInt()
            "up" -> vertical -= input.nextInt()
        }
    }
    println(horizontal * vertical)
}