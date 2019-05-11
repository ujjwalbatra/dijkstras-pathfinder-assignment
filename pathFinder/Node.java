package pathFinder;

public class Node {
    private Vertex vertex;
    private Vertex previous;
    private int cost;

    public Node(Vertex vertex, Vertex previous, int cost) {
        this.vertex = vertex;
        this.previous = previous;
        this.cost = cost;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
