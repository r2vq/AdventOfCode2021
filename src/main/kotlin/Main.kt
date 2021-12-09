import lib.Input

fun main() {
    val lines = Input()
        .getLines()
        .map { line ->
            line
                .split("")
                .filter { num -> num.isNotEmpty() }
                .map { num -> num.toInt() }
        }
    val cells = lines
        .asSequence()
        .mapIndexed { rowIndex, row ->
            row.mapIndexed { colIndex, col ->
                val isLowerThanLeft = row.getOrNull(colIndex - 1)?.isGreaterThan(col) ?: true
                val isLowerThanTop = lines.getOrNull(rowIndex - 1)?.getOrNull(colIndex)?.isGreaterThan(col) ?: true
                val isLowerThanRight = row.getOrNull(colIndex + 1)?.isGreaterThan(col) ?: true
                val isLowerThanBottom = lines.getOrNull(rowIndex + 1)?.getOrNull(colIndex)?.isGreaterThan(col) ?: true
                Cell(
                    isLowPoint = isLowerThanLeft && isLowerThanTop && isLowerThanRight && isLowerThanBottom,
                    height = col
                )
            }
        }
        .flatten()
        .filter { it.isLowPoint }
        .map { it.height + 1 }
        .reduce { acc, cell -> acc + cell }
    println(cells)
}

fun Int.isGreaterThan(other: Int): Boolean {
    return this > other
}

fun Int.isLowerThan(other: Int): Boolean {
    return this < other
}

data class Cell(
    val isLowPoint: Boolean,
    val height: Int
)