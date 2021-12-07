import lib.Input

fun main() {
    var max = Integer.MIN_VALUE
    var min = Integer.MAX_VALUE
    val crabs = Input().getLines().first().split(",").map { it.toInt() }
    crabs.forEach {
        if (it > max) {
            max = it
        }
        if (it < min) {
            min = it
        }
    }
    val distances = arrayOfNulls<Long>(max + 1).mapIndexed { currentPosition, _ ->
        var totalDistance = 0
        crabs.forEach { position ->
            totalDistance += kotlin.math.abs(position - currentPosition)
        }
        totalDistance
    }
    var smallestDistance: Pair<Int, Int> = -1 to Int.MAX_VALUE
    distances.forEachIndexed { index, i ->
        if (i < smallestDistance.second) {
            smallestDistance = index to i
        }
    }
    println(smallestDistance)
}