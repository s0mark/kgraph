interface Graph<T, N : Node<T, N>> {
    val nodes: Collection<N>
    fun addNodeOf(value: T): N
    fun addNode(node: N)
    fun removeNode(node: N)
    fun addEdge(from: N, to: N)
    fun removeEdge(from: N, to: N)
}

interface SimpleGraph<T, N : Node<T, N>> : Graph<T, N>

interface MultiGraph<T, N : Node<T, N>> : Graph<T, N> {
    fun removeAllEdges(from: N, to: N)
}

interface DirectedGraph<T, N : Node<T, N>> : Graph<T, N>

interface UndirectedGraph<T, N : Node<T, N>> : Graph<T, N>

interface WeightedGraph<T, N : Node<T, N>> : Graph<T, N> {
    fun addEdge(from: N, to: N, weight: Double)
    override fun addEdge(from: N, to: N) = addEdge(from, to, 1.0)
}

interface UnweightedGraph<T, N : Node<T, N>> : Graph<T, N>

interface UnweightedSimpleGraph<T, N : Node<T, N>> : SimpleGraph<T, N>, UnweightedGraph<T, N> {
    fun complement(): UnweightedSimpleGraph<T, N>
}