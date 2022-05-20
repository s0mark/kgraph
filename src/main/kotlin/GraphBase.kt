abstract class GraphBase<T, N : NodeBase<T, N>> : Graph<T, N> {
    protected val _nodes: MutableCollection<N> = mutableSetOf()
    override val nodes: Collection<N> = _nodes

    constructor()
    constructor(vararg edges: Pair<T, T>) :this() {
        val valueMap: MutableMap<T, N> = mutableMapOf()
        for (edge in edges) {
            if (nodes.none { it.value == edge.first })
                valueMap[edge.first] = addNodeOf(edge.first)
            if (nodes.none { it.value == edge.second })
                valueMap[edge.second] = addNodeOf(edge.second)
            addEdge(valueMap.getValue(edge.first), valueMap.getValue(edge.second))
        }
    }

    override fun addNode(node: N) {
        _nodes.add(node)
    }

    override fun removeNode(node: N) {
        nodes.forEach {
            it.removeEdge(node)
        }
        _nodes.remove(node)
    }

    protected fun addIfMissing(vararg nodes: N) {
        nodes.forEach { node ->
            if (!nodes.contains(node)) addNode(node)
        }
    }
}

abstract class UndirectedGraphBase<T, N : NodeBase<T, N>> : GraphBase<T, N>, UndirectedGraph<T, N> {
    constructor() :super()
    constructor(vararg edges: Pair<T, T>) :super(*edges)

    override fun addEdge(from: N, to: N) {
        addIfMissing(from, to)
        from.addEdge(to)
        to.addEdge(from)
    }

    override fun removeEdge(from: N, to: N) {
        from.removeEdge(to)
        to.removeEdge(from)
    }
}

abstract class DirectedGraphBase<T, N : NodeBase<T, N>> : GraphBase<T, N>, DirectedGraph<T, N> {
    constructor() :super()
    constructor(vararg edges: Pair<T, T>) :super(*edges)

    override fun addEdge(from: N, to: N) {
        addIfMissing(from, to)
        from.addEdge(to)
    }

    override fun removeEdge(from: N, to: N) {
        from.removeEdge(to)
    }
}