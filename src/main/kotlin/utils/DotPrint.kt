package utils

import DirectedGraph
import Graph
import Node
import UndirectedGraph
import WeightedDirectedGraph
import WeightedUndirectedGraph

private fun<T, N : Node<T, N>> Node<T, N>.label() = "\"$value\""

private fun<T, N : Node<T, N>> graphToDot(
    graph: Graph<T, out N>,
    type: String,
    edgeLabels: StringBuilder.() -> Unit,
    name: String,
): String {
    return buildString {
        appendLine("$type ${if (name.isEmpty()) "" else "$name "}{")
        graph.forEachNode { appendLine("\t${it.label()};") }
        edgeLabels()
        appendLine("}")
    }
}

fun<T, N : Node<T, N>> undirectedToDot(
    graph: UndirectedGraph<T, out N>,
    name: String,
    edgeLabel: (N, N) -> String = { _, _ -> "" },
): String {
    return graphToDot(
        graph,
        type = "graph",
        edgeLabels = {
            graph.forEachEdge { node, neighbor ->
                appendLine("\t${node.label()} -- ${neighbor.label()}${edgeLabel(node, neighbor)};")
            }
        },
        name = name
    )
}

fun<T, N : Node<T, N>> UndirectedGraph<T, out N>.toDot(name: String = "") = undirectedToDot(this, name)

fun<T> WeightedUndirectedGraph<T>.toDot(name: String = "")
    = undirectedToDot(this, name) { node, neighbor -> "[label=${node.getWeight(neighbor)}]" }

fun<T, N : Node<T, N>> directedToDot(
    graph: DirectedGraph<T, out N>,
    name: String,
    edgeLabel: (N, N) -> String = { _, _ -> "" },
): String {
    return graphToDot(
        graph,
        type = "digraph",
        edgeLabels = {
            graph.forEachEdge { node, neighbor ->
                appendLine("\t${node.label()} -> ${neighbor.label()}${edgeLabel(node, neighbor)};")
            }
        },
        name = name
    )
}

fun<T, N : Node<T, N>> DirectedGraph<T, out N>.toDot(name: String = "") = directedToDot(this, name)

fun<T> WeightedDirectedGraph<T>.toDot(name: String = "")
    = directedToDot(this, name) { node, neighbor -> "[label=${node.getWeight(neighbor)}]" }