package aud.test;

import aud.A234Tree;
import aud.util.Permutations;

import org.junit.*;
import static org.junit.Assert.*;

public class A234TreeTest {

  @Test
  public void testA234Tree() {
    String[] keys={"a","b","c","d","e","f","g","h","i"};
    int      n=keys.length;

    for (int[] p : new Permutations(n)) {

      A234Tree<String> tree=new A234Tree<>(false);

      for (int i : p) {
        String k=tree.find(keys[i]);
        assertNull(k);
        tree.insert(keys[i]);
        k=tree.find(keys[i]);
        assertNotNull(k);
        assertEquals(0, k.compareTo(keys[i]));
        tree.checkConsistency();
      }

      tree=new A234Tree<>(true);

      for (int i : p) {
        String k=tree.find(keys[i]);
        assertNull(k);
        tree.insert(keys[i]);
        k=tree.find(keys[i]);
        assertNotNull(k);
        assertEquals(0, k.compareTo(keys[i]));
        tree.checkConsistency();
      }
    }
  }

  public static void main(String[] args) {
    org.junit.runner.JUnitCore.main("aud.test.A234TreeTest");
  }
}
