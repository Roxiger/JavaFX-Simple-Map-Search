import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Arrows extends Group {

    Line main;
    Line left;
    Line right;

    public Arrows() {
        main = new Line();
        left = new Line();
        right = new Line();
        this.getChildren().addAll(main, left, right);
        setDown();
    }

    public void setDown(){
        main.setStartX(0);
        main.setStartY(0);
        main.setEndX(0);
        main.setEndY(100);
        main.setStrokeWidth(0);
        left.setStartX(main.getEndX());
        left.setStartY(main.getEndY());
        left.setEndX(main.getEndX() - 8);
        left.setEndY(main.getEndY() - 8);
        left.setStrokeWidth(2);
        left.setStroke(Color.BLUE);
        right.setStartX(main.getEndX());
        right.setStartY(main.getEndY());
        right.setEndX(main.getEndX() + 8);
        right.setEndY(main.getEndY() - 8);
        right.setStrokeWidth(2);
        right.setStroke(Color.BLUE);
    }


}
