package examprep.hashtable;

import java.util.Arrays;

public class HashTable {
    private static final int NONE = Integer.MIN_VALUE;

    public static int[] createEmptyHashTable(final int size) {
        final int[] emptyHashTable = new int[size];
        for (int i = 0; i < size; ++i) {
            emptyHashTable[i] = NONE;
        }
        return emptyHashTable;
    }

    private static int hashFunc(final int x, final int collisions) {
        return (3 * x + 7 + collisions) % 11;
    }

    public static int[][] linearHashing(final int size, final int[] values) {
        final int[] hashtable = createEmptyHashTable(size);
        final int[] collisions = createEmptyHashTable(size);

        for (int i = 0; i < values.length; ++i) {
            for (int j = 0; true; ++j) {
                final int hashVal = hashFunc(values[i], j);
                if (hashtable[hashVal] == NONE) {
                    hashtable[hashVal] = values[i];
                    collisions[hashVal] = j;
                    break;
                }
                if (j >= size) {
                    System.err.println("hashtable is full, cant insert more values!");
                    System.out.println(Arrays.deepToString(new int[][]{hashtable, collisions}));
                }
            }
        }
        return new int[][]{hashtable, collisions};
    }

    private static String beautifyHashingResultValue(final int[][] result) {
        String str = Arrays.deepToString(result);
        str = str.replace(String.valueOf(NONE), "-");
        str = str.replace("], [", "]\n[");
        str = str.replace("[[", "[");
        str = str.replace("]]", "]");
        return str;
    }

    public static void main(final String[] args) {
        final int[][] result = linearHashing(11, new int[]{0, 1, 2, 3, 5, 8, 13, 21});
        System.out.println(beautifyHashingResultValue(result));

    }
}
