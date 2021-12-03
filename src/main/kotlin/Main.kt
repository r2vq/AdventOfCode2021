import java.util.*

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    val items = mutableListOf<String>().apply {
        while (input.hasNext()) {
            add(input.next())
        }
    }.toList()
    var filtered = items
    for (i in 0 until 12) {
        filtered = filtered.filterMostCommonBit(i)
        if (filtered.size == 1) {
            println("breaking at $i")
            break
        } else {
            println("size: ${filtered.size}")
        }
    }
    println("${filtered.size}, ${filtered.first()}")
    println("011101110101".toInt(2) * "100100010010".toInt(2))
}

fun List<String>.filterMostCommonBit(position: Int): List<String> {
    var total = 0
    var count = 0
    forEach { word ->
        total += if (word[position] == '1') 1 else 0
        count += 1
    }
    val mostCommon = if (total * 2 < count) '1' else '0'
    println("$mostCommon :: $total :: $count")
    return filter {
        it[position] == mostCommon
    }
}

// oxygen  011101110101
// gamma   010001110111
// co2     100100010010
// epsilon 100101011001

fun Char.toBinary(): Int = if (this == '1') 1 else 0
fun Int.toGamma(half: Int): String = if (this >= half) "1" else "0"
fun Int.toEpsilon(half: Int): String = if (this <= half) "1" else "0"