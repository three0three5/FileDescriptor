import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TxtFilePrinter {
    public static void printFilesByPaths(ArrayList<String> paths) {
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
