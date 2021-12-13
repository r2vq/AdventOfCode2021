import java.util.*

typealias Coordinate = Pair<Int, Int>
typealias Grid = List<MutableList<Boolean>>

fun main() {
    val scanner = Scanner(System.`in`)
    val (coordinates, instructions) = scanner.makeInput()

    val folded = instructions
        .fold(coordinates.toList()) { currentCoordinates, instruction ->
            instruction.fold(currentCoordinates)
        }
    val baseGrid = folded
        .reduce { acc, curr ->
            acc.first.coerceAtLeast(curr.first) to acc.second.coerceAtLeast(curr.second)
        }
        .let { (maxWidth, maxHeight) ->
            arrayOfNulls<List<List<Boolean>>>(maxHeight + 1)
                .map { arrayOfNulls<List<Boolean>>(maxWidth + 1).map { false }.toMutableList() }
        }
    folded
        .fold(baseGrid) { grid: List<MutableList<Boolean>>, (x, y) ->
            grid[y][x] = true
            grid
        }
        .draw()
}

fun Scanner.makeInput(): Pair<MutableList<Coordinate>, MutableList<Fold>> {
    val coordinates = mutableListOf<Coordinate>()
    val instructions = mutableListOf<Fold>()
    var isFull = false
    while (hasNextLine()) {
        val line = nextLine()
        when {
            isFull -> {
                if (line.contains("y")) {
                    instructions.add(VerticalFold(line.split("=")[1].toInt()))
                } else if (line.contains("x")) {
                    instructions.add(HorizontalFold(line.split("=")[1].toInt()))
                }
            }
            line.isEmpty() -> {
                isFull = true
            }
            else -> {
                coordinates.add(line.split(",").let { (x, y) -> x.toInt() to y.toInt() })
            }
        }
    }
    return coordinates to instructions
}

fun Grid.draw() = forEach { row ->
    row
        .map { isActive -> if (isActive) "█" else " " }
        .joinToString("") { char -> char }
        .let { line -> println(line) }
}

interface Fold {
    val position: Int
    fun fold(coordinates: List<Coordinate>): List<Coordinate>
}

class HorizontalFold(override val position: Int) : Fold {
    override fun fold(coordinates: List<Coordinate>): List<Coordinate> = coordinates.map { coordinate ->
        coordinate.run {
            takeIf { first <= position } ?: copy(first = position - (first - position))
        }
    }
}

class VerticalFold(override val position: Int) : Fold {
    override fun fold(coordinates: List<Coordinate>): List<Coordinate> = coordinates.map { coordinate ->
        coordinate.run {
            takeIf { second <= position } ?: copy(second = position - (second - position))
        }
    }
}