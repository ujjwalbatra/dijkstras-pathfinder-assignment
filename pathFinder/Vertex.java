package pathFinder;

import map.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Vertex {
    private Coordinate coordinate;
    private Set<Edge> edges;

    public Vertex(Coordinate coordinate) {
        this.edges = new HashSet<>();
        this.coordinate = coordinate;
    }

    void addEdge(Edge edge) {
        this.edges.add(edge);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Set<Edge> getEdges() {
        return edges;
    }
}