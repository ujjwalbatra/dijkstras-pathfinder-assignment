package graph;

import map.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Path implements Comparable<Path> {

    private Vertex vertex;
    private int distance;
    boolean validPath;
    List<Coordinate> path;

    public Path(Vertex vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
        this.validPath = true;
        this.path = new ArrayList<Coordinate>();
    }

    public Vertex getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isValidPath() {
        return validPath;
    }

    public void setValidPath(boolean validPath) {
        this.validPath = validPath;
    }

    public List<Coordinate> getPath() {
        return path;
    }

    public boolean equals(Path o){
    return this.getDistance() == o.getDistance();
    }

    @Override
    public int compareTo(Path o) {
        if(this.equals(o))
            return 0;
        else if(getDistance() > o.getDistance())
            return 1;
        else
            return -1;
    }
}
