import java.util.*

fun main(args: Array<String>) {
    val input = Scanner(System.`in`)
    var first = 0
    var second = 0
    var third = 0
    var fourth = 0
    var fifth = 0
    var sixth = 0
    var seventh = 0
    var eigth = 0
    var ninth = 0
    var tenth = 0
    var eleventh = 0
    var twelfth = 0
    var count = 0
    while (input.hasNext()) {
        val current = input.next()
        first += current[0].toBinary()
        second += current[1].toBinary()
        third += current[2].toBinary()
        fourth += current[3].toBinary()
        fifth += current[4].toBinary()
        sixth += current[5].toBinary()
        seventh += current[6].toBinary()
        eigth += current[7].toBinary()
        ninth += current[8].toBinary()
        tenth += current[9].toBinary()
        eleventh += current[10].toBinary()
        twelfth += current[11].toBinary()
        count += 1
    }
    val half = count / 2
    val gamma = buildString {
        append(first.toGamma(half))
        append(second.toGamma(half))
        append(third.toGamma(half))
        append(fourth.toGamma(half))
        append(fifth.toGamma(half))
        append(sixth.toGamma(half))
        append(seventh.toGamma(half))
        append(eigth.toGamma(half))
        append(ninth.toGamma(half))
        append(tenth.toGamma(half))
        append(eleventh.toGamma(half))
        append(twelfth.toGamma(half))
    }.toInt(2)
    val epsilon = buildString {
        append(first.toEpsilon(half))
        append(second.toEpsilon(half))
        append(third.toEpsilon(half))
        append(fourth.toEpsilon(half))
        append(fifth.toEpsilon(half))
        append(sixth.toEpsilon(half))
        append(seventh.toEpsilon(half))
        append(eigth.toEpsilon(half))
        append(ninth.toEpsilon(half))
        append(tenth.toEpsilon(half))
        append(eleventh.toEpsilon(half))
        append(twelfth.toEpsilon(half))
    }.toInt(2)
    println(count)
    println(gamma)
    println(epsilon)
    println(gamma * epsilon)
}

fun Char.toBinary(): Int = if (this == '1') 1 else 0
fun Int.toGamma(half: Int): String = if (this > half) "1" else "0"
fun Int.toEpsilon(half: Int): String = if (this < half) "1" else "0"