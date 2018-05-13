import javafx.scene.control.TextArea;
import java.util.ArrayList;

public class AlgoByCoords implements ISearch {

    Graph graph;
    private TextArea textArea;

    /**
     * Constructor
     * @param graph
     * @param textArea
     */
    public AlgoByCoords(Graph graph, TextArea textArea) {
        this.graph = graph;
        this.textArea = textArea;
    }

    /**
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public ArrayList<String> hasPath(String start, String end) {
        if (!graph.containsNode(start) || !graph.containsNode(start)) {
            return null;
        }

        graph.resetAll();

        ArrayList<Node> queue = new ArrayList<>();
        Node temp;
        queue.add(graph.getNode(start));

        while (!queue.isEmpty()) {
            temp = queue.get(0);
            GraphUtil.printNodeInfo(temp, textArea);
            queue.remove(0);
            temp.tested = true;
            if (temp.name.equals(end)) {
                GraphUtil.printKMandPrice(graph, end, textArea);
                ArrayList<String> path = new ArrayList<>();
                GraphUtil.printPath(graph.getNode(start), graph.getNode(end), path);
                return path;
            }
            if (!temp.expanded) {
                for (Link link : temp.listLinks) {
                    Node rNode = link.relatedNode;
                    if (!link.relatedNode.tested && !queue.contains(rNode)) {
                        GraphUtil.calcDistance(rNode, graph.getNode(end));
                        GraphUtil.setParentByPrice(temp, link);
                        GraphUtil.sortQueueByDistance(queue, rNode);
                        rNode.parent = temp;
                    }
                }
                temp.expanded = true;
            }

        } // end while
        return null;
    }// end hasPath

}
