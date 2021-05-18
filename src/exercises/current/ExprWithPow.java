package exercises.current;

import aud.Queue;
import aud.example.expr.*;  //to extend ExpressionParser2
import aud.example.expr.Number;
import aud.util.*;

//-----------------------------------------------------------------//
/**ExpressionParser which is able the process `power to`.*/
public class ExprWithPow extends ExpressionParser2{
    /**
     * <product> ::= <power> | <power> "*" <product> | <power> "/" <product>
     * <factor> ::= <number> | <symbol> | "-" <factor> | "+" <factor> | "(" * <expression> ")"
     */
    @Override
    public ExpressionTree product(int level){
        verbose(level,"<product>");

        // temporary storage
        Queue<ExpressionTree> expr = new Queue<ExpressionTree>();
        Queue<Integer>        op   = new Queue<Integer>();

        ExpressionTree tree=pow(level+1);

        // parse iteratively
        while (lookahead()==Tokenizer.TIMES || lookahead()==Tokenizer.DIVIDE) {
            op.enqueue(lookahead());
            nextToken();
            expr.enqueue(pow(level+1));
        }

        // setup tree
        while (!op.is_empty()) {
            if (op.dequeue()==Tokenizer.TIMES)
                tree=new ExpressionTree(new Times(),tree,expr.dequeue());
            else
                tree=new ExpressionTree(new Divide(),tree,expr.dequeue());
        }
        return tree;
    }

    /**
     * pow ::= factor | factor "^" pow
     */
    public ExpressionTree pow(int level) {
        verbose(level, "<pow>");

        ExpressionTree left = factor(level+1);

        if (lookahead()==Tokenizer.POWER) { // False if next token is just a factor, true if followed by "*"
            nextToken();
            ExpressionTree right= pow(level+1);
            return new ExpressionTree(new Power(),left,right);
        }
        return left;
    }

    //----------------------------------------------------------------//
    public static void main(String[] args) {
    }
}
