class WeightedGraph<T> : UndirectedBaseGraph<T, WeightedNode<T>>() {
    override fun addNodeOf(value: T): WeightedNode<T> {
        return WeightedNode(value).also(::addNode)
    }

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>) = addEdge(from, to, 1.0)

    fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>, weight: Double) {
        if (!_nodes.contains(from)) addNode(from)
        if (!_nodes.contains(to)) addNode(to)
        from.addEdge(to, weight)
        to.addEdge(from, weight)
    }

    override fun toDot(): String {
        val nodes = nodes.toList()
        fun WeightedNode<T>.label() = "\"$this\""
        val dot = buildString {
            appendLine("graph G {")
            nodes.forEachIndexed { i, node ->
                appendLine("\t${node.label()};")
                node.neighbors.forEach { neighbor ->
                    if (nodes.indexOf(neighbor) > i)
                        appendLine("\t${node.label()} -- ${neighbor.label()} [label=${node.getWeight(neighbor)}];")
                }
            }
            appendLine("}")
        }
        return dot
    }
}