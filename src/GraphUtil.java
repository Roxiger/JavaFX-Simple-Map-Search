import javafx.scene.control.TextArea;
import java.util.ArrayList;

public class GraphUtil {
    /**
     *
     * @param graph
     * @param end
     * @return
     */
    public static ArrayList<String> printPathByParent(Graph graph, String end) {

        ArrayList<String> path = new ArrayList<>();
        path.add(end);
        Node next = graph.getNode(end).parent;
        while (next != null) {
            System.out.println("next: " + next.name);
            path.add(next.name);
            next = next.parent;
        }
        return path;
    }

    /**
     *
     * @param stopNode
     * @param goalNode
     * @param path
     */
    public static void printPath(Node stopNode, Node goalNode, ArrayList<String> path) {
        if (!(goalNode.name == stopNode.name)) {
            path.add(goalNode.name);
            printPath(stopNode, goalNode.parent, path);
        } else {
            path.add(stopNode.name);
            return;
        }
    }

    /**
     *
     * @param queue
     * @param node
     * @return
     */
    public static ArrayList<Node> sortQueueByDistance(ArrayList<Node> queue, Node node) {

        boolean isNotAdded = true;

        for (int i = 0; i < queue.size(); i++) {
            if (node.distance < queue.get(i).distance) {
                queue.add(i, node);
                isNotAdded = false;
                break;
            }
        } // loop over queue
        if (isNotAdded) {
            queue.add(node);
        }

        return queue;
    }

    /**
     *
     * @param start
     * @param end
     */
    public static void calcDistance(Node start, Node end) {
        start.distance = Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2));
    }

    /**
     *
     * @param n
     * @param textArea
     */
    public static void printNodeInfo(Node n, TextArea textArea) {
        String parentName;
        if (n.parent == null) {
            parentName = "No parent";
        } else {
            parentName = n.parent.name;
        }
        textArea.setText(textArea.getText() + "\n" + "Node name: " + n.name + ", KM: " + n.km + ", Price: " + n.price
                + ", Parent: " + parentName);
    }

    /**
     *
     * @param p
     * @param l
     */
    public static void setParentCostAndPrice(Node p, Link l) {
        double newKM = p.km + l.pathLenght + p.weight;
        double newPrice = p.price + l.price;
        Node rNode = l.relatedNode;
        if (rNode.parent == null || rNode.km > newKM) {
            rNode.km = newKM;
            rNode.parent = p;
            rNode.price = newPrice;
        }
    }

    /**
     *
     * @param p
     * @param l
     */
    public static void setParentByPrice(Node p, Link l) {
        double newKM = p.km + l.pathLenght + p.weight;
        double newPrice = p.price + l.price;
        System.out.println(p.name + " -> " + l.relatedNode.name + " " + newPrice);
        Node rNode = l.relatedNode;
        if (rNode.parent == null || rNode.price > newPrice) {
            rNode.km = newKM;
            rNode.parent = p;
            rNode.price = newPrice;
        }
    }

    /**
     *
     * @param l
     * @return
     */
    public static double calculatePrice(Link l) {
        double newPrice = 0;
        switch (l.roadType) {
            case 0:
                newPrice += calculateType0Price(l.pathLenght);
                break;
            case 1:
                newPrice += calculateType1Price(l.pathLenght);
                break;
            default:
                break;
        }
        return newPrice;
    }

    /**
     *
     * @param length
     * @return
     */
    public static double calculateType0Price(double length) {
        double price = 3;
        length -= 10;
        if (length > 0) {
            price += length / 2;
        }
        return price;
    }

    /**
     *
     * @param lenght
     * @return
     */
    public static double calculateType1Price(double lenght) {
        double price = 0;
        int a;
        lenght -= 5;
        if (lenght > 0) {
            a = (int)(lenght / 10);
            price = (a + 1) * 2;
        }

        return price;
    }

    /**
     *
     * @param p
     * @param l
     */
    public static void setParentCost(Node p, Link l) {
        double newCost = p.km + l.pathLenght;
        Node rNode = l.relatedNode;
        if (rNode.parent == null || rNode.km > newCost) {
            rNode.km = newCost;
            rNode.parent = p;
        }
    }

    /**
     *
     * @param graph
     * @param end
     * @param textArea
     */
    public static void printKMandPrice(Graph graph, String end, TextArea textArea) {
        textArea.setText(textArea.getText() + "\n" + "KM: " + graph.getNode(end).km);
        textArea.setText(textArea.getText() + "\n" + "Price: " + graph.getNode(end).price);
    }
}
