import java.util.HashSet;

public interface GraphNode<K> {
    K getKey();
    void addRequires(K other);
    void addRequiredBy(K other);
    HashSet<K> getRequires();
    HashSet<K> getRequiredBy();
}
