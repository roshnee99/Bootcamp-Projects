package map_quest.project3;

import map_quest.Edge;
import map_quest.Vertex;
import map_quest.graph_impl.DirectedGraph;
import map_quest.graph_impl.UndirectedGraph;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ShortestPathUnweighted {

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
        Edge ai = new Edge(a, i);
        Edge ih = new Edge(i, h);
        List<Edge> edges = Arrays.asList(ab, ac, bd, bf, de, cg, gh, hi, hj, ik, ai, ih);
        DirectedGraph graph = new DirectedGraph(vertices, edges);

        // how do we find the shortest path from a to k?

        // first, let's use the fact that all the edges are of weight 1
        // Use breadth first traversal to find the shortest path, since it tries to make sure we take the least edges
        Stack<Vertex> path = graph.getShortestPathBreadthFirst(a, k);
        while (!path.isEmpty()) {
            System.out.print(path.pop() + " -> ");
        }
        System.out.println();
        Stack<Vertex> nonExistentPath = graph.getShortestPathBreadthFirst(a, l);
        while (!nonExistentPath.isEmpty()) {
            System.out.print(nonExistentPath.pop() + " -> ");
        }
        System.out.println();
        System.out.println("Now error");
        graph.getShortestPathBreadthFirst(a, new Vertex("z"));

    }

}
