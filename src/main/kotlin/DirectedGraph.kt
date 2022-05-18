class DirectedGraph<T> : DirectedBaseGraph<T, SimpleNode<T>>() {
    override fun addNodeOf(value: T): SimpleNode<T> {
        return SimpleNode(value).also(::addNode)
    }
}