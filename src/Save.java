import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Save {

    /**
     *  Method for saving the current nodes on their current positions
     * @param graph
     * @param stage
     */
    public static void save(Graph graph, Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\save"));

        //Testing
        System.out.println(System.getProperty("user.dir") + "\\save");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save Graph");
        File file = fileChooser.showSaveDialog(stage);

        PrintWriter printWriter = null;
        BufferedWriter bufferedWriter = null;
        if (file != null) {
            try {
                printWriter = new PrintWriter(file);
                bufferedWriter = new BufferedWriter(new PrintWriter(printWriter));
                for (Node node : graph.getMap().values()) {
                    bufferedWriter.write("#" + node.name + "," + node.weight + "," + node.layoutX + "," + node.layoutY + "#");
                    bufferedWriter.newLine();
                }
                for (Node node : graph.getMap().values()) {
                    for (Link link : node.listLinks) {
                        bufferedWriter.write("$" + node.name + "," + link.relatedNode.name + "," + link.pathLenght + "," + link.roadType + "$");
                        bufferedWriter.newLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                printWriter.close();
            } finally {
                try {

                    if (bufferedWriter != null)
                        bufferedWriter.close();

                    if (printWriter != null)
                        printWriter.close();

                } catch (IOException ex) {

                    ex.printStackTrace();

                }
            }
        }
    }
}
