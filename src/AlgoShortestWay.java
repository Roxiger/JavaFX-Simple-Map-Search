import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class AlgoShortestWay implements ISearch{

    private Graph graph;
    private TextArea textArea;

    public AlgoShortestWay(Graph graph, TextArea textArea) {
        this.graph = graph;
        this.textArea = textArea;
    }

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
            if (!temp.expanded) {
                for (Link link : temp.listLinks) {
                    Node rNode = link.relatedNode;
                    GraphUtil.setParentCostAndPrice(temp, link);
                    queue.add(rNode);
                }
                temp.expanded = true;
            }
        } // end while for queue

        if (graph.getNode(end).parent == null) {
            return null;
        } else {
            GraphUtil.printKMandPrice(graph, end, textArea);
            ArrayList<String> path = new ArrayList<>();
            GraphUtil.printPath(graph.getNode(start), graph.getNode(end), path);
            return path;
            //GrapthUtil.printPathByParent(graph, end);
        }
    }
}
