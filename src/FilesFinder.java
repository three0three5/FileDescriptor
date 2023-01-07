import java.io.File;
import java.util.List;

public interface FilesFinder {
    List<File> getFiles(String fileExtension);
}
