import org.junit.Assert.assertEquals
import org.junit.Test

class SimpleGraphTest {

    @Test
    fun `modify nodes`() {
        val graph: SimpleGraph<Int> = SimpleGraph()
        val nodes: List<SimpleNode<Int>> = (0.. 2).map { SimpleNode(it) }
        graph.addNode(nodes[0])
        graph.addNode(nodes[1])
        graph.addNode(nodes[2])
        nodes.forEach { assert(it in graph.nodes) }
        graph.removeNode(nodes[0])
        assert(nodes[0] !in graph.nodes)
    }

    @Test
    fun `modify edges`() {
        val graph: SimpleGraph<Int> = SimpleGraph()
        (0.. 2).forEach { graph.addNode(SimpleNode(it)) }
        val nodes: List<SimpleNode<Int>> = graph.nodes.toList()
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[1], nodes[2])
        assert(nodes[1] in nodes[0].neighbors)
        assert(nodes[2] !in nodes[0].neighbors)
        assert(nodes[0] in nodes[1].neighbors)
        assert(nodes[2] in nodes[1].neighbors)
        assert(nodes[0] !in nodes[2].neighbors)
        assert(nodes[1] in nodes[2].neighbors)
        graph.removeEdge(nodes[0], nodes[1])
        assert(nodes[1] !in nodes[0].neighbors)
        assert(nodes[0] !in nodes[1].neighbors)
    }

    @Test
    fun `create complement`() {
        val graph: SimpleGraph<Int> = SimpleGraph()
        (0.. 3).forEach { graph.addNode(SimpleNode(it)) }
        var nodes: List<SimpleNode<Int>> = graph.nodes.toList()
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[1], nodes[3])
        val complementGraph = graph.complement()
        graph.nodes.forEach { assert(it !in complementGraph.nodes) }
        nodes = complementGraph.nodes.toList()
        assert(nodes[3] in nodes[0].neighbors)
        assert(nodes[2] in nodes[1].neighbors)
        assert(nodes[3] in nodes[2].neighbors)
    }

    @Test
    fun `find shortest path`() {
        val graph: SimpleGraph<Int> = SimpleGraph()
        (0.. 5).forEach { graph.addNode(SimpleNode(it)) }
        val nodes: List<SimpleNode<Int>> = graph.nodes.toList()
        graph.addEdge(nodes[0], nodes[4])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[2], nodes[5])
        graph.addEdge(nodes[3], nodes[4])
        graph.addEdge(nodes[4], nodes[5])
        assertEquals(4, graph.distanceBetween(nodes[0], nodes[1]))
        assertEquals(3, graph.distanceBetween(nodes[0], nodes[2]))
        assertEquals(2, graph.distanceBetween(nodes[0], nodes[3]))
        assertEquals(1, graph.distanceBetween(nodes[0], nodes[4]))
        assertEquals(2, graph.distanceBetween(nodes[0], nodes[5]))
        assertEquals(1, graph.distanceBetween(nodes[1], nodes[2]))
        assertEquals(2, graph.distanceBetween(nodes[1], nodes[3]))
        assertEquals(3, graph.distanceBetween(nodes[1], nodes[4]))
        assertEquals(2, graph.distanceBetween(nodes[1], nodes[5]))
        assertEquals(1, graph.distanceBetween(nodes[2], nodes[3]))
        assertEquals(2, graph.distanceBetween(nodes[2], nodes[4]))
        assertEquals(1, graph.distanceBetween(nodes[2], nodes[5]))
        assertEquals(1, graph.distanceBetween(nodes[3], nodes[4]))
        assertEquals(2, graph.distanceBetween(nodes[3], nodes[5]))
        assertEquals(1, graph.distanceBetween(nodes[4], nodes[5]))
    }
}
