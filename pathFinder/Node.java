package pathFinder;

public class Node {
    private Vertex vertex;
    private Vertex previous;
    private int weight;

    public Node(Vertex vertex, Vertex previous, int weight) {
        this.vertex = vertex;
        this.previous = previous;
        this.weight = weight;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
