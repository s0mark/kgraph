import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class WeightedGraphTest {
    private val graph = WeightedUndirectedGraph<Int>()
    private lateinit var nodes: List<WeightedNode<Int>>
    private val tolerance = 0.00001

    @Before
    fun setup() {
        nodes = (0..2).map { graph.addNodeOf(it) }
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
        val nodes: List<WeightedNode<Int>> = (0.. 2).map { WeightedNode(it) }
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
    fun `no loop edges`() {
        graph.addEdge(nodes[0], nodes[0])
        assert(nodes[0] !in nodes[0].neighbors)
    }

    @Test
    fun `no multiple edges between nodes`() {
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[1])
        assert(nodes[0] in nodes[1].neighbors)
        assert(nodes[1] in nodes[0].neighbors)
        graph.removeEdge(nodes[0], nodes[1])
        assert(nodes[0] !in nodes[1].neighbors)
        assert(nodes[1] !in nodes[0].neighbors)
    }

    @Test
    fun `modify edges`() {
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[1], nodes[2], 3.0)
        assert(nodes[1] in nodes[0].neighbors)
        assertEquals(1.0, nodes[0].getWeight(nodes[1])!!, tolerance)
        assert(nodes[2] !in nodes[0].neighbors)
        assertNull(nodes[0].getWeight(nodes[2]))
        assert(nodes[0] in nodes[1].neighbors)
        assertEquals(1.0, nodes[1].getWeight(nodes[0])!!, tolerance)
        assert(nodes[2] in nodes[1].neighbors)
        assertEquals(3.0, nodes[1].getWeight(nodes[2])!!, tolerance)
        assert(nodes[0] !in nodes[2].neighbors)
        assertNull(nodes[2].getWeight(nodes[0]))
        assert(nodes[1] in nodes[2].neighbors)
        assertEquals(3.0, nodes[2].getWeight(nodes[1])!!, tolerance)
        graph.removeEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[1], nodes[2], -2.0)
        assert(nodes[0] !in nodes[1].neighbors)
        assertNull(nodes[1].getWeight(nodes[0]))
        assert(nodes[1] !in nodes[0].neighbors)
        assertNull(nodes[0].getWeight(nodes[1]))
        assert(nodes[1] in nodes[2].neighbors)
        assertEquals(-2.0, nodes[2].getWeight(nodes[1])!!, tolerance)
        assert(nodes[2] in nodes[1].neighbors)
        assertEquals(-2.0, nodes[1].getWeight(nodes[2])!!, tolerance)
        graph.removeNode(nodes[2])
        assert(nodes[2] !in nodes[1].neighbors)
        assertNull(nodes[1].getWeight(nodes[2]))
    }
}