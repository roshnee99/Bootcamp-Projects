package d_map_quest.graph_impl;

import d_map_quest.Node;

import java.util.*;

public class UndirectedLinkedListGraph {

    private Set<Node> nodes;

    public UndirectedLinkedListGraph(Collection<Node> nodes) {
        this.nodes = new HashSet<>(nodes);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public String getNodesAsString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes) {
            sb.append(node).append(" ");
        }
        return sb.toString();
    }

    public void printGraph() {
        for (Node node : nodes) {
            System.out.print(node + ": ");
            for (Node neighbor : node.getNeighbors()) {
                System.out.print(neighbor + ", ");
            }
            System.out.println();
        }
    }

    public boolean areDirectlyConnected(Node node1, Node node2) {
        return node1.getNeighbors().contains(node2);
    }

    public boolean isReachable(Node node1, Node node2) {
        Set<Node> checked = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node1);
        queue.addAll(node1.getNeighbors());
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == node2) {
                return true;
            }
            if (!checked.contains(current)) {
                checked.add(current);
                queue.addAll(current.getNeighbors());
            }
        }
        return false;
    }

    public List<Set<Node>> getNeighborhoods() {
        List<Set<Node>> neighborhoods = new ArrayList<>();
        Set<Node> inNeighborhoodAlready = new HashSet<>();
        for (Node node : nodes) {
            if (!inNeighborhoodAlready.contains(node)) {
                Set<Node> neighborhood = new HashSet<>();
                Set<Node> restOfNodes = new HashSet<>(nodes);
                for (Node n : restOfNodes) {
                    if (isReachable(node, n)) {
                        neighborhood.add(n);
                        inNeighborhoodAlready.add(n);
                    }
                }
                neighborhoods.add(neighborhood);
            }
        }
        return neighborhoods;
    }

}
