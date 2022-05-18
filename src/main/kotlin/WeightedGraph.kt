class WeightedUndirectedGraph<T> : UndirectedGraphBase<T, WeightedNode<T>>(), WeightedGraph<T, WeightedNode<T>> {
    override fun addNodeOf(value: T): WeightedNode<T> {
        return WeightedNode(value).also(::addNode)
    }

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>) = super<WeightedGraph>.addEdge(from, to)

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>, weight: Double) {
        if (!_nodes.contains(from)) addNode(from)
        if (!_nodes.contains(to)) addNode(to)
        from.addEdge(to, weight)
        to.addEdge(from, weight)
    }
}

class WeightedDirectedGraph<T> : DirectedGraphBase<T, WeightedNode<T>>(), WeightedGraph<T, WeightedNode<T>> {
    override fun addNodeOf(value: T): WeightedNode<T> {
        return WeightedNode(value).also(::addNode)
    }

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>) = super<WeightedGraph>.addEdge(from, to)

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>, weight: Double) {
        if (_nodes.contains(from)) addNode(from)
        if (_nodes.contains(to)) addNode(to)
        from.addEdge(to, weight)
    }
}