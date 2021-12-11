import lib.Input

typealias Grid = List<List<Octopus>>

var totalFlashes = 0L

fun main() {
    val numbers = Input()
        .getLines()
        .map { line ->
            line
                .split("")
                .mapNotNull { char -> char.takeIf { it.isNotEmpty() } }
                .map { char -> char.toInt().let { Octopus(it) } }
        }

    numbers.draw()
    numbers.takeSteps().draw()
    println("Total Flashes: $totalFlashes")
}

fun Grid.takeSteps(count: Int = 0): Grid {
    println("step: $count")
    return takeIf {
        var allZero = true
        forEach { row -> row.forEach { octopus -> if (octopus.energy != 0) allZero = false } }
        allZero
    } ?: step().flash().clear().takeSteps(count + 1)
}

//fun Grid.takeSteps(count: Int): Grid = takeIf { count == 0 } ?: step().flash().clear().takeSteps(count - 1)

fun Grid.step(): Grid = map { row -> row.map { octopus -> octopus.copy(energy = octopus.energy + 1) } }

fun Grid.flash(): Grid {
    val flashed = mutableListOf<Pair<Int, Int>>()
    val result: List<List<Octopus>> = mapIndexed { i, row ->
        row.mapIndexed { j, octopus ->
            if (octopus.energy > 9) {
                totalFlashes += 1
                flashed.add(i to j)
                Octopus(0, true)
            } else {
                octopus
            }
        }
    }
    return if (flashed.isNotEmpty()) {
        var map = result
        flashed.forEach { (rowIndex, colIndex) ->
            map = map.mapIndexed { i, row ->
                row.mapIndexed { j, cell ->
                    when {
                        (i - 1 == rowIndex && j - 1 == colIndex) ||
                                (i - 1 == rowIndex && j == colIndex) ||
                                (i - 1 == rowIndex && j + 1 == colIndex) ||
                                (i == rowIndex && j - 1 == colIndex) ||
                                (i == rowIndex && j + 1 == colIndex) ||
                                (i + 1 == rowIndex && j - 1 == colIndex) ||
                                (i + 1 == rowIndex && j == colIndex) ||
                                (i + 1 == rowIndex && j + 1 == colIndex) -> {
                            cell.copy(energy = cell.energy + 1)
                        }
                        else -> cell
                    }
                }
            }
        }
        map.flash().mapIndexed { i, row ->
            row.mapIndexed { j, cell ->
                if (flashed.contains(i to j)) {
                    cell.copy(energy = 0)
                } else {
                    cell
                }
            }
        }
    } else {
        result
    }
}

fun Grid.clear(): Grid = map { row -> row.map { octopus -> octopus.copy(didFlash = false) } }

fun Grid.draw(): Grid {
    println("=========================================")
    map { row -> row.map { String.format("%2d", it.energy) } }
        .forEach { row -> println(row) }
    println("=========================================")
    return this
}

data class Octopus(
    val energy: Int,
    val didFlash: Boolean = false,
)