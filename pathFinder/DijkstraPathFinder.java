package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder {

    private Graph graph;
    private Set<Node> nodesExplored;
    private Queue<Node> frontier;
    private Set<Vertex> sources;
    private Set<Vertex> destinations;
    private Queue<Vertex> wayPoints;
    private List<Coordinate> optimalPath;
    private int coordinatesExplored;

    public DijkstraPathFinder(PathMap map) {

        // make a graph of the given map and initialize the data structures used for Dijkstra's algorithm
        this.graph = new Graph(map);
        this.nodesExplored = new HashSet<>();
        this.optimalPath = new ArrayList<>();

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

        this.coordinatesExplored = 0;
    } // end of DijkstraPathFinder()


    @Override
    public List<Coordinate> findPath() {
        // You can replace this with your favourite list, but note it must be a
        // list type
        List<Coordinate> path = new ArrayList<>();
        int pathCost = 0;

        Node srcNode = null;
        Node destNode = null;

        // for every source destination combination
        for (Vertex src : this.sources) {
            for (Vertex dest : this.destinations) {
                this.flashMomory();

                path = new ArrayList<>();

                for (Node node : this.frontier) {

                    if (src.equals(node.getVertex())) {
                        srcNode = node;
                    }

                    if (dest.equals(node.getVertex())) {
                        destNode = node;
                    }
                }

                tracePath(srcNode, destNode);

                int newPathCost = destNode.getCost();

                if (newPathCost > pathCost) {
                    Node currNode = destNode;

                    while (!currNode.equals(srcNode)) {
                        path.add(currNode.getVertex().getCoordinate());
                        this.nodesExplored.remove(currNode);
                        currNode = findExploredNodeForVertex(currNode.getPrevious());
                    }
                    this.optimalPath = path;
                    this.coordinatesExplored = nodesExplored.size();
                }

            }
        }
        Collections.reverse(this.optimalPath);

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

    private void tracePath(Node source, Node destination) {

        if (source == null || destination == null) return;

        Vertex prevVertex = null;

        // add source node to the explored nodes with cost 0
        for (Node currNode : this.frontier) {

            if (currNode.equals(source)) {
                this.frontier.remove(currNode);

                currNode.setCost(0);
                currNode.setPrevious(prevVertex);
                this.nodesExplored.add(currNode);
                updateNeighboursCost(currNode);
                prevVertex = currNode.getVertex();
                break;
            }
        }

        Node currNode;

        while (!this.nodesExplored.contains(destination)) {
            currNode = frontier.poll();
            currNode.setPrevious(prevVertex);
            prevVertex = currNode.getVertex();
            updateNeighboursCost(currNode);
            this.nodesExplored.add(currNode);
        }
    }


    private void updateNeighboursCost(Node node) {

        for (Edge edge : node.getVertex().getEdges()) {
            for (Node unexploredNode : this.frontier) {

                Vertex neighbour = edge.getTo();
                if (unexploredNode.getVertex().equals(neighbour)) {
                    int predecessorCost = node.getCost();

                    // edge weight includes terrain cost
                    int costOfReachingNeighbour = edge.getWeight();
                    unexploredNode.setCost(predecessorCost + costOfReachingNeighbour);
                }
            }
        }
    }

    private void flashMomory() {
        this.nodesExplored = new HashSet<>();
        this.frontier = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.getCost() - o2.getCost();
            }
        });

    }


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)

        // placeholder
        return this.coordinatesExplored;
    } // end of cellsExplored()


} // end of class DijsktraPathFinder
