package utilTests

import DirectedSimpleGraph
import SimpleNode
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import utils.*

class TraverseDirectedTest {
    private lateinit var graph: DirectedSimpleGraph<Int>
    private lateinit var nodes: List<SimpleNode<Int>>

    @Before
    fun setup() {
        graph = DirectedSimpleGraph()
        (0.. 4).forEach { graph.addNodeOf(it) }
        nodes = graph.nodes.toList()
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[0], nodes[3])
        graph.addEdge(nodes[2], nodes[1])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[3], nodes[4])
        graph.addEdge(nodes[4], nodes[2])
    }

    @Test
    fun `default iteration on directed graph`() {
        val expectedOrder = listOf(nodes[0], nodes[1], nodes[2], nodes[3], nodes[4])
        var i = 0
        for (node in graph) {
            assertEquals(expectedOrder[i++], node)
        }
        i = 0
        graph.forEachNode { assertEquals(expectedOrder[i++], it) }
    }

    @Test
    fun `BFS on directed graph`() {
        val expectedOrder = listOf(nodes[0], nodes[1], nodes[2], nodes[3], nodes[4])
        var i = 0
        graph.bfs(nodes[0]) {
            assertEquals(expectedOrder[i++], it)
        }
    }

    @Test
    fun `DFS on directed graph`() {
        val expectedOrder = listOf(nodes[0], nodes[3], nodes[4], nodes[2], nodes[1])
        var i = 0
        graph.dfs(nodes[0]) {
            assertEquals(expectedOrder[i++], it)
        }
    }

    @Test
    fun `traverse directed edges`() {
        val edges = nodes.associateWith { mutableListOf<SimpleNode<Int>>().apply { addAll(it.neighbors) } }
        graph.forEachEdge { node, neighbor ->
            assert(neighbor in edges.getValue(node))
            edges.getValue(node).remove(neighbor)
        }
        edges.values.forEach {
            assert(it.isEmpty())
        }
    }
}