import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class AddNode extends Dialog<Node> {

    private Node node;

    Label nodeName;
    Label nodeWeight;
    TextField nodeNameTextField;
    TextField nodeWeightTextField;
    ButtonType buttonTypeAdd;
    ButtonType buttonTypeCancel;

    /**
     * Constructor
     * @param graph
     */
    public AddNode(Graph graph) {
        super();

        nodeName = new Label();
        nodeName.setText("Name: ");
        nodeWeight = new Label();
        nodeWeight.setText("Weight: ");

        nodeNameTextField = new TextField();
        nodeWeightTextField = new TextField();

        GridPane grid = new GridPane();
        grid.add(nodeName, 1, 1);
        grid.add(nodeNameTextField, 2, 1);
        grid.add(nodeWeight, 1, 2);
        grid.add(nodeWeightTextField, 2, 2);

        DialogPane dialog = getDialogPane();
        setTitle("Add a new Node");
        dialog.setContent(grid);

        buttonTypeAdd = new ButtonType("Add Node", ButtonData.OK_DONE);
        buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        dialog.getButtonTypes().addAll(buttonTypeAdd, buttonTypeCancel);

        setResultConverter(dialogButton -> {
            return node;
        });

        final Button okButton = (Button) dialog.lookupButton(buttonTypeAdd);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {

            if (isFloat(nodeWeightTextField) && !isEmpty(nodeWeightTextField) && !isNameTaken(nodeNameTextField, graph)) {
                node = new Node(nodeNameTextField.getText().toUpperCase(), Float.parseFloat(nodeWeightTextField.getText()));
            } else {
                event.consume();
            }
        });

        nodeWeightTextField.setOnKeyPressed(e -> {
            nodeWeightTextField.setStyle("-fx-text-fill: rgb(0, 0, 0);");
        });

        nodeNameTextField.setOnKeyPressed(e -> {
            nodeNameTextField.setStyle("-fx-text-fill: rgb(0, 0, 0);");
        });
    }// end of constructor

    /**
     *  Check if the number in the text field is float number
     * @param textfield
     * @return
     */
    private boolean isFloat(TextField textfield) {
        try {
            Float.parseFloat(textfield.getText());
            return true;
        } catch (NumberFormatException e) {
            textfield.setStyle("-fx-text-fill: rgb(255, 0, 0);");
            return false;
        }
    }

    /**
     *  Check if the txtField is empty
     * @param textfield
     * @return
     */
    private boolean isEmpty(TextField textfield) {
        if (textfield.getText().equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if the name of the graph is already used
     * @param textfield
     * @param graph
     * @return
     */
    private boolean isNameTaken(TextField textfield, Graph graph){
        for(Node node: graph.getMap().values()){
            if(node.name.equals(textfield.getText())){
                textfield.setStyle("-fx-text-fill: rgb(255, 0, 0);");
                return true;
            }
        }
        return false;
    }
}
