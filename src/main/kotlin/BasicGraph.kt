open class BasicNode<T>(
    override val value: T
): Node<T> {
    protected val _neighbors: MutableCollection<BasicNode<T>> = mutableListOf()
    override val neighbors: Collection<BasicNode<T>> = _neighbors

    internal open fun addEdge(node: BasicNode<T>) {
        _neighbors.add(node)
    }

    internal open fun removeEdge(node: BasicNode<T>) {
        _neighbors.remove(node)
    }

    override fun toString(): String = "$value"
}

open class BasicGraph<T> : Graph<T, BasicNode<T>> {
    protected val _nodes: MutableCollection<BasicNode<T>> = mutableSetOf()
    override val nodes: Collection<BasicNode<T>> = _nodes

    override fun addNode(node: BasicNode<T>) {
        _nodes.add(node)
    }

    override fun removeNode(node: BasicNode<T>) {
        _nodes.remove(node)
    }

    override fun addEdge(from: BasicNode<T>, to: BasicNode<T>) {
        if (!_nodes.contains(from)) _nodes.add(from)
        if (!_nodes.contains(to)) _nodes.add(to)
        from.addEdge(to)
        to.addEdge(from)
    }

    override fun removeEdge(from: BasicNode<T>, to: BasicNode<T>) {
        from.removeEdge(to)
        to.removeEdge(from)
    }

    override fun complement(): BasicGraph<T> {
        val complementGraph = BasicGraph<T>()
        nodes.map { BasicNode(it.value) }.forEach(complementGraph::addNode)
        nodes.forEach { node ->
            nodes.forEach { neighbor ->
                if (neighbor !in node.neighbors)
                    complementGraph.addEdge(node, neighbor)
            }
        }
        return complementGraph
    }

    override fun distanceBetween(start: Node<T>, end: Node<T>): Number {
        val notVisited: MutableList<Node<T>> = mutableListOf<Node<T>>().apply { addAll(nodes) }
        val distances: MutableMap<Node<T>, Int> = mutableMapOf<Node<T>, Int>().apply {
            nodes.forEach { put(it, Int.MAX_VALUE - 1) }
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