package map_quest;

public class Vertex {

    private String name;

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return "(" + name + ")";
    }

}
