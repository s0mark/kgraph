package utilTests

import SimpleNode
import UndirectedSimpleGraph
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import utils.*

class TraverseUndirectedTest {
    private lateinit var graph: UndirectedSimpleGraph<Int>
    private lateinit var nodes: List<SimpleNode<Int>>

    @Before
    fun setup() {
        graph = UndirectedSimpleGraph()
        (0.. 4).forEach { graph.addNodeOf(it) }
        nodes = graph.nodes.toList()
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[0], nodes[3])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[3], nodes[4])
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
    fun `BFS on undirected graph`() {
        val expectedOrder = mutableListOf(nodes[0], nodes[1], nodes[2], nodes[3], nodes[4])
        var i = 0
        graph.bfs(nodes[0]) {
            assertEquals(expectedOrder[i++], it)
        }
        i = 0
        for (node in graph) {
            assertEquals(expectedOrder[i++], node)
        }
    }

    @Test
    fun `DFS on undirected graph`() {
        val expectedOrder = mutableListOf(nodes[0], nodes[3], nodes[4], nodes[2], nodes[1])
        var i = 0
        graph.dfs(nodes[0]) {
            assertEquals(expectedOrder[i++], it)
        }
    }

    @Test
    fun `traverse undirected edges`() {
        val edges = nodes.associateWith { mutableListOf<SimpleNode<Int>>().apply { addAll(it.neighbors) } }
        graph.forEachEdge { node, neighbor ->
            assert(neighbor in edges.getValue(node))
            assert(node in edges.getValue(neighbor))
            edges.getValue(node).remove(neighbor)
            edges.getValue(neighbor).remove(node)
        }
        edges.values.forEach {
            assert(it.isEmpty())
        }
    }
}