package pathFinder;

/**
 * represents an edge with from and to Vertices and the edge weight
 */
public class Edge {
    private Vertex from;
    private Vertex to;
    private int weight; // includes terrain cost

    public Edge(Vertex from, Vertex to, int weight) {
        this.from = from;
        this.to = to;
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