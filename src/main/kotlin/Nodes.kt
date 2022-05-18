interface Node<T, out N : Node<T, N>> {
    val value: T
    val neighbors: Collection<N>
}

abstract class NodeBase<T, N : Node<T, N>>(
    override val value: T
) : Node<T, N> {
    internal abstract fun addEdge(to: N)
    internal abstract fun removeEdge(to: N)
    override fun toString(): String = "$value"
}

class SimpleNode<T>(value: T) : NodeBase<T, SimpleNode<T>>(value) {
    private val _neighbors: MutableCollection<SimpleNode<T>> = mutableSetOf()
    override val neighbors: Collection<SimpleNode<T>> = _neighbors

    override fun addEdge(to: SimpleNode<T>) {
        _neighbors.add(to)
    }

    override fun removeEdge(to: SimpleNode<T>) {
        _neighbors.remove(to)
    }
}

class MultiNode<T>(value: T) : NodeBase<T, MultiNode<T>>(value) {
    private val _neighbors: MutableCollection<MultiNode<T>> = mutableListOf()
    override val neighbors: Collection<MultiNode<T>> = _neighbors

    override fun addEdge(to: MultiNode<T>) {
        _neighbors.add(to)
    }

    override fun removeEdge(to: MultiNode<T>) {
        _neighbors.remove(to)
    }

    internal fun removeAllEdges(to: MultiNode<T>) {
        while (to in neighbors) _neighbors.remove(to)
    }
}

class WeightedNode<T>(value: T) : NodeBase<T, WeightedNode<T>>(value) {
    private val _neighbors: MutableMap<WeightedNode<T>, Double> = mutableMapOf()
    override val neighbors: Collection<WeightedNode<T>> = _neighbors.keys

    override fun addEdge(to: WeightedNode<T>) = addEdge(to, 1.0)

    internal fun addEdge(node: WeightedNode<T>, weight: Double) {
        _neighbors[node] = weight
    }

    override fun removeEdge(to: WeightedNode<T>) {
        _neighbors.remove(to)
    }

    fun getWeight(to: WeightedNode<T>): Double? = _neighbors[to]
}