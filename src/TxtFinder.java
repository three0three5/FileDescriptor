import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для поиска текстовых файлов в некотором пути.
 */
public class TxtFinder implements FilesFinder {
    private final String path;

    /**
     * Конструктор для объектов TxtFinder
     * @param path строка-путь, где будет произведен поиск файлов
     */
    public TxtFinder(String path) {
        this.path = path;
    }

    /**
     * Поиск файлов заданного типа.
     * @param filesExtension расширение файлов
     * @return список из найденных файлов
     */
    @Override
    public List<File> getFiles(String filesExtension) {
        return getFilesFromPath(path, "txt");
    }

    private List<File> getFilesFromPath(String currentPath, String type) {
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
                if (fileType.equals(type) && x.canRead()) {
                    files.add(x);
                }
            } else if (x.isDirectory()) {
                List<File> fromDirectory = getFilesFromPath(x.getPath(), type);
                files.addAll(fromDirectory);
            }
        }
        return files;
    }
}
