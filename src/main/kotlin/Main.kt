import lib.Input

fun main() {
    Input()
        .getLines()
        .map { line -> line.toOutput() }
        .reduce { acc, curr -> acc + curr }
        .let { println(it) }
}

fun String.toOutput(): Int {
    val (input, output) = split("|").map { it.trim().split(" ") }
    val one = input.toOne()
    val seven = one.remaining.toSeven()
    val four = seven.remaining.toFour()
    val eight = four.remaining.toEight()
    val top = seven.segment.toSet().subtract(one.segment.toSet())
    val nine = eight.remaining.toNine(four.segment.toSet(), top)
    val bottomLeft = eight.segment.toSet().subtract(nine.segment.toSet())
    val two = nine.remaining.toTwo(bottomLeft.first())
    val bottomRight = seven.segment.toSet().subtract(two.segment.toSet())
    val topRight = one.segment.toSet().subtract(bottomRight)
    val six = two.remaining.toSix(topRight.first())
    val zero = six.remaining.toZero()
    val three = zero.remaining.toThree(topRight.first())
    val five = three.remaining.toFive()
    return output
        .map {
            it.toNumber(
                one.segment.toSet(),
                two.segment.toSet(),
                three.segment.toSet(),
                four.segment.toSet(),
                five.segment.toSet(),
                six.segment.toSet(),
                seven.segment.toSet(),
                eight.segment.toSet(),
                nine.segment.toSet(),
                zero.segment.toSet(),
            )
        }
        .joinToString(separator = "") { it }
        .toInt()
}

fun String.toNumber(
    one: Set<Char>,
    two: Set<Char>,
    three: Set<Char>,
    four: Set<Char>,
    five: Set<Char>,
    six: Set<Char>,
    seven: Set<Char>,
    eight: Set<Char>,
    nine: Set<Char>,
    zero: Set<Char>,
): String = when (toSet()) {
    one -> "1"
    two -> "2"
    three -> "3"
    four -> "4"
    five -> "5"
    six -> "6"
    seven -> "7"
    eight -> "8"
    nine -> "9"
    zero -> "0"
    else -> "-1"
}

fun List<String>.toOne(): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment -> segment.length == 2 }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toSeven(): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment -> segment.length == 3 }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toFour(): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment -> segment.length == 4 }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toEight(): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment -> segment.length == 7 }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toNine(fourSegment: Set<Char>, top: Set<Char>): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment ->
        segment.length == 6 && segment.toSet().subtract(fourSegment).subtract(top).size == 1
    }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toTwo(bottomLeft: Char): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment ->
        segment.length == 5 && segment.contains(bottomLeft)
    }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toSix(topRight: Char): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment ->
        segment.length == 6 && !segment.contains(topRight)
    }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toZero(): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment ->
        segment.length == 6
    }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toThree(topRight: Char): Result {
    val mutable = toMutableList()
    val index = mutable.indexOfFirst { segment ->
        segment.length == 5 && segment.contains(topRight)
    }
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

fun List<String>.toFive(): Result {
    val mutable = toMutableList()
    val index = 0
    val segment = mutable.removeAt(index)
    return Result(segment, mutable)
}

data class Result(
    val segment: String,
    val remaining: List<String>,
)