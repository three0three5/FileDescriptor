import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TxtFinder implements FilesFinder {
    private final String path;

    public TxtFinder(String path) {
        this.path = path;
    }

    @Override
    public List<File> getFiles() {
        return getFilesFromPath(path);
    }

    private List<File> getFilesFromPath(String currentPath) {
        ArrayList<File> files = new ArrayList<>();
        File dir = new File(currentPath);
        File[] currentFiles = dir.listFiles();
        if (currentFiles == null) {
            return null;
        }
        for (File x : currentFiles) {
            if (x.isFile()) {
                int index = x.getName().lastIndexOf('.');
                String fileType = x.getName().substring(index + 1);
                if (fileType.equals("txt") && x.canRead()) {
                    files.add(x);
                }
            } else if (x.isDirectory()) {
                List<File> fromDirectory = getFilesFromPath(x.getPath());
                files.addAll(fromDirectory);
            }
        }
        return files;
    }
}
