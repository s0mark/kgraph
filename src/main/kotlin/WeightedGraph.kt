class WeightedUndirectedGraph<T>
    : UndirectedGraphBase<T, WeightedNode<T>>, WeightedGraph<T, WeightedNode<T>>, SimpleGraph<T, WeightedNode<T>> {
    constructor() :super()
    constructor(vararg edges: Pair<T, T>) :super(*edges)

    override fun addNodeOf(value: T): WeightedNode<T> {
        return WeightedNode(value).also(::addNode)
    }

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>) = super<WeightedGraph>.addEdge(from, to)

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>, weight: Double) {
        addIfMissing(from, to)
        from.addEdge(to, weight)
        to.addEdge(from, weight)
    }
}

class WeightedDirectedGraph<T>
    : DirectedGraphBase<T, WeightedNode<T>>, WeightedGraph<T, WeightedNode<T>>, SimpleGraph<T, WeightedNode<T>> {
    constructor() :super()
    constructor(vararg edges: Pair<T, T>) :super(*edges)

    override fun addNodeOf(value: T): WeightedNode<T> {
        return WeightedNode(value).also(::addNode)
    }

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>) = super<WeightedGraph>.addEdge(from, to)

    override fun addEdge(from: WeightedNode<T>, to: WeightedNode<T>, weight: Double) {
        addIfMissing(from, to)
        from.addEdge(to, weight)
    }
}