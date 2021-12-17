package aud.util;

import java.io.*;
import java.util.regex.Pattern;

import aud.Vector;
import aud.BinaryTree;
import aud.example.expr.SyntaxError;

public class ParseTree {
  static class Node {
    Vector<String> keys = null;
    Vector<Node> children = null;

    Node(Vector<String> keys,
         Vector<Node> children) {
      this.keys = keys;
      this.children = children;

      assert (keys != null) && (children != null) && !keys.empty();
      if (children.size() > keys.size() + 1) {
        throw new SyntaxError("Too many children for '" + keys + "': " +
                              "got " + children.size() + ", expect <= " +
                              (keys.size() + 1));
      }
    }
    Node(String key) {
      keys = new Vector<String>();
      children = new Vector<Node>();
      keys.push_back(key);
    }

    @Override
    public String toString() {
      String result = "(";

      if (keys.size() > 1) {
        result+="("+String.join(" ",keys)+")";
      }
      else {
        result+=keys.front();
      }

      // for (Node c : children) {
      //   result += " " + (c == null ? "()" : c);
      // }

      for (int i=0; i <= keys.size(); ++i) {
        if (i >= children.size() || children.at(i) == null)
          result += " ()";
        else
          result += " " + children.at(i);
      }

      return result+")";
    }
  }

  Tokenizer scanner_ = null;
  Node tree_ = null;

  int arity_ = 0;
  boolean verbose_ = false;

  static public class Tokenizer extends LexicalScanner {

    protected static final Pattern P_LEFTPAREN = Pattern.compile("\\(");
    protected static final Pattern P_RIGHTPAREN = Pattern.compile("\\)");
    protected static final Pattern P_KEY =
        Pattern.compile("[_a-zA-Z0-9]?(\\w|[_/:])+");

    public static final int LEFT_PAREN='(';
    public static final int RIGHT_PAREN=')';
    public static final int KEY=0x100;

    protected static final LexicalScanner.Rule[] RULES= {
      new LexicalScanner.Rule(LEFT_PAREN, P_LEFTPAREN),
      new LexicalScanner.Rule(RIGHT_PAREN, P_RIGHTPAREN),
      new LexicalScanner.Rule(KEY, P_KEY)
    };

    public Tokenizer(String input) {
      super(RULES, input);
    }
  }

  ParseTree(String input) {
    tree_ = parse(input);
  }

  public int arity() {
    return arity_;
  }

  public boolean isEmpty() { return tree_ == null; }

  public boolean isBinary() {
    return arity_<=2;
  }

  Node parse(String input) {
    scanner_ = new Tokenizer(input);
    scanner_.next(); // lookahead

    expect(Tokenizer.LEFT_PAREN, "(");

    Node tree = parse_tree();

    expect(Tokenizer.END_OF_INPUT,"<end of input>");

    return tree;
  }

  protected int lookahead() { return scanner_.matchedTokenId(); }

  protected void nextToken() {
    if (scanner_.next() == Tokenizer.NO_MATCH)
      throw new SyntaxError("unknown token: lexcial analysis failed at '"
                            +scanner_.remainder()+"'");
  }

  protected String matchedText() { return scanner_.matchedText(); }

  protected void expect(int tokenid, String text) {
    if (lookahead() != tokenid)
      throw new SyntaxError("expected '"+text+"' got '"+
			    scanner_.matchedText()+"'\nbefore '"+
			    scanner_.remainder() +"'");
  }

  Node parse_tree() {
    if (verbose_)
      System.err.println("parse_tree: " + "'" +
                         matchedText() + "' " + scanner_.remainder());

    if (lookahead() == Tokenizer.KEY) {
      String key = matchedText();
      nextToken();

      return new Node(key);
    }

    expect(Tokenizer.LEFT_PAREN, "(");
    nextToken();

    if (lookahead() == Tokenizer.RIGHT_PAREN) {
      nextToken();

      return null; // empty tree
    }

    Vector<String> keys = parse_keys();
    int arity = keys.size() + 1;
    this.arity_=arity>this.arity_ ? arity : this.arity_;

    Vector<Node> children = parse_children();

    expect(Tokenizer.RIGHT_PAREN, ")");
    nextToken();

    return new Node(keys, children);
  }

  Vector<String> parse_keys() {
    if (verbose_)
      System.err.println("parse_keys: " + "'" +
                         matchedText() + "' " + scanner_.remainder());

    Vector<String> keys = new Vector<String>();

    if (lookahead() == Tokenizer.LEFT_PAREN) {
      nextToken();

      do {
        keys.push_back(parse_key());
      } while (lookahead() == Tokenizer.KEY);

      expect(Tokenizer.RIGHT_PAREN, ") [after keys]");
      nextToken();
    }
    else {
      keys.push_back(parse_key());
    }

    assert !keys.empty() : "got >= 1 keys";

    return keys;
  }

  String parse_key() {
    if (verbose_)
      System.err.println("parse_key: " + "'" +
                         matchedText() + "' " + scanner_.remainder());

    expect(Tokenizer.KEY, "key or node id");
    String result = matchedText();
    nextToken();
    return result;
  }

  Vector<Node> parse_children() {
    if (verbose_)
      System.err.println("parse_children: " + "'" +
                         matchedText() + "' " + scanner_.remainder());

    Vector<Node> children = new Vector<Node>();

    while (lookahead() == Tokenizer.LEFT_PAREN || lookahead() == Tokenizer.KEY) {
      children.push_back(parse_tree());
    }

    return children;
  }

  public BinaryTree<String> toBinaryTree() {
    if (!isBinary()) {
      throw new RuntimeException("Cannot convert " + arity_ +
                                 "-ary tree to BinaryTree!");
    }

    return toBinaryTree(tree_);
  }

  BinaryTree<String> toBinaryTree(Node node) {
    if (node == null) {
      return null ;
    }
    assert node.keys.size()==1;

    Node left = node.children.size() > 0 ? node.children.at(0) : null;
    Node right = node.children.size() > 1 ? node.children.at(1) : null;

    return new BinaryTree<String>(node.keys.front(),
                                  toBinaryTree(left), toBinaryTree(right));
  }

  static String readFromInputStream(InputStream inputStream)
      throws IOException {
    StringBuilder resultStringBuilder = new StringBuilder();
    try (BufferedReader br
         = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      while ((line = br.readLine()) != null) {
        resultStringBuilder.append(line).append("\n");
      }
    }
    return resultStringBuilder.toString();
  }

  public static void usage() {
    System.err.println("java ParseTree [options] [tree]\n\n" +
                       "Parses Lisp-like tree representation. " +
                       "Binary trees can be visualized.\n\n" +
                       "A tree is represented as follows\n " +
                       "- \"()\" is an empty (sub-)tree.\n" +
                       "- \"(a)\" is a tree with a single root node " +
                       "labeled \"a\".\n" +
                       "- A label of a node is indeed a key or a list " +
                       "of keys.\n" +
                       "  Its length determines the maximum number of " +
                       "children for the node,\n" +
                       "  and recursively the arity of the tree.\n" +
                       "  A binary tree has only nodes with a single " +
                       "  key or empty nodes.\n" +
                       "  Lists of keys are represented as (a b ...):\n" +
                       "  the list (a b c) defines a node with three keys\n" +
                       "  and a maximum of four children.\n" +
                       "- A key is a string (without quotes), use \"/\" to" +
                       "indicate\n  extra attributes, e.g., \"a/r\" for " +
                       "  a red node \"a\" in a Red-Black-tree.\n" +
                       "- Finally, a tree is a list of a key (or list of keys)\n" +
                       "  followed by a list of its children/(sub-)trees.\n" +
                       "- Each child is a tree (...) or a single label\n" +
                       "  without parentheses representing a single binary " +
                       "node.\n" +
                       "\nOptions:\n\n" +
                       "  -h | --help   show this message\n" +
                       "  -s | --show   visualize binary tree\n" +
                       "  -a | --arity  print arity (maximum number of children)\n" +
                       "  -p | --print  print tree\n" +
                       "  -i | --input FILE\n" +
                       "                read tree from FILE, \"-i -\" means " +
                       "read from\n" +
                       "                standard input\n\n" +
                       "If -i is not specified, all non-options are concatenated " +
                       "as input string.\nKeep in mind that you need to quote " +
                       "parenthesis on the command line!\n\n" +
                       "The program prints a syntax error if the input is " +
                       "not a valid tree.");

    System.exit(-1);
  }

  public static void main(String[] args) {
    String input = "";
    String filename = null;
    boolean show = false;
    boolean arity = false;
    boolean print = false;

    for (int i=0;i<args.length;++i) {
      String arg = args[i];

      if (arg.equals("-h") || arg.equals("--help")) {
        usage();
      }
      else if (arg.equals("-s") || arg.equals("--show")) {
        show = true;
      }
      else if (arg.equals("-p") || arg.equals("--print")) {
        print = true;
      }
      else if (arg.equals("-a") || arg.equals("--arity")) {
        arity = true;
      }
      else if (arg.equals("-i") || arg.equals("--input")) {
        if (i+1 == args.length) {
          System.err.println("Missing file name after " + arg + "!");
          System.exit(-1);
        }
        filename = args[++i];
      }
      else {
        input += arg + ' ';
      }
    }
    if (filename != null && input.length()>0) {
      System.err.println("Conflicting arguments: " +
                         "cannot specify -i and tree on command line.");
      System.exit(-1);
    }

    if (filename != null) {
      InputStream in = null;
      try {
        in = filename.equals("-") ?
              System.in : new FileInputStream(new File(filename));

        input = readFromInputStream(in);
      } catch (IOException error) {
        System.err.println(error);
        System.exit(-1);
      }
      finally {
        try { in.close(); } catch (IOException error) {}
      }
    }

    try {
      ParseTree instance = new ParseTree(input);

      if (print) {
        System.out.println(instance.tree_);
      }
      if (arity) {
        System.out.println("arity: " + instance.arity());
      }

      if (show) {
        if (!instance.isBinary()) {
          System.err.println("Can only show binary trees!");
          System.exit(-1);
        }

        if (instance.isEmpty()) {
          System.err.println("Empty tree. Nothing to show.");
        }
        else {
          BinaryTree<String> binary_tree = instance.toBinaryTree();
          DotViewer.displayWindow(binary_tree,"BinaryTree").setExitOnClose();
        }
      }
    } catch(SyntaxError error) {
      System.err.println(error);
      System.exit(-1);
    } catch (RuntimeException error) {
      System.err.println(error);
      System.exit(-1);
    }
  }
}
