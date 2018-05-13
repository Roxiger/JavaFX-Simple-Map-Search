import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Relations extends Group{

    Line line;
    Text lineLength;
    Text weight;
    Arrows arrow;

    /**
     *
     * @param parent
     * @param child
     */
    public Relations(Node parent, Link child) {

        Node temp = child.relatedNode;
        line = new Line();
        line.setStrokeWidth(3);
        arrow = new Arrows();
        weight = new Text("" + parent.weight);
        lineLength = new Text("" + child.pathLenght);
        getChildren().addAll(line, lineLength, arrow, weight);

        if (parent.listLinks.contains(child) && containsP(child, parent)) {
            line.setStroke(Color.BLUE);
            getChildren().remove(arrow);
        } else {
            line.setStroke(Color.RED);
        }

        line.startXProperty().bind(parent.layoutXProperty().add(parent.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind(parent.layoutYProperty().add(parent.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind(temp.layoutXProperty().add(temp.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind(temp.layoutYProperty().add(temp.getBoundsInParent().getHeight() / 2.0));

        weight.layoutXProperty().bind(parent.layoutXProperty().add(parent.getBoundsInParent().getWidth() / 4.0));
        weight.layoutYProperty().bind(parent.layoutYProperty().add(-5));

        setTextLayout(parent, temp);

        parent.layoutXProperty().addListener(e -> {
            setTextLayout(parent, temp);
            setArrowRotate(parent, temp);
        });

        parent.layoutYProperty().addListener(e -> {
            setTextLayout(parent, temp);
            setArrowRotate(parent, temp);
        });

        temp.layoutXProperty().addListener(e -> {
            setTextLayout(parent, temp);
            setArrowRotate(parent, temp);
        });

        temp.layoutYProperty().addListener(e -> {
            setTextLayout(parent, temp);
            setArrowRotate(parent, temp);
        });

    }

    /**
     *
     * @param parent
     * @param child
     */
    private void setTextLayout(Node parent, Node child) {
        lineLength.layoutXProperty().bind(parent.layoutXProperty().add(parent.getBoundsInParent().getWidth() / 2.0 - lineLength.getBoundsInParent().getWidth() / 2.0 + (line.getEndX() - line.getStartX()) / 2));
        lineLength.layoutYProperty().bind(parent.layoutYProperty().add(parent.getBoundsInParent().getHeight() / 4.0 + lineLength.getBoundsInParent().getHeight() / 4.0 + (line.getEndY() - line.getStartY()) / 2));
        arrow.relocate(line.getStartX() - arrow.getLayoutBounds().getWidth() / 2, line.getStartY() - arrow.getLayoutBounds().getHeight() / 2);

        setArrowRotate(parent, child);
    }

    /**
     *
     * @param child
     * @param parent
     * @return
     */
    private boolean containsP(Link child, Node parent) {
        for (Link link : child.relatedNode.listLinks) {
            if (link.relatedNode == parent) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param parent
     * @param child
     * @return
     */
    private boolean isDown(Node parent, Node child) {
        if (parent.layoutY < child.layoutY) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param parent
     * @param child
     * @return
     */
    private boolean isRight(Node parent, Node child) {
        if (parent.layoutX < child.layoutX) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    private double calculateAngle(double a, double b) {
        return -1 * Math.atan(b / a);
    }

    /**
     *
     * @param parent
     * @param child
     */
    private void setArrowRotate(Node parent, Node child) {
        double a;
        double b;
        double angle;
        if (isDown(parent, child)) {
            a = child.layoutY - parent.layoutY;
            if (isRight(parent, child)) {
                b = child.layoutX - parent.layoutX;
                angle = -1 * Math.toDegrees(calculateAngle(b, a)) - 90;
            } else {
                b = parent.layoutX - child.layoutX;
                angle = Math.toDegrees(calculateAngle(b, a)) + 90;
            }
        } else {
            a = parent.layoutY - child.layoutY;
            if (isRight(parent, child)) {
                b = child.layoutX - parent.layoutX;
                angle = -1 * Math.toDegrees(calculateAngle(a, b)) + 180;
            } else {
                b = parent.layoutX - child.layoutX;
                angle = Math.toDegrees(calculateAngle(a, b)) - 180;
            }
        }
        arrow.setRotate(angle);
    }

}
