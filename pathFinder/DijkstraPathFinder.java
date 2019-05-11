package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder {

    private Graph graph;
    private Set<Vertex> coordinatesExplored;
    private Queue<Node> frontier;
    private Set<Vertex> sources;
    private Set<Vertex> destinations;

    public DijkstraPathFinder(PathMap map) {

        // make a graph of the given map and initialize the data structures used for Dijkstra's algorithm
        this.graph = new Graph(map);
        this.coordinatesExplored = new HashSet<>();

        // priority queue in ascending order of cost of reaching the node (edge weight + terrain)
        this.frontier = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getCost() - o2.getCost();
            }
        });

        // get source vertex and destinations on the graph
        this.sources = this.graph.getSources();
        this.destinations = this.graph.getDestinations();

        this.initializeFrontier();
    } // end of DijkstraPathFinder()

    /**
     * Initialize frontier with nodes containing vertices and cost as infinity
     */
    private void initializeFrontier() {
        int infinity = Integer.MAX_VALUE;

        for (Vertex vertex : this.graph.getVertices()) {
            Node newNode = new Node(vertex, null, infinity);
            this.frontier.add(newNode);
        }
    }


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
