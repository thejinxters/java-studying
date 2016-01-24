package weighted;

import java.util.*;

/**
 * Created by jinxters on 1/20/16.
 */
public class GraphUtilities {

    /**
     * Creates Minimum spanning Tree for undirected graph
     * @param graph
     */
    public static void Prims(Graph graph){

    }

    /**
     * Creates Minimum spanning Tree
     * @param graph
     */
    public static void Kruskals(Graph graph, Vertex source){
//        Collection<Vertex> vertices = graph.getVertices();
//        Comparator<Edge> comparator = new EdgeWeightComparator<>();
//        Queue<Edge> edges = new PriorityQueue<>(100, comparator);
//        for (Vertex v : vertices){
//            for (Edge e: v.getEdges()){
//                edges.add(e);
//            }
//        }
//        Graph tree = new Graph();
//        while (!edges.isEmpty()){
//            Edge edge = edges.remove();
//
//        }
        // THIS NO WORK... because edges do not keep track of their source vertex and queue might add same node many times


    }

    /**
     * Find the shortest path
     * @param graph
     * @param source
     */
    public static void Dijkstra(Graph graph, Vertex source){
        Collection<Vertex> vertices = graph.getVertices();
        int numberOfVertices = vertices.size();
        PriorityQueue<VertexCostComparator> queue = new PriorityQueue<>(numberOfVertices, new VertexCostComparator());
        HashMap<String, VertexCostComparator> map = new HashMap<>();
        HashMap<String, Vertex> origMap = new HashMap<>();
        // Initialize queue with all of the vertices
        for (Vertex v: vertices){
            VertexCostComparator vertex = new VertexCostComparator(v.getName());
            if (v.getName().equals(source.getName())){
                vertex.setCost(0);
            }
            queue.add(vertex);
            map.put(vertex.getName(), vertex);
            origMap.put(v.getName(), v);
        }

        while (!queue.isEmpty()){
            VertexCostComparator current = queue.remove();
            double dist = current.getCost();
            for (Edge e : origMap.get(current.getName()).getEdges()){
                String name = e.getTarget().getName();
                double weight = e.getWeight();
                VertexCostComparator vertex = map.get(name);
                double new_dist = weight + dist;
                if (new_dist < vertex.getCost()) {
                    queue.remove(vertex);
                    vertex.setCost(new_dist);
                    queue.add(vertex);

                }
            }
        }

        for (VertexCostComparator v : map.values()){
            System.out.println(source + " to " + v + " with cost " + v.getCost());
        }
    }



    public static void BelmanFord(Graph graph, Vertex source){
        Collection<Vertex> vertices = graph.getVertices();
        int n = vertices.size();
        HashMap<String, BFHelper> distances = new HashMap<>();
        int i = 1;

        // Build Hashmap form vertices
        for (Vertex v : vertices){
            BFHelper helper = new BFHelper(v);
            if(v.getName().equals(source.getName())){
                helper.setDistance(0);
            }
            distances.put(v.getName(), helper);
        }

        // Do the calculations
        for(i = 0; i<n-1; i++){
            for(BFHelper current: distances.values()){
                for(Edge e : current.getVertex().getEdges()){
                    double cur = current.getDistance();
                    BFHelper v = distances.get(e.getTarget().getName());
                    double old = v.getDistance();
                    if (cur + e.getWeight() < old){
                        v.setDistance(cur + e.getWeight());
                        v.setPrev(current);
                    }
                }
            }
        }

        // Check for negative cycles
        for(BFHelper current: distances.values()){
            for(Edge e : current.getVertex().getEdges()){
                double cur = current.getDistance();
                BFHelper v = distances.get(e.getTarget().getName());
                double old = v.getDistance();
                if (cur + e.getWeight() < old){
                    System.out.println("NEGATIVE CYCLE DETECTED");
                    return;
                }
            }
        }

        for(BFHelper current: distances.values()){
            System.out.println("Vertex "+current.getVertex()+" with distance "+current.getDistance());
            BFHelper cursor = current;
            System.out.print(current.getVertex());
            while(cursor.getPrev() != null){
                System.out.print(" " + cursor.getPrev().getVertex());
                cursor = cursor.getPrev();
            }
            System.out.println("");
        }

    }

}

class BFHelper {
    private Vertex vertex;
    private double distance;
    private BFHelper prev;

    public BFHelper(Vertex vertex){
        this.vertex = vertex;
        distance = Double.POSITIVE_INFINITY;
        prev = null;
    }
    public void setDistance(double distance){
        this.distance = distance;
    }

    public double getDistance(){
        return distance;
    }

    public Vertex getVertex(){
        return vertex;
    }

    public void setPrev(BFHelper prev){
        this.prev = prev;
    }
    public BFHelper getPrev(){
        return prev;
    }
}