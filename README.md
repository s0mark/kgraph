# kgraph
Basic graph classes implemented in Kotlin.

# Implemented classes
The following graph classes are implemented.

| class                 | Simplicity | Direction  | Weights    | inherits from       | uses         |
|-----------------------|------------|------------|------------|---------------------|--------------|
| UndirectedSimpleGraph | Simple     | Undirected | Unweighted | UndirectedGraphBase | SimpleNode   |
| UndirectedMultiGraph  | Multi      | Undirected | Unweighted | UndirectedGraphBase | MultiNode    |
| DirectedSimpleGraph   | Simple     | Directed   | Unweighted | DirectedGraphBase   | SimpleNode   |
| DirectedMultiGraph    | Multi      | Directed   | Unweighted | DirectedGraphBase   | MultiNode    |
| WeightedGraph         | Simple     | Undirected | Weighted   | UndirectedGraphBase | WeightedNode |
| -                     | Multi      | Undirected | Weighted   | -                   | -            |
| DirectedWeightedGraph | Simple     | Directed   | Weighted   | DirectedGraphBase   | WeightedNode |
| -                     | Multi      | Directed   | Weighted   | -                   | -            |