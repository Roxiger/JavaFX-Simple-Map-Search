import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Open {

    public static void open(Graph graph, Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") + "\\save"));

        //Testing
        System.out.println(System.getProperty("user.dir") + "\\save");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);

        ArrayList<String> list = new ArrayList<>();
        if (file != null) {
            try {
                Path filePath = file.toPath();
                Stream<String> stream = Files.lines(filePath);
                stream.forEach(list::add);
                saveToGraph(graph, list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void saveToGraph(Graph graph, ArrayList<String> list) {
        graph.clearAll();
        String[] separatedLine;
        for (String line : list) {
            if (line.contains("#")) {
                line = line.replace("#", "");
                System.out.println(line);
                separatedLine = line.split(",");
                try {
                    Node node = new Node(separatedLine[0], Double.parseDouble(separatedLine[1]),
                            Double.parseDouble(separatedLine[2]), Double.parseDouble(separatedLine[3]));
                    graph.addNode(node);
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }

            }
            if (line.contains("$")) {
                line = line.replace("$", "");
                System.out.println(line);
                separatedLine = line.split(",");
                try {
                    graph.oneWayLink(separatedLine[0], separatedLine[1], Double.parseDouble(separatedLine[2]),
                            Integer.parseInt(separatedLine[3]));
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
        }
    }

}
