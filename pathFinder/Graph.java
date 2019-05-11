package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;
    private Set<Vertex> sources;
    private Set<Vertex> destinations;

    public Graph(PathMap map) {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.sources = new HashSet<>();
        this.destinations = new HashSet<>();

        this.createVertices(map);
        this.createEdges();
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

                // add vertices containing coordinates to the graph
                Vertex vertex = null;
                if (!coordinate.getImpassable()) {
                    vertex = new Vertex(coordinate);
                    this.vertices.add(vertex);
                }

                // add vetex to the set of sources and destinations if it is an origin or dest cell
                if (map.originCells.contains(coordinate)) {
                    this.sources.add(vertex);
                } else if (map.destCells.contains(coordinate)) {
                    this.destinations.add(vertex);
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

                boolean edgeExist = false;

                if (vertex.getCoordinate().getColumn() == vertex2.getCoordinate().getColumn()
                        && vertex.getCoordinate().getRow() == vertex2.getCoordinate().getRow() + 1) {
                    edgeExist = true;
                }

                if (vertex.getCoordinate().getColumn() == vertex2.getCoordinate().getColumn() + 1
                        && vertex.getCoordinate().getRow() == vertex2.getCoordinate().getRow()) {
                    edgeExist = true;
                }

                // add terrain cost to the edge depending on the target vertex
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
}