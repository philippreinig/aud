package aud.test;

import aud.graph.AdjacencyMatrix;

import org.junit.*;
import static org.junit.Assert.*;

public class AdjacencyMatrixTest {

  @Test
  public void testMatrix() {
    AdjacencyMatrix<Integer> m=new AdjacencyMatrix<Integer>(false);
    assertFalse(m.isSymmetricMatrix());

    m.set(1,1,1); m.set(1,2,4); m.set(1,3,7);
    m.set(2,1,2); m.set(2,2,5); m.set(2,3,8);
    m.set(3,1,3); m.set(3,2,6); m.set(3,3,9);

    assertSame(m.nnz(),9);

    m.clearColumnAndRow(2);

    assertSame(m.nnz(),4);
    assertSame(m.get(1,1),1); assertSame(m.get(1,3),7);
    assertSame(m.get(3,1),3); assertSame(m.get(3,3),9);
  }

  @Test
  public void testSymmatrixMatrix() {
    AdjacencyMatrix<Integer> m=new AdjacencyMatrix<Integer>(true);
    assertTrue(m.isSymmetricMatrix());

    m.set(1,1,1);
    m.set(2,1,2); m.set(2,2,4);
    m.set(3,1,3); m.set(3,2,5); m.set(3,3,6);

    assertSame(m.nnz(),9);

    m.clearColumnAndRow(2);

    assertSame(m.nnz(),4);
    assertSame(m.get(1,1),1); assertSame(m.get(1,3),3);
    assertSame(m.get(3,1),3); assertSame(m.get(3,3),6);
  }

  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("aud.test.AdjacencyMatrixTest");
  }
}
