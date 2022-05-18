import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import utils.bfs
import utils.dfs
import utils.distanceBetween

class GraphUtilTest {
    private lateinit var graph: UndirectedSimpleGraph<Int>
    private lateinit var nodes: List<SimpleNode<Int>>
    private val tolerance = 0.00001

    @Before
    fun setup() {
        graph = UndirectedSimpleGraph()
        (0.. 4).forEach { graph.addNode(SimpleNode(it)) }
        nodes = graph.nodes.toList()
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[0], nodes[3])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[3], nodes[4])
    }

    @Test
    fun `BFS test`() {
        val expectedOrder = mutableListOf(nodes[0], nodes[1], nodes[2], nodes[3], nodes[4])
        for (node in graph.bfs(nodes[0])) {
            assertEquals(expectedOrder.removeFirst(), node)
        }
    }

    @Test
    fun `DFS test`() {
        val expectedOrder = mutableListOf(nodes[0], nodes[3], nodes[4], nodes[2], nodes[1])
        for (node in graph.dfs(nodes[0])) {
            assertEquals(expectedOrder.removeFirst(), node)
        }
    }

    @Test
    fun `find shortest path in simple graph`() {
        val graph: UndirectedSimpleGraph<Int> = UndirectedSimpleGraph()
        (0.. 5).forEach { graph.addNode(SimpleNode(it)) }
        val nodes: List<SimpleNode<Int>> = graph.nodes.toList()
        graph.addEdge(nodes[0], nodes[4])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[2], nodes[5])
        graph.addEdge(nodes[3], nodes[4])
        graph.addEdge(nodes[4], nodes[5])
        assertEquals(4.0, graph.distanceBetween(nodes[0], nodes[1]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[3]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[0], nodes[4]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[5]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[1], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[3]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[1], nodes[4]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[5]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[3]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[2], nodes[4]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[5]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[3], nodes[4]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[3], nodes[5]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[4], nodes[5]), tolerance)
    }

    @Test
    fun `find shortest path in weighted graph`() {
        val graph: WeightedUndirectedGraph<Int> = WeightedUndirectedGraph()
        (0.. 5).forEach { graph.addNode(WeightedNode(it)) }
        val nodes: List<WeightedNode<Int>> = graph.nodes.toList()
        graph.addEdge(nodes[0], nodes[4], 1.0)
        graph.addEdge(nodes[1], nodes[2], 2.0)
        graph.addEdge(nodes[2], nodes[3], 2.0)
        graph.addEdge(nodes[2], nodes[5], 4.0)
        graph.addEdge(nodes[3], nodes[4], 1.0)
        graph.addEdge(nodes[4], nodes[5], 2.0)
        assertEquals(6.0, graph.distanceBetween(nodes[0], nodes[1]), tolerance)
        assertEquals(4.0, graph.distanceBetween(nodes[0], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[3]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[0], nodes[4]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[5]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[2]), tolerance)
        assertEquals(4.0, graph.distanceBetween(nodes[1], nodes[3]), tolerance)
        assertEquals(5.0, graph.distanceBetween(nodes[1], nodes[4]), tolerance)
        assertEquals(6.0, graph.distanceBetween(nodes[1], nodes[5]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[2], nodes[3]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[2], nodes[4]), tolerance)
        assertEquals(4.0, graph.distanceBetween(nodes[2], nodes[5]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[3], nodes[4]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[3], nodes[5]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[4], nodes[5]), tolerance)
    }
}