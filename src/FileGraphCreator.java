import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileGraphCreator {
    private final HashMap<String, GraphNode<String>> files = new HashMap<>();
    private final String type;

    public FileGraphCreator(String type) {
        if (type.isBlank()) {
            this.type = "";
            return;
        }
        this.type = "." + type;
    }
    public void setFiles(ArrayList<File> files) {
        for (File x : files) {
            GraphNode<String> file = new FileHeader(x);
            this.files.put(file.getKey(), file);
        }
        try {
            makeConnections();
        } catch (RuntimeException e) {
            System.out.println(ConstStrings.ERROR_WHILE_WRAPPING);
            System.out.println(e.getMessage());
        }
    }

    public HashMap<String, GraphNode<String>> getFilesGraph() {
        return files;
    }

    private void makeConnections() {
        for (String key : files.keySet()) {

            StringBuilder fileText = new StringBuilder();
            GraphNode<String> x = files.get(key);

            try (BufferedReader br = new BufferedReader(new FileReader(x.getKey()))) {
                String currentLine;
                while ((currentLine = br.readLine()) != null) {
                    fileText.append(currentLine).append("\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ArrayList<String> requiresPaths = getRequires(String.valueOf(fileText));
            if (!requiresPaths.isEmpty()) {
                for (String k : requiresPaths) {
                    x.addRequires(k);
                }
            }

            makeReversedConnections();
        }
    }

    private void makeReversedConnections() {
        for (String key : files.keySet()) {
            HashSet<String> requires = files.get(key).getRequires();
            for (String req : requires) {
                GraphNode<String> otherFile = files.get(req);
                otherFile.addRequiredBy(key);
            }
        }
    }

    private String extractPath(String text, String type) {
        return text.substring(9, text.length() - 1) + type;
    }

    private ArrayList<String> getRequires(String fileText) {
        ArrayList<String> result = new ArrayList<>();
        String pattern = "require '[\\\\\\w\\/\\s_\\-.]+'";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(fileText);
        while (m.find()) {
            String path = extractPath(m.group(0), type);
            if ((new File(path)).canRead()) {
                result.add(path);
            } else {
                System.out.println("\n!Предупреждение: файл " + path + " не удалось прочесть." +
                        "Зависимость не установлена\n");
            }
        }
        return result;
    }
}
