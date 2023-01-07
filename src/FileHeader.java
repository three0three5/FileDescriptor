import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс-обёртка над файлами, позволяющий хранить и получать путь этого файла,
 * пути файлов, требуемого для данного файла, и пути файлов, которые требуют
 * данный файл
 */
public class FileHeader implements GraphNode<String> {
    private final String key;
    private final Set<String> requiredNodes = new HashSet<>();
    private final Set<String> requiredByFiles = new HashSet<>();

    /**
     * Конструктор для создания объекта FileHeader.
     * @param file файл, который оборачивается в FileHeader.
     */
    public FileHeader(File file) {
        this.key = file.getPath();
    }

    /**
     * Позволяет получить путь файла.
     * @return строка, являющаяся путём файла (не абсолютным).
     */
    public String getKey() {
        return key;
    }

    /**
     * Добавляет в требуемые файлы еще один файл.
     * @param other строка-путь добавляемого файла.
     */
    public void addRequires(String other) {
        requiredNodes.add(other);
    }

    /**
     * Добавляет в коллекцию файлов, требующих данный файл, еще один файл.
     * @param other строка-путь добавляемого файла.
     */
    public void addRequiredBy(String other) {
        requiredByFiles.add(other);
    }

    /**
     * Получить множество из файлов, которые требуются для данного файла.
     * @return Set<String></> из путей файлов.
     */
    public Set<String> getRequires() {
        return requiredNodes;
    }

    /**
     * Получить множество файлов, которые требуют данный файл.
     * @return Set<String></> из путей файлов.
     */
    public Set<String> getRequiredBy() {
        return requiredByFiles;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        FileHeader otherFile = (FileHeader) other;
        return otherFile.getKey().equals(this.getKey());
    }

    @Override
    public int hashCode() {
        return this.getKey().hashCode();
    }
}
