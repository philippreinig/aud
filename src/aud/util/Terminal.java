package aud.util;

import java.io.PrintStream;

public class Terminal {

  public static final int BLACK=0;
  public static final int RED=1;
  public static final int GREEN=2;
  public static final int YELLOW=3;
  public static final int BLUE=4;
  public static final int MAGENTA=5;
  public static final int CYAN=6;
  public static final int WHITE=7;

  public static final int HIGHLIGHT=8;

  public static final int HI_BLACK=8;
  public static final int HI_RED=9;
  public static final int HI_GREEN=10;
  public static final int HI_YELLOW=11;
  public static final int HI_BLUE=12;
  public static final int HI_MAGENTA=13;
  public static final int HI_CYAN=14;
  public static final int HI_WHITE=15;

  /** set foreground color */
  public static final String[] FGCOLOR={
    "\033[30m", // black
    "\033[31m", // red
    "\033[32m", // green
    "\033[33m", // yellow
    "\033[34m", // blue
    "\033[35m", // magenta
    "\033[36m", // cyan
    "\033[37m", // white
    "\033[90m", // hight intensity black
    "\033[91m", // hight intensity red
    "\033[92m", // hight intensity green
    "\033[93m", // hight intensity yellow
    "\033[94m", // hight intensity blue
    "\033[95m", // hight intensity magenta
    "\033[96m", // hight intensity cyan
    "\033[97m", // hight intensity white
  };
  /** set background color */
  public static final String[] BGCOLOR={
    "\033[40m", // black
    "\033[41m", // red
    "\033[42m", // green
    "\033[43m", // yellow
    "\033[44m", // blue
    "\033[45m", // magenta
    "\033[46m", // cyan
    "\033[47m", // white
    "\033[100m", // high intensity black
    "\033[101m", // high intensity red
    "\033[102m", // high intensity green
    "\033[103m", // high intensity yellow
    "\033[104m", // high intensity blue
    "\033[105m", // high intensity magenta
    "\033[106m", // high intensity cyan
    "\033[107m", // high intensity white
  };
  /** set bold text */
  public static final String NORMAL="\033[0m";
  /** set normal text */
  public static final String BOLD="\033[1m";

  /** clear screen and reset cursor position to upper left corner */
  public static final String CLEAR_SCREEN="\033[2J\033[1;1H";
  /** reset cursor position to upper left corner */
  public static final String HOME="\033[1;1H";

  /** show cursor */
  public static final String SHOW_CURSOR="\033[?25h";
  /** hide cursor */
  public static final String HIDE_CURSOR="\033[?25l";


  /** invert colors */
  public boolean invert_color=false;
  /** swap foreground and background */
  public boolean invert_foreground_background=false;

  /** output stream, user is responsible for flush */
  public PrintStream out=System.out;

  /** assume terminal emulation using ANSI escape codes */
  boolean ansi_=true;
  /** current foreground color */
  int cur_fg_ = -1;
  /** current background color */
  int cur_bg_ = -1;
  /** bold/normal font? */
  int cur_font_ = -1;

  /** Constructor.
      @param ansiassume terminal emulation is available
   */
  public Terminal(boolean ansi) {
    ansi_=ansi;
    if (!ansi)
      System.err.println("Assuming dumb terminal.");
  }
  /** Constructor: guess availability of terminal emulation.
      Simple rule: assume that Windows systems don't provide terminal
      emulation, all other systems do.
   */
  public Terminal() {
    this(!System.getProperty("os.name").startsWith("Windows"));
  }

  /** Is this a dumb terminal without colors? */
  public boolean dumb() { return !ansi_; }

  /** set foreground color */
  public void fg(int color) {
    assert(color>=0);

    if (!ansi_)
      cur_fg_=-1;
    else {
      String[] table=invert_foreground_background ? BGCOLOR : FGCOLOR;
      color=invert_color ? table.length-color%table.length : color;
      out.print(table[color]);
      cur_fg_=color;
    }
  }

  /** set background color */
  public void bg(int color) {
    assert(color>=0);

    if (!ansi_)
      cur_bg_=-1;
    else {
      String[] table=invert_foreground_background ? FGCOLOR : BGCOLOR;
      color=invert_color ? table.length-color%table.length : color;
      out.print(table[color]);
      cur_bg_=color;
    }
  }

  /** clear screen (for {@code clear=false}, move only cursor) */
  public void cls(boolean clear) {
    if (ansi_)
      out.print(clear ? CLEAR_SCREEN : HOME);
    else
      out.print("\n\n");
  }
  /** switch use of bold font */
  public void bold(boolean b) {
     if (ansi_)
       cur_font_=-1;
     else {
       boolean cur_b=(cur_font_==1);
       if (cur_b!=b) {
         out.print(b ? BOLD : NORMAL);
         cur_font_=b ? 1 : 0;
       }
     }
  }
  /** clear screen ({@code cls(true)}) */
  public void cls() { cls(true); }

  /** reset to black on white, normal font */
  public void reset() {
    fg(WHITE);
    bg(BLACK);
    bold(false);
  }

  /** show cursor */
  public void showCursor() {
    if (ansi_)
      out.print(SHOW_CURSOR);
  }
  /** hide cursor */
  public void hideCursor() {
    if (ansi_)
      out.print(HIDE_CURSOR);
  }

  /** singleton instance */
  public static Terminal instance = new Terminal();
};
