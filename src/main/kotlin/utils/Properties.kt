package utils

import DirectedGraph
import Node
import UndirectedGraph

val<T, N : Node<T, N>> DirectedGraph<T, N>.isAcyclic: Boolean
    get() {
        val visitMap = nodes.associateWith { false }.toMutableMap()
        var acyclic = true

        fun N.discover(discovered: MutableList<N>): MutableList<N> {
            visitMap[this] = true
            neighbors.forEach { neighbor ->
                if (neighbor === this) acyclic = false
                else if (!visitMap.getValue(neighbor))
                    neighbor.discover(discovered)
                if (!acyclic) return discovered
            }
            if (this in discovered) acyclic = false
            return discovered.apply { addAll(neighbors) }
        }

        for ((node, visited) in visitMap) {
            if (!visited) node.discover(mutableListOf())
            if (!acyclic) break
        }
        return acyclic
    }

val<T, N : Node<T, N>> UndirectedGraph<T, N>.isConnected: Boolean
    get() {
        if (nodes.isEmpty()) return true
        val visitMap = nodes.associateWith { false }.toMutableMap()

        fun N.discover() {
            visitMap[this] = true
            neighbors.forEach { neighbor ->
                if (!visitMap.getValue(neighbor))
                    neighbor.discover()
            }
        }

        nodes.first().discover()
        return visitMap.values.all { it }
    }