import org.junit.Assert.assertEquals
import org.junit.Test

class BasicGraphTest {

    @Test
    fun `modify nodes`() {
        val graph: BasicGraph<Int> = BasicGraph()
        val nodes: List<BasicNode<Int>> = (0.. 2).map { BasicNode(it) }
        graph.addNode(nodes[0])
        graph.addNode(nodes[1])
        graph.addNode(nodes[2])
        nodes.forEach { assert(it in graph.nodes) }
        graph.removeNode(nodes[0])
        assert(nodes[0] !in graph.nodes)
    }

    @Test
    fun `modify edges`() {
        val graph: BasicGraph<Int> = BasicGraph()
        (0.. 2).forEach { graph.addNode(BasicNode(it)) }
        graph.addEdge(graph.nodes[0], graph.nodes[1])
        graph.addEdge(graph.nodes[1], graph.nodes[2])
        assert(graph.nodes[1] in graph.nodes[0].neighbors)
        assert(graph.nodes[2] !in graph.nodes[0].neighbors)
        assert(graph.nodes[0] in graph.nodes[1].neighbors)
        assert(graph.nodes[2] in graph.nodes[1].neighbors)
        assert(graph.nodes[0] !in graph.nodes[2].neighbors)
        assert(graph.nodes[1] in graph.nodes[2].neighbors)
        graph.removeEdge(graph.nodes[0], graph.nodes[1])
        assert(graph.nodes[1] !in graph.nodes[0].neighbors)
        assert(graph.nodes[0] !in graph.nodes[1].neighbors)
    }

    @Test
    fun `create complement`() {
        val graph: BasicGraph<Int> = BasicGraph()
        (0.. 3).forEach { graph.addNode(BasicNode(it)) }
        graph.addEdge(graph.nodes[0], graph.nodes[1])
        graph.addEdge(graph.nodes[0], graph.nodes[2])
        graph.addEdge(graph.nodes[1], graph.nodes[3])
        val complementGraph = graph.complement()
        graph.nodes.forEach { assert(it !in complementGraph.nodes) }
        assert(graph.nodes[3] in graph.nodes[0].neighbors)
        assert(graph.nodes[2] in graph.nodes[1].neighbors)
        assert(graph.nodes[3] in graph.nodes[2].neighbors)
    }

    @Test
    fun `find shortest path`() {
        val graph: BasicGraph<Int> = BasicGraph()
        (0.. 5).forEach { graph.addNode(BasicNode(it)) }
        graph.addEdge(graph.nodes[0], graph.nodes[4])
        graph.addEdge(graph.nodes[1], graph.nodes[2])
        graph.addEdge(graph.nodes[2], graph.nodes[3])
        graph.addEdge(graph.nodes[2], graph.nodes[5])
        graph.addEdge(graph.nodes[3], graph.nodes[4])
        graph.addEdge(graph.nodes[4], graph.nodes[5])
        assertEquals(4, graph.distanceBetween(graph.nodes[0], graph.nodes[1]))
        assertEquals(3, graph.distanceBetween(graph.nodes[0], graph.nodes[2]))
        assertEquals(2, graph.distanceBetween(graph.nodes[0], graph.nodes[3]))
        assertEquals(1, graph.distanceBetween(graph.nodes[0], graph.nodes[4]))
        assertEquals(2, graph.distanceBetween(graph.nodes[0], graph.nodes[5]))
        assertEquals(1, graph.distanceBetween(graph.nodes[1], graph.nodes[2]))
        assertEquals(2, graph.distanceBetween(graph.nodes[1], graph.nodes[3]))
        assertEquals(3, graph.distanceBetween(graph.nodes[1], graph.nodes[4]))
        assertEquals(2, graph.distanceBetween(graph.nodes[1], graph.nodes[5]))
        assertEquals(1, graph.distanceBetween(graph.nodes[2], graph.nodes[3]))
        assertEquals(2, graph.distanceBetween(graph.nodes[2], graph.nodes[4]))
        assertEquals(1, graph.distanceBetween(graph.nodes[2], graph.nodes[5]))
        assertEquals(1, graph.distanceBetween(graph.nodes[3], graph.nodes[4]))
        assertEquals(2, graph.distanceBetween(graph.nodes[3], graph.nodes[5]))
        assertEquals(1, graph.distanceBetween(graph.nodes[4], graph.nodes[5]))
    }
}
