open class BasicNode<T>(
    override val value: T
): Node<T> {
    private val _neighbors: MutableList<BasicNode<T>> = mutableListOf()
    override val neighbors: List<BasicNode<T>> = _neighbors

    fun addEdge(edge: BasicEdge<T>) {
        _neighbors.add(if (edge.from == this) edge.to else edge.from)
    }

    fun removeEdge(edge: BasicEdge<T>) {
        _neighbors.remove(if (edge.from == this) edge.to else edge.from)
    }

    override fun toString(): String = "$value"
}

open class BasicEdge<T>(
    override val from: BasicNode<T>,
    override val to: BasicNode<T>,
) : Edge<T, BasicNode<T>>

open class BasicGraph<T> : Graph<T, BasicNode<T>, BasicEdge<T>> {
    private val _nodes: MutableList<BasicNode<T>> = mutableListOf()
    val nodes: List<BasicNode<T>> = _nodes
    private val edges: MutableList<BasicEdge<T>> = mutableListOf()

    override fun addNode(node: BasicNode<T>) {
        _nodes.add(node)
    }

    override fun removeNode(node: BasicNode<T>) {
        _nodes.remove(node)
    }

    override fun addEdge(edge: BasicEdge<T>) {
        edges.add(edge)
        edge.from.addEdge(edge)
        edge.to.addEdge(edge)
    }

    override fun removeEdge(edge: BasicEdge<T>) {
        edges.remove(edge)
        edge.from.removeEdge(edge)
        edge.to.removeEdge(edge)
    }

    override fun complement(): BasicGraph<T> {
        val complementGraph = BasicGraph<T>()
        _nodes.map { BasicNode(it.value) }.forEach(complementGraph._nodes::add)
        _nodes.forEach { node ->
            _nodes.forEach { neighbor ->
                if (neighbor !in node.neighbors)
                    complementGraph.addEdge(BasicEdge(node, neighbor))
            }
        }
        return complementGraph
    }

    override fun distanceBetween(start: Node<T>, end: Node<T>): Number {
        val notVisited: MutableList<Node<T>> = mutableListOf<Node<T>>().apply { addAll(_nodes) }
        val distances: MutableMap<Node<T>, Int> = mutableMapOf<Node<T>, Int>().apply {
            _nodes.forEach { put(it, Int.MAX_VALUE - 1) }
            put(start, 0)
        }

        while (notVisited.isNotEmpty()) {
            val visiting = notVisited.minBy { distances[it] ?: throw IllegalArgumentException("Node not in graph") }

            visiting.neighbors
                .filter { distances[it]!! > distances[visiting]!! + 1 }
                .forEach { distances[it] = distances[visiting]!! + 1 }

            notVisited.remove(visiting)
        }

        return distances[end]!!
    }
}

fun <T, R : Comparable<R>> Iterable<T>.minBy(selector: (T) -> R): T {
    val iterator = iterator()
    var min = iterator.next()
    while (iterator.hasNext()) {
        val e = iterator.next()
        if (selector(min) > selector(e)) min = e
    }
    return min
}