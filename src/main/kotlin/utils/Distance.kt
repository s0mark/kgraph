package utils

import Graph
import Node
import WeightedUndirectedGraph
import WeightedNode

private fun<T, N : Node<T, N>> Graph<T, N>.distanceBetween(
    start: N,
    end: N,
    distance: (N, N) -> Double
): Double {
    val notVisited: MutableList<N> = mutableListOf<N>().apply { addAll(nodes) }
    val distances: MutableMap<N, Double> = mutableMapOf<N, Double>().apply {
        forEachNode { put(it, Double.POSITIVE_INFINITY) }
        put(start, 0.0)
    }

    while (notVisited.isNotEmpty()) {
        val visiting = notVisited.minBy { distances.getValue(it) }

        visiting.neighbors
            .filter { distances.getValue(it) > (distances.getValue(visiting) + distance(visiting, it)) }
            .forEach { distances[it] = distances.getValue(visiting) + distance(visiting, it) }

        notVisited.remove(visiting)
    }

    return distances.getValue(end)
}

fun<T, N : Node<T, N>> Graph<T, N>.distanceBetween(start: N, end: N): Double = distanceBetween(start, end) { _, _ -> 1.0 }

fun<T> WeightedUndirectedGraph<T>.distanceBetween(start: WeightedNode<T>, end: WeightedNode<T>): Double = distanceBetween(start, end) { from, to -> from.getWeight(to)!! }

private fun <T, R : Comparable<R>> Iterable<T>.minBy(selector: (T) -> R): T {
    val iterator = iterator()
    var min = iterator.next()
    while (iterator.hasNext()) {
        val e = iterator.next()
        if (selector(min) > selector(e)) min = e
    }
    return min
}