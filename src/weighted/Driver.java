package weighted;

/**
 * Created by jinxters on 1/20/16.
 */
public class Driver {
    public static void main(String[] args) {
        Graph graph = new DirectedGraph();

        graph.addEdge("4", "5", 0.35);
        graph.addEdge("5", "4", 0.35);
        graph.addEdge("4", "7", 0.37);
        graph.addEdge("5", "7", 0.28);
        graph.addEdge("7", "5", 0.28);
        graph.addEdge("5", "1", 0.32);
        graph.addEdge("0", "4", 0.38);
        graph.addEdge("0", "2", 0.26);
        graph.addEdge("7", "3", 0.39);
        graph.addEdge("1", "3", 0.29);
        graph.addEdge("2", "7", 0.34);
        graph.addEdge("6", "2", 0.40);
        graph.addEdge("3", "6", 0.52);
        graph.addEdge("6", "0", 0.58);
        graph.addEdge("6", "4", 0.93);

        System.out.println(graph);

//        GraphUtilities.Dijkstra(graph, graph.getVertex("3"));
        GraphUtilities.BelmanFord(graph, graph.getVertex("3"));
    }
}
