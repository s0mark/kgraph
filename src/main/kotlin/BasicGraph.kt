open class BasicNode<T>(
    override val value: T
): Node<T> {
    private val _neighbors: MutableList<BasicNode<T>> = mutableListOf()
    override val neighbors: List<BasicNode<T>> = _neighbors

    fun addEdge(node: BasicNode<T>) {
        _neighbors.add(node)
    }

    fun removeEdge(node: BasicNode<T>) {
        _neighbors.remove(node)
    }

    override fun toString(): String = "$value"
}

open class BasicGraph<T> : Graph<T, BasicNode<T>> {
    private val _nodes: MutableList<BasicNode<T>> = mutableListOf()
    val nodes: List<BasicNode<T>> = _nodes

    override fun addNode(node: BasicNode<T>) {
        _nodes.add(node)
    }

    override fun removeNode(node: BasicNode<T>) {
        _nodes.remove(node)
    }

    override fun addEdge(from: BasicNode<T>, to: BasicNode<T>) {
        from.addEdge(to)
        to.addEdge(from)
    }

    override fun removeEdge(from: BasicNode<T>, to: BasicNode<T>) {
        from.removeEdge(to)
        to.removeEdge(from)
    }

    override fun complement(): BasicGraph<T> {
        val complementGraph = BasicGraph<T>()
        _nodes.map { BasicNode(it.value) }.forEach(complementGraph._nodes::add)
        _nodes.forEach { node ->
            _nodes.forEach { neighbor ->
                if (neighbor !in node.neighbors)
                    complementGraph.addEdge(node, neighbor)
            }
        }
        return complementGraph
    }

    override fun distanceBetween(start: Node<T>, end: Node<T>): Number {
        val notVisited: MutableList<Node<T>> = mutableListOf<Node<T>>().apply { addAll(_nodes) }
        val distances: MutableMap<Node<T>, Int> = mutableMapOf<Node<T>, Int>().apply {
            _nodes.forEach { put(it, Int.MAX_VALUE - 1) }
            put(start, 0)
        }

        while (notVisited.isNotEmpty()) {
            val visiting = notVisited.minBy { distances.getValue(it) }

            visiting.neighbors
                .filter { distances.getValue(it) > distances.getValue(visiting) + 1 }
                .forEach { distances[it] = distances.getValue(visiting) + 1 }

            notVisited.remove(visiting)
        }

        return distances.getValue(end)
    }
}

fun <T, R : Comparable<R>> Iterable<T>.minBy(selector: (T) -> R): T {
    val iterator = iterator()
    var min = iterator.next()
    while (iterator.hasNext()) {
        val e = iterator.next()
        if (selector(min) > selector(e)) min = e
    }
    return min
}