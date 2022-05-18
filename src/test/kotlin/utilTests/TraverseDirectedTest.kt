package utilTests

import DirectedSimpleGraph
import SimpleNode
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.bfs
import utils.dfs
import utils.forEachEdge

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
    fun `BFS on directed graph`() {
        val expectedOrder = mutableListOf(nodes[0], nodes[1], nodes[2], nodes[3], nodes[4])
        graph.bfs(nodes[0]) {
            Assert.assertEquals(expectedOrder.removeFirst(), it)
        }
    }

    @Test
    fun `DFS on directed graph`() {
        val expectedOrder = mutableListOf(nodes[0], nodes[3], nodes[4], nodes[2], nodes[1])
        graph.dfs(nodes[0]) {
            Assert.assertEquals(expectedOrder.removeFirst(), it)
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