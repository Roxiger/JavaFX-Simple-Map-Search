import java.util.ArrayList;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Node extends StackPane implements INode {


    public String name;
    public double x, y;
    public double weight;
    public double distance;
    public double km;
    public double price;
    public ArrayList<Link> listLinks = new ArrayList<>();

    public Circle circle = new Circle();
    public Text text = new Text();
    public Text weightText = new Text();
    public double layoutX, layoutY;


    public boolean tested, expanded;
    public int depth;
    public Node parent;

    public Node(String name) {
        this.name = name;
        parent = null;
        setImage();
    }

    public Node(String name, double weight) {
        this.name = name;
        this.weight = weight;
        setImage();
    }

    public Node(String name, double weight, double x, double y) {
        this.name = name;
        this.weight = weight;
        setImage();
        setLayoutPos(x, y);
    }

    public void reset() {
        this.tested = false;
        this.expanded = false;
        this.depth = 0;
        this.parent = null;
        this.circle.setFill(Color.WHITE);
        this.km = 0;
        this.price = 0;
    }

    public void setImage() {
        text.setText(name);
        text.setFont(Font.font("Verdana", 20));
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setRadius(15);
        circle.relocate(circle.getRadius() / 2, circle.getRadius() / 2);
        updateGroup();
    }

    public void updateGroup() {
        getChildren().clear();
        getChildren().addAll(circle, text);
    }

    public void setLayoutPos(double x, double y){
        layoutX = x;
        layoutY = y;
        this.x = layoutX;
        this.y = layoutY;
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
    }

    public void removeLink(String toLink) {
        for(Link link : listLinks){
            if(link.relatedNode.name.equals(toLink)){
                listLinks.remove(link);
                break;
            }
        }
    }
}
