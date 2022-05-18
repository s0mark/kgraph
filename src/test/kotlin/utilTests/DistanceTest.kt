package utilTests

import SimpleNode
import UndirectedSimpleGraph
import WeightedNode
import WeightedUndirectedGraph
import org.junit.Assert
import org.junit.Test
import utils.distanceBetween

class DistanceTest {
    private val tolerance = 0.00001

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
        Assert.assertEquals(4.0, graph.distanceBetween(nodes[0], nodes[1]), tolerance)
        Assert.assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[2]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[3]), tolerance)
        Assert.assertEquals(1.0, graph.distanceBetween(nodes[0], nodes[4]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[5]), tolerance)
        Assert.assertEquals(1.0, graph.distanceBetween(nodes[1], nodes[2]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[3]), tolerance)
        Assert.assertEquals(3.0, graph.distanceBetween(nodes[1], nodes[4]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[5]), tolerance)
        Assert.assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[3]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[2], nodes[4]), tolerance)
        Assert.assertEquals(1.0, graph.distanceBetween(nodes[2], nodes[5]), tolerance)
        Assert.assertEquals(1.0, graph.distanceBetween(nodes[3], nodes[4]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[3], nodes[5]), tolerance)
        Assert.assertEquals(1.0, graph.distanceBetween(nodes[4], nodes[5]), tolerance)
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
        Assert.assertEquals(6.0, graph.distanceBetween(nodes[0], nodes[1]), tolerance)
        Assert.assertEquals(4.0, graph.distanceBetween(nodes[0], nodes[2]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[0], nodes[3]), tolerance)
        Assert.assertEquals(1.0, graph.distanceBetween(nodes[0], nodes[4]), tolerance)
        Assert.assertEquals(3.0, graph.distanceBetween(nodes[0], nodes[5]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[1], nodes[2]), tolerance)
        Assert.assertEquals(4.0, graph.distanceBetween(nodes[1], nodes[3]), tolerance)
        Assert.assertEquals(5.0, graph.distanceBetween(nodes[1], nodes[4]), tolerance)
        Assert.assertEquals(6.0, graph.distanceBetween(nodes[1], nodes[5]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[2], nodes[3]), tolerance)
        Assert.assertEquals(3.0, graph.distanceBetween(nodes[2], nodes[4]), tolerance)
        Assert.assertEquals(4.0, graph.distanceBetween(nodes[2], nodes[5]), tolerance)
        Assert.assertEquals(1.0, graph.distanceBetween(nodes[3], nodes[4]), tolerance)
        Assert.assertEquals(3.0, graph.distanceBetween(nodes[3], nodes[5]), tolerance)
        Assert.assertEquals(2.0, graph.distanceBetween(nodes[4], nodes[5]), tolerance)
    }

    @Test
    fun `find shortest path in directed graph`(): Unit = TODO()

    @Test
    fun `find shortest path in weighted directed graph`(): Unit = TODO()
}