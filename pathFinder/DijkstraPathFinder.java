package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder {

    private Graph graph;
    private Set<Node> nodesExplored;
    private List<Node> frontier;
    private Set<Vertex> sources;
    private Set<Vertex> destinations;
    private List<Vertex> wayPoints;
    private List<Coordinate> optimalPath;
    private int coordinatesExplored;

    public DijkstraPathFinder(PathMap map) {

        // make a graph of the given map and initialize the data structures used for Dijkstra's algorithm
        this.graph = new Graph(map);
        this.optimalPath = new ArrayList<>();

        // reset frontier and nodes explored
        this.flashMemory();

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
                this.flashMemory();
                this.initialiseFrontier(src);

                srcNode = frontier.get(0);

                path = new ArrayList<>();

                for (Node node : this.frontier) {
                    if (dest.equals(node.getVertex())) {
                        destNode = node;
                    }
                }

                this.tracePath(srcNode, destNode);

                int newPathCost = destNode.getCost();

                if (newPathCost < pathCost) {
                    pathCost = newPathCost;

                    Node currNode = destNode;
                    while (currNode.getCost() != 0) {
                        path.add(currNode.getVertex().getCoordinate());
                        currNode = currNode.getPrevious();
                    }
//                    path.add(currNode.getVertex().getCoordinate()); to add source vertex to the path as well
                    Collections.reverse(path);
                    this.optimalPath = path;
                }

            }
        }

        return this.optimalPath;
    } // end of findPath()

    private Node findExploredNodeForVertex(Vertex vertex) {
        for (Node node : this.nodesExplored) {
            if (vertex.equals(node.getVertex())) {
                return node;
            }
        }

        return null;
    }

    /**
     * finds shortest path using dijktra's algorithm
     *
     * @param source      of the path
     * @param destination of the path
     */
    private void tracePath(Node source, Node destination) {

        if (source == null || destination == null) return;

        Node currNode;

        while (!this.nodesExplored.contains(destination) && !this.frontier.isEmpty()) {
            currNode = frontier.remove(0);
            updateNeighboursCost(currNode);
            this.nodesExplored.add(currNode);
        }
    }


    /**
     * update costs of the neighbour if it is lesser than current estimate
     *
     * @param node the node whose neighbours are to be updated
     */
    private void updateNeighboursCost(Node node) {

        for (Edge edge : node.getVertex().getEdges()) {
            for (Node unexploredNode : this.frontier) {

                Vertex neighbour = edge.getTo();
                if (unexploredNode.getVertex().equals(neighbour)) {
                    int predecessorCost = node.getCost();

                    // edge weight includes terrain cost
                    int costOfReachingNeighbour = edge.getWeight() + predecessorCost;

                    // check if cost has reduced
                    if (unexploredNode.getCost() > costOfReachingNeighbour) {
                        unexploredNode.setCost(costOfReachingNeighbour);
                        unexploredNode.setPrevious(node);
                    }
                }
            }
        }

        this.sortFrontier();
    }

    private void flashMemory() {
        this.nodesExplored = new HashSet<>();
        this.frontier = new ArrayList<>();
    }

    /**
     * initialises the frontier with distance of source node as 0 and other nodes as infinity
     *
     * @param src source vertex for a path
     */
    private void initialiseFrontier(Vertex src) {
        for (Vertex vertex : this.graph.getVertices()) {
            Node newNode;

            // if node is a source then add node to frontier with distance 0.
            // else add node with distance infinity (Integer.MAX_VALUE)
            if (vertex == src) {
                newNode = new Node(vertex, 0);
                this.frontier.add(newNode);
            } else {
                newNode = new Node(vertex);
                this.frontier.add(newNode);
            }
        }
        this.sortFrontier();
    }

    /**
     * this is to rearrange elements in order of priorities.
     */
    private void sortFrontier() {
        this.frontier.sort(Comparator.comparingInt(Node::getCost));
    }


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)

        // placeholder
        return this.coordinatesExplored;
    } // end of cellsExplored()


} // end of class DijsktraPathFinder
