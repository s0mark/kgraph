abstract class NodeBase<T, N : Node<T, N>>(
    override val value: T
) : Node<T, N> {
    internal abstract fun addEdge(node: N)
    internal abstract fun removeEdge(node: N)
    override fun toString(): String = "$value"
}

class SimpleNode<T>(value: T) : NodeBase<T, SimpleNode<T>>(value) {
    private val _neighbors: MutableCollection<SimpleNode<T>> = mutableSetOf()
    override val neighbors: Collection<SimpleNode<T>> = _neighbors

    override fun addEdge(node: SimpleNode<T>) {
        _neighbors.add(node)
    }

    override fun removeEdge(node: SimpleNode<T>) {
        _neighbors.remove(node)
    }
}

class WeightedNode<T>(value: T) : NodeBase<T, WeightedNode<T>>(value) {
    private val _neighbors: MutableMap<WeightedNode<T>, Double> = mutableMapOf()
    override val neighbors: Collection<WeightedNode<T>> = _neighbors.keys

    override fun addEdge(node: WeightedNode<T>) = addEdge(node, 1.0)

    internal fun addEdge(node: WeightedNode<T>, weight: Double) {
        _neighbors[node] = weight
    }

    override fun removeEdge(node: WeightedNode<T>) {
        _neighbors.remove(node)
    }

    fun getWeight(to: WeightedNode<T>): Double? = _neighbors[to]
}