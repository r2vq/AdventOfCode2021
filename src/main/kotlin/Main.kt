import lib.Input
import java.util.*

val points = mapOf(
    ")" to 1,
    "]" to 2,
    "}" to 3,
    ">" to 4,
)

val brackets = mapOf(
    "(" to ")",
    "[" to "]",
    "{" to "}",
    "<" to ">",
)

fun main() {
    Input()
        .getLines()
        .map { line -> line.split("").filter { char -> char.isNotEmpty() } }
        .mapNotNull { line -> line.checkIncomplete() }
        .map { scores -> scores.calculateScore() }
        .sorted()
        .let { it[it.size / 2] }
        .let { println(it) }
}

fun List<String>.checkIncomplete(): Stack<String>? {
    val expected: Stack<String> = Stack()

    forEach { char ->
        if (brackets.keys.contains(char)) {
            expected.push(brackets[char])
        } else if (expected.pop() != char) {
            return null
        }
    }

    return expected
}

fun Stack<String>.calculateScore(): Long = foldRight(0L) { char: String, score: Long ->
    score.times(5) + (points[char] ?: 0)
}