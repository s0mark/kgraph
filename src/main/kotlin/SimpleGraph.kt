class SimpleNode<T>(value: T) : NodeBase<T, SimpleNode<T>>(value) {
    private val _neighbors: MutableCollection<SimpleNode<T>> = mutableSetOf()
    override val neighbors: Collection<SimpleNode<T>> = _neighbors

    override fun addEdge(node: SimpleNode<T>) {
        _neighbors.add(node)
    }

    override fun removeEdge(node: SimpleNode<T>) {
        _neighbors.remove(node)
    }
}

class SimpleGraph<T> : GraphBase<T, SimpleNode<T>>() {
    override fun addNodeOf(value: T): SimpleNode<T> {
        return SimpleNode(value).also(::addNode)
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