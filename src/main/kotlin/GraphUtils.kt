open class SFSIterator<T, N : Node<T>>(graph: Graph<T, out N>, start: N? = null) : Iterator<N> {
    private val visitMap: MutableMap<N, Boolean> = graph.nodes.associateWith { false }.toMutableMap()
    protected val toVisit: MutableList<N> = mutableListOf(start ?: graph.nodes.first())

    private fun N.discover() {
        visitMap[this] = true
        for (neighbor in neighbors) {
            if (!visitMap.getValue(neighbor as N) && neighbor !in toVisit) toVisit.add(neighbor)
        }
        toVisit.remove(this)
    }

    open fun nextToVisit(): N = toVisit.random()

    override fun hasNext(): Boolean = !visitMap.all { (_, visited) -> visited }

    override fun next(): N {
        val visiting = if (toVisit.isNotEmpty())
            nextToVisit()
        else
            visitMap.filter { (_, visited) -> !visited }.keys.random()
        visiting.discover()
        return visiting
    }
}

class BFSIterator<T, N : Node<T>>(graph: Graph<T, out N>, start: N?) : SFSIterator<T, N>(graph, start) {
    override fun nextToVisit(): N {
        return toVisit.first()
    }
}

fun<T, N : Node<T>> Graph<T, out N>.bfs(start: N? = null): Iterator<N> = BFSIterator(this, start)

class DFSIterator<T, N : Node<T>>(graph: Graph<T, out N>, start: N?) : SFSIterator<T, N>(graph, start) {
    override fun nextToVisit(): N {
        return toVisit.last()
    }
}

fun<T, N : Node<T>> Graph<T, out N>.dfs(start: N? = null): Iterator<N> = DFSIterator(this, start)