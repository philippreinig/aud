package aud.test;

import aud.RedBlackTree;
import aud.util.Permutations;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RedBlackTreeTest {

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("aud.test.RedBlackTreeTest");
    }

    @Test
    public void testRedBlackTree() {
        String[] keys = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        int n = keys.length;

        for (int[] p : new Permutations(n)) {

            RedBlackTree<String, String> tree = new RedBlackTree<String, String>();

            for (int i : p) {
                String k = tree.find(keys[i]);
                assertEquals(k, null);
                tree.insert(keys[i], keys[i]);
                k = tree.find(keys[i]);
                assertTrue(k != null);
                assertTrue(k.compareTo(keys[i]) == 0);
                tree.checkConsistency();
            }
        }
    }
}
