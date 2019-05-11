package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder {
    // TODO: You might need to implement some attributes

    public DijkstraPathFinder(PathMap map) {
        // TODO :Implement
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

                    if (vertex.coordinate.getColumn() == vertex.coordinate.getColumn()
                            && vertex.coordinate.getRow() == vertex2.coordinate.getRow() + 1) {
                        edgeExist = true;
                    }

                    if (vertex.coordinate.getColumn() == vertex.coordinate.getColumn() + 1
                            && vertex.coordinate.getRow() == vertex2.coordinate.getRow()) {
                        edgeExist = true;
                    }

                    if (edgeExist) {
                        Edge edge = new Edge(vertex, vertex2, 1);
                        vertex.addEdge(edge);

                        edge = new Edge(vertex2, vertex, 1);
                        vertex2.addEdge(edge);
                    }
                }
            }
        }
    }

    public class Vertex {
        private Coordinate coordinate;
        private Set<Edge> edges;

        Vertex(Coordinate coordinate) {
            this.edges = new HashSet<>();
            this.coordinate = coordinate;
        }

        void addEdge(Edge edge) {
            this.edges.add(edge);
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public Set<Edge> getEdges() {
            return edges;
        }
    }

    public class Edge {
        private Vertex to;
        private Vertex from;
        private int weight;

        Edge(Vertex to, Vertex from, int weight) {
            this.to = to;
            this.from = from;
            this.weight = weight;
        }

        public Vertex getTo() {
            return to;
        }

        public Vertex getFrom() {
            return from;
        }

        public int getWeight() {
            return weight;
        }
    }


} // end of class DijsktraPathFinder
