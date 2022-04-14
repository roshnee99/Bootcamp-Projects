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

    /**
     * Uses a variation of breadth first search to traverse through graph and determine the shortest path from start to end
     * Completes two maps, one for the distance from start to each vertex, and one for the parent of each vertex
     * Using the parent vertex, one can backtrack to find the shortest path because eventually will reach the start
     * Can use this to reconstruct the shortest distance, or can use the start to each vertex distance to just know the distance
     *
     * It will populate the maps provided so that they can be accessed easily by the callee
     * @param start the vertex to start at
     * @param destinationToPrevious mapping of vertex to node to get there. Helps log the path taken. Should be empty when passed.
     * @param shortestDistanceMap mapping of vertex to the distance. Should be empty when passed.
     */
    public void getDijktrasShortestPaths(Vertex start, Map<Vertex, Vertex> destinationToPrevious, Map<Vertex, Integer> shortestDistanceMap) {
        shortestDistanceMap.put(start, 0);
        destinationToPrevious.put(start, null);
        // priority queue of all the shortest distances to all vertices
        Queue<DistanceWrapperComparable> queue = new PriorityQueue<>();
        queue.add(new DistanceWrapperComparable(start, 0));
        // set of all visited vertices
        Set<Vertex> visited = new HashSet<>();
        // initialize queue with all vertices
        for (Vertex v : adjacencyList.keySet()) {
            if (!v.equals(start)) {
                shortestDistanceMap.put(v, Integer.MAX_VALUE);
                queue.add(new DistanceWrapperComparable(v, Integer.MAX_VALUE));
            }
        }
        // go through queue until all vertices are visited
        while (!queue.isEmpty()) {
            DistanceWrapperComparable current = queue.remove();
            Vertex currentVertex = current.getVertex();
            Set<Vertex> neighbors = this.getPossibleDestinations(currentVertex);
            for (Vertex dest : neighbors) {
                if (!visited.contains(dest)) {
                    // get distance of (start to current) + (current to dest)
                    int tempDistance = shortestDistanceMap.get(currentVertex) + this.getEdge(currentVertex, dest).getWeight();
                    // if this new path is shorter, update the shortest distance map
                    if (tempDistance < shortestDistanceMap.get(dest)) {
                        // update queue distance
                        queue.remove(new DistanceWrapperComparable(dest, shortestDistanceMap.get(dest)));
                        shortestDistanceMap.put(dest, tempDistance);
                        destinationToPrevious.put(dest, currentVertex);
                        queue.add(new DistanceWrapperComparable(dest, shortestDistanceMap.get(dest)));
                    }
                }
            }
            // once we've visited all neighbors, we can consider this vertex visited
            visited.add(currentVertex);
        }
    }

    public void printShortestPathInfo(Vertex start, Vertex end, Map<Vertex, Vertex> destinationToPrevious, Map<Vertex, Integer> shortestDistanceMap) {
        // shortest distance from a to k
        System.out.println("Shortest distance from " + start + " to " + end + ": " + shortestDistanceMap.get(end));
        // path from a to k
        Stack<Edge> edgePath = new Stack<>();
        Vertex current = end;
        while (current != null) {
            Vertex parent = destinationToPrevious.get(current);
            edgePath.push(this.getEdge(parent, current));
            current = parent;
        }
        System.out.print(start);
        while (!edgePath.isEmpty()) {
            Edge currentEdge = edgePath.pop();
            if (currentEdge != null) {
                System.out.print(" - " + currentEdge.getWeight() + " -> " + currentEdge.getEnd());
            }
        }
        System.out.println();
    }

}
