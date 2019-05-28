package graph;

import map.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Vertex {

    private HashSet<Edge> edgeSet;
    private Coordinate coordinate;

    public Vertex(Coordinate coordinate){
        this.coordinate = coordinate;
        edgeSet = new HashSet<Edge>();
    }

    public void addEdge(Edge edge){
        edgeSet.add(edge);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public HashSet<Edge> getEdgeSet() {
        return edgeSet;
    }
}
