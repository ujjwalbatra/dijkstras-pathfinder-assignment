package pathFinder;

import map.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Vertex {
    private Coordinate coordinate;
    private Set<Edge> outNeighbours;

    public Vertex(Coordinate coordinate) {
        this.outNeighbours = new HashSet<>();
        this.coordinate = coordinate;
    }

    void addOutNeighbour(Edge edge) {
        this.outNeighbours.add(edge);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Set<Edge> getOutNeighbours() {
        return outNeighbours;
    }
}