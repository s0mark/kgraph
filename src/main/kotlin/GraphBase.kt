abstract class GraphBase<T, N : NodeBase<T, N>> : Graph<T, N> {
    protected val _nodes: MutableCollection<N> = mutableSetOf()
    override val nodes: Collection<N> = _nodes

    override fun addNode(node: N) {
        _nodes.add(node)
    }

    override fun removeNode(node: N) {
        nodes.forEach {
            it.removeEdge(node)
        }
        _nodes.remove(node)
    }
}

abstract class UndirectedGraphBase<T, N : NodeBase<T, N>> : GraphBase<T, N>() {
    override fun addEdge(from: N, to: N) {
        if (!_nodes.contains(from)) addNode(from)
        if (!_nodes.contains(to)) addNode(to)
        from.addEdge(to)
        to.addEdge(from)
    }

    override fun removeEdge(from: N, to: N) {
        from.removeEdge(to)
        to.removeEdge(from)
    }
}

abstract class DirectedGraphBase<T, N : NodeBase<T, N>> : GraphBase<T, N>(), DirectedGraph<T, N> {
    override fun addEdge(from: N, to: N) {
        if (!_nodes.contains(from)) addNode(from)
        if (!_nodes.contains(to)) addNode(to)
        from.addEdge(to)
    }

    override fun removeEdge(from: N, to: N) {
        from.removeEdge(to)
    }
}