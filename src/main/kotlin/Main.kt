import lib.Input

fun main() {
    val heightsGrid = Input().getLines().map { row -> row.toCharArray().map { it.digitToInt() } }
    val cellsGrid = heightsGrid
        .mapIndexed { rowIndex, heightsRow ->
            heightsRow.mapIndexed { colIndex, height ->
                val isLowerThanLeft = height.isLowerThan(heightsGrid, rowIndex, colIndex - 1) ?: true
                val isLowerThanTop = height.isLowerThan(heightsGrid, rowIndex - 1, colIndex) ?: true
                val isLowerThanRight = height.isLowerThan(heightsGrid, rowIndex, colIndex + 1) ?: true
                val isLowerThanBottom = height.isLowerThan(heightsGrid, rowIndex + 1, colIndex) ?: true
                Cell(
                    isLowPoint = isLowerThanLeft && isLowerThanTop && isLowerThanRight && isLowerThanBottom,
                    height = height,
                    rowIndex = rowIndex,
                    colIndex = colIndex
                )
            }
        }

    val (first, second, third) = cellsGrid.flatten()
        .filter { it.isLowPoint }
        .map { cell -> cell.findNeighbours(cellsGrid).size }
        .fold(0 to 0 to 0) { (first, second, third), basin ->
            when {
                basin > first -> basin to first to second
                basin > second -> first to basin to second
                basin > third -> first to second to basin
                else -> first to second to third
            }
        }

    println("$first * $second * $third =")
    println("${first * second * third}")
}

infix fun <T, U, V> Pair<T, U>.to(third: V) = Triple(first, second, third)

fun Int.isLowerThan(heightGrid: List<List<Int>>, rowIndex: Int, colIndex: Int): Boolean? = heightGrid
    .getOrNull(rowIndex)
    ?.getOrNull(colIndex)
    ?.let { this < it }

fun Cell.findNeighbours(cellGrid: List<List<Cell>>): Set<Cell> = listOfNotNull(
    neighbouringBasin(cellGrid, rowIndex - 1, colIndex),
    neighbouringBasin(cellGrid, rowIndex, colIndex - 1),
    neighbouringBasin(cellGrid, rowIndex + 1, colIndex),
    neighbouringBasin(cellGrid, rowIndex, colIndex + 1),
)
    .flatten()
    .toSet()
    .plus(this)

fun Cell.neighbouringBasin(cellGrid: List<List<Cell>>, rowIndex: Int, colIndex: Int): Set<Cell>? = cellGrid
    .getOrNull(rowIndex)
    ?.getOrNull(colIndex)
    ?.takeIf { other -> other.height in height + 1 until 9 }
    ?.findNeighbours(cellGrid)

data class Cell(
    val isLowPoint: Boolean,
    val height: Int,
    val rowIndex: Int,
    val colIndex: Int
)