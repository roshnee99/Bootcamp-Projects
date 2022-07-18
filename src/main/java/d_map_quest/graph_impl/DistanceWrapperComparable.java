package d_map_quest.graph_impl;

import d_map_quest.Vertex;

import java.util.PriorityQueue;

public class DistanceWrapperComparable implements Comparable<DistanceWrapperComparable> {

    private Vertex vertex;
    private int distance;

    public DistanceWrapperComparable(Vertex vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(DistanceWrapperComparable o) {
        if (this.distance > o.getDistance()) {
            return 1;
        } else if (this.distance == o.getDistance()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistanceWrapperComparable that = (DistanceWrapperComparable) o;
        return that.getVertex().equals(this.getVertex()) && that.getDistance() == this.getDistance();
    }

    public static void main(String[] args) {
        Vertex a = new Vertex("a");
        Vertex b = new Vertex("b");
        Vertex c = new Vertex("c");
        DistanceWrapperComparable test1 = new DistanceWrapperComparable(a, 3);
        DistanceWrapperComparable test2 = new DistanceWrapperComparable(b, 2);
        DistanceWrapperComparable test3 = new DistanceWrapperComparable(c, 1);
        PriorityQueue<DistanceWrapperComparable> testQueue = new PriorityQueue<>();
        testQueue.add(test1);
        testQueue.add(test3);
        testQueue.add(test2);

        while (!testQueue.isEmpty()) {
            System.out.println(testQueue.remove().getVertex());
        }
    }
}
