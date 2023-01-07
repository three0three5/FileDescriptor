import java.util.*;

/**
 * Класс для топологической сортировки графов.
 * @param <T> тип ключа вершин графа
 */
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
    private final Map<T, ColoredNode<T>> graph = new HashMap<>();
    private final List<T> sorted = new ArrayList<>();

    /**
     * Конструктор для объектов класса GraphSorter.
     * Без графа такой объект не имеет смысла, поэтому параметр graph обязателен
     * @param graph словарь из ключей и обёрток для вершин графа
     */
    public GraphSorter(Map<T, GraphNode<T>> graph) {
        for (T key : graph.keySet()) {
            ColoredNode<T> node = new ColoredNode<>(graph.get(key), Color.WHITE);
            this.graph.put(key, node);
        }
    }

    /**
     * Метод-сеттер для многоразового использования объекта GraphSorter.
     * Позволяет работать с другими графами в случае необходимости
     * @param graph словарь из ключей и обёрток для вершин графа
     */
    public void setGraph(Map<T, GraphNode<T>> graph) {
        for (T key : graph.keySet()) {
            ColoredNode<T> node = new ColoredNode<>(graph.get(key), Color.WHITE);
            this.graph.put(key, node);
        }
    }

    /**
     * Возвращает список ключей того типа, которым был инициализирован GraphSorter.
     * Список отсортирован в топологическом порядке.
     * @return топологически отсортированный List<T></>
     */
    public List<T> getSortedKeys() {
        try {
            sortKeys();
        } catch (CycledGraphException e) {
            System.out.println(e.getMessage());
        }
        return sorted;
    }

    private Set<T> getStartPoints() {
        Set<T> result = new HashSet<>();
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
        Set<T> requires = currentNode.node.getRequires();
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
        Set<T> entryPoints = getStartPoints();
        if (entryPoints.isEmpty()) {
            System.out.println(ConstOutputStrings.NO_START_POINTS);
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
