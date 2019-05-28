package pathFinder;

import graph.*;
import map.Coordinate;
import map.PathMap;

import java.util.*;

public class DijkstraPathFinder implements PathFinder {

    private Map<Coordinate, Vertex> vertices;
    private Map<Coordinate, Vertex> originVerticies;
    private Map<Coordinate, Vertex> destVerticies;
    private Map<Coordinate, Vertex> waypointVerticies;
    private PriorityQueue<Path> shortestPaths;

    public DijkstraPathFinder(PathMap map) {
        Graph graph = new Graph(map);
        vertices = graph.getVertices();
        originVerticies = graph.getOriginVerticies();
        destVerticies = graph.getDestVerticies();
        waypointVerticies = graph.getWaypointVerticies();
        shortestPaths = new PriorityQueue<Path>();


    } // end of DijkstraPathFinder()

    @Override
    public List<Coordinate> findPath() {
        List<Coordinate> shortestPath = new ArrayList<Coordinate>();

        for (Vertex origin : originVerticies.values()) {
            Map<Coordinate, Vertex> visitedVerticies = new HashMap<Coordinate, Vertex>(); //Map storing visited verticies
            Map<Coordinate, Path> paths = new HashMap<Coordinate, Path>(); //Map storing paths from origin
            PriorityQueue<Path> pathsMinHeap = new SpecialMinHeap(); //Min Heap used for selecting lowest distance

            //add each vertex from the graph to the path map and the min heap map
            for (Vertex vertex : vertices.values()) {
                Path path = new Path(vertex, Integer.MAX_VALUE);
                paths.put(vertex.getCoordinate(), path);
                pathsMinHeap.add(path);
            }

            //set distance from origin to itself to 0
            DistanceUpdater.updateDistance(paths, pathsMinHeap, paths.get(origin.getCoordinate()), 0);

            //iterate through list until min heap is empty
            while (!pathsMinHeap.isEmpty()) {
                Path minPath = pathsMinHeap.remove();
                Vertex minPathVertex = minPath.getVertex();
                visitedVerticies.put(minPathVertex.getCoordinate(), minPathVertex);

                //iterate through adjacent edges of current vertex and update distances
                HashSet<Edge> edges = minPathVertex.getEdgeSet();
                for (Edge edge : edges) {
                    Coordinate coord = edge.getToVertex().getCoordinate();
                    if (!visitedVerticies.containsKey(coord)) {
                        int newDist = minPath.getDistance() + edge.getCost();
                        int currentDist = paths.get(coord).getDistance();
                        //update distance from the origin to the current toVertex on the edge
                        if (currentDist > newDist) {
                            DistanceUpdater.updateDistance(paths, pathsMinHeap, paths.get(coord), newDist);
                            paths.get(coord).getPath().addAll(minPath.getPath());
                            paths.get(coord).getPath().add(coord);
                        }
                    }
                }

            }
            for(Vertex vertex : destVerticies.values()){
                shortestPaths.add(paths.get(vertex.getCoordinate()));
            }
        }
        shortestPath = shortestPaths.remove().getPath();
        return shortestPath;
    } // end of findPath()


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)

        // placeholder
        return 0;
    } // end of cellsExplored()

} // end of class DijsktraPathFinder
