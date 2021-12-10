package lib

import java.util.*
import java.util.regex.Pattern

class Input(private val scanner: Scanner = Scanner(System.`in`)) {

    fun getInts(): List<Int> = generateSequence {
        if (scanner.hasNextInt()) {
            scanner.nextInt()
        } else {
            null
        }
    }
        .toList()
        .also { scanner.close() }

    fun getNextInts(vararg delimiters: String = arrayOf(",")): List<Int> = generateSequence {
        if (scanner.hasNextLine()) {
            scanner.nextLine().split(*delimiters).map { it.toInt() }
        } else {
            null
        }
    }
        .flatten()
        .toList()
        .also { scanner.close() }

    fun getLines(): List<String> = generateSequence {
        if (scanner.hasNext()) {
            scanner.nextLine()
        } else {
            null
        }
    }
        .toList()
        .also { scanner.close() }

    fun get(pattern: String): List<String> = generateSequence {
        if (scanner.hasNext(pattern)) {
            scanner.next(pattern)
        } else {
            null
        }
    }
        .toList()
        .also { scanner.close() }

    fun get(pattern: Pattern): List<String> = generateSequence {
        if (scanner.hasNext(pattern)) {
            scanner.next(pattern)
        } else {
            null
        }
    }
        .toList()
        .also { scanner.close() }
}