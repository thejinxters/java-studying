package old;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Graph graph = new UndirectedGraph();
        graph.addNode("bob", 42);
        graph.addNode("greg", 50);
        graph.addNode("gah", 12);
        graph.addNode("ingl", 78);
        graph.addNode("gretzky", 99);
        graph.addNode("albia", 0);
        graph.addNode("slamma", 80);
        graph.addNode("jamma", 583);
        graph.addNode("tamborine", 3);
        graph.addEdge("bob", "greg");
        graph.addEdge("bob", "gretzky");
        graph.addEdge("greg", "gretzky");
        graph.addEdge("bob", "slamma");
        graph.addEdge("gretzky", "jamma");
        graph.addEdge("jamma", "albia");
        graph.addEdge("gah", "tamborine");

//        System.out.println(graph);
//
        System.out.println(GraphUtilities.DFS(graph.getNode("bob"), 0, new ArrayList<>()));
//        System.out.println(orig.GraphUtilities.BFS(graph.getNode("bob"), 53));

        System.out.println(" ");
        Graph.Node node = GraphUtilities.nonRecursiveDFS(graph.getNode("bob"), 0);
        System.out.println(node);
        System.out.println(" ");
//        orig.GraphUtilities.getConnectedComponents(graph);


    }
}
