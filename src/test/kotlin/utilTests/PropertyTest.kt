package utilTests

import DirectedMultiGraph
import DirectedSimpleGraph
import UndirectedMultiGraph
import UndirectedSimpleGraph
import WeightedDirectedGraph
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import utils.isAcyclic
import utils.isConnected
import utils.maxFlow

class PropertyTest {
    private val tolerance = 0.00001

    @Test
    fun `is simple undirected graph connected`() {
        val graph = UndirectedSimpleGraph<Int>()
        val nodes = (0..4).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[1], nodes[3])
        graph.addEdge(nodes[1], nodes[4])
        graph.addEdge(nodes[2], nodes[4])
        assert(graph.isConnected)
        graph.removeEdge(nodes[2], nodes[4])
        assert(graph.isConnected)
        graph.removeEdge(nodes[0], nodes[2])
        assertFalse(graph.isConnected)
        graph.removeNode(nodes[2])
        assert(graph.isConnected)
    }

    @Test
    fun `is undirected multigraph connected`() {
        val graph = UndirectedMultiGraph<Int>()
        val nodes = (0..4).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[1], nodes[3])
        graph.addEdge(nodes[1], nodes[4])
        graph.addEdge(nodes[2], nodes[4])
        assert(graph.isConnected)
        graph.removeEdge(nodes[2], nodes[4])
        assert(graph.isConnected)
        graph.removeEdge(nodes[0], nodes[2])
        assert(graph.isConnected)
        graph.removeEdge(nodes[0], nodes[2])
        assertFalse(graph.isConnected)
        graph.removeNode(nodes[2])
        assert(graph.isConnected)
    }

    @Test
    fun `is directed simple graph acyclic`() {
        val graph = DirectedSimpleGraph<Int>()
        val nodes = (0..4).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[1], nodes[3])
        graph.addEdge(nodes[1], nodes[4])
        graph.addEdge(nodes[2], nodes[4])
        assert(graph.isAcyclic)
        graph.addEdge(nodes[2], nodes[2])
        assert(graph.isAcyclic)
        graph.addEdge(nodes[3], nodes[0])
        assertFalse(graph.isAcyclic)
        graph.removeNode(nodes[0])
        assert(graph.isAcyclic)
    }

    @Test
    fun `is directed multigraph acyclic`() {
        val graph = DirectedMultiGraph<Int>()
        val nodes = (0..4).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[1], nodes[3])
        graph.addEdge(nodes[1], nodes[4])
        graph.addEdge(nodes[2], nodes[4])
        assert(graph.isAcyclic)
        graph.addEdge(nodes[2], nodes[2])
        assertFalse(graph.isAcyclic)
        graph.removeEdge(nodes[2], nodes[2])
        assert(graph.isAcyclic)
        graph.addEdge(nodes[3], nodes[0])
        assertFalse(graph.isAcyclic)
        graph.removeNode(nodes[0])
        assert(graph.isAcyclic)
    }

    @Test
    fun `max flow in unweighted graph`() {
        val graph = DirectedSimpleGraph<Int>()
        val nodes = (0..3).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[0], nodes[2])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[1], nodes[3])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[3], nodes[0])
        assertEquals(1.0, graph.maxFlow(nodes[0], nodes[1]), tolerance)
        assertEquals(1.0, graph.maxFlow(nodes[1], nodes[0]), tolerance)
        assertEquals(2.0, graph.maxFlow(nodes[0], nodes[2]), tolerance)
        assertEquals(1.0, graph.maxFlow(nodes[2], nodes[0]), tolerance)
        assertEquals(2.0, graph.maxFlow(nodes[0], nodes[3]), tolerance)
        assertEquals(1.0, graph.maxFlow(nodes[3], nodes[0]), tolerance)
        assertEquals(2.0, graph.maxFlow(nodes[1], nodes[2]), tolerance)
        assertEquals(1.0, graph.maxFlow(nodes[2], nodes[1]), tolerance)
        assertEquals(2.0, graph.maxFlow(nodes[1], nodes[3]), tolerance)
        assertEquals(1.0, graph.maxFlow(nodes[3], nodes[1]), tolerance)
        assertEquals(1.0, graph.maxFlow(nodes[2], nodes[3]), tolerance)
        assertEquals(1.0, graph.maxFlow(nodes[3], nodes[2]), tolerance)
        assertEquals(2.0, graph.maxFlow, tolerance)
    }

    @Test
    fun `max flow in weighted graph`() {
        val graph = WeightedDirectedGraph<Int>()
        val nodes = (0..3).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[1], 3.0)
        graph.addEdge(nodes[0], nodes[2], 6.0)
        graph.addEdge(nodes[1], nodes[2], 10.0)
        graph.addEdge(nodes[1], nodes[3], 7.0)
        graph.addEdge(nodes[2], nodes[3], 9.0)
        graph.addEdge(nodes[3], nodes[0], 8.0)
        assertEquals(3.0, graph.maxFlow(nodes[0], nodes[1]), tolerance)
        assertEquals(8.0, graph.maxFlow(nodes[1], nodes[0]), tolerance)
        assertEquals(9.0, graph.maxFlow(nodes[0], nodes[2]), tolerance)
        assertEquals(8.0, graph.maxFlow(nodes[2], nodes[0]), tolerance)
        assertEquals(9.0, graph.maxFlow(nodes[0], nodes[3]), tolerance)
        assertEquals(8.0, graph.maxFlow(nodes[3], nodes[0]), tolerance)
        assertEquals(16.0, graph.maxFlow(nodes[1], nodes[2]), tolerance)
        assertEquals(3.0, graph.maxFlow(nodes[2], nodes[1]), tolerance)
        assertEquals(16.0, graph.maxFlow(nodes[1], nodes[3]), tolerance)
        assertEquals(3.0, graph.maxFlow(nodes[3], nodes[1]), tolerance)
        assertEquals(9.0, graph.maxFlow(nodes[2], nodes[3]), tolerance)
        assertEquals(8.0, graph.maxFlow(nodes[3], nodes[2]), tolerance)
        assertEquals(16.0, graph.maxFlow, tolerance)
    }
}