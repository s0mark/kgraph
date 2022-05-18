package utils

import DirectedGraph
import Node
import UndirectedGraph
import WeightedDirectedGraph
import WeightedUndirectedGraph

private fun<T, N : Node<T, N>> Node<T, N>.label() = "\"$this\""

fun<T, N : Node<T, N>> undirectedToDot(
    graph: UndirectedGraph<T, out N>,
    edgeLabel: (N, N) -> String = { _, _ -> "" },
): String {
    val nodes = graph.nodes.toList()
    val dot = buildString {
        appendLine("graph G {")
        nodes.forEachIndexed { i, node ->
            appendLine("\t${node.label()};")
            node.neighbors.forEach { neighbor ->
                if (nodes.indexOf(neighbor) > i)
                    append("\t${node.label()} -- ${neighbor.label()}${edgeLabel(node, neighbor)};")
            }
        }
        appendLine("}")
    }
    return dot
}

fun<T, N : Node<T, N>> UndirectedGraph<T, out N>.toDot() = undirectedToDot(this)

fun<T> WeightedUndirectedGraph<T>.toDot() = undirectedToDot(this) { node, neighbor -> "${node.getWeight(neighbor)}" }

fun<T, N : Node<T, N>> directedToDot(
    graph: DirectedGraph<T, out N>,
    edgeLabel: (N, N) -> String = { _, _ -> "" },
): String {
    val dot = buildString {
        appendLine("digraph G {")
        val nodes = graph.nodes.toList()
        nodes.forEach { node ->
            appendLine("\t${node.label()};")
            node.neighbors.forEach { neighbor ->
                appendLine("\t${node.label()} -> ${neighbor.label()}${edgeLabel(node, neighbor)};")
            }
        }
        appendLine("}")
    }
    return dot
}

fun<T, N : Node<T, N>> DirectedGraph<T, out N>.toDot() = directedToDot(this)

fun<T> WeightedDirectedGraph<T>.toDot() = directedToDot(this) { node, neighbor -> "${node.getWeight(neighbor)}" }