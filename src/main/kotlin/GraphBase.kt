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

    open fun toDot(): String {
        val nodes = nodes.toList()
        fun N.label() = "\"$this\""
        val dot = buildString {
            appendLine("graph G {")
            nodes.forEachIndexed { i, node ->
                appendLine("\t${node.label()};")
                node.neighbors.forEach { neighbor ->
                    if (nodes.indexOf(neighbor) > i)
                        appendLine("\t${node.label()} -- ${neighbor.label()};")
                }
            }
            appendLine("}")
        }
        return dot
    }
}

abstract class UndirectedBaseGraph<T, N : NodeBase<T, N>> : GraphBase<T, N>() {
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