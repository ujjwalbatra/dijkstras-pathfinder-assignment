package graph;

/**
 * Ensures valid path is removed from min queue
 */

import java.util.PriorityQueue;

public class SpecialMinHeap extends PriorityQueue<Path> {

    @Override
    public Path remove() {

        Path path;
        do {
            path = super.remove();
        } while (!path.validPath && !this.isEmpty());

        return path;
    }
}