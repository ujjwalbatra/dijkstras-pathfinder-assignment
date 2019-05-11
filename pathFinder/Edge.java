package pathFinder;

public class Edge {
    private Vertex to;
    private Vertex from;
    private int weight;

    public Edge(Vertex to, Vertex from, int weight) {
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