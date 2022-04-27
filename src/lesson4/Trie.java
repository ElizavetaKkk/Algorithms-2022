package lesson4;

import java.util.*;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Префиксное дерево для строк
 */
public class Trie extends AbstractSet<String> implements Set<String> {

    private static class Node {
        SortedMap<Character, Node> children = new TreeMap<>();
    }

    private final Node root = new Node();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root.children.clear();
        size = 0;
    }

    private String withZero(String initial) {
        return initial + (char) 0;
    }

    @Nullable
    private Node findNode(String element) {
        Node current = root;
        for (char character : element.toCharArray()) {
            if (current == null) return null;
            current = current.children.get(character);
        }
        return current;
    }

    @Override
    public boolean contains(Object o) {
        String element = (String) o;
        return findNode(withZero(element)) != null;
    }

    @Override
    public boolean add(String element) {
        Node current = root;
        boolean modified = false;
        for (char character : withZero(element).toCharArray()) {
            Node child = current.children.get(character);
            if (child != null) {
                current = child;
            } else {
                modified = true;
                Node newChild = new Node();
                current.children.put(character, newChild);
                current = newChild;
            }
        }
        if (modified) {
            size++;
        }
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        String element = (String) o;
        Node current = findNode(element);
        if (current == null) return false;
        if (current.children.remove((char) 0) != null) {
            size--;
            return true;
        }
        return false;
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    @NotNull
    @Override
    public Iterator<String> iterator() {
        return new TrieIterator();
    }

    public class TrieIterator implements Iterator<String> {
        private final Deque<Iterator<Map.Entry<Character, Node>>> deque = new ArrayDeque<>();
        private final StringBuilder builder = new StringBuilder();
        private String nextWord;
        private int count = 0;

        private TrieIterator() {
            deque.push(root.children.entrySet().iterator());
        }

        @Override
        public boolean hasNext() {
            return size > count;
        }
        // Трудоемксость = O(1)
        // Ресурсоёмкость = O(1)

        @Override
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            findNext();
            return nextWord;
        }
        // Трудоемксость = O(N)
        // Ресурсоемкость = O(H), H - длина самого длинного слова

        public void findNext() {
            Iterator<Map.Entry<Character, Node>> iterator = deque.peek();
            while (iterator != null){
                while (iterator.hasNext()) {
                    Map.Entry<Character, Node> next = iterator.next();
                    char key = next.getKey();
                    Node val = next.getValue();
                    if (key == (char) 0) {
                        this.nextWord = builder.toString();
                        count++;
                        return;
                    }
                    iterator = val.children.entrySet().iterator();
                    deque.push(iterator);
                    builder.append(key);
                }
                deque.pop();
                if (!builder.isEmpty()) {
                    builder.deleteCharAt(builder.length() - 1);
                }
                iterator = deque.peek();
            }
        }

        @Override
        public void remove() {
            if (nextWord == null) throw new IllegalStateException();
            if (deque.peek() != null) {
                deque.peek().remove();
                nextWord = null;
                count--;
                size--;
            }
        }
        // Трудоемксость = O(1)
        // Ресурсоемкость = O(1)
    }
}