class SimpleGraph<T> : UndirectedGraphBase<T, SimpleNode<T>>() {
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

class MultiGraph<T> : UndirectedGraphBase<T, MultiNode<T>>() {
    override fun addNodeOf(value: T): MultiNode<T> {
        return MultiNode(value).also(::addNode)
    }

    fun removeAllEdges(from: MultiNode<T>, to: MultiNode<T>) {
        from.removeAllEdges(to)
        to.removeAllEdges(from)
    }
}