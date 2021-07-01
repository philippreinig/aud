package exercises.week05;

import aud.example.expr.Number;
import aud.example.expr.*;
import aud.util.DotViewer;

public class ExampleExpression {
    public static void main(String[] args) {
        System.out.println("------ Expression Tree ------------");
        ExpressionTree et = new ExpressionTree(new Divide(),
                new ExpressionTree(new Plus(),
                        new ExpressionTree(new Number(1)),
                        new ExpressionTree(new Times(),
                                new ExpressionTree(new Number(5)),
                                new ExpressionTree(new Number(3)))),
                new ExpressionTree(new Number(2)));

        System.out.println(et.getValue());
        DotViewer.displayWindow(et.toDot(), et.toString());

        System.out.println("------ Expression Parser -----------");
        String expression1 = "(1+5*3)/2";

        ExpressionParser ep = new ExpressionParser();
        ep.setVerbose(true);
        ExpressionTree et2 = ep.parse(expression1);
        System.out.println("Value of expression: " + et.getValue());
        System.out.println("Expression in preorder: " + et2.preorder().toString());
        System.out.println("Expression in inorder: " + et2.inorder().toString());
        System.out.println("Expression in postorder: " + et2.postorder().toString());


    }
}
