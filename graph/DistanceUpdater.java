package graph;

/**
 * This class is created as a work-around to enable the min heap to be updatable whilst dequeueing the correct min item
 */

import java.util.Map;
import java.util.PriorityQueue;

public class DistanceUpdater {

    /**
     *
     * @param pathMap Map of all pathsh
     * @param minHeap minHeap of all paths
     * @param path the path to be updated
     * @param newValue the new distance of the path
     */
    public static void updateDistance(Map pathMap, PriorityQueue<Path> minHeap, Path path, int newValue){
        Path newPath = new Path(path.getVertex(),newValue); //new path created with new distance value
        newPath.getPath().addAll(path.getPath()); //the existing path is added to the new path
        path.setValidPath(false); // the existing path in the heap is labelled invalid
        minHeap.add(newPath); // new VALID path added to min heap (see SpecialMinHeap Class)
        //pathMap.remove(path.getVertex().getCoordinate()); //existing path is removed from the Map of paths
        pathMap.put(newPath.getVertex().getCoordinate(),newPath); // old path is replaced with new path in map
    }

}
