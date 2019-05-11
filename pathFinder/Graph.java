package pathFinder;

import map.Coordinate;
import map.PathMap;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;

    public Graph(PathMap map) {
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();

        this.createVertices(map.cells);
        this.createEdges();
    }

    /**
     * Initializes coordinates of the graph if they are not impassable
     *
     * @param coordinates 2d array of coordinates to be added to the graph
     */
    private void createVertices(Coordinate[][] coordinates) {
        for (Coordinate[] row : coordinates) {
            for (Coordinate coordinate : row) {
                if (!coordinate.getImpassable()) {
                    Vertex vertex = new Vertex(coordinate);
                    vertices.add(vertex);
                }
            }
        }
    }

    /**
     * Creates edges with weight 1 for the existing vertices in the graph
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
}