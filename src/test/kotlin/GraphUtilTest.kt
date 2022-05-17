import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class GraphUtilTest {
    private lateinit var graph: BasicGraph<Int>
    private lateinit var nodes: List<BasicNode<Int>>

    @Before
    fun setup() {
        graph = BasicGraph()
        (0.. 4).forEach { graph.addNode(BasicNode(it)) }
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
}