package weighted;

/**
 * Created by jinxters on 1/20/16.
 */
public class DirectedGraph extends Graph {
    @Override
    public void addEdge(String name1, String name2, double weight) {
        if(!vertices.containsKey(name1)){
            addVertex(name1);
        }
        if(!vertices.containsKey(name2)){
            addVertex(name2);
        }
        Vertex v1 = vertices.get(name1);
        Vertex v2 = vertices.get(name2);
        v1.addEdge(v2, weight);
    }
}
