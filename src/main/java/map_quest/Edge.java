package map_quest;

public class Edge {

    private int weight;
    private Vertex start;
    private Vertex end;

    public Edge(Vertex start, Vertex end) {
        this(start, end, 1);
    }

    public Edge(Vertex start, Vertex end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    public String toString() {
        return start + " --|" + weight + "|--> " + end;
    }

    public static void main(String[] args) {
        Edge e = new Edge(new Vertex("A"), new Vertex("B"), 7);
        System.out.println(e);
    }
}
