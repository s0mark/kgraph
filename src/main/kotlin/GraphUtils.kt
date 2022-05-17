enum class SearchNodeState {
    UNSEEN,
    DISCOVERED,
    VISITED,
}

open class SFSIterator<T, N : Node<T>>(private val graph: Graph<T, out N>, start: N? = null) : Iterator<N> {
    protected val visitMap: MutableMap<N, SearchNodeState> = mutableMapOf<N, SearchNodeState>().apply {
        graph.nodes.forEach { put(it, SearchNodeState.UNSEEN) }
        put(start ?: graph.nodes.first(), SearchNodeState.DISCOVERED)
    }

    open fun nextToVisit(): N {
        return visitMap.filter { (_, state) -> state == SearchNodeState.DISCOVERED }.keys.random()
    }

    override fun hasNext(): Boolean = visitMap.any { (_, state) -> state != SearchNodeState.VISITED }

    override fun next(): N {
        val visiting = nextToVisit()
        visiting.neighbors.forEach { neighbor ->
            if (visitMap.getValue(neighbor as N) == SearchNodeState.UNSEEN) visitMap[neighbor] = SearchNodeState.DISCOVERED
        }
        visitMap[visiting] = SearchNodeState.VISITED
        return visiting
    }
}