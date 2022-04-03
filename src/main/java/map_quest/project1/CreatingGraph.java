package map_quest.project1;

import map_quest.UndirectedGraph;
import map_quest.Node;

import java.util.Arrays;

public class CreatingGraph {

    public static void main(String[] args) {
        // what is a graph?
        // graph is a collection of nodes and edges
        // nodes are connected by edges

        // for now, let us assume that all edges are of length one and go both ways
        // this means that from one node, we can easily access all other nodes
        // let us create a graph with 5 nodes
        // some of nodes can be connected, and some might not
        Node nodeA = new Node("nodeA");
        Node nodeB = new Node("nodeB");
        Node nodeC = new Node("nodeC");
        Node nodeD = new Node("nodeD");
        Node nodeE = new Node("nodeE");

        nodeA.addNeighbor(nodeB)
                .addNeighbor(nodeC)
                .addNeighbor(nodeD)
                .addNeighbor(nodeE);
        nodeC.addNeighbor(nodeD);


        // now, let us print out all the nodes and their neighbors
        UndirectedGraph graph = new UndirectedGraph(Arrays.asList(nodeA, nodeB, nodeC, nodeD, nodeE));
        graph.printGraph();

    }

}
