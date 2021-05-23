package aud.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {VectorTest.class,
                SListTest.class,
                DListTest.class,
                StackTest.class,
                QueueTest.class,
                BinaryTreeTest.class,
                BinarySearchTreeTest.class,
                AVLTreeTest.class,
                A234TreeTest.class,
                RedBlackTreeTest.class,
                BTreeTest.class,
                SparseMatrixCSTest.class,
        }
)
public class RunTests {
}
