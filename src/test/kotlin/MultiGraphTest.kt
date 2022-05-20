import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MultiGraphTest {
    private val graph = UndirectedMultiGraph<Int>()
    private lateinit var nodes: List<MultiNode<Int>>

    @Before
    fun setup() {
        nodes = (0.. 2).map { graph.addNodeOf(it) }
    }

    @Test
    fun `create node`() {
        val nodes = (0..2).map { graph.addNodeOf(it) }
        assertEquals(0, nodes[0].value)
        assertEquals(1, nodes[1].value)
        assertEquals(2, nodes[2].value)
        nodes.forEach { assert(it in graph.nodes) }
    }

    @Test
    fun `modify nodes`() {
        val nodes = (0.. 2).map { MultiNode(it) }
        graph.addNode(nodes[0])
        graph.addNode(nodes[1])
        graph.addNode(nodes[2])
        nodes.forEach { assert(it in graph.nodes) }
        graph.removeNode(nodes[0])
        assert(nodes[0] !in graph.nodes)
    }

    @Test
    fun `edges are bidirectional`() {
        graph.addEdge(nodes[0], nodes[1])
        assert(nodes[0] in nodes[1].neighbors)
        assert(nodes[1] in nodes[0].neighbors)
    }

    @Test
    fun `multiple edges between nodes`() {
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[1])
        assert(nodes[0] in nodes[1].neighbors)
        assert(nodes[1] in nodes[0].neighbors)
        graph.removeEdge(nodes[0], nodes[1])
        assert(nodes[0] in nodes[1].neighbors)
        assert(nodes[1] in nodes[0].neighbors)
        graph.removeAllEdges(nodes[0], nodes[1])
        assert(nodes[0] !in nodes[1].neighbors)
    }

    @Test
    fun `modify edges`() {
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[1], nodes[2])
        assert(nodes[1] in nodes[0].neighbors)
        assert(nodes[2] !in nodes[0].neighbors)
        assert(nodes[0] in nodes[1].neighbors)
        assert(nodes[2] in nodes[1].neighbors)
        assert(nodes[0] !in nodes[2].neighbors)
        assert(nodes[1] in nodes[2].neighbors)
        graph.removeAllEdges(nodes[0], nodes[1])
        graph.removeEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[0], nodes[2])
        assert(nodes[0] !in nodes[1].neighbors)
        assert(nodes[1] !in nodes[0].neighbors)
        assert(nodes[0] in nodes[2].neighbors)
        assert(nodes[2] in nodes[0].neighbors)
        assert(nodes[2] in nodes[1].neighbors)
        assert(nodes[1] in nodes[2].neighbors)
        graph.removeNode(nodes[2])
        assert(nodes[2] !in nodes[0].neighbors)
        assert(nodes[2] !in nodes[1].neighbors)
    }
}