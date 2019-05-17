package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DijkstraPathFinder implements PathFinder {

    private Graph graph;

    private Set<Vertex> sources;
    private Set<Vertex> destinations;
    private List<Vertex> wayPoints;
    private List<Coordinate> optimalPath;
    private int coordinatesExplored;

    public DijkstraPathFinder(PathMap map) {

        // make a graph of the given map and initialize the data structures used for Dijkstra's algorithm
        this.graph = new Graph(map);
        this.optimalPath = new ArrayList<>();

        // get source vertex and destinations on the graph
        this.sources = this.graph.getSources();
        this.destinations = this.graph.getDestinations();
        this.wayPoints = this.graph.getWayPoints();
        this.coordinatesExplored = 0;
    } // end of DijkstraPathFinder()


    @Override
    public List<Coordinate> findPath() {
        // You can replace this with your favourite list, but note it must be a
        // list type
        List<Coordinate> path;
        int pathCost = Integer.MAX_VALUE;

        Node srcNode = null;
        Node destNode = null;

        // for every source destination combination
        for (Vertex src : this.sources) {
            for (Vertex dest : this.destinations) {
                this.graph.initialiseFrontier(src);

                srcNode = this.graph.getFrontier().get(0);

                path = new ArrayList<>();

                for (Node node : this.graph.getFrontier()) {
                    if (dest.equals(node.getVertex())) {
                        destNode = node;
                    }
                }

                // trace optimal path
                this.graph.tracePath(srcNode, destNode);

                int newPathCost = destNode.getCost();

                // if path cost is smaller than previous then update optimal path
                if (newPathCost < pathCost) {
                    pathCost = newPathCost;
                    Node currNode = destNode;

                    while (currNode.getCost() != 0) {
                        path.add(currNode.getVertex().getCoordinate());
                        currNode = currNode.getPrevious();
                    }

                    // path.add(currNode.getVertex().getCoordinate()); to add source vertex to the path as well
                    Collections.reverse(path);
                    this.optimalPath = path;
                    this.coordinatesExplored = this.graph.getNodesExplored().size();
                }

            }
        }

        return this.optimalPath;
    } // end of findPath()


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)
        // placeholder
        return this.coordinatesExplored;
    } // end of cellsExplored()


} // end of class DijsktraPathFinder
