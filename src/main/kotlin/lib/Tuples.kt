package lib

/**
 * Creates a tuple of type [Triple] from this and [that].
 *
 * @see [kotlin.to]
 */
infix fun <T, U, V> Pair<T, U>.to(that: V) = Triple(first, second, that)