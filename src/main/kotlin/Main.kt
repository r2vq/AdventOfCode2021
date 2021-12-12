import lib.Input

fun main() {
    val nodes = Input().getLines().map { it.split("-") }.createNodes()
    val startNode = nodes.getValue("start")
    val paths = nodes.travel(startNode, setOf(startNode.name))
    println(paths)
}

fun Map<String, Node>.travel(node: Node, expired: Set<String>): Int = if (node.name == "end") {
    1
} else {
    node
        .connections
        .map { connection ->
            if (!expired.contains(connection)) {
                val connectionNode = getValue(connection)
                val childExpired = expired.takeUnless { !connectionNode.isLarge } ?: expired.plus(connection)
                travel(connectionNode, childExpired)
            } else {
                0
            }
        }
        .reduce { acc, i -> acc + i }
}

fun List<List<String>>.createNodes(): Map<String, Node> = fold(mutableMapOf()) { nodes, pair ->
    val (firstName, secondName) = pair
    val first = nodes.lazyAddIfNew(firstName) {
        Node(
            name = firstName,
            connections = emptySet(),
            isLarge = firstName.isAllUpperCase()
        )
    }
    val second = nodes.lazyAddIfNew(secondName) {
        Node(
            name = secondName,
            connections = emptySet(),
            isLarge = secondName.isAllUpperCase()
        )
    }
    nodes.transform(firstName) {
        copy(
            connections = connections.plus(second.name)
        )
    }
    nodes.transform(secondName) {
        copy(
            connections = connections.plus(first.name)
        )
    }
}

fun <T, U> MutableMap<T, U>.lazyAddIfNew(key: T, block: () -> U): U {
    val value = block()
    if (!contains(key)) {
        put(key, value)
    }
    return value
}

fun <T, U> MutableMap<T, U>.transform(key: T, transform: U.() -> U): MutableMap<T, U> =
    this.also { set(key, getValue(key).transform()) }

fun String.isAllUpperCase(): Boolean {
    forEach {
        if (!it.isUpperCase()) {
            return false
        }
    }
    return true
}

data class Node(
    val name: String,
    val connections: Set<String>,
    val isLarge: Boolean,
) {
    override fun toString(): String {
        val connectionNames =
            connections.fold(listOf<String>()) { acc, node -> acc + node }.joinToString(", ") { it }
        return "Node(name=$name, connections=$connectionNames, isLarge=$isLarge)"
    }
}