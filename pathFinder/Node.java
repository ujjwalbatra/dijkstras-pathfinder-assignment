package pathFinder;

/**
 * represents a node for implementation of Dijkstra's frontier
 */
public class Node {
    private Vertex vertex;
    private Node previous;
    private int cost;

    public Node(Vertex vertex) {
        this.vertex = vertex;
        this.previous = null;
        this.cost = Integer.MAX_VALUE;
    }

    public Node(Vertex vertex, int cost) {
        this.vertex = vertex;
        this.previous = null;
        this.cost = cost;
    }

    public Node(Vertex vertex, Node previous, int cost) {
        this.vertex = vertex;
        this.previous = previous;
        this.cost = cost;
    }


    public Vertex getVertex() {
        return this.vertex;
    }

    public Node getPrevious() {
        return this.previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
