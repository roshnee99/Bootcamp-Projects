package map_quest.project1;

import map_quest.graph_impl.UndirectedLinkedListGraph;
import map_quest.Node;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PartitioningGraph {

    public static void main(String[] args) {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");

        /*
               A          E
             /   \       /
            B     C     F
           /
          D

         */
        nodeA.addNeighbor(nodeB).addNeighbor(nodeC);
        nodeB.addNeighbor(nodeD);
        nodeE.addNeighbor(nodeF);
        UndirectedLinkedListGraph graph = new UndirectedLinkedListGraph(Arrays.asList(nodeA, nodeB, nodeC, nodeD, nodeE, nodeF));
        graph.printGraph();

        System.out.println("\n");

        // can we determine if two nodes are connected in a graph?
        System.out.println("A and B are connected: " + graph.areDirectlyConnected(nodeA, nodeB));
        System.out.println("B and C are connected: " + graph.areDirectlyConnected(nodeB, nodeC));

        // by doing this, can we determine what are the clusters of nodes that are reachable from a given node?
        // for example, in our graph, from A you can get to D. There's a cluster of A, B, C, D
        // Then there's a second cluster of E and F

        // how to check "isReachable"
        System.out.println("\n");
        System.out.println("D is reachable from A: " + graph.isReachable(nodeA, nodeD));
        System.out.println("A is reachable from E: " + graph.isReachable(nodeA, nodeE));

        // how to check neighborhoods
        System.out.println("\n");
        List<Set<Node>> neighborhoods = graph.getNeighborhoods();
        for (Set<Node> neighborhood : neighborhoods) {
            System.out.println("*********************Neighborhood************************");
            UndirectedLinkedListGraph subGraph = new UndirectedLinkedListGraph(neighborhood);
            System.out.println("Members of neighborhood: " + subGraph.getNodesAsString());
            subGraph.printGraph();
        }

    }
}
