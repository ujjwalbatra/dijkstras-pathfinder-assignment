package graph;

import map.Coordinate;
import map.PathMap;
import sun.plugin.dom.core.CoreConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<Coordinate, Vertex> vertices;
    private Map<Coordinate,Vertex> originVerticies;
    private Map<Coordinate,Vertex> destVerticies;
    private Map<Coordinate,Vertex> waypointVerticies;


    public Graph(PathMap map) {

        vertices = new HashMap<Coordinate, Vertex>();
        originVerticies = new HashMap<Coordinate, Vertex>();
        destVerticies = new HashMap<Coordinate, Vertex>();
        waypointVerticies = new HashMap<Coordinate, Vertex>();
        Coordinate cells[][] = map.cells;

        // parse graph
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (!cells[i][j].getImpassable()) {
                    Coordinate coord = cells[i][j];
                    Vertex vertex = new Vertex(coord);
                    vertices.put(coord, vertex);
                    try {
                        if ( vertices.get(cells[i][j - 1]) != null) {
                            Vertex leftVertex = vertices.get(cells[i][j - 1]);
                            vertex.addEdge(new Edge(vertex, leftVertex, leftVertex.getCoordinate().getTerrainCost()));
                            leftVertex.addEdge(new Edge(leftVertex, vertex, vertex.getCoordinate().getTerrainCost()));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                    try {
                        if (vertices.get(cells[i - 1][j]) != null) {
                            Vertex upperVertex = vertices.get(cells[i - 1][j]);
                            vertex.addEdge(new Edge(vertex, upperVertex, upperVertex.getCoordinate().getTerrainCost()));
                            upperVertex.addEdge(new Edge(upperVertex, vertex, vertex.getCoordinate().getTerrainCost()));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
            }
        }

        //parse orginVerticies
        for(Coordinate coord : map.originCells){
            originVerticies.put(coord,vertices.get(coord));
        }

        //parse destVerticies
        for(Coordinate coord : map.destCells){
            destVerticies.put(coord,new Vertex(coord));
        }

        //parse waypointVerticies
        for(Coordinate coord : map.waypointCells){
            waypointVerticies.put(coord,vertices.get(coord));
        }

    }

    public Map<Coordinate, Vertex> getVertices() {
        return vertices;
    }

    public Map<Coordinate, Vertex> getOriginVerticies() {
        return originVerticies;
    }

    public Map<Coordinate, Vertex> getDestVerticies() {
        return destVerticies;
    }

    public Map<Coordinate, Vertex> getWaypointVerticies() {
        return waypointVerticies;
    }



}