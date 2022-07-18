package d_map_quest;

import java.util.HashSet;
import java.util.Set;

public class Node {

    private String nodeName;
    private Set<Node> neighbors;

    public Node(String nodeName) {
        this.nodeName = nodeName;
        this.neighbors = new HashSet<>();
    }

    public Node addNeighbor(Node node) {
        neighbors.add(node);
        node.addBackNeighbor(this);
        return this;
    }

    private Node addBackNeighbor(Node node) {
        neighbors.add(node);
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public Set<Node> getNeighbors() {
        return neighbors;
    }

    public int getDegree() {
        return neighbors.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        if (!nodeName.equals(node.nodeName)) return false;
        return neighbors.equals(node.neighbors);
    }


    @Override
    public String toString() {
        return "(" + nodeName + ")";
    }
}
