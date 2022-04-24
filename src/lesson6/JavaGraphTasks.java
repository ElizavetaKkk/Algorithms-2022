package lesson6;

import kotlin.NotImplementedError;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        Set<Set<Graph.Vertex>> indSets = new HashSet<>();
        for (Graph.Vertex vertex1 : graph.getVertices()){
            //Set независимых вершин в данном проходе цикла
            Set<Graph.Vertex> indVertices = new HashSet<>();
            //Set для неподходящих вершин, так как они являются соседями вершин из indVertices
            Set<Graph.Vertex> unsVertices = new HashSet<>();
            for (Graph.Vertex vertex2 : graph.getVertices()){
                if (!unsVertices.contains(vertex2) && !graph.getNeighbors(vertex1).contains(vertex2)) {
                    unsVertices.addAll(graph.getNeighbors(vertex2));
                    indVertices.add(vertex2);
                }
            }
            indSets.add(indVertices);
        }
        Set<Graph.Vertex> res = new HashSet<>();
        for (Set<Graph.Vertex> set : indSets) {
            if (res.size() < set.size()) res = set;
        }
        return res;
    }
    //Трудоемкость = O(N * N)
    //Ресурсоемкость = O(N)

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    public static Path longestSimplePath(Graph graph) {
        Stack<Path> paths = new Stack<>();
        Path res = new Path();
        Set<Graph.Vertex> vertices = graph.getVertices();
        int longest = 0;
        for (Graph.Vertex vertex: vertices) paths.push(new Path(vertex));
        while (!paths.isEmpty()) {
            //Ищем самый длинный путь
            Path path = paths.pop();
            if (longest < path.getLength()){
                res = path;
                longest = res.getLength();
            }
            //Соседи последней вершины path
            Set<Graph.Vertex> neighbours = graph.getNeighbors(path.getVertices().get(path.getLength()));
            for (Graph.Vertex neighbour : neighbours){
                if(!path.contains(neighbour)) paths.push(new Path(path, graph, neighbour));
            }
        }
        return res;
    }
    //Трудоемкость = O(N!)
    //Ресурсоемкость = O(N^2)
    //Максимальный размер paths = N - 1 + (N - 1) - 1 + (N - 2) - 1 +..+ (N - (N + 1)) =
    // = N + (N - 1) + (N - 2) +..+ 2 + 1 - 1*(N - 1) = N * (N + 1) / 2 - (N - 1) = (N + N^2 - 2N + 2)/2 =
    // = (N^2 - N + 2)/2 -> O((N^2 - N + 2)/2) -> O(N^2)

    /**
     * Балда
     * Сложная
     *
     * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
     * поэтому задача присутствует в этом разделе
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {
        throw new NotImplementedError();
    }
}
