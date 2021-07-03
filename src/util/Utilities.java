package util;

import java.util.Arrays;
import java.util.Map;

public class Utilities {
    private Utilities() {
    }

    public static <T> void print2DArray(final T[][] array) {
        String str = Arrays.deepToString(array);
        str = str.replace("], ", "]\n");
        str = str.substring(1, str.length() - 1);
        str = str.replace(" 0.0", " -").replace("null", "-").replace("[0.0", "[-");
        System.out.println(str);
    }

    public static <K, V> void printMap(final Map<K, V> map) {
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
