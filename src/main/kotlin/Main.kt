import java.util.*

fun main() {
    var maxX = 0
    var maxY = 0
    val coordinates = Scanner(System.`in`)
        .getCoordinates()
        .onEach { pipe ->
            maxX = maxX.coerceAtLeast(pipe.startX).coerceAtLeast(pipe.endX)
            maxY = maxY.coerceAtLeast(pipe.startY).coerceAtLeast(pipe.endY)
        }

    val grid = makeGrid(maxX, maxY)

    coordinates.forEach { coordinate ->
        val xIncrement = getIncrement(coordinate.startX, coordinate.endX)
        val yIncrement = getIncrement(coordinate.startY, coordinate.endY)
        var currentX = coordinate.startX
        var currentY = coordinate.startY

        while (currentX != coordinate.endX || currentY != coordinate.endY) {
            grid[currentY][currentX] += 1

            currentX += xIncrement
            currentY += yIncrement
        }
        grid[currentY][currentX] += 1
    }

    println(grid.flatten().filter { it >= 2 }.size)
}

fun Scanner.getCoordinates(): List<Pipe> = mutableListOf<Pipe>().apply {
    while (hasNext()) add(nextLine().toPipe())
    close()
}

fun String.toPipe(): Pipe {
    val (start, end) = split(" -> ")
    val (startX, startY) = start.split(",").map { it.toInt() }
    val (endX, endY) = end.split(",").map { it.toInt() }
    return Pipe(startX, startY, endX, endY)
}

fun makeGrid(maxX: Int, maxY: Int): MutableList<MutableList<Int>> = mutableListOf<MutableList<Int>>().apply {
    for (i in 0..maxY) mutableListOf<Int>()
        .apply { for (j in 0..maxX) add(0) }
        .let { add(it) }
}

fun getIncrement(start: Int, end: Int) = when {
    start < end -> 1
    start > end -> -1
    else -> 0
}

data class Pipe(
    val startX: Int,
    val startY: Int,
    val endX: Int,
    val endY: Int,
)