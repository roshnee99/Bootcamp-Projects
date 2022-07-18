package d_map_quest.graph_impl;

import d_map_quest.Edge;
import d_map_quest.Vertex;

import java.util.Collection;

public class UndirectedGraph extends DirectedGraph{

    public UndirectedGraph(){
        super();
    }

    public UndirectedGraph(Collection<Vertex> vertices, Collection<Edge> edges){
        super(vertices, edges);
    }

    @Override
    public boolean addEdge(Edge edge){
        boolean initialAdd = super.addEdge(edge);
        Edge reverseEdge = new Edge(edge.getEnd(), edge.getStart(), edge.getWeight());
        boolean backAdd = super.addEdge(reverseEdge);
        return initialAdd && backAdd;
    }

}
