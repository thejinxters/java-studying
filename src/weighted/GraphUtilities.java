package weighted;

import java.util.*;

/**
 * Created by jinxters on 1/20/16.
 */
public class GraphUtilities {

    public static Vertex DFS(Vertex source, String dest){
        HashMap<String, Vertex> visited = new HashMap<>();
        Stack<Vertex> stack = new Stack<>();

        visited.put(source.getName(), source);
        stack.push(source);

        while(!stack.isEmpty()){
            Vertex current = stack.pop();

            for (Edge e : current.getEdges()){
                Vertex vertex = e.getTarget();

                if(vertex.getName().equals(dest)){
                    return vertex;
                }

                if(!visited.containsKey(vertex.getName())) {
                    visited.put(vertex.getName(), vertex);
                    stack.push(vertex);
                }
            }
        }

        return null;
    }

    public static Vertex BFS(Vertex source, String dest){
        HashSet<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();

        visited.add(source);
        queue.add(source);

        while(!queue.isEmpty()){
            Vertex current = queue.remove();
            for( Edge e : current.getEdges()){
                Vertex child = e.getTarget();

                if (child.getName().equals(dest)){
                    return child;
                }

                if (!visited.contains(child)){
                    visited.add(child);
                    queue.add(child);
                }
            }
        }

        return null;


    }

    /**
     * Find the shortest path
     * @param graph
     * @param source
     */
    public static void Dijkstra(Graph graph, Vertex source){
        Collection<Vertex> vertices = graph.getVertices();
        int numberOfVertices = vertices.size();
        PriorityQueue<DHelper> queue = new PriorityQueue<>(numberOfVertices, new DHelper());
        HashMap<String, DHelper> map = new HashMap<>();

        // Initialize queue with all of the vertices
        for (Vertex v: vertices){
            DHelper vertex = new DHelper(v);
            if (v.getName().equals(source.getName())){
                vertex.setCost(0);
            }
            queue.add(vertex);
            map.put(vertex.getVertex().getName(), vertex);
        }

        while (!queue.isEmpty()){
            DHelper current = queue.remove();
            double dist = current.getCost();
            for (Edge e : current.getVertex().getEdges()){
                String name = e.getTarget().getName();
                double weight = e.getWeight();
                DHelper vertex = map.get(name);
                double new_dist = weight + dist;
                if (new_dist < vertex.getCost()) {
                    queue.remove(vertex);
                    vertex.setCost(new_dist);
                    queue.add(vertex);

                }
            }
        }

        for (DHelper v : map.values()){
            System.out.println(source + " to " + v.getVertex() + " with cost " + v.getCost());
        }
    }



    public static void BelmanFord(Graph graph, Vertex source){
        Collection<Vertex> vertices = graph.getVertices();
        int n = vertices.size();
        HashMap<String, BFHelper> distances = new HashMap<>();

        // Build Hashmap form vertices
        for (Vertex v : vertices){
            BFHelper helper = new BFHelper(v);
            if(v.getName().equals(source.getName())){
                helper.setDistance(0);
            }
            distances.put(v.getName(), helper);
        }

        // Do the calculations
        for(int i = 0; i<n-1; i++){
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
            System.out.print("Path: "+current.getVertex());
            while(cursor.getPrev() != null){
                System.out.print(" " + cursor.getPrev().getVertex());
                cursor = cursor.getPrev();
            }
            System.out.println("");
        }

    }


    /**
     * Tarjan Algorithm for detecting graph cycles
     * @param graph
     */
    public static void Tarjan(Graph graph){
        Collection<Vertex> vertices = graph.getVertices();
        HashMap<String, Integer> highlinks = new HashMap<>();
        HashMap<String, Integer> lowlinks = new HashMap<>();

        int index = 0;
        Stack<Vertex> stack = new Stack<>();

        for (Vertex v: vertices){
            if (!highlinks.containsKey(v.getName())){
                strongConnect(v, index, highlinks, lowlinks, stack);
            }
        }
    }

    private static void strongConnect(Vertex v, int index, HashMap<String, Integer> highlinks, HashMap<String, Integer> lowlinks, Stack<Vertex> stack) {
        // Check if strongly connected
        highlinks.put(v.getName(), index);
        lowlinks.put(v.getName(), index);
        index++;
        stack.push(v);

        for (Edge e: v.getEdges()){
            if(!highlinks.containsKey(e.getTarget().getName())){
                //successor has not been visited!
                strongConnect(e.getTarget(), index, highlinks, lowlinks, stack);
                int minimum = Math.min(lowlinks.get(v.getName()), lowlinks.get(e.getTarget().getName()));
                lowlinks.put(v.getName(),minimum);
            }
            else if (stack.contains(e.getTarget())){
                int minimum = Math.min(lowlinks.get(v.getName()), highlinks.get(e.getTarget().getName()));
                lowlinks.put(v.getName(), minimum);
            }
        }

        if (lowlinks.get(v.getName()).equals(highlinks.get(v.getName()))){
            ArrayList<Vertex> stronglyConnected = new ArrayList<>();
            Vertex w;
            do {
                w = stack.pop();
                stronglyConnected.add(w);
            } while (!w.getName().equals(v.getName()));
            System.out.println("Strongly connected component: ");
            for (Vertex vertex : stronglyConnected){
                System.out.print(vertex+" ");
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

class DHelper implements Comparator<DHelper>{
    private Vertex vertex;
    private double cost;

    public DHelper() {
        this.vertex = null;
        cost = Double.POSITIVE_INFINITY;
    }


    public DHelper(Vertex vertex) {
        this.vertex = vertex;
        cost = Double.POSITIVE_INFINITY;
    }

    public void setCost(double cost){
        this.cost = cost;
    }

    public double getCost(){
        return cost;
    }

    public Vertex getVertex(){
        return vertex;
    }

    @Override
    public int compare(DHelper o1, DHelper o2) {
        if (o1.cost < o2.cost) return -1;
        if (o1.cost > o2.cost) return 1;
        return 0;
    }
}