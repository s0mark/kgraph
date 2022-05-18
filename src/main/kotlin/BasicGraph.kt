class UndirectedSimpleGraph<T> : UndirectedGraphBase<T, SimpleNode<T>>(), SimpleGraph<T, SimpleNode<T>> {
    override fun addNodeOf(value: T): SimpleNode<T> {
        return SimpleNode(value).also(::addNode)
    }

    override fun complement(): UndirectedSimpleGraph<T> {
        val complementGraph = UndirectedSimpleGraph<T>()
        val nodesInComplement = nodes.associateWith { SimpleNode(it.value) }
        nodesInComplement.values.forEach(complementGraph::addNode)
        nodes.forEachIndexed { i, node ->
            nodes.forEach { neighbor ->
                if (nodes.indexOf(neighbor) > i && neighbor !in node.neighbors)
                    complementGraph.addEdge(
                        nodesInComplement.getValue(node),
                        nodesInComplement.getValue(neighbor)
                    )
            }
        }
        return complementGraph
    }
}

class UndirectedMultiGraph<T> : UndirectedGraphBase<T, MultiNode<T>>(), MultiGraph<T, MultiNode<T>> {
    override fun addNodeOf(value: T): MultiNode<T> {
        return MultiNode(value).also(::addNode)
    }

    override fun removeAllEdges(from: MultiNode<T>, to: MultiNode<T>) {
        from.removeAllEdges(to)
        to.removeAllEdges(from)
    }
}