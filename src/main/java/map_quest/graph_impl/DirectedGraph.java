package map_quest.graph_impl;

import map_quest.Edge;
import map_quest.Vertex;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class DirectedGraph {

    /*
     * Key is the start vertex
     * The value is a Map, where the keyset are the "dest" vertices from that start vertex
     *                     the values of this map are edges, from which we can get the weight

     */
    private Map<Vertex, Map<Vertex, Edge>> adjacencyList;

    public DirectedGraph(Collection<Vertex> vertices, Collection<Edge> edges) {
        this();
        initializeVertices(vertices);
        initializeEdges(edges);
    }

    public DirectedGraph() {
        this.adjacencyList = new HashMap<>();
    }

    private void initializeVertices(Collection<Vertex> vertices) {
        for (Vertex v : vertices) {
            addVertex(v);
        }
    }

    private void initializeEdges(Collection<Edge> edges) {
        for (Edge e : edges) {
            addEdge(e);
        }
    }

    /**
     * Adds a vertex to the graph
     * @param v Vertex to add to the graph
     * @return True if vertex didn't exist in graph and was successfully added
     */
    public boolean addVertex(Vertex v) {
        if (!adjacencyList.containsKey(v)) {
            adjacencyList.put(v, new HashMap<>());
            return true;
        }
        return false;
    }

    /**
     * Adds an edge to the graph, if both vertices exist in graph
     * @param e Edge to be added to the graph
     * @return True if the edge was added to the graph
     */
    public boolean addEdge(Edge e) {
        if (adjacencyList.containsKey(e.getStart()) && adjacencyList.containsKey(e.getEnd())) {
            adjacencyList.get(e.getStart()).put(e.getEnd(), e);
            return true;
        }
        return false;
    }

    public Set<Vertex> getPossibleDestinations(Vertex start) {
        if (!adjacencyList.containsKey(start)) {
            return new HashSet<>();
        }
        return adjacencyList.get(start).keySet();
    }

    public Edge getEdge(Vertex start, Vertex dest) {
        if (adjacencyList.containsKey(start) && adjacencyList.get(start).containsKey(dest)) {
            return adjacencyList.get(start).get(dest);
        }
        return null;
    }

    /**
     * A depth first ordering of the graph starts at the start node, and picks a neighbor
     * It goes all the way down that graph, and then picks another neighbor, and so on...
     * It uses a stack, because it tries to stay deep for as long as possible
     *
     * @param start the vertex to start with
     * @return a depth first ordering of all the vertices that are reachable from the start vertex
     */
    public List<Vertex> depthFirstOrderingOfGraph(Vertex start) {
        List<Vertex> ordering = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Stack<Vertex> stack = new Stack<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            Vertex current = stack.pop();
            if (!visited.contains(current)) {
                ordering.add(current);
                visited.add(current);
                Set<Vertex> possibleDestinations = getPossibleDestinations(current);
                for (Vertex v : possibleDestinations) {
                    stack.push(v);
                }
            }
        }
        return ordering;
    }

    /**
     * A breadth first ordering of the graph starts at the start node, and then it goes to all of its immediate neighbors
     * After that, it goes through that children's immediate neighbors, and so on...
     * It uses a queue, because it tries to stay shallow for as long as possible
     *
     * @param start the vertex to start with
     * @return a breadth first ordering of all the vertices that are reachable from the start vertex
     */
    public List<Vertex> breadthFirstOrderingOfGraph(Vertex start) {
        List<Vertex> ordering = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex current = queue.remove();
            if (!visited.contains(current)) {
                ordering.add(current);
                visited.add(current);
                Set<Vertex> possibleDestinations = getPossibleDestinations(current);
                queue.addAll(possibleDestinations);
            }
        }
        return ordering;
    }

    /**
     * Uses breadth first to find the path that has the least number of edges
     * @param start the start Vertex. Will return empty path if start not in graph
     * @param end the end Vertex. Will return empty path if end not in graph
     * @return The path, if exists, from start to end in Stack. If no path, will just return end Vertex.
     *         If start or end not in graph, will return empty Stack
     * @throws IllegalArgumentException if start or end are not in graph
     */
    public Stack<Vertex> getShortestPathBreadthFirst(Vertex start, Vertex end) {
        if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
            throw new IllegalArgumentException("Start or end vertex not in graph");
        }
        // contains next vertex to visit, and its parent
        Queue<Pair<Vertex, Vertex>> queue = new LinkedList<>();
        queue.add(Pair.of(start, null));
        // contains all vertices we've visited, and their parents as values
        Map<Vertex, Vertex> visitedToParent = new HashMap<>();
        boolean foundDest = false;
        // do BFS while maintaining parents, until end is found
        while (!queue.isEmpty() && !foundDest) {
            Pair<Vertex, Vertex> currentPair = queue.remove();
            Vertex current = currentPair.getKey();
            Vertex currentParent = currentPair.getValue();
            if (!visitedToParent.containsKey(current)) {
                visitedToParent.put(current, currentParent);
                if (current.equals(end)) {
                    foundDest = true;
                }
                Set<Vertex> destinations = getPossibleDestinations(current);
                for (Vertex dest : destinations) {
                    queue.add(Pair.of(dest, current));
                }
            }
        }
        Stack<Vertex> path = new Stack<>();
        Vertex current = end;
        while (current != null) {
            Vertex parent = visitedToParent.get(current);
            path.push(current);
            current = parent;
        }
        return path;
    }

}
