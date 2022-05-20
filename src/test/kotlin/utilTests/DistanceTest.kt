package utilTests

import DirectedSimpleGraph
import UndirectedSimpleGraph
import WeightedDirectedGraph
import WeightedUndirectedGraph
import org.junit.Assert.assertEquals
import org.junit.Test
import utils.distanceBetween

class DistanceTest {
    private val tolerance = 0.00001

    @Test
    fun `find shortest path in simple graph`() {
        val graph: UndirectedSimpleGraph<Int> = UndirectedSimpleGraph()
        val nodes = (0.. 5).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[4])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[2], nodes[5])
        graph.addEdge(nodes[3], nodes[4])
        graph.addEdge(nodes[4], nodes[5])
        assertEquals(0.0, graph.distanceBetween(nodes[0], nodes[0]), tolerance)
        assertEquals(4.0, graph.distanceBetween(nodes[0], nodes[1]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[3]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[0], nodes[4]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[1], nodes[1]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[1], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[3]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[1], nodes[4]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[5]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[3]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[2], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[2], nodes[4]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[3], nodes[3]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[3], nodes[4]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[3], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[4], nodes[4]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[4], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[5], nodes[5]), tolerance)
    }

    @Test
    fun `find shortest path in weighted graph`() {
        val graph: WeightedUndirectedGraph<Int> = WeightedUndirectedGraph()
        val nodes = (0.. 5).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[4], 1.0)
        graph.addEdge(nodes[1], nodes[2], 2.0)
        graph.addEdge(nodes[2], nodes[3], 2.0)
        graph.addEdge(nodes[2], nodes[5], 4.0)
        graph.addEdge(nodes[3], nodes[4], 1.0)
        graph.addEdge(nodes[4], nodes[5], 2.0)
        assertEquals(0.0, graph.distanceBetween(nodes[0], nodes[0]), tolerance)
        assertEquals(6.0, graph.distanceBetween(nodes[0], nodes[1]), tolerance)
        assertEquals(4.0, graph.distanceBetween(nodes[0], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[3]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[0], nodes[4]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[1], nodes[1]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[2]), tolerance)
        assertEquals(4.0, graph.distanceBetween(nodes[1], nodes[3]), tolerance)
        assertEquals(5.0, graph.distanceBetween(nodes[1], nodes[4]), tolerance)
        assertEquals(6.0, graph.distanceBetween(nodes[1], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[2], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[2], nodes[3]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[2], nodes[4]), tolerance)
        assertEquals(4.0, graph.distanceBetween(nodes[2], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[3], nodes[3]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[3], nodes[4]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[3], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[4], nodes[4]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[4], nodes[5]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[5], nodes[5]), tolerance)
    }

    @Test
    fun `find shortest path in directed graph`() {
        val graph: DirectedSimpleGraph<Int> = DirectedSimpleGraph()
        val nodes = (0.. 4).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[1])
        graph.addEdge(nodes[1], nodes[2])
        graph.addEdge(nodes[2], nodes[3])
        graph.addEdge(nodes[3], nodes[1])
        graph.addEdge(nodes[4], nodes[3])
        graph.addEdge(nodes[2], nodes[4])
        assertEquals(0.0, graph.distanceBetween(nodes[0], nodes[0]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[0], nodes[1]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[2]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[3]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[4]), tolerance)
        assertEquals(Double.POSITIVE_INFINITY, graph.distanceBetween(nodes[1], nodes[0]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[1], nodes[1]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[1], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[3]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[4]), tolerance)
        assertEquals(Double.POSITIVE_INFINITY, graph.distanceBetween(nodes[2], nodes[0]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[2], nodes[1]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[2], nodes[2]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[3]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[4]), tolerance)
        assertEquals(Double.POSITIVE_INFINITY, graph.distanceBetween(nodes[3], nodes[0]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[3], nodes[1]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[3], nodes[2]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[3], nodes[3]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[3], nodes[4]), tolerance)
        assertEquals(Double.POSITIVE_INFINITY, graph.distanceBetween(nodes[4], nodes[0]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[4], nodes[1]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[4], nodes[2]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[4], nodes[3]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[4], nodes[4]), tolerance)
    }

    @Test
    fun `find shortest path in weighted directed graph`() {
        val graph: WeightedDirectedGraph<Int> = WeightedDirectedGraph()
        val nodes = (0.. 4).map { graph.addNodeOf(it) }
        graph.addEdge(nodes[0], nodes[1], 1.0)
        graph.addEdge(nodes[1], nodes[2], 2.0)
        graph.addEdge(nodes[2], nodes[3], 4.0)
        graph.addEdge(nodes[2], nodes[4], 1.0)
        graph.addEdge(nodes[3], nodes[1], 3.0)
        graph.addEdge(nodes[4], nodes[3], 2.0)
        assertEquals(0.0, graph.distanceBetween(nodes[0], nodes[0]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[0], nodes[1]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[2]), tolerance)
        assertEquals(6.0, graph.distanceBetween(nodes[0], nodes[3]), tolerance)
        assertEquals(4.0, graph.distanceBetween(nodes[0], nodes[4]), tolerance)
        assertEquals(Double.POSITIVE_INFINITY, graph.distanceBetween(nodes[1], nodes[0]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[1], nodes[1]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[2]), tolerance)
        assertEquals(5.0, graph.distanceBetween(nodes[1], nodes[3]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[1], nodes[4]), tolerance)
        assertEquals(Double.POSITIVE_INFINITY, graph.distanceBetween(nodes[2], nodes[0]), tolerance)
        assertEquals(6.0, graph.distanceBetween(nodes[2], nodes[1]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[2], nodes[2]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[2], nodes[3]), tolerance)
        assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[4]), tolerance)
        assertEquals(Double.POSITIVE_INFINITY, graph.distanceBetween(nodes[3], nodes[0]), tolerance)
        assertEquals(3.0, graph.distanceBetween(nodes[3], nodes[1]), tolerance)
        assertEquals(5.0, graph.distanceBetween(nodes[3], nodes[2]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[3], nodes[3]), tolerance)
        assertEquals(6.0, graph.distanceBetween(nodes[3], nodes[4]), tolerance)
        assertEquals(Double.POSITIVE_INFINITY, graph.distanceBetween(nodes[4], nodes[0]), tolerance)
        assertEquals(5.0, graph.distanceBetween(nodes[4], nodes[1]), tolerance)
        assertEquals(7.0, graph.distanceBetween(nodes[4], nodes[2]), tolerance)
        assertEquals(2.0, graph.distanceBetween(nodes[4], nodes[3]), tolerance)
        assertEquals(0.0, graph.distanceBetween(nodes[4], nodes[4]), tolerance)
    }
}