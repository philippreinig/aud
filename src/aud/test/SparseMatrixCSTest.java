package aud.test;

import aud.Vector;
import aud.graph.matrix.SparseMatrixCS;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class SparseMatrixCSTest {

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("aud.test.SparseMatrixCSTest");
    }

    @Test
    public void testMatrix() {
        SparseMatrixCS<Integer> m = new SparseMatrixCS<Integer>();
        assertSame(m.nnz(), 0);
        assertSame(m.getNumRows(), 0);
        assertSame(m.getNumColumns(), 0);
        assertSame(m.getMinRowIndex(), 0);
        assertSame(m.getMinColumnIndex(), 0);

        m.set(2, 2, 2);
        m.set(9, 9, 9);

        assertSame(m.getNumRows(), 9);
        assertSame(m.getNumColumns(), 9);
        assertSame(m.getMinRowIndex(), 2);
        assertSame(m.getMinColumnIndex(), 2);

        assertSame(m.get(2, 2), 2);
        assertSame(m.get(9, 9), 9);
        assertSame(m.nnz(), 2);

        m.set(2, 2, 3);
        assertSame(m.get(2, 2), 3);
        assertSame(m.nnz(), 2);

        m.set(2, 2, null);
        assertSame(m.get(2, 2), null);
        assertSame(m.nnz(), 1);

        m.set(2, 2, 2);

        m.set(4, 2, -1);
        assertSame(m.get(4, 2), -1);
        assertSame(m.get(2, 4), null);

        int[] ri = m.getColumnRowIndices(2);
        Vector<Integer> v = m.getColumnEntries(2);

        assertSame(ri.length, 2);
        assertSame(m.columnDegree(2), 2);
        assertSame(ri[0], 2);
        assertSame(ri[1], 4);
        assertEquals(v.at(0), new Integer(2));
        assertEquals(v.at(1), new Integer(-1));

        assertSame(m.spones().nnz(), m.nnz());
    }
}
