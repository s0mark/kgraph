class UndirectedSimpleGraph<T>
    : UndirectedGraphBase<T, SimpleNode<T>>(), UnweightedSimpleGraph<T, SimpleNode<T>> {
    override fun addNodeOf(value: T): SimpleNode<T> {
        return SimpleNode(value).also(::addNode)
    }

    override fun complement(): UndirectedSimpleGraph<T> {
        val complementGraph = UndirectedSimpleGraph<T>()
        val nodesInComplement = nodes.associateWith { complementGraph.addNodeOf(it.value) }
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

class UndirectedMultiGraph<T>
    : UndirectedGraphBase<T, MultiNode<T>>(), UnweightedGraph<T, MultiNode<T>>, MultiGraph<T, MultiNode<T>> {
    override fun addNodeOf(value: T): MultiNode<T> {
        return MultiNode(value).also(::addNode)
    }

    override fun removeNode(node: MultiNode<T>) {
        nodes.forEach {
            it.removeAllEdges(node)
        }
        _nodes.remove(node)
    }

    override fun removeAllEdges(from: MultiNode<T>, to: MultiNode<T>) {
        from.removeAllEdges(to)
        to.removeAllEdges(from)
    }
}