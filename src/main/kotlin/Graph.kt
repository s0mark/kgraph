interface Node<T> {
    val value: T
    val neighbors: Collection<Node<T>>
}

interface Graph<T, N : Node<T>> {
    val nodes: Collection<N>
    fun addNode(node: N)
    fun removeNode(node: N)
    fun addEdge(from: N, to: N)
    fun removeEdge(from: N, to: N)

    fun complement(): Graph<T, N>
    fun distanceBetween(start: Node<T>, end: Node<T>): Number
}