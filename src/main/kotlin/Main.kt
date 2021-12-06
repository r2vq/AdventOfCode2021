import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val fishes = scanner.nextLine().split(",").map { it.toInt() }
    println("Initial state: ${fishes.joinToString(",") { it.toString() }}")
    val school = fishes.toSchool()
    println("Initial state transformed: [$school]")
    println(school.after(256).sum())
}

fun List<Int>.toSchool(): School {
    val school = arrayOfNulls<Long>(9).map { 0L }.toMutableList()
    forEach { fish ->
        school[fish] += 1L
    }
    return School(
        zero = school[0],
        one = school[1],
        two = school[2],
        three = school[3],
        four = school[4],
        five = school[5],
        six = school[6],
        seven = school[7],
        eight = school[8],
    )
}

data class School(
    val zero: Long,
    val one: Long,
    val two: Long,
    val three: Long,
    val four: Long,
    val five: Long,
    val six: Long,
    val seven: Long,
    val eight: Long,
) {
    override fun toString(): String = mutableListOf<String>()
        .apply {
            if (zero > 0) add("zero=$zero")
            if (one > 0) add("one=$one")
            if (two > 0) add("two=$two")
            if (three > 0) add("three=$three")
            if (four > 0) add("four=$four")
            if (five > 0) add("five=$five")
            if (six > 0) add("six=$six")
            if (seven > 0) add("seven=$seven")
            if (eight > 0) add("eight=$eight")
        }
        .joinToString(", ") { it }
}

fun School.sum(): Long = zero + one + two + three + four + five + six + seven + eight

fun School.after(days: Int, currentDay: Int = 1): School = School(
    zero = one,
    one = two,
    two = three,
    three = four,
    four = five,
    five = six,
    six = zero + seven,
    seven = eight,
    eight = zero
)
    .also { school -> println("After $currentDay ${if (currentDay == 1) "Day" else "Days"}: [$school]") }
    .run { takeUnless { currentDay < days } ?: after(days, currentDay + 1) }
