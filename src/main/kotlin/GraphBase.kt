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

    protected fun addIfMissing(vararg nodes: N) {
        nodes.forEach { node ->
            if (!nodes.contains(node)) addNode(node)
        }
    }
}

abstract class UndirectedGraphBase<T, N : NodeBase<T, N>> : GraphBase<T, N>(), UndirectedGraph<T, N> {
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

abstract class DirectedGraphBase<T, N : NodeBase<T, N>> : GraphBase<T, N>(), DirectedGraph<T, N> {
    override fun addEdge(from: N, to: N) {
        addIfMissing(from, to)
        from.addEdge(to)
    }

    override fun removeEdge(from: N, to: N) {
        from.removeEdge(to)
    }

    override val isAcyclic: Boolean by lazy {
        val visitMap = nodes.associateWith { false }.toMutableMap()
        var acyclic = true

        fun N.discover(discovered: MutableList<N>): MutableList<N> {
            visitMap[this] = true
            neighbors.forEach { neighbor ->
                if (!visitMap.getValue(neighbor))
                    neighbor.discover(discovered)
                if (!acyclic) return discovered
            }
            if (this in discovered) acyclic = false
            return discovered.apply { addAll(neighbors) }
        }

        for ((node, visited) in visitMap) {
            if (!visited) node.discover(mutableListOf())
            if (!acyclic) break
        }
        acyclic
    }
}