import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class TxtFilePrinter {
    /**
     * Выводит в консоль текстовое содержимое файлов из списка.
     * @param paths список из строк-путей файлов
     */
    public static void printFilesByPaths(List<String> paths) {
        if (paths.isEmpty()) {
            return;
        }
        for (String path : paths) {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    System.out.println(currentLine);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}
