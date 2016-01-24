package old;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jinxters on 1/16/16.
 */
public class Graph {

    private int numberOfVertices;
    protected int numberOfEdges;
    private HashMap<String, Node> map;

    public Graph(){
        numberOfVertices = 0;
        numberOfEdges = 0;
        map = new HashMap<>();
    }

    public boolean addNode(String name, int data){
        if(map.containsKey(name)){
            return false;
        }
        Node node = new Node(name,data);
        map.put(name, node);
        numberOfVertices++;
        return true;
    }

    public void removeNode(String name){
        if(map.containsKey(name)){
            map.remove(name);
            for (Node n : map.values()){
                n.removeEdge(name);
            }
        }
    }

    public boolean addEdge(String name1, String name2){
        if(map.containsKey(name1) && map.containsKey(name2)){
            return addEdge(map.get(name1), map.get(name2));
        }
        return false;
    }

    public boolean addEdge(Node node1, Node node2){
        boolean e1 = node1.addEdge(node2);
        boolean e2 = node2.addEdge(node1);
        if(e1 && e2){
            numberOfEdges++;
            return true;
        }
        else{
            return false;
        }
    }

    public Node getNode(String name){
        if(map.containsKey(name)){
            return map.get(name);
        }
        return null;
    }

    public int getNumberOfVertices(){
        return numberOfVertices;
    }

    public int getNumberOfEdges(){
        return numberOfEdges;
    }

    public ArrayList<Node> getNodes(){
        return new ArrayList<Node>(map.values());
    }

    public String toString(){
        String graph = "";
        for (Graph.Node n : getNodes()){
            graph += "parent: " + n + "\n";
            for (Graph.Node child : n.getConnections()){
                graph += "\tchild: " + child + "\n";
            }
        }
        return graph;
    }


    protected class Node {
        private HashMap<String, Node> map;
        private String name;
        private int data;

        public Node(String name, int data){
            map = new HashMap<>();
            this.name = name;
            this.data = data;
        }

        public boolean addEdge(Node node){
            if(map.containsKey(node.getName())){
                return false;
            }
            map.put(node.getName(), node);
            return true;
        }

        public void removeEdge(String name){
            if(map.containsKey(name)){
                map.remove(name);
            }
        }

        public String getName(){
            return name;
        }

        public int getData(){
            return data;
        }

        public ArrayList<Node> getConnections(){
            return new ArrayList<Node>(map.values());
        }

        public String toString() {
            return "<Node name: " + this.getName() + ", data: " + this.getData() +">";
        }
    }
}


class UndirectedGraph extends Graph{

    @Override
    public boolean addEdge(Node node1, Node node2){
        boolean e1 = node1.addEdge(node2);
        if(e1){
            numberOfEdges++;
            return true;
        }
        else{
            return false;
        }
    }

}
