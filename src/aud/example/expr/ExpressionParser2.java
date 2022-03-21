package aud.example.expr;

import aud.Queue;

/**
 * Same as {@link ExpressionParser} but using modified grammar to provide
 * the usual <b>left-associative</b> expressions.<p>
 * <p>
 * Here is the "correct" grammar, that implements left-associative expressions,
 * i.e., {@code a+b+c=(a+b)+c} and {@code a*b*c=(a*b)*c}.
 *
 * <pre><code>
 * expression ::= sum
 *
 * sum        ::= product |
 * sum '+' product | sum '-' product
 *
 * product    ::= factor |
 * product '*' factor | product '/' factor
 *
 * factor     ::= number | symbol | '(' expression ')' |
 * '+' factor | '-' factor
 * </code></pre>
 *
 * <b>Important note:</b> We cannot implement this directly with a recursive
 * descent parser as this would result in an infinite recursion! (This
 * type of recursion is generally referred to as <em>left recursion</em> and
 * requires a transformation of the grammar.)<p>
 * <p>
 * I don't go into details but provide a simple solution right ahead:
 * model part of the recursion (the <em>left recursion</em>) using an
 * iterative implementation. (Can you see how the transformed grammar
 * would look like?)
 *
 * @see ExpressionParser
 */
public class ExpressionParser2 extends ExpressionParser {


    //
    // Here is the implementation of BNF rules as methods:
    //

    /**
     * test and example for usage
     */
    public static void main(final String[] args) {

        final String input = (args.length > 0) ? args[0] : "a*b*c";

        System.out.println("input = '" + input + "'");

        final ExpressionParser2 p = new ExpressionParser2();
        p.setVerbose(true);
        final ExpressionTree tree = p.parse(input);

        System.out.println("output = '" + tree + "'");

        System.out.println(tree.postorder().toString());

        System.out.println(tree.toTikZ());
    }

    /**
     * parse sum<p>
     * <pre><code>
     * sum ::= product |
     * sum  '+' product | sum '-' product
     * </code></pre>
     */
    @Override
    protected ExpressionTree sum(final int level) {
      this.verbose(level, "<sum>");

        // temporary storage
        final Queue<ExpressionTree> expr = new Queue<ExpressionTree>();
        final Queue<Integer> op = new Queue<Integer>();

        ExpressionTree tree = this.product(level + 1);

        // parse iteratively
        while (this.lookahead() == Tokenizer.PLUS || this.lookahead() == Tokenizer.MINUS) {
            op.enqueue(this.lookahead());
          this.nextToken();
            expr.enqueue(this.product(level + 1));
        }

        // setup tree
        while (!op.isEmpty()) {
            if (op.dequeue() == Tokenizer.PLUS)
                tree = new ExpressionTree(new Plus(), tree, expr.dequeue());
            else
                tree = new ExpressionTree(new Minus(), tree, expr.dequeue());
        }
        return tree;
    }

    /**
     * parse product<p>
     * <pre><code>
     * product ::= factor |
     * product '*' factor | product '/' factor
     * </code></pre>
     */
    @Override
    protected ExpressionTree product(final int level) {
      this.verbose(level, "<product>");

        // temporary storage
        final Queue<ExpressionTree> expr = new Queue<ExpressionTree>();
        final Queue<Integer> op = new Queue<Integer>();

        ExpressionTree tree = this.factor(level + 1);

        // parse iteratively
        while (this.lookahead() == Tokenizer.TIMES || this.lookahead() == Tokenizer.DIVIDE) {
            op.enqueue(this.lookahead());
          this.nextToken();
            expr.enqueue(this.factor(level + 1));
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
}
