import lib.Input

import kotlin.math.absoluteValue

fun main() {
    var start: Long
    var finish: Long
    Input()
        .getNextInts()
        .also { start = System.currentTimeMillis() }
        .mapDistances()
        .min
        .also { finish = System.currentTimeMillis() }
        .let(::println)
    println("Finished in ${finish - start}ms")
}

/**
 * Determine the total distance of EACH crab to every horizontal position. Then return the smallest position.
 *
 * e.g. If there are crabs in the following positions: [2, 3], then this will make a List with 4 elements for positions
 * from 0 until the largest number (3). Then it will populate the list with the distances for both crabs For example,
 * position 0 is 2 away from the first crab (3 fuel) and 3 away from the second crab (6 fuel), so it will have 9, the
 * second position will have 4... and so on until we have the list [9, 4, 1, 1].
 */
fun List<Int>.mapDistances(): List<Int> = arrayOfNulls<Int>(max)
    .mapIndexed { index, _ ->
        fold(0) { accDistance, position ->
            position
                .minus(index)
                .absoluteValue
                .triangle
                .plus(accDistance)
        }
    }

/**
 * Get the smallest Int in the List.
 */
val List<Int>.min get() = reduce { acc, i -> acc.coerceAtMost(i) }

/**
 * Get the largest Int in the list.
 */
val List<Int>.max get() = reduce { acc, i -> acc.coerceAtLeast(i) }

/**
 * Get the [triangle number](https://en.wikipedia.org/wiki/Triangular_number) of this Int. This is useful since crab
 * ships calculate distance using triangle numbers. i.e. moving 4 spaces away will use 1 + 2 + 3 + 4 fuel.
 */
val Int.triangle get() = this * (this + 1) / 2