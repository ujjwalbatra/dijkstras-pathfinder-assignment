package graph;

import java.util.PriorityQueue;

public class Edge {

    private int cost;
    private Vertex fromVertex;
    private Vertex toVertex;


    public Edge(Vertex fromVertex, Vertex toVertex, int cost){
        this.cost = cost;
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Vertex getFromVertex() {
        return fromVertex;
    }

    public void setFromVertex(Vertex fromVertex) {
        this.fromVertex = fromVertex;
    }

    public Vertex getToVertex() {
        return toVertex;
    }

    public void setToVertex(Vertex toVertex) {
        this.toVertex = toVertex;
    }
}
