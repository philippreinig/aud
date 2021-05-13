package exercises.current;

import aud.example.expr.*;  //to extend ExpressionParser2
import aud.example.expr.Number;
import aud.util.*;

//-----------------------------------------------------------------//
/**ExpressionParser which is able the process `power to`.*/
public class ExprWithPow extends ExpressionParser2{

    // TODO: maybe overwrite some methods

    //----------------------------------------------------------------//
    public ExpressionTree pow(int level) {
        // TODO: - implement this method which handles `power to` input
        //       - feel free to find a better solution without using this
        //         method
        return new ExpressionTree(new Number(5));
    }

    //----------------------------------------------------------------//
    public static void main(String[] args) {
        // TODO: test your code with appropriate examples
    }
}
