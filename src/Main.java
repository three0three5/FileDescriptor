import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
//    public static void printEverything(HashMap<String, GraphNode<String>> graph) {
//        for (String x : graph.keySet()) {
//            System.out.println("The file " + x + " requires: ");
//            GraphNode file = graph.get(x);
//            for (String key : file.getRequires()) {
//                System.out.println(key);
//            }
//            System.out.println();
//            System.out.println("And required by: ");
//            for (String key : file.getRequiredBy()) {
//                System.out.println(key);
//            }
//            System.out.println();
//        }
//    }
    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        while (menu.isRunning()) {
            menu.proceed();
        }
        FilesFinder finder = new TxtFinder(menu.getFolderPath());
        ArrayList<File> txtFiles = finder.getFiles();

        FileGraphCreator creator = new FileGraphCreator("txt");
        creator.setFiles(txtFiles);

        HashMap<String, GraphNode<String>> graph = creator.getFilesGraph();
        // printEverything(graph);
        GraphSorter<String> sorter = new GraphSorter<>(graph);

        ArrayList<String> keys = sorter.getSortedKeys();
        if (keys.isEmpty()) {
            System.out.println("Отсортировать файлы не удалось!");
        } else {
            System.out.println("Вывод файлов в консоль: ");
        }
        TxtFilePrinter.printFilesByPaths(keys);
    }
}