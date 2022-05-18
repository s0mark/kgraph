package utils

import DirectedGraphBase
import NodeBase
import UndirectedGraphBase
import WeightedDirectedGraph
import WeightedUndirectedGraph

private fun<T, N : NodeBase<T, N>> NodeBase<T, N>.label() = "\"$this\""

fun<T, N : NodeBase<T, N>> undirectedToDot(
    graph: UndirectedGraphBase<T, out N>,
    edgeLabel: (N, N) -> String = { _, _ -> "" },
): String {
    val nodes = graph.nodes.toList()
    val dot = buildString {
        appendLine("graph G {")
        nodes.forEachIndexed { i, node ->
            appendLine("\t${node.label()};")
            node.neighbors.forEach { neighbor ->
                if (nodes.indexOf(neighbor) > i)
                    append("\t${node.label()} -- ${neighbor.label()} ${edgeLabel(node, neighbor)};")
            }
        }
        appendLine("}")
    }
    return dot
}

fun<T, N : NodeBase<T, N>> UndirectedGraphBase<T, out N>.toDot() = undirectedToDot(this)

fun<T> WeightedUndirectedGraph<T>.toDot() = undirectedToDot(this) { node, neighbor -> "${node.getWeight(neighbor)}" }

fun<T, N : NodeBase<T, N>> directedToDot(
    graph: DirectedGraphBase<T, out N>,
    edgeLabel: (N, N) -> String = { _, _ -> "" },
): String {
    val dot = buildString {
        appendLine("digraph G {")
        val nodes = graph.nodes.toList()
        nodes.forEach { node ->
            appendLine("\t${node.label()};")
            node.neighbors.forEach { neighbor ->
                appendLine("\t${node.label()} -> ${neighbor.label()} ${edgeLabel(node, neighbor)};")
            }
        }
        appendLine("}")
    }
    return dot
}

fun<T, N : NodeBase<T, N>> DirectedGraphBase<T, out N>.toDot() = directedToDot(this)

fun<T> WeightedDirectedGraph<T>.toDot() = directedToDot(this) { node, neighbor -> "${node.getWeight(neighbor)}" }