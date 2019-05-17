package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;
    private Set<Vertex> sources;
    private Set<Vertex> destinations;
    private List<Vertex> wayPoints;
    private Set<Node> nodesExplored;
    private List<Node> frontier;

    public Graph(PathMap map) {
        this.vertices = new ArrayList<>();

        // all the edges for out neighbours. it also includes terrain cost depending on target vertex
        this.edges = new ArrayList<>();

        this.sources = new HashSet<>();
        this.destinations = new HashSet<>();
        this.wayPoints = new ArrayList<>();


        this.createVertices(map);
        this.createEdges();
        // reset frontier and nodes explored
        this.flashMemory();
    }

    /**
     * Initializes coordinates of the graph if they are not impassable
     *
     * @param map containing 2d array of coordinates to be added to the graph and list of origin and destination
     */
    private void createVertices(PathMap map) {
        Coordinate[][] coordinates = map.cells;

        for (Coordinate[] row : coordinates) {
            for (Coordinate coordinate : row) {

                // add passable vertices containing coordinates to the graph
                Vertex vertex = null;

                if (!coordinate.getImpassable()) {
                    vertex = new Vertex(coordinate);
                    this.vertices.add(vertex);
                } else continue;

                // add vetex to the set of sources, destinations or waypoints if it is an origin or dest cell
                if (map.originCells.contains(coordinate)) {
                    this.sources.add(vertex);
                } else if (map.destCells.contains(coordinate)) {
                    this.destinations.add(vertex);
                } else if (map.waypointCells.contains(coordinate)) {
                    this.wayPoints.add(vertex);
                }
            }
        }
    }

    /**
     * Creates edges with weight 1 and terrain cost of the target for the existing vertices in the graph
     */
    private void createEdges() {
        // for every vertex pair check if they are adjacent, if yes then create an edge
        for (Vertex vertex : this.vertices) {
            for (Vertex vertex2 : this.vertices) {

                if (vertex.equals(vertex2)) continue;

                boolean edgeExist = false;

                //  check if they are adjacent
                int vertR = vertex.getCoordinate().getRow();
                int vertC = vertex.getCoordinate().getColumn();
                int vert2R = vertex2.getCoordinate().getRow();
                int vert2C = vertex2.getCoordinate().getColumn();

                if (vertC == vert2C && vertR + 1 == vert2R) {
                    edgeExist = true;
                }

                if (vertC + 1 == vert2C && vertR == vert2R) {
                    edgeExist = true;
                }

                // if vertices are adjacent then create 2 edges between them for each vertex as source once
                // add terrain cost to the edge depending on the target vertex
                // add edge to the associated vertex as well
                if (edgeExist) {

                    int terrainCost = vertex2.getCoordinate().getTerrainCost();

                    Edge edge = new Edge(vertex, vertex2, 1 + terrainCost);
                    vertex.addEdge(edge);

                    terrainCost = vertex.getCoordinate().getTerrainCost();

                    edge = new Edge(vertex2, vertex, 1 + terrainCost);
                    vertex2.addEdge(edge);
                }
            }
        }
    }

    /**
     * finds shortest path using dijktra's algorithm
     *
     * @param source      of the path
     * @param destination of the path
     */
    public void tracePath(Node source, Node destination) {

        if (source == null || destination == null) return;

        Node currNode;

        while (!this.nodesExplored.contains(destination) && !this.frontier.isEmpty()) {
            currNode = frontier.remove(0);
            updateNeighboursCost(currNode);
            this.nodesExplored.add(currNode);
        }
    }

    /**
     * initialises the frontier with distance of source node as 0 and other nodes as infinity
     *
     * @param src source vertex for a path
     */
    public void initialiseFrontier(Vertex src) {
        for (Vertex vertex : this.getVertices()) {
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

    public Set<Vertex> getSources() {
        return sources;
    }

    public Set<Vertex> getDestinations() {
        return destinations;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Vertex> getWayPoints() {
        return wayPoints;
    }

    public Set<Node> getNodesExplored() {
        return nodesExplored;
    }

    public List<Node> getFrontier() {
        return frontier;
    }
}