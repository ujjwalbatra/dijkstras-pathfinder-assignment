package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder {

    private Graph graph;
    private Set<Vertex> coordinatesExplored;
    private Queue<Node> frontier;

    public DijkstraPathFinder(PathMap map) {

        // make a graph of the given map
        this.graph = new Graph(map);
        this.coordinatesExplored = new HashSet<>();

        this.frontier = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getWeight() - o2.getWeight();
            }
        });



    } // end of DijkstraPathFinder()


    @Override
    public List<Coordinate> findPath() {
        // You can replace this with your favourite list, but note it must be a
        // list type
        List<Coordinate> path = new ArrayList<>();

        // TODO: Implement

        return path;
    } // end of findPath()


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)

        // placeholder
        return 0;
    } // end of cellsExplored()


} // end of class DijsktraPathFinder
