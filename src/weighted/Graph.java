package weighted;

import java.util.*;

/**
 * Created by jinxters on 1/20/16.
 */
public abstract class Graph {
    HashMap<String, Vertex> vertices;

    public Graph(){
        vertices = new HashMap<>();
    }

    public void addVertex(String name){
        if(!vertices.containsKey(name)){
            Vertex vertex = new Vertex(name);
            vertices.put(name, vertex);
        }
    }

    public void removeVertex(String name){
        if(!vertices.containsKey(name)){
            vertices.remove(name);
        }
        // TODO: Remove Edges to this vertex
    }

    public boolean adjacent(String name1, String name2){
        if(vertices.containsKey(name1) && vertices.containsKey(name2)){
            Vertex v1 = vertices.get(name1);

            for(Edge e : v1.getEdges()){
                if(e.getTarget().getName().equals(name2)){
                    return true;
                }
            }
        }
        return false;
    }

    public Vertex getVertex(String name){
        if (vertices.containsKey(name)) {
            return vertices.get(name);
        }
        return null;
    }

    public Collection<Vertex> getVertices(){
        return vertices.values();
    }

    abstract public void addEdge(String name1, String name2, double weight);

    @Override
    public String toString(){
        String graph = "";
        for (Vertex n : getVertices()){
            graph += "parent: " + n + "\n";
            for (Edge edge : n.getEdges()){
                graph += "\tchild: " + edge.getTarget() + " with weight " + edge.getWeight() + "\n";
            }
        }
        return graph;
    }
}

class Vertex {
    private final String name;
    private Set<Edge> edges;

    public Vertex(String name){
        this.name = name;
        edges = new HashSet<>();
    }

    public void addEdge(Vertex target, double weight){
        Edge edge = new Edge(target, weight);
        edges.add(edge);
    }

    public Set<Edge> getEdges(){
        return edges;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}


class Edge {
    private final Vertex target;
    private final double weight;

    public Edge(Vertex target, double weight) {
        this.target = target;
        this.weight = weight;
    }

    public Vertex getTarget(){
        return target;
    }

    public double getWeight(){
        return weight;
    }
}
