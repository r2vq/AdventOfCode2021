package lib

@Suppress("MemberVisibilityCanBePrivate", "unused")
class Counter<T>(collection: Collection<T>) {
    val map: Map<T, Long> = collection
        .fold(mutableMapOf()) { map, t ->
            map.apply { put(t, get(t)?.plus(1) ?: 1) }
        }

    operator fun get(key: T): Long = map[key] ?: 0

    override fun toString(): String = mutableListOf<String>().apply {
        map.forEach { (key, count) ->
            add("$key: $count")
        }
    }.joinToString(prefix = "[", separator = "], [", postfix = "]") { it }

    companion object {
        fun <T> of(c: Collection<T>): Counter<T> = Counter(c)

        fun of(s: String, delimiter: String = ""): Counter<String> {
            return of(s.split(delimiter))
        }

        fun <T> of(a: Array<T>): Counter<T> {
            return Counter(a.toList())
        }
    }
}