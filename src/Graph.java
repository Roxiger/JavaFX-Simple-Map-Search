import javafx.scene.control.ContextMenu;
import javafx.stage.Stage;
import java.util.HashMap;

public class Graph {

    MouseMovements mouseMovements = new MouseMovements(this);

    private HashMap<String, Node> map = new HashMap<String, Node>();

    /**
     * Creating a new Node
     * @param node
     */
    public void addNode(Node node) {
        map.put(node.name, node);
    }

    /**
     * Creating One Way Link
     * @param start
     * @param end
     * @param length
     * @param roadType
     */
    public void oneWayLink(String start, String end, double length, int roadType){
        if(map.containsKey(start) && map.containsKey(end)){
            System.out.println("Oneway link");
            //map.get(start).links.add(map.get(end));
            Link link = new Link(map.get(end), length, roadType);
            link.setPrice(GraphUtil.calculatePrice(link));
            map.get(start).listLinks.add(link);
            System.out.println(start + ", " + link.relatedNode.name + " price is: " + link.price);
        } else {
            System.out.println("Missing nodes");
        }
    }

    /**
     * Creating Two Way Link
     * @param start
     * @param end
     * @param length
     * @param roadType
     */
    public void twoWayLink(String start, String end, double length, int roadType){
        oneWayLink(start, end, length, roadType);
        oneWayLink(end, start, length, roadType);
    }

    /**
     *  Resets the nodes
     */
    public void resetAll() {
        for (Node node : map.values()) {
            node.reset();
        }
    }

    /**
     *  Clean the whole map
     */
    public void clearAll(){
        map.clear();
    }

    /**
     *  Checks if a certain nodes is it on the map
     * @param name
     * @return
     */
    public boolean containsNode(String name){
        return map.containsKey(name);
    }

    /**
     * Get a certain node
     * @param name
     * @return
     */
    public Node getNode(String name){
        return map.get(name);
    }

    /**
     *
     * @return
     */
    public HashMap<String, Node> getMap() {
        return map;
    }

    /**
     * Making the nodes draggable
     */
    public void makeDragable() {
        for (Node node : map.values()) {
            mouseMovements.makeDraggable(node);
        }
    }

    /**
     * Makes a certain node draggable
     * @param node
     */
    public void makeDragable(Node node){
        mouseMovements.makeDraggable(node);
    }

    /**
     *
     * @param menu
     * @param stage
     */
    public void makeClicable(ContextMenu menu, Stage stage) {
        for (Node node : map.values()) {
            mouseMovements.makeClickable(node, menu, stage);
        }
    }

    /**
     *
     * @param node
     * @param menu
     * @param stage
     */
    public void makeClicable(Node node, ContextMenu menu, Stage stage) {
        mouseMovements.makeClickable(node, menu, stage);
    }

    /**
     *
     * @param node
     */
    public void deleteNode(Node node) {
        for(Node nodes : map.values()){
            for(Link nodesLinks: nodes.listLinks){
                map.get(nodesLinks.relatedNode.name).removeLink(node.name);
            }
        }
        map.remove(node.name);
    }

    /**
     *
     */
    public void printAllLinks(){
        for(Node node: map.values()){
            System.out.println("Node " + node.name + " has: ");
            for(Link links : node.listLinks){
                System.out.println("Link: " + links.relatedNode.name);
            }
        }
    }

}
