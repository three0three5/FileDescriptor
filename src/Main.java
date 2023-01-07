import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Menu menu = new SimpleMenu();
        while (menu.isRunning()) {
            menu.proceed();
        }
        String path = menu.getFolderPath();
        FilesFinder finder = new TxtFinder(path);
        List<File> txtFiles = finder.getFiles();

        GraphCreator<File> creator = new FileGraphCreator("txt", path);
        creator.setNodes(txtFiles);

        Map<String, GraphNode<String>> graph = creator.getGraphNodes();
        GraphSorter<String> sorter = new GraphSorter<>(graph);

        List<String> keys = sorter.getSortedKeys();
        if (keys.isEmpty()) {
            System.out.println("Отсортировать файлы не удалось!");
        } else {
            System.out.println("Вывод файлов в консоль: ");
        }
        TxtFilePrinter.printFilesByPaths(keys);
    }
}