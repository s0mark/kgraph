package utils

import Graph
import Node

fun<T, N : Node<T, N>> Graph<T, N>.distanceBetween(start: N, end: N): Double {
    val notVisited: MutableList<N> = mutableListOf<N>().apply { addAll(nodes) }
    val distances: MutableMap<N, Double> = mutableMapOf<N, Double>().apply {
        nodes.forEach { put(it, Double.POSITIVE_INFINITY) }
        put(start, 0.0)
    }

    while (notVisited.isNotEmpty()) {
        val visiting = notVisited.minBy { distances.getValue(it) }

        visiting.neighbors
            .filter { distances.getValue(it) > distances.getValue(visiting) + 1 }
            .forEach { distances[it] = distances.getValue(visiting) + 1 }

        notVisited.remove(visiting)
    }

    return distances.getValue(end)
}

private fun <T, R : Comparable<R>> Iterable<T>.minBy(selector: (T) -> R): T {
    val iterator = iterator()
    var min = iterator.next()
    while (iterator.hasNext()) {
        val e = iterator.next()
        if (selector(min) > selector(e)) min = e
    }
    return min
}