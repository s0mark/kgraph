class DirectedSimpleGraph<T> : DirectedGraphBase<T, SimpleNode<T>>(), SimpleGraph<T, SimpleNode<T>> {
    override fun addNodeOf(value: T): SimpleNode<T> {
        return SimpleNode(value).also(::addNode)
    }

    override fun complement(): SimpleGraph<T, SimpleNode<T>> {
        val complementGraph = DirectedSimpleGraph<T>()
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

class DirectedMultiGraph<T> : DirectedGraphBase<T, MultiNode<T>>(), MultiGraph<T, MultiNode<T>> {
    override fun addNodeOf(value: T): MultiNode<T> {
        return MultiNode(value).also(::addNode)
    }

    override fun removeAllEdges(from: MultiNode<T>, to: MultiNode<T>) {
        from.removeAllEdges(to)
    }
}