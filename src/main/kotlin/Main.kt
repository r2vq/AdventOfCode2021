import java.util.*

fun main() {
    val input = Scanner(System.`in`)

    var maxX = 0
    var maxY = 0
    val coordinates = mutableListOf<Pipe>().apply {
        while (input.hasNext()) {
            val raw = input.nextLine()
            val (start, end) = raw.split(" -> ")
            val (startX, startY) = start.split(",").map { it.toInt() }
            val (endX, endY) = end.split(",").map { it.toInt() }
            add(Pipe(startX, startY, endX, endY))
        }
    }
        .onEach { pipe ->
            println(pipe)
            if (pipe.startX > maxX) maxX = pipe.startX
            if (pipe.startY > maxY) maxY = pipe.startY
            if (pipe.endX > maxX) maxX = pipe.endX
            if (pipe.endY > maxY) maxY = pipe.endY
        }

    val grid = mutableListOf<MutableList<Int>>().apply {
        for (i in 0..maxY) {
            mutableListOf<Int>().apply {
                for (j in 0..maxX) {
                    add(0)
                }
            }.let { row -> add(row) }
        }
    }

    coordinates.forEach { coordinate ->
        val startX = coordinate.startX.coerceAtMost(coordinate.endX)
        val endX = coordinate.startX.coerceAtLeast(coordinate.endX)
        val startY = coordinate.startY.coerceAtMost(coordinate.endY)
        val endY = coordinate.startY.coerceAtLeast(coordinate.endY)
        if (startX == endX) {
            // vertical
            for (y in startY..endY) {
                grid[y][coordinate.startX] += 1
            }
        } else if (startY == endY) {
            // horizontal
            for (x in startX..endX) {
                grid[coordinate.startY][x] += 1
            }
        } else {
            // diagonal
            val isLTR = coordinate.startX < coordinate.endX
            val isUTD = coordinate.startY < coordinate.endY
            var currentX = coordinate.startX
            var currentY = coordinate.startY
            if (isLTR) {
                if (isUTD) {
                    while (currentX <= coordinate.endX && currentY <= coordinate.endY) {
                        grid[currentY][currentX] += 1
                        currentX += 1
                        currentY += 1
                    }
                } else {
                    while (currentX <= coordinate.endX && currentY >= coordinate.endY) {
                        grid[currentY][currentX] += 1
                        currentX += 1
                        currentY -= 1
                    }
                }
            } else {
                if (isUTD) {
                    while (currentX >= coordinate.endX && currentY <= coordinate.endY) {
                        grid[currentY][currentX] += 1
                        currentX -= 1
                        currentY += 1
                    }
                } else {
                    while (currentX >= coordinate.endX && currentY >= coordinate.endY) {
                        grid[currentY][currentX] += 1
                        currentX -= 1
                        currentY -= 1
                    }
                }
            }
        }
    }

    println(coordinates.size)
    println(maxX)
    println(maxY)
    println(grid)
    val overlapping = grid.flatten().filter { it >= 2 }.size
    println(overlapping)
}

data class Pipe(
    val startX: Int,
    val startY: Int,
    val endX: Int,
    val endY: Int,
)