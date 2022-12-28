import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphSorter<T> {
    enum Color {
        WHITE,
        GRAY,
        BLACK
    }
    static class ColoredNode<T> {
        GraphNode<T> node;
        Color color;

        public ColoredNode(GraphNode<T> node, Color color) {
            this.node = node;
            this.color = color;
        }
    }
    private final HashMap<T, ColoredNode<T>> graph = new HashMap<>();
    private final ArrayList<T> sorted = new ArrayList<>();

    public GraphSorter(HashMap<T, GraphNode<T>> graph) {
        for (T key : graph.keySet()) {
            ColoredNode<T> node = new ColoredNode<>(graph.get(key), Color.WHITE);
            this.graph.put(key, node);
        }
    }

    public void setGraph(HashMap<T, GraphNode<T>> graph) {
        for (T key : graph.keySet()) {
            ColoredNode<T> node = new ColoredNode<>(graph.get(key), Color.WHITE);
            this.graph.put(key, node);
        }
    }

    public ArrayList<T> getSortedKeys() {
        try {
            sortKeys();
        } catch (CycledGraphException e) {
            System.out.println(e.getMessage());
        }
        return sorted;
    }

    private HashSet<T> getStartPoints() {
        HashSet<T> result = new HashSet<>();
        for (T key : graph.keySet()) {
            ColoredNode<T> node = graph.get(key);
            if (node.node.getRequiredBy().isEmpty()) {
                result.add(key);
            }
        }
        return result;
    }

    private void addSortedNodes(T key) throws CycledGraphException {
        ColoredNode<T> currentNode = graph.get(key);
        if (currentNode.color == Color.GRAY) {
            throw new CycledGraphException();
        } else if (currentNode.color == Color.BLACK) {
            return;
        }
        currentNode.color = Color.GRAY;
        HashSet<T> requires = currentNode.node.getRequires();
        if (requires.isEmpty()) {
            if (currentNode.color != Color.BLACK) {
                currentNode.color = Color.BLACK;
                sorted.add(key);
            }
            return;
        }
        for (T required : requires) {
            try {
                addSortedNodes(required);
            } catch (CycledGraphException e) {
                System.out.println("Возникла проблема с: " + required.toString());
                throw new CycledGraphException();
            }
        }
        if (currentNode.color != Color.BLACK) {
            currentNode.color = Color.BLACK;
            sorted.add(key);
        }
    }

    private void sortKeys() throws CycledGraphException {
        HashSet<T> entryPoints = getStartPoints();
        if (entryPoints.isEmpty()) {
            System.out.println(ConstStrings.NO_START_POINTS);
            throw new CycledGraphException();
        }
        for (T start : entryPoints) {
            try {
                addSortedNodes(start);
            } catch (CycledGraphException e) {
                System.out.println(e.getMessage());
                sorted.clear();
                return;
            }
        }
    }
}
