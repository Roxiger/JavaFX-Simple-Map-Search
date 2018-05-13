import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MouseMovements {

    Graph graph;
    //coords
    double x;
    double y;
    ContextMenu menu;
    Stage stage;

    public MouseMovements(Graph graph) {
        this.graph = graph;
    }// end of constructor


    //Methods for operating with the Nodes -> drag, click, press on them
    public void makeDraggable(final Node node) {
        node.setOnMousePressed(onMousePressedEventHandler);
        node.setOnMouseDragged(onMouseDraggedEventHandler);
    }

    public void makeClickable(Node node, ContextMenu menu, Stage stage) {
        node.setOnMouseClicked(onMouseClickedEventHandler);
        this.menu = menu;
        this.stage = stage;
    }



    //Events Handlers
    EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
        /**
         * Clicking on the Node with the SECONDARY mouse button
         * @param event
         */
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                Node node = (Node) event.getSource();
                menu.show(stage, event.getScreenX(), event.getScreenY());
                menu.getItems().get(0).setId(node.name);
            }
        }
    };

    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        /**
         * Getting coords when clicked
         * @param event
         */
        @Override
        public void handle(MouseEvent event) {

            Node node = (Node) event.getSource();

            x = node.getBoundsInParent().getMinX() - event.getScreenX();
            y = node.getBoundsInParent().getMinY() - event.getScreenY();
        }
    };

    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        /**
         * Dragging Node, uses coords
         * @param event
         */
        @Override
        public void handle(MouseEvent event) {
            Node node = (Node) event.getSource();

            double offsetX = event.getScreenX() + x;
            double offsetY = event.getScreenY() + y;
            node.relocate(offsetX, offsetY);
            node.setLayoutPos(offsetX, offsetY);
            node.x = offsetX;
            node.y = offsetY;
        }
    };

}
