interface Node<T, out N : Node<T, N>> {
    val value: T
    val neighbors: Collection<N>
}

interface Graph<T, N : Node<T, N>> {
    val nodes: Collection<N>
    fun addNodeOf(value: T): N
    fun addNode(node: N)
    fun removeNode(node: N)
    fun addEdge(from: N, to: N)
    fun removeEdge(from: N, to: N)
}