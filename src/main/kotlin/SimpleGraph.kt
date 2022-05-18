class SimpleNode<T>(value: T) : NodeBase<T, SimpleNode<T>>(value) {
    private val _neighbors: MutableCollection<SimpleNode<T>> = mutableListOf()
    override val neighbors: Collection<SimpleNode<T>> = _neighbors

    internal fun addEdge(node: SimpleNode<T>) {
        _neighbors.add(node)
    }

    internal fun removeEdge(node: SimpleNode<T>) {
        _neighbors.remove(node)
    }
}

class SimpleGraph<T> : GraphBase<T, SimpleNode<T>>() {
    override fun addNodeOf(value: T): SimpleNode<T> {
        return SimpleNode(value).also(::addNode)
    }

    override fun addNode(node: SimpleNode<T>) {
        _nodes.add(node)
    }

    override fun removeNode(node: SimpleNode<T>) {
        _nodes.remove(node)
    }

    override fun addEdge(from: SimpleNode<T>, to: SimpleNode<T>) {
        if (!_nodes.contains(from)) _nodes.add(from)
        if (!_nodes.contains(to)) _nodes.add(to)
        from.addEdge(to)
        to.addEdge(from)
    }

    override fun removeEdge(from: SimpleNode<T>, to: SimpleNode<T>) {
        from.removeEdge(to)
        to.removeEdge(from)
    }

    fun complement(): SimpleGraph<T> {
        val complementGraph = SimpleGraph<T>()
        val nodesInComplement = nodes.associateWith { SimpleNode(it.value) }
        nodesInComplement.values.forEach(complementGraph::addNode)
        nodes.forEach { node ->
            nodes.forEach { neighbor ->
                if (neighbor !in node.neighbors)
                    complementGraph.addEdge(
                        nodesInComplement.getValue(node),
                        nodesInComplement.getValue(neighbor)
                    )
            }
        }
        return complementGraph
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