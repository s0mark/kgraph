interface Node<T> {
    val value: T
    val neighbors: List<Node<T>>
}

interface Edge<T, N : Node<T>> {
    val from: N
    val to: N
}

interface Graph<T, N : Node<T>, E : Edge<T, N>> {
    fun addNode(node: N)
    fun removeNode(node: N)
    fun addEdge(edge: E)
    fun removeEdge(edge: E)

    fun complement(): Graph<T, N, E>
    fun distanceBetween(start: Node<T>, end: Node<T>): Number
}