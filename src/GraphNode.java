import java.util.Set;

public interface GraphNode<K> {
    K getKey();
    void addRequires(K other);
    void addRequiredBy(K other);
    Set<K> getRequires();
    Set<K> getRequiredBy();
}
