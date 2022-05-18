abstract class NodeBase<T, N : Node<T, N>>(
    override val value: T
) : Node<T, N> {
    override fun toString(): String = "$value"
}

abstract class GraphBase<T, N : NodeBase<T, N>> : Graph<T, N> {
    protected val _nodes: MutableCollection<N> = mutableSetOf()
    override val nodes: Collection<N> = _nodes

    override fun distanceBetween(start: N, end: N): Number {
        val notVisited: MutableList<N> = mutableListOf<N>().apply { addAll(nodes) }
        val distances: MutableMap<N, Int> = mutableMapOf<N, Int>().apply {
            nodes.forEach { put(it, Int.MAX_VALUE - 1) }
            put(start, 0)
        }

        while (notVisited.isNotEmpty()) {
            val visiting = notVisited.minBy { distances.getValue(it) }

            visiting.neighbors
                .filter { distances.getValue(it) > distances.getValue(visiting) + 1 }
                .forEach { distances[it] = distances.getValue(visiting) + 1 }

            notVisited.remove(visiting)
        }

        return distances.getValue(end)
    }

    fun toDot(): String {
        val nodes = nodes.toList()
        fun N.label() = "\"$this\""
        val dot = buildString {
            appendLine("graph G {")
            nodes.forEachIndexed { i, node ->
                appendLine("\t${node.label()};")
                node.neighbors.forEach { neighbor ->
                    if (nodes.indexOf(neighbor) > i) appendLine("\t${node.label()} -- ${neighbor.label()};")
                }
            }
            appendLine("}")
        }
        return dot
    }
}