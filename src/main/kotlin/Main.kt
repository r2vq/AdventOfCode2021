import lib.Input
import java.util.*

val points = mapOf(
    ")" to 3,
    "]" to 57,
    "}" to 1197,
    ">" to 25137,
)

val brackets = mapOf(
    "(" to ")",
    "[" to "]",
    "{" to "}",
    "<" to ">",
)

fun main() {
    Input().getLines()
        .map { line -> line.split("").filter { char -> char.isNotEmpty() } }
        .map { line -> line.checkIfCorrupted() }
        .reduce { acc, i -> acc + i }
        .let(::println)
}

fun List<String>.checkIfCorrupted(): Int {
    val expected: Stack<String> = Stack()

    forEach { char ->
        if (brackets.keys.contains(char)) {
            expected.push(brackets[char])
        } else if (expected.peek() == char) {
            expected.pop()
        } else {
            val wanted = expected.pop()
            println("Expected $wanted but found $char instead.")
            return points[char] ?: 0
        }
    }

    return 0
}