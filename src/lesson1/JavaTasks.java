package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws ParseException, IOException {
        FileWriter writer = new FileWriter(outputName);
        FileReader reader = new FileReader(inputName);
        // Задаем нужный формат времени и добавляем в list только подходящи под него строки
        // При несоответсвии формату бросаем исключение
        SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss aa");
        ArrayList<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            try {
                list.add((int) format.parse(line).getTime());
            } catch (ParseException e) {
                throw new ParseException("Incorrect date format", e.getErrorOffset());
            }
        }
        // Сортируем отобранные строки и записываем их в файл в исходном формате
        StringBuilder line = new StringBuilder();
        list.stream().sorted().forEach(value -> line.append(format.format(new Date(value))).append("\n"));
        writer.write(line.toString());
    }
    // Трудоемкость = O(N*log N)
    // Ресурсоемкость = O(N)

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        int min = 2730;
        int max = 5000;
        ArrayList<Integer> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        String str = reader.readLine();
        // При записи чисел из файла в list переводим их в целые (умножение на 10) и сдвигаем на min
        while (str != null) {
            int t = (int) (Double.parseDouble(str) * 10 + min);
            list.add(t);
            str = reader.readLine();
        }
        reader.close();
        // Переносим числа в массив, чтобы использовать метод countingSort
        int size = list.size();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = list.get(i);
        }
        arr = Sorts.countingSort(arr, min + max);
        // Переводим значения в изначальный формат и записываем в выходной файл
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
        for (int el : arr) {
            double t = (double) (el - min) / 10;
            writer.write(String.valueOf(t));
            writer.newLine();
        }
        writer.close();
    }
    // Трудоемкость = O(N + K), где K = 2730 + 5000 + 1 = 7731
    // Ресурсоемкость = O(K)

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputName));
        String s = reader.readLine();
        // Записываем в map, сколько раз повторялось каждое из чисел
        while (s != null) {
            int n = 1;
            int t = Integer.parseInt(s);
            list.add(t);
            if (map.containsKey(t)) {
                n += map.get(t);
                map.replace(t, n);
            } else {
                map.put(t, n);
            }
            s = reader.readLine();
        }
        int maxN = 0;
        int maxKey = 0;
        // Выбор числа с максимальным кол-вом повторений
        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            if (entry.getValue() > maxN) {
                maxN = entry.getValue();
                maxKey = entry.getKey();
            } else if (entry.getValue() == maxN && entry.getKey() < maxKey) {
                maxKey = entry.getKey();
            }
        }
        // Сначала записываем в файл все числа, кроме выбранных на предыдущем шаге, а после - самые часто встречаемые
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            for (Integer el : list) {
                if (el != maxKey) {
                    writer.write(el + System.lineSeparator());
                }
            }
            while (maxN != 0) {
                writer.write(maxKey + System.lineSeparator());
                maxN--;
            }
        }
    }
    // Трудоемкость = O(N)
    // Ресурсоемкость = O(N)

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
