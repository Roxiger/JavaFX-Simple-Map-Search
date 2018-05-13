import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class UI extends Application {
    //Declaration

    Graph graph;

    //Mouse coords
    double mouseX, mouseY;

    static Boolean noPath = false;
    static ArrayList<String> pathAR = new ArrayList<>();

    BorderPane borderPane;
    Pane window;
    VBox mainBar;
    Group groupPathAnimation = new Group();
    ArrayList<Relations> relations = new ArrayList<>();

    MenuBar mbar;
//    MyMenuHandler handler;
    Menu fileMenu;
    //Menu items
    MenuItem newMI;
    MenuItem openMI;
    MenuItem saveMI;
    MenuItem exitMI;

    //Menu items when click - add/remove node
    MenuItem deleteMI;
    MenuItem addMI;

    //First tool bar items
    ToolBar firstToolBar;
    Label algoChoose;
    Label startLabel;
    Label endLabel;
    Label throughLabel;
    Label goThroughPointsLabel;
    TextField startTF;
    TextField endTF;
    ArrayList<String> throughPoints;
    ComboBox<String> searchAlgoCb;
    Button searchButton;
    Button addPointButton;
    //filling the Algo Dropdown
    ObservableList<String> algoOptions = FXCollections.observableArrayList( "By Coords", "The Shortest Way", "The Cheapest Way");
    //Ways DropDown
    ObservableList<String>  waysLinksOptions= FXCollections.observableArrayList( "One Way", "Two Way");
    //Types DropDown
    ObservableList<Integer>  typesLinksOptions= FXCollections.observableArrayList( 0,1,2);

    //It saves the nodes
    ArrayList<String> throughOptions = new ArrayList<>();

    //Links ToolBar
    ToolBar linksToolBar;

    Label startLinkLabel;
    TextField startLinkTF;

    Label endLinkLabel;
    TextField endLinkTF;

    Label wayLinkLabel;
    ComboBox<String> waysLinkCB; // combobox----> One Way ; Two way - DONE!!!

    Label typeLinkLabel;
    ComboBox<Integer> typeLinkCB; // combobox ---> второкласен(2), първокласен(1) и магистрален(0);

    Label distanceLinkLable;
    TextField distanceLinkTF;

    Button createLinkButton;
    Button removeLinkButton;

    //ToolBar for showing the path;
    ToolBar pathToolBar;
    Label pathLabel;

    //Displaying the trace of the nodes and stuff
    TextArea textArea;

    ComboBox<String> pointsComboBox;

    final ContextMenu addContextMenu = new ContextMenu();
    final ContextMenu deleteContextMenu = new ContextMenu();

    public UI(Stage stage) {
        try {
            init();
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initUI(Stage stage) { new UI(stage);}

    @Override
    public void start(Stage primaryStage) throws Exception {

        graph = new Graph();

        borderPane = new BorderPane();
        window = new Pane();
        borderPane.setCenter(window);

        mainBar = new VBox();
        firstToolBar = new ToolBar();
        mbar = new MenuBar();
        mbar.prefWidthProperty().bind(primaryStage.widthProperty());
//        handler = new MyMenuHandler();
        fileMenu = new Menu("File");
        mbar.getMenus().add(fileMenu);

        //Creating a menu item and putting it into Menu
        newMI = new MenuItem("New");
//        newMI.setOnAction(handler);
        fileMenu.getItems().add(newMI);

        openMI = new MenuItem("Open");
//        openMI.setOnAction(handler);
        fileMenu.getItems().add(openMI);

        saveMI = new MenuItem("Save");
//        saveMI.setOnAction(handler);
        fileMenu.getItems().add(saveMI);

        //Seperatior in the menu
        fileMenu.getItems().add(new SeparatorMenuItem());

        exitMI = new MenuItem("Exit");
        exitMI.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });

        fileMenu.getItems().add(exitMI);
        mainBar.getChildren().add(mbar);

        //First toolbar init
        algoChoose = new Label("Choose an algorithm:");
        searchAlgoCb = new ComboBox<>(algoOptions);
        startLabel = new Label("From:");
        startTF = new TextField();
        endLabel = new Label("To:");
        endTF = new TextField();
        throughLabel = new Label("Through:");
        throughPoints = new ArrayList<>(); // points which should go first
        goThroughPointsLabel = new Label();
        addPointButton = new Button("Add point");
        searchButton = new Button("Search!");
        pointsComboBox = new ComboBox<>();
        //put elements into the first toolbar
        firstToolBar.getItems().addAll(algoChoose,searchAlgoCb,
                new Separator(),startLabel,startTF,
                new Separator(),endLabel,endTF,
                new Separator(),throughLabel,pointsComboBox,
                goThroughPointsLabel,
                addPointButton,new Separator(),searchButton);


        //TODO check if algo empty

        //Links Toolbar elements
        linksToolBar = new ToolBar();
        startLinkLabel = new Label("From:");
        startLinkTF = new TextField();
        endLinkLabel = new Label("To:");
        endLinkTF = new TextField();
        wayLinkLabel = new Label("Way:");
        waysLinkCB = new ComboBox<String>(waysLinksOptions);
//        wayLinkTF = new TextField();
        typeLinkLabel = new Label("Type:");
        typeLinkCB = new ComboBox<Integer>(typesLinksOptions);
//        typeLinkTF = new TextField();
        distanceLinkLable = new Label("Distance:");
        distanceLinkTF = new TextField();
        createLinkButton = new Button("Create link");
        removeLinkButton = new Button("Remove link");


        //putting the elements into the
        linksToolBar.getItems().addAll(startLinkLabel, startLinkTF,
                new Separator(),endLinkLabel, endLinkTF,
                new Separator(),wayLinkLabel,waysLinkCB,
                new Separator(), typeLinkLabel,typeLinkCB,
                new Separator(), distanceLinkLable,distanceLinkTF,
                new Separator(),createLinkButton,
                new Separator(), removeLinkButton);

        //Path toolbar and label in it
        pathToolBar = new ToolBar();
        pathLabel = new Label();

        //Text Area init
        textArea = new TextArea();
        textArea.prefWidthProperty().bind(primaryStage.widthProperty());
        textArea.prefHeightProperty().setValue(200);
        textArea.setEditable(false);

        //Click menu for add/delete nodes
        deleteMI = new MenuItem("Delete this Node");
        addMI = new MenuItem("Add a new Node");

        //Inserting the menu items into the context menus
        addContextMenu.getItems().addAll(addMI);
        deleteContextMenu.getItems().add(deleteMI);

        //add path label to the tool bar
        pathToolBar.getItems().add(pathLabel);


        //putting the first toolbar after the file menu by index
        mainBar.getChildren().add(1,firstToolBar);
        mainBar.getChildren().add(2,linksToolBar);
        mainBar.getChildren().add(3,pathToolBar);

        //Inserting the elements in the GUI
        borderPane.setTop(mainBar);
        borderPane.setBottom(textArea);

        //Size of the scene
        Scene scene = new Scene(borderPane, 1200, 600);

        //Double click left button for showing the menu from which you add a new node
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                    mouseX = e.getX();
                    mouseY = e.getY() - firstToolBar.getHeight();
                    addContextMenu.show(primaryStage, e.getScreenX(), e.getScreenY());
                }
            }
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode().equals(KeyCode.F5)) {
                    reDraw();
                    graph.resetAll();
                    textArea.clear();
                    pathLabel.setText("");
                    if (window.getChildren().contains(groupPathAnimation)) {
                        window.getChildren().remove(groupPathAnimation);
                    }
                }
                if (key.getCode().equals(KeyCode.F2)) {
                    graph.printAllLinks();
                }
            }
        });

        //Adding functionality to the text area
        textArea.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent key) {
                if (key.getCode().equals(KeyCode.F5)) {
                    reDraw();
                    graph.resetAll();
                    textArea.clear();
                    pathLabel.setText("");
                    if (window.getChildren().contains(groupPathAnimation)) {
                        window.getChildren().remove(groupPathAnimation);
                    }
                }
                if (key.getCode().equals(KeyCode.F2)) {
                    graph.printAllLinks();
                }
            }
        });

        //Adding functionality to Add Node
        addMI.setOnAction(e -> {
            new AddNode(graph).showAndWait().ifPresent((Node node) -> {
                if (node != null) {
                    node.setLayoutPos(mouseX - node.circle.getRadius(), mouseY - node.circle.getRadius());
                    graph.addNode(node);
                    window.getChildren().add(node);
                    graph.makeDragable(node);
                    graph.makeClicable(node, deleteContextMenu, primaryStage);
                    pointsComboBox.getItems().add(node.name);
                    reDraw();
                }
            });
        });

        //Delete the node
        deleteMI.setOnAction(e -> {
            Node node = graph.getNode(deleteMI.getId());
            graph.deleteNode(node);
            pointsComboBox.getItems().remove(node.name);
            window.getChildren().remove(node);
            reDraw();
        });

        //Menu Item from File for cleaning the map
        newMI.setOnAction(e ->{
            graph.clearAll();
            reDraw();
        });

        //Menu Item from File for saving the map
        saveMI.setOnAction(e -> {
            Save.save(graph, primaryStage);
        });

        //Menu Item from File for opening a text file with nodes
        openMI.setOnAction(e -> {
            Open.open(graph, primaryStage);
            reDraw();
            graph.makeDragable();
            graph.makeClicable(deleteContextMenu, primaryStage);
            fillComboBoxWithGraphPoints(graph, pointsComboBox);
        });


        //Functionality for adding points to go through
        addPointButton.setOnAction(event -> {
            throughPoints.add(pointsComboBox.getSelectionModel().getSelectedItem());
            goThroughPointsLabel.setText(goThroughPointsLabel.getText() + pointsComboBox.getSelectionModel().getSelectedItem() + ", ");
        });


        //Links buttons and stuff

        //Action creating links button
        createLinkButton.setOnAction(event -> {
            String fromLink = startLinkTF.getText().toUpperCase();
            String toLink = endLinkTF.getText().toUpperCase();
            String ways = waysLinkCB.getValue();
            System.out.print(fromLink);

            int type = 0;
            double distance = 0;

            try {
//                type = Integer.parseInt(typeLinkTF.getText()); //TODO maybe change to dropdown
                type = typeLinkCB.getValue();

                System.out.println("Value of the type: " + type);


                distance = Double.parseDouble(distanceLinkTF.getText());
            } catch (NumberFormatException e2) {
                System.out.println(e2);
                resetTextBoxes();
                //make an error
            }

            if (type >= 0 && type <= 2) {
                if (doesGraphContainsNodes(graph, fromLink, toLink)) {
                    if (ways.equals("One Way")) {
//                        System.out.print("testing one way");
                        graph.getNode(fromLink).removeLink(toLink);
                        graph.oneWayLink(fromLink, toLink, distance, type);

                    } else if (ways.equals("Two Way")) {
                        graph.getNode(fromLink).removeLink(toLink);
                        graph.getNode(toLink).removeLink(fromLink);
                        graph.twoWayLink(fromLink, toLink, distance, type);
                    }
                }
            }
            resetTextBoxes();
            reDraw();
        });

        //Action for removing links button
        removeLinkButton.setOnAction(event -> {
            String fromLink = startLinkTF.getText().toUpperCase();
            String toLink = endLinkTF.getText().toUpperCase();
            String ways = waysLinkCB.getValue();
            System.out.print(fromLink);
            if (doesGraphContainsNodes(graph, fromLink, toLink)) {
                if (ways.equals("One Way")) {
                    graph.getNode(fromLink).removeLink(toLink);
                } else if (ways.equals("Two Way")) {
                    graph.getNode(fromLink).removeLink(toLink);
                    graph.getNode(toLink).removeLink(fromLink);
                }
            }
            resetTextBoxes();
            reDraw();
        });


        //Searching Actions

        searchButton.setOnAction(event -> {
            switch (searchAlgoCb.getSelectionModel().getSelectedItem()) {
                case "By Coords":
                    throughPoints.add(0, startTF.getText().toUpperCase());
                    throughPoints.add(endTF.getText().toUpperCase());
                    setTextToTextArea("Greedy By Coordinates", textArea);
                    for (int i = 0; i < throughPoints.size() - 1; i++) {
                        if(noPath){
                            break;
                        }
                        search(new AlgoByCoords(graph, textArea), throughPoints.get(i),
                                throughPoints.get(i + 1), graph, pathLabel, textArea);
                    }
                    printPathToTextArea(pathLabel, textArea);
                    resetSearch();
                    break;
                case "The Shortest Way":
                    throughPoints.add(0, startTF.getText().toUpperCase());
                    throughPoints.add(endTF.getText().toUpperCase());
                    setTextToTextArea("Shortest Path", textArea);
                    for (int i = 0; i < throughPoints.size() - 1; i++) {
                        if(noPath){
                            break;
                        }
                        search(new AlgoShortestWay(graph, textArea), throughPoints.get(i), throughPoints.get(i + 1),
                                graph, pathLabel, textArea);
                    }
                    printPathToTextArea(pathLabel, textArea);
                    resetSearch();
                    break;

                case "The Cheapest Way":
                    throughPoints.add(0, startTF.getText().toUpperCase());
                    throughPoints.add(endTF.getText().toUpperCase());
                    setTextToTextArea("Cheapest Way", textArea);
                    for (int i = 0; i < throughPoints.size() - 1; i++) {
                        if(noPath){
                            break;
                        }
                        search(new AlgoCheapestWay(graph, textArea), throughPoints.get(i), throughPoints.get(i + 1),
                                graph, pathLabel, textArea);
                    }
                    printPathToTextArea(pathLabel, textArea);
                    for(int i = 0; i < pathAR.size()-1 ;i++){
                        System.out.println("From " + pathAR.get(i) + " to " + pathAR.get(i+1) + " price is : " + graph.getNode(pathAR.get(i+1)).price);
                    }
                    resetSearch();
                    break;
            }
        });

        //Name for the stage AKA -> the name of the program
        primaryStage.setTitle("Search the Map");
        primaryStage.setScene(scene);
        primaryStage.show();
    }// end of start()


    //Searching methods and stuff

    /**
     *
     * @param s
     * @param start
     * @param end
     * @param graph
     * @param pathLabel
     * @param textArea
     */
    public static void search(ISearch s, String start, String end, Graph graph, Label pathLabel, TextArea textArea) {
        ArrayList<String> tempAR = new ArrayList<>();
        tempAR = s.hasPath(start, end);
        textArea.setText(textArea.getText() + "\n");
        if (tempAR == null) {
            textArea.setText(textArea.getText() + "\n" + "No Way found!");
            pathLabel.setText("No Way found!");
            noPath = true;
        } else {
            for (int i = tempAR.size() - 1; i >= 0; i--) {
                textArea.setText(textArea.getText() + tempAR.get(i) + " -> ");
                pathAR.add(tempAR.get(i));
            }
        }
    }

    /**
     *
     */
    private void resetSearch(){
        goThroughPointsLabel.setText("");
        throughPoints.clear();
        pathAR.clear();
        noPath = false;
    }

    /**
     *
     * @param pathLabel
     * @param textArea
     */
    private void printPathToTextArea(Label pathLabel, TextArea textArea) {
        pathLabel.setText("Way is: ");
        textArea.setText(textArea.getText() + "\n" + "Full Way is: ");
        for (int i = 0; i < pathAR.size(); i++) {
            graph.getNode(pathAR.get(i)).circle.setFill(Color.GREEN);
            pathLabel.setText(pathLabel.getText() + pathAR.get(i) + " -> ");
            textArea.setText(textArea.getText() + pathAR.get(i) + " -> ");
        }
    }


    /**
     *
     * @param string
     * @param textArea
     */
    private void setTextToTextArea(String string, TextArea textArea) {
        if (textArea.getText().equals("")) {
            textArea.setText(
                    textArea.getText() + "New search started: " + string + "\nChosen cities: " + throughPoints);
        } else {
            textArea.setText(textArea.getText() + "\n\n" + "New search started: " + string + "\nChosen cities: "
                    + throughPoints);
        }
    }



    /**
     *
     * @param graph
     * @param from
     * @param to
     * @return
     */
    public boolean doesGraphContainsNodes(Graph graph, String from, String to) {
        System.out.println("yeyy");
        if (graph.containsNode(from) && graph.containsNode(to)) {
            System.out.println(from);
            System.out.println(to);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    public void resetTextBoxes() {
        startLinkTF.clear();
        endLinkTF.clear();
//        typeLinkTF.clear();
        distanceLinkTF.clear();
    }

    /**
     *
     */
    private void reDraw() {
        window.getChildren().clear();
        drawRelations();
        drawGraph();
    }

    /**
     *
     */
    private void drawGraph() {
        HashMap<String, Node> map = graph.getMap();

        for (Node node : map.values()) {
            window.getChildren().add(node);
        }
    }

    /**
     *
     */
    private void drawRelations() {
        relations.clear();

        for (Node parent : graph.getMap().values()) {
            for (Link child : parent.listLinks) {
                relations.add(new Relations(parent, child));
            }
        }
        window.getChildren().addAll(relations);
    }

    /**
     *
     * @param graph
     * @param cb
     */
    private void fillComboBoxWithGraphPoints(Graph graph, ComboBox<String> cb) {
        cb.getItems().clear();
        for (Node node : graph.getMap().values()) {
            cb.getItems().add(node.name);
        }
        cb.getSelectionModel().select(0);
    }

}
