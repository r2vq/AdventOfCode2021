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
                val top = if (rowIndex > 0) col.isLowerThan(lines[rowIndex - 1][colIndex]) else true
                val left = if (colIndex > 0) col.isLowerThan(row[colIndex - 1]) else true
                val bottom = if (rowIndex + 1 < lines.size) col.isLowerThan(lines[rowIndex + 1][colIndex]) else true
                val right = if (colIndex + 1 < row.size) col.isLowerThan(row[colIndex + 1]) else true
                Cell(left && top && right && bottom, col, rowIndex, colIndex)
            }
        }
    val basins = cells.flatten()
        .filter { it.isLowPoint }
        .map { cell -> cell.findNeighbours(cells.toList()) }
        .map { grid -> grid.map { it.height }.size }
        .onEach { println(it) }
        .toList()

    var first = 0
    var second = 0
    var third = 0

    basins.forEach { basin ->
        if (basin > first) {
            third = second
            second = first
            first = basin
        } else if (basin > second) {
            third = second
            second = first
        } else if (basin > third) {
            third = basin
        }
    }

    println("$first * $second * $third = ${first * second * third}")
}

fun Cell.findNeighbours(grid: List<List<Cell>>): Set<Cell> = listOfNotNull(
    if (rowIndex > 0 && height < grid[rowIndex - 1][colIndex].height && grid[rowIndex - 1][colIndex].height < 9) {
        grid[rowIndex - 1][colIndex].findNeighbours(grid)
    } else {
        null
    },
    if (colIndex > 0 && height < grid[rowIndex][colIndex - 1].height && grid[rowIndex][colIndex - 1].height < 9) {
        grid[rowIndex][colIndex - 1].findNeighbours(grid)
    } else {
        null
    },
    if (rowIndex + 1 < grid.size && height < grid[rowIndex + 1][colIndex].height && grid[rowIndex + 1][colIndex].height < 9) {
        grid[rowIndex + 1][colIndex].findNeighbours(grid)
    } else {
        null
    },
    if (colIndex + 1 < grid[rowIndex].size && height < grid[rowIndex][colIndex + 1].height && grid[rowIndex][colIndex + 1].height < 9) {
        grid[rowIndex][colIndex + 1].findNeighbours(grid)
    } else {
        null
    }
)
    .flatten()
    .plus(this)
    .toSet()

fun Int.isLowerThan(other: Int): Boolean {
    return this < other
}

data class Cell(
    val isLowPoint: Boolean,
    val height: Int,
    val rowIndex: Int,
    val colIndex: Int
)