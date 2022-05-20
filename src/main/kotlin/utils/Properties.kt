package utils

import DirectedGraph
import Node
import UndirectedGraph
import WeightedDirectedGraph
import WeightedNode

val<T, N : Node<T, N>> DirectedGraph<T, N>.isAcyclic: Boolean
    get() {
        val visitMap = nodes.associateWith { false }.toMutableMap()
        var acyclic = true

        fun N.discover(discovered: MutableList<N>): MutableList<N> {
            visitMap[this] = true
            neighbors.forEach { neighbor ->
                if (neighbor === this) acyclic = false
                else if (!visitMap.getValue(neighbor))
                    neighbor.discover(discovered)
                if (!acyclic) return discovered
            }
            if (this in discovered) acyclic = false
            return discovered.apply { addAll(neighbors) }
        }

        for ((node, visited) in visitMap) {
            if (!visited) node.discover(mutableListOf())
            if (!acyclic) break
        }
        return acyclic
    }

private class CorrectorGraph<T, N : Node<T, N>>(graph: DirectedGraph<T, N>, from: N, to: N, val edgeCapacity: (N, N) -> Double) {
    private val corrector = WeightedDirectedGraph<T>()
    val nodeMap = graph.nodes.associateWith { corrector.addNodeOf(it.value) }
    fun N.c() = nodeMap.getValue(this)
    val source = from.c()
    val target = to.c()
    init {
        graph.forEachEdge { node, neighbor ->
            corrector.addEdge(neighbor.c(), node.c(), 0.0)
            corrector.addEdge(node.c(), neighbor.c(), edgeCapacity(node, neighbor))
        }
    }

    var flow: Double = 0.0
        private set

    fun ArrayDeque<WeightedNode<T>>.forEdges(action: (WeightedNode<T>, WeightedNode<T>, Double) -> Unit) {
        this.reduce { node, neighbor ->
            action(node, neighbor, node.getWeight(neighbor)!!)
            neighbor
        }
    }

    fun findCorrectingPath(): ArrayDeque<WeightedNode<T>> {
        val visitMap = corrector.nodes.associateWith { false }.toMutableMap()
        val path: ArrayDeque<WeightedNode<T>> = ArrayDeque()
        fun WeightedNode<T>.discover(): Boolean {
            visitMap[this] = true
            path.add(this)
            if (this === target) return true
            neighbors.forEach { neighbor ->
                if (!visitMap.getValue(neighbor) && getWeight(neighbor)!! > 0) {
                    val routeFound = neighbor.discover()
                    if (routeFound) return true
                }
            }
            path.removeLast()
            return false
        }
        source.discover()
        return path
    }

    fun improveWithPath(correctingPath: ArrayDeque<WeightedNode<T>>) {
        var correction = Double.POSITIVE_INFINITY
        correctingPath.forEdges { _, _, remainingCapacity ->
            if (remainingCapacity < correction) correction = remainingCapacity
        }
        correctingPath.forEdges { node, neighbor, remainingCapacity ->
            corrector.apply {
                val currentFlow = neighbor.getWeight(node)!!
                removeEdge(node, neighbor).also { addEdge(node, neighbor, remainingCapacity - correction) }
                removeEdge(neighbor, node).also { addEdge(neighbor, node, currentFlow + correction) }
            }
        }
        flow += correction
    }
}

fun<T, N : Node<T, N>> maxFlow(
    graph: DirectedGraph<T, N>,
    from: N,
    to: N,
    edgeCapacity: (N, N) -> Double = { _, _ -> 1.0 },
): Double {
    val corrector = CorrectorGraph(graph, from, to, edgeCapacity)
    var correctingPath = corrector.findCorrectingPath()
    while (correctingPath.size > 1) {
        corrector.improveWithPath(correctingPath)
        correctingPath = corrector.findCorrectingPath()
    }
    return corrector.flow
}

fun<T, N : Node<T, N>> maxFlow(
    graph: DirectedGraph<T, N>,
    edgeCapacity: (N, N) -> Double = { _, _ -> 1.0 },
): Double {
    return graph.nodes.maxOfOrNull { node ->
        graph.nodes.maxOfOrNull { neighbor ->
            if (node !== neighbor) maxFlow(graph, node, neighbor, edgeCapacity) else 0.0
        } ?: 0.0
    } ?: 0.0
}

fun<T, N : Node<T, N>> DirectedGraph<T, N>.maxFlow(from: N, to: N): Double = maxFlow(this, from, to)

fun<T> WeightedDirectedGraph<T>.maxFlow(from: WeightedNode<T>, to: WeightedNode<T>): Double
    = maxFlow(this, from, to) { node, neighbor -> node.getWeight(neighbor)!! }

val<T, N : Node<T, N>> DirectedGraph<T, N>.maxFlow: Double
    get() = maxFlow(this)

val<T> WeightedDirectedGraph<T>.maxFlow: Double
    get() = maxFlow(this) { node, neighbor -> node.getWeight(neighbor)!! }

val<T, N : Node<T, N>> UndirectedGraph<T, N>.isConnected: Boolean
    get() {
        if (nodes.isEmpty()) return true
        val visitMap = nodes.associateWith { false }.toMutableMap()

        fun N.discover() {
            visitMap[this] = true
            neighbors.forEach { neighbor ->
                if (!visitMap.getValue(neighbor))
                    neighbor.discover()
            }
        }

        nodes.first().discover()
        return visitMap.values.all { it }
    }