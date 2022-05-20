package utilTests

import DirectedMultiGraph
import DirectedSimpleGraph
import UndirectedMultiGraph
import UndirectedSimpleGraph
import org.junit.Assert.assertFalse
import org.junit.Test
import utils.isAcyclic
import utils.isConnected

class PropertyTest {

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
}