package exercises.week06;

import aud.Queue;
import aud.example.expr.*;

//-----------------------------------------------------------------//

/**
 * ExpressionParser which is able the process `power to`.
 */
public class ExprWithPow extends ExpressionParser2 {
    //----------------------------------------------------------------//
    public static void main(final String[] args) {
    }

    /**
     * <product> ::= <power> | <power> "*" <product> | <power> "/" <product>
     * <factor> ::= <number> | <symbol> | "-" <factor> | "+" <factor> | "(" * <expression> ")"
     */
    @Override
    public ExpressionTree product(final int level) {
        this.verbose(level, "<product>");

        // temporary storage
        final Queue<ExpressionTree> expr = new Queue<ExpressionTree>();
        final Queue<Integer> op = new Queue<Integer>();

        ExpressionTree tree = this.pow(level + 1);

        // parse iteratively
        while (this.lookahead() == Tokenizer.TIMES || this.lookahead() == Tokenizer.DIVIDE) {
            op.enqueue(this.lookahead());
            this.nextToken();
            expr.enqueue(this.pow(level + 1));
        }

        // setup tree
        while (!op.isEmpty()) {
            if (op.dequeue() == Tokenizer.TIMES)
                tree = new ExpressionTree(new Times(), tree, expr.dequeue());
            else
                tree = new ExpressionTree(new Divide(), tree, expr.dequeue());
        }
        return tree;
    }

    /**
     * pow ::= factor | factor "^" pow
     */
    public ExpressionTree pow(final int level) {
        this.verbose(level, "<pow>");

        final ExpressionTree left = this.factor(level + 1);

        if (this.lookahead() == Tokenizer.POWER) { // False if next token is just a factor, true if followed by "*"
            this.nextToken();
            final ExpressionTree right = this.pow(level + 1);
            return new ExpressionTree(new Power(), left, right);
        }
        return left;
    }
}
