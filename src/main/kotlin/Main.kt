import lib.Input

fun main() {
    Input().getLines()
        .map { line -> line.split("|").last() }
        .map { line -> line.split(" ").filter { num -> num.isNotEmpty() } }
        .flatten()
        .filter {
            when (it.length) {
                2, 3, 4, 7 -> true
                else -> false
            }
        }
        .let { println(it.size) }
}