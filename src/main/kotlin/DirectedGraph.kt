class DirectedSimpleGraph<T>
    : DirectedGraphBase<T, SimpleNode<T>>, UnweightedSimpleGraph<T, SimpleNode<T>> {
    constructor() :super()
    constructor(vararg edges: Pair<T, T>) :super(*edges)

    override fun addNodeOf(value: T): SimpleNode<T> {
        return SimpleNode(value).also(::addNode)
    }

    override fun complement(): UnweightedSimpleGraph<T, SimpleNode<T>> {
        val complementGraph = DirectedSimpleGraph<T>()
        val nodesInComplement = nodes.associateWith { complementGraph.addNodeOf(it.value) }
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

class DirectedMultiGraph<T>
    : DirectedGraphBase<T, MultiNode<T>>, UnweightedGraph<T, MultiNode<T>>, MultiGraph<T, MultiNode<T>> {
    constructor() :super()
    constructor(vararg edges: Pair<T, T>) :super(*edges)

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
    }
}