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
    private List<List<Vertex>> allPossiblePaths;

    public DijkstraPathFinder(PathMap map) {

        // make a graph of the given map and initialize the data structures used for Dijkstra's algorithm
        this.graph = new Graph(map);
        this.optimalPath = new ArrayList<>();

        // get source vertex and destinations on the graph
        this.sources = this.graph.getSources();
        this.destinations = this.graph.getDestinations();
        this.wayPoints = this.graph.getWayPoints();
        this.coordinatesExplored = 0;

        this.allPossiblePaths = this.getPossiblePaths();
    } // end of DijkstraPathFinder()


    @Override
    public List<Coordinate> findPath() {
        // You can replace this with your favourite list, but note it must be a
        // list type
        int optimalPathCost = Integer.MAX_VALUE;

        // for all paths find the path cost
        for (List<Vertex> path : this.allPossiblePaths) {
            int initialCost = 0;
            Node srcNode = null;
            Node destNode = null;
            List<Coordinate> currentPath = new ArrayList<>();
            int currentPathNodesExplored = 0;


            // for a path find the distance by traversing all vertices of that path
            for (int j = 1; j < path.size(); j++) {
                Node previousNode;

                previousNode = destNode;

                this.graph.initialiseFrontier(path.get(j - 1), previousNode, initialCost);
                srcNode = this.graph.getFrontier().get(0);

                // find destination node in the frontier
                Vertex dest = path.get(j);
                for (Node node : this.graph.getFrontier()) {

                    if (dest.equals(node.getVertex())) {
                        destNode = node;
                    }
                }

                this.graph.tracePath(srcNode, destNode);
                currentPath.addAll(this.buildCurrentPath(initialCost, destNode));

                currentPathNodesExplored += this.graph.getNodesExplored().size();

                initialCost = destNode.getCost();
            }

            currentPath.add(destNode.getVertex().getCoordinate());
            int currentPathCost = destNode.getCost();

            if (optimalPathCost > currentPathCost) {
                this.coordinatesExplored = currentPathNodesExplored;
                this.optimalPath = currentPath;
                optimalPathCost = currentPathCost;
            }

        }


        return this.optimalPath;
    } // end of findPath()

    /**
     * @return path found by Dijkstra's to the destination
     */
    private List<Coordinate> buildCurrentPath(int initialCost, Node destNode) {
        List<Coordinate> path = new ArrayList<>();
        Node currNode = destNode.getPrevious();

        while (currNode.getCost() != initialCost) {
            path.add(currNode.getVertex().getCoordinate());
            currNode = currNode.getPrevious();
        }

        // add the source node
        path.add(currNode.getVertex().getCoordinate());

        Collections.reverse(path);

        return path;
    }

    /**
     * generates all permutations for the patters source -> all way points -> destination
     *
     * @return all possible paths from multiple sources to multiple destinations including waypoints
     */
    private List<List<Vertex>> getPossiblePaths() {
        List<List<Vertex>> allPermutaions = new ArrayList<>(); //includes src and dest
        List<List<Vertex>> allWaypointPermutation; //does't include src and dest

        allWaypointPermutation = this.getWaypointsPermutations(this.wayPoints);

        if (allWaypointPermutation.size() != 0) {

            // add all sources and destinations to waypoint permutations permutations
            for (List<Vertex> permutation : allWaypointPermutation) {
                this.sources.forEach(src -> this.destinations.forEach(dest -> {
                    ArrayList<Vertex> atomicPermutation = new ArrayList<>(permutation);
                    atomicPermutation.add(0, src);
                    atomicPermutation.add(dest);
                    allPermutaions.add(atomicPermutation);
                }));
            }

        } else {
            this.sources.forEach(src -> this.destinations.forEach(dest -> {
                ArrayList<Vertex> atomicPermutation = new ArrayList<>();
                atomicPermutation.add(0, src);
                atomicPermutation.add(dest);
                allPermutaions.add(atomicPermutation);
            }));
        }

        return allPermutaions;
    }

    /**
     * generate all permutations for a given list
     *
     * @param waypoints the list to be permuted
     *
     * @return all permutations of the given list
     */
    private List<List<Vertex>> getWaypointsPermutations(List<Vertex> waypoints) {

        if (waypoints.size() == 0) {
            return new ArrayList<>(new ArrayList<>());
        }

        List<List<Vertex>> allPermutaions = new ArrayList<>(Permutations.of(waypoints));


        return allPermutaions;
    }


    @Override
    public int coordinatesExplored() {
        // TODO: Implement (optional)
        // placeholder
        return this.coordinatesExplored;
    } // end of cellsExplored()


} // end of class DijsktraPathFinder
