import lib.Input

private const val NODE_START = "start"
private const val NODE_END = "end"

fun main() {
    val nodes = Input().getLines().map { it.split("-") }.createNodes()
    val startNode = nodes.getValue(NODE_START)
    val paths = nodes.filter { node -> node.key != NODE_START && node.key != NODE_END && !node.value.isLarge }
        .flatMap { exception -> mapOf(startNode.name to 1).traverse(nodes, startNode, exception.value, startNode.name) }
        .toSet()

    println(paths.size)
}

fun List<List<String>>.createNodes(): Map<String, Node> = fold(mapOf()) { nodes, (firstName, secondName) ->
    nodes
        .lazyPlusIfNotExists(firstName) {
            Node(
                name = firstName,
                connections = emptySet(),
                isLarge = firstName.isAllUpperCase()
            )
        }
        .lazyPlusIfNotExists(secondName) {
            Node(
                name = secondName,
                connections = emptySet(),
                isLarge = secondName.isAllUpperCase()
            )
        }
        .update(firstName) { copy(connections = connections.plus(secondName)) }
        .update(secondName) { copy(connections = connections.plus(firstName)) }
}

fun Map<String, Int>.traverse(nodes: Map<String, Node>, node: Node, exception: Node, path: String): List<String?> =
    if (node.name == NODE_END) {
        listOf(path)
    } else {
        node.connections.mapNotNull { connectionName ->
            val connection = nodes.getValue(connectionName)
            when {
                connection.isLarge || !contains(connectionName) -> plus(connectionName to 0)
                    .traverse(
                        nodes = nodes,
                        node = connection,
                        exception = exception,
                        path = "$path,$connectionName"
                    )
                connectionName == exception.name && get(exception.name) == 0 -> plus(connectionName to 1)
                    .traverse(
                        nodes = nodes,
                        node = connection,
                        exception = exception,
                        path = "$path,$connectionName"
                    )
                else -> null
            }
        }.flatten()
    }

fun <T, U> Map<T, U>.lazyPlusIfNotExists(key: T, initializer: () -> U): Map<T, U> = takeUnless { !it.contains(key) }
    ?: plus(key to initializer())

fun <T, U> Map<T, U>.update(key: T, transformer: U.() -> U): Map<T, U> = plus(key to getValue(key).transformer())

fun String.isAllUpperCase(): Boolean = this == uppercase()

data class Node(
    val name: String,
    val connections: Set<String>,
    val isLarge: Boolean,
)