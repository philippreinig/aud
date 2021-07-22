package aud.example.graph;

import aud.util.LexicalScanner;

import java.util.regex.Pattern;

/** Breaks input string into pieces ("tokens").<p>
    @see GraphParser
 */
public class Tokenizer extends LexicalScanner {

  protected static final Pattern P_LEFTBRACE= Pattern.compile("\\[");
  protected static final Pattern P_RIGHTBRACE = Pattern.compile("\\]");
  protected static final Pattern P_LEFTPAREN = Pattern.compile("\\(");
  protected static final Pattern P_RIGHTPAREN = Pattern.compile("\\)");
  protected static final Pattern P_EDGE = Pattern.compile("--");
  protected static final Pattern P_DEDGE = Pattern.compile("->");
  protected static final Pattern P_AT = Pattern.compile("@");
  protected static final Pattern P_COMMA = Pattern.compile(",");

  public static final int LEFT_BRACE='[';
  public static final int RIGHT_BRACE=']';
  public static final int LEFT_PAREN='(';
  public static final int RIGHT_PAREN=')';
  public static final int AT='@';
  public static final int COMMA=',';

  public static final int IDENTIFIER=0x100;
  public static final int NUMBER=0x101;
  public static final int EDGE=0x102;
  public static final int DEDGE=0x103;


  protected static final LexicalScanner.Rule[] RULES =
  {
    new LexicalScanner.Rule(EDGE,P_EDGE),
    new LexicalScanner.Rule(DEDGE,P_DEDGE),
    new LexicalScanner.Rule(AT,P_AT),
    new LexicalScanner.Rule(COMMA,P_COMMA),
    new LexicalScanner.Rule(LEFT_BRACE,P_LEFTBRACE),
    new LexicalScanner.Rule(RIGHT_BRACE,P_RIGHTBRACE),
    new LexicalScanner.Rule(LEFT_PAREN,P_LEFTPAREN),
    new LexicalScanner.Rule(RIGHT_PAREN,P_RIGHTPAREN),
    new LexicalScanner.Rule(NUMBER,LexicalScanner.P_FLOAT),
    new LexicalScanner.Rule(IDENTIFIER,LexicalScanner.P_IDENTIFIER)
  };

  /** create new tokenizer for {@code input} */
  public Tokenizer(String input) {
    super(RULES,input);
  }
}
