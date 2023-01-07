import java.util.List;
import java.util.Map;

public interface GraphCreator<T> {
    void setNodes(List<T> nodes);
    Map<String, GraphNode<String>> getGraphNodes();
}
