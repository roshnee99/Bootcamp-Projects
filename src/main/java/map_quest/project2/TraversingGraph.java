package map_quest.project2;

import map_quest.Edge;
import map_quest.Vertex;
import map_quest.graph_impl.DirectedGraph;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TraversingGraph {

    public static void main(String[] args) {
        // Creating the graph
        Vertex a = new Vertex("a");
        Vertex b = new Vertex("b");
        Vertex c = new Vertex("c");
        Vertex d = new Vertex("d");
        Vertex e = new Vertex("e");
        Vertex f = new Vertex("f");
        Vertex g = new Vertex("g");
        Vertex h = new Vertex("h");
        Vertex i = new Vertex("i");
        Vertex j = new Vertex("j");
        Vertex k = new Vertex("k");
        Vertex l = new Vertex("l");
        List<Vertex> vertices = Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, l);
        Edge ab = new Edge(a, b);
        Edge ac = new Edge(a, c);
        Edge bd = new Edge(b, d);
        Edge bf = new Edge(b, f);
        Edge de = new Edge(d, e);
        Edge cg = new Edge(c, g);
        Edge gh = new Edge(g, h);
        Edge hi = new Edge(h, i);
        Edge hj = new Edge(h, j);
        Edge ik = new Edge(i, k);
        List<Edge> edges = Arrays.asList(ab, ac, bd, bf, de, cg, gh, hi, hj, ik);

        // the graph uses an "adjacency list" representation
        // discuss that for some time. Also always recall a graph is collection of vertices and edges
        DirectedGraph graph = new DirectedGraph(vertices, edges);

        // getting neighbors of vertex a
        Set<Vertex> neighborsOfA = graph.getPossibleDestinations(a);
        System.out.print("Neighbors of (a): ");
        for (Vertex neighbor : neighborsOfA) {
            System.out.print(neighbor + " ");
        }
        System.out.println();

        // doing a breadth first ordering of the graph
        System.out.println("Breadth first ordering: ");
        List<Vertex> breadthFirst = graph.breadthFirstOrderingOfGraph(a);
        for (Vertex v : breadthFirst) {
            System.out.print(v + " ");
        }
        System.out.println("\n");

        // doing a depth first ordering of the graph
        System.out.println("Depth first ordering: ");
        List<Vertex> depthFirst = graph.depthFirstOrderingOfGraph(a);
        for (Vertex v : depthFirst) {
            System.out.print(v + " ");
        }
        System.out.println();
    }

}
