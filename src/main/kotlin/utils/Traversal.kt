package utils

import DirectedGraph
import Graph
import Node
import UndirectedGraph

open class SFSIterator<T, N : Node<T, N>>(graph: Graph<T, out N>, start: N? = null) : Iterator<N> {
    private val visitMap: MutableMap<N, Boolean> = graph.nodes.associateWith { false }.toMutableMap()
    protected val toVisit: MutableList<N> = mutableListOf(start ?: graph.nodes.first())

    private fun N.discover() {
        visitMap[this] = true
        for (neighbor in neighbors) {
            if (!visitMap.getValue(neighbor) && neighbor !in toVisit) toVisit.add(neighbor)
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

class BFSIterator<T, N : Node<T, N>>(graph: Graph<T, out N>, start: N? = null) : SFSIterator<T, N>(graph, start) {
    override fun nextToVisit(): N {
        return toVisit.first()
    }
}

fun<T, N : Node<T, N>> Graph<T, out N>.bfsIterator(start: N? = null): Iterator<N> = BFSIterator(this, start)

fun<T, N : Node<T, N>> Graph<T, out N>.bfs(start: N? = null, action: (N) -> Unit) {
    this.bfsIterator(start).forEach { action(it) }
}

operator fun<T, N : Node<T, N>> Graph<T, out N>.iterator(): Iterator<N> = nodes.iterator()

class DFSIterator<T, N : Node<T, N>>(graph: Graph<T, out N>, start: N? = null) : SFSIterator<T, N>(graph, start) {
    override fun nextToVisit(): N {
        return toVisit.last()
    }
}

fun<T, N : Node<T, N>> Graph<T, out N>.dfsIterator(start: N? = null): Iterator<N> = DFSIterator(this, start)

fun<T, N : Node<T, N>> Graph<T, out N>.dfs(start: N? = null, action: (N) -> Unit) {
    this.dfsIterator(start).forEach { action(it) }
}

fun<T, N : Node<T, N>> Graph<T, out N>.forEachNode(action: (N) -> Unit) {
    nodes.forEach { action(it) }
}

fun<T, N : Node<T, N>> UndirectedGraph<T, out N>.forEachEdge(action: (N, N) -> Unit) {
    nodes.forEachIndexed { i, node ->
        node.neighbors.forEach { neighbor ->
            if (nodes.indexOf(neighbor) > i)
                action(node, neighbor)
        }
    }
}

fun<T, N : Node<T, N>> DirectedGraph<T, out N>.forEachEdge(action: (N, N) -> Unit) {
    nodes.forEach { node ->
        node.neighbors.forEach { neighbor ->
            action(node, neighbor)
        }
    }
}