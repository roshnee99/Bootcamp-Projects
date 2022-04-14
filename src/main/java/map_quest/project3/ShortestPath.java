package map_quest.project3;

import map_quest.Edge;
import map_quest.Vertex;
import map_quest.graph_impl.DirectedGraph;

import java.util.*;

public class ShortestPath {

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
        List<Vertex> vertices = Arrays.asList(a, b, c, d, e, f, g, h, i, j, k);

        Edge ab = new Edge(a, b, 1);
        Edge ac = new Edge(a, c, 3);
        Edge af = new Edge(a, f, 8);
        Edge bc = new Edge(b, c, 1);
        Edge cd = new Edge(c, d, 2);
        Edge de = new Edge(d, e, 1);
        Edge ef = new Edge(e, f, 1);
        Edge fg = new Edge(f, g, 2);
        Edge gh = new Edge(g, h, 3);
        Edge gi = new Edge(g, i, 4);
        Edge gj = new Edge(g, j, 5);
        Edge hk = new Edge(h, k, 1);
        Edge ik = new Edge(i, k, 2);
        Edge jk = new Edge(j, k, 2);
        List<Edge> edges = Arrays.asList(ab, ac, af, bc, cd, de, ef, fg, gh, gi, gj, hk, ik, jk);
        DirectedGraph graph = new DirectedGraph(vertices, edges);

        // find least number of edges to take
        Stack<Vertex> path = graph.getShortestPathBreadthFirst(a, k);
        while (!path.isEmpty()) {
            System.out.print(path.pop() + " -> ");
        }
        System.out.println("\n");

        // finding the shortest path on a weighted graph
        Map<Vertex, Vertex> childToParentMap = new HashMap<>();
        Map<Vertex, Integer> distanceMap = new HashMap<>();
        graph.getDijktrasShortestPaths(a, childToParentMap, distanceMap);
        graph.printShortestPathInfo(a, k, childToParentMap, distanceMap);
        graph.printShortestPathInfo(a, f, childToParentMap, distanceMap);
    }

}
