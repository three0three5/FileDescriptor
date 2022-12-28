import java.io.File;
import java.util.HashSet;

public class FileHeader implements GraphNode<String> {
    private final String key;
    private final HashSet<String> requiredNodes = new HashSet<>();
    private final HashSet<String> requiredByFiles = new HashSet<>();

    public FileHeader(File file) {
        this.key = file.getPath();
    }

    public String getKey() {
        return key;
    }

    public void addRequires(String other) {
        requiredNodes.add(other);
    }

    public void addRequiredBy(String other) {
        requiredByFiles.add(other);
    }

    public HashSet<String> getRequires() {
        return requiredNodes;
    }

    public HashSet<String> getRequiredBy() {
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
