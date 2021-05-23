package exercises.archive;

import aud.BinaryTree;
import aud.BinaryTreeTraversal;
import aud.example.expr.Number;
import aud.example.expr.*;
import aud.util.DotViewer;

public class Differentiation {
    private static final AtomicExpression.Type PLUS = AtomicExpression.Type.OpPlus;
    private static final AtomicExpression.Type MINUS = AtomicExpression.Type.OpMinus;
    private static final AtomicExpression.Type UNARY_MINUS = AtomicExpression.Type.OpUnaryMinus;
    private static final AtomicExpression.Type TIMES = AtomicExpression.Type.OpTimes;
    private static final AtomicExpression.Type SYMBOL = AtomicExpression.Type.TSymbol;
    private static final AtomicExpression.Type TNUMBER = AtomicExpression.Type.TNumber;

    //----------------------------------------------------------------//
    //----------------------------------------------------------------//
    public static ExpressionTree differentiate(BinaryTree<AtomicExpression> tree, String var) {
        ExpressionTree expTree = new ExpressionTree(tree.getData().clone());

        AtomicExpression.Type type = tree.getData().getType();
        if (type == SYMBOL) {
            if (tree.getData().toString().equals(var)) expTree.setData(new Number(1));
            else expTree.setData(new Number(0));
        } else if (type == PLUS) {
            expTree = new ExpressionTree(tree.getData().clone(), differentiate(tree.getLeft(), var), differentiate(tree.getRight(), var));
        } else if (type == TIMES) {
            expTree.setData(new Plus());
            expTree.setLeft(new ExpressionTree(new Times(), differentiate(tree.getLeft(), var), (ExpressionTree) tree.getRight()));
            expTree.setRight(new ExpressionTree(new Times(), (ExpressionTree) tree.getLeft(), differentiate(tree.getRight(), var)));
        } else if (type == UNARY_MINUS) {
            if (tree.getLeft() == null) {
                throw new RuntimeException();
            } else {
                if (tree.getLeft().getData().toString().equals(var)) {
                    expTree.setData(new UnaryMinus());
                    expTree.setLeft(new ExpressionTree(new Number(1)));
                } else {
                    expTree.setData(new UnaryMinus());
                    expTree.setLeft(differentiate(tree.getLeft(), var));
                }
            }
        } else if (type == MINUS) {
            expTree = new ExpressionTree(tree.getData().clone(), differentiate(tree.getLeft(), var), differentiate(tree.getRight(), var));
        } else if (type == TNUMBER) {
            if (treeContainsVar(tree, var)) expTree.setData(tree.getData().clone());
            else expTree.setData(new Number(0));

        } else {
            System.err.println("Not handled: " + type + ": " + tree.getData());
        }

        return expTree;

    }

    private static boolean treeContainsVar(BinaryTree<AtomicExpression> tree, String var) {
        BinaryTreeTraversal<AtomicExpression>.Inorder traverser = tree.inorder();
        for (BinaryTree<AtomicExpression> node : traverser) {
            if (node.getData().toString().equals(var)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        ExpressionTree tree = parser.parse("-(x*y)");

        BinaryTreeTraversal<AtomicExpression>.Inorder traverser = tree.inorder();

        for (BinaryTree<AtomicExpression> node : traverser) {
            System.out.println(node.getData().getType().toString());
        }

        DotViewer.displayWindow(tree.toDot(), "Tree");
        DotViewer.displayWindow(differentiate(tree, "x").toDot(), "Differentiated Tree");

//        String expected = "((-1)*y)+((-x)*0)";
//        String is_actually =  "(-1*y)+((-x)*0)";
//
//        DotViewer.displayWindow(new ExpressionParser2().parse(expected).toDot(), "expected");
//        DotViewer.displayWindow(new ExpressionParser2().parse(is_actually).toDot(), "is_actually");
    }
}