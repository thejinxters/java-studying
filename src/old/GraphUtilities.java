package old;

import java.util.*;

/**
 * Created by jinxters on 1/19/16.
 */
public class GraphUtilities {


    /**
     * Finds a node using a Depth First Search
     *
     * @param start starting node
     * @param value value we are searching for
     * @param visited a list to track visited nodes
     * @return node containing the value or null
     */
    public static Graph.Node DFS(Graph.Node start, int value, ArrayList<Graph.Node> visited){
        if (start.getData() == value){
            return start;
        }
        visited.add(start);
        for (Graph.Node node : start.getConnections()){
            if(!visited.contains(node)) {
                Graph.Node found = DFS(node, value, visited);
                if (found != null){
                    return found;
                }
            }
        }
        return null;
    }

    public static Graph.Node nonRecursiveDFS(Graph.Node start, int value){
        Stack<Graph.Node> stack = new Stack<>();
        Set<Graph.Node> visited = new HashSet<>();
        stack.push(start);
        visited.add(start);
        while(!stack.isEmpty()){
            Graph.Node current = stack.pop();
            if(current.getData() == value){ return current; }

            for(Graph.Node child : current.getConnections()){
                if(!visited.contains(child)){
                    visited.add(child);
                    stack.push(child);
                }
            }
        }
        return null;
    }


    /**
     * Finds a node using a Breadth First Search
     *
     * @param start starting node
     * @param value value we are searching for
     * @return node containing the value or null
     */
    public static Graph.Node BFS(Graph.Node start, int value){
        Queue<Graph.Node> queue = new LinkedList<>();
        ArrayList<Graph.Node> visited = new ArrayList<>();
        queue.add(start);
        visited.add(start);
        while(!queue.isEmpty()){
            Graph.Node current = queue.remove();
            if(current.getData() == value){ return current; }
            for(Graph.Node node : current.getConnections()){
                if(!visited.contains(node)){
                    visited.add(node);
                    queue.add(node);
                }
            }
        }
        return null;
    }


    /**
     * Prints all connected Components within the orig.Graph
     *
     * @param graph
     */
    public static void getConnectedComponents(Graph graph){
        Set<String> visited = new HashSet<>();
        ArrayList<Graph.Node> allNodes = graph.getNodes();

        for(Graph.Node node : allNodes){
            if(visited.contains(node.getName())){
                continue;
            }
            visited.add(node.getName());

            ArrayList<Graph.Node> connected = new ArrayList<>();
            Queue<Graph.Node> queue = new LinkedList<>();
            queue.add(node);
            connected.add(node);
            while(!queue.isEmpty()){
                Graph.Node current = queue.remove();
                for (Graph.Node child: current.getConnections()){
                    if(!visited.contains(child.getName())){
                        visited.add(child.getName());
                        connected.add(child);
                        queue.add(child);
                    }
                }
            }
            String connectedNodes = "";
            for (Graph.Node connectedNode: connected ){
                connectedNodes += " " + connectedNode;
            }
            System.out.println(connectedNodes);
        }
    }


}
