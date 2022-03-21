package aud.example.grid;

import aud.PriorityQueue;
import aud.Queue;
import aud.Stack;
import aud.util.Terminal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Comparator;

// Grid2.java is same as Grid.java but A^* handles tunnels correctly!
// - Must preprocess: discover all tunnels.
// - Fix heuristic  (there are other/better options).

/**
 * Undirected graph that is defined implicitly by a regular 2d
 * grid.<p>
 * <p>
 * The graph is defined by 4-neighborhoods (north,east,south,west) on
 * a 2-dimensional grid. You can imagine the grid as a "pixel image"
 * with each pixel represented as a {@link #Cell}.
 * <p>
 * For this graph, we <b>don't</b> use the {@link AbstractGraph}
 * interface.
 * <p>
 * We use simple "ASCII images" for input and visualization. Note
 * that the output uses <a
 * href="http://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape
 * codes</a> for displaying colors. Colors will be displayed
 * correctly only on a "command window" with a terminal
 * emulation. This is typically provided by any <b>Unix
 * terminal</b>. (It will <em>not</em> work with the standard Windows
 * command prompt {@code cmd.exe}! Use the <tt>-no-color</tt> option
 * in this case.)
 */
public class Grid {

    /**
     * example grid (argument to {@link #setup}
     */
    public final static String EXAMPLE =
            "+-+         +----- --------+    +----+\n" +
                    "| |  +------+----- ---+    |         |\n" +
                    "|           |  A      |    |    |    |\n" +
                    "| |  +------+----- ---+    |    |\n" +
                    "+-+         |                   +----+\n" +
                    "            +----- --------+\n" +
                    "\n" +
                    "                                          +\n";
    /**
     * delay in {@link #display} in milliseconds
     */
    public int delay = 100;
    int num_rows_ = 0;
    int num_columns_ = 0;
    Cell[] cell_ = null;
    int num_opened_ = 0;
    boolean show_parent_ = false;
    boolean ansi_term_ = true;
    int frame_ = 0;
    private aud.HashMap<Character, int[]> tunnels_ = null;

    ;

    /**
     * construct an empty grid, use {@link #read_file} or {@link #setup}
     */
    public Grid() {
    }

    ;

    /**
     * construct grid by calling {@link #read_file}
     */
    public Grid(final String filename) {
      this.read_file(filename);
    }

    ;

    /**
     * print help message and exit
     */
    protected static void usage() {
        System.err.println
                ("java aud.example.graph.TraversalExample " +
                        "[-g file] [-m method] [-s s0] [-d s1] [-v N]\n" +
                        " -g FILE read grid from FILE\n" +
                        " -m METHOD traversal {dfs,idfs,bfs,dijkstra,astar}\n" +
                        " -p show parent cell by an arrow (requires color)\n" +
                        " -s START start node (one character, default: 'A')\n" +
                        " -d DESTINATION destination node for A* (default: none)\n" +
                        " -delay MILLISECONDS set delay after each step\n" +
                        " -no-color don't use ANSI codes\n" +
                        " -color force use of ANSI codes (requires terminal emulation)\n" +
                        " -show display initial grid and exit"
                );
        System.exit(-1);
    }

    public static void main(final String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // make sure we are leaving cleanly on SIGINT
                Terminal.instance.reset();
                Terminal.instance.showCursor();
                Terminal.instance.out.flush();
            }
        });


        final Grid g = new Grid();

        // prcess argumentys
        String method = "dfs";
        char start = 'A', destination = 'A';

        for (int i = 0; i < args.length; ++i) {
            if (args[i].compareTo("-g") == 0) {
                g.read_file(args[++i]);
                if (g.size() == 0) {
                    System.err.println("error: grid is empty");
                    System.exit(-1);
                }
            } else if (args[i].compareTo("-m") == 0)
                method = args[++i];
            else if (args[i].compareTo("-s") == 0)
                start = args[++i].charAt(0);
            else if (args[i].compareTo("-d") == 0)
                destination = args[++i].charAt(0);
            else if (args[i].compareTo("-delay") == 0)
                g.delay = Integer.parseInt(args[++i]);
            else if (args[i].compareTo("-p") == 0)
                g.show_parent_ = true;
            else if (args[i].compareTo("-no-color") == 0)
                Terminal.instance = new Terminal(false);
            else if (args[i].compareTo("-color") == 0)
                Terminal.instance = new Terminal(true);
            else if (args[i].compareTo("-show") == 0)
                method = null;
            else
                usage();
        }

        if (g.size() == 0)
            g.setup(EXAMPLE); // use default grid

        g.display();

        if (method == null) // show only
            System.exit(0);

        // find source node (or exit)
        final int s0 = g.find_first(start);

        // find destination node if given (or exit)
        final int s1 = (destination != start ? g.find_first(destination) : -1);

        if (method.compareTo("dfs") == 0)
            g.dfs_recursive(s0);
        else if (method.compareTo("idfs") == 0)
            g.dfs_iterative(s0);
        else if (method.compareTo("bfs") == 0)
            g.bfs(s0);
        else if (method.compareTo("dijkstra") == 0)
            g.dijkstra(s0, s1);
        else if (method.compareTo("astar") == 0) {
            if (s1 < 0) {
                System.err.println("A* requires destination, use '-d'");
                System.exit(-1);
            }
            g.astar(s0, s1);
        } else
            usage();

    }

    /**
     * read file and treat contents as cells, calls {@link #setup}
     */
    public void read_file(final String filename) {
        final StringBuilder sb = new StringBuilder();
        try {
            final FileReader fr = new FileReader(filename);
            final BufferedReader in = new BufferedReader(fr);
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (final Exception e) {
            System.err.println("error while reading '" + filename + "'");
            System.err.println(e.toString());
            System.exit(-1);
        }
      this.setup(sb.toString());
    }

    /**
     * setup grid from string: create contents as grid cells
     */
    public void setup(final String[] lines) {
      this.num_rows_ = lines.length;
      this.num_columns_ = 0;      // find maximum number of columns
        for (final String line : lines) {
          this.num_columns_ = (this.num_columns_ < line.length() ? line.length() : this.num_columns_);
        }

      this.cell_ = new Cell[this.size()];
        for (int i = 0; i < this.size(); ++i)
          this.cell_[i] = new Cell();

        int i = 0;
        for (final String line : lines) {
            for (int j = 0; j < line.length(); ++j) {
              this.cell(i, j).value = line.charAt(j);
            }
            ++i;
        }
    }

    /**
     * setup from multi-line string, e.g., {@link #EXAMPLE}
     */
    public void setup(final String text) {
      this.setup(text.split("\\n"));
    }

    /**
     * get number of rows
     */
    public int num_rows() {
        return this.num_rows_;
    }

    /**
     * get number of columns
     */
    public int num_columns() {
        return this.num_columns_;
    }

    /**
     * get total number of cells
     */
    public int size() {
        return this.num_rows_ * this.num_columns_;
    }

    /**
     * get cell index from coordinates
     */
    public int cell_index(final int i, final int j) {
        assert (0 <= i && i < this.num_rows_);
        assert (0 <= j && j < this.num_columns_);
        return i * this.num_columns_ + j;
    }

    /**
     * get row from cell index
     */
    public int get_row(final int _index) {
        return _index / this.num_columns_;
    }

    /**
     * get columns from cell index
     */
    public int get_column(final int _index) {
        return _index % this.num_columns_;
    }

    /**
     * get cell (i,j)
     */
    public Cell cell(final int i, final int j) {
        return this.cell_[this.cell_index(i, j)];
    }

    /**
     * get neighbors of cell {@code index} (4-neighborhood)
     */
    public int[] neighbors(final int index) {
        final int i = this.get_row(index);
        final int j = this.get_column(index);
        int north = -1, east = -1, south = -1, west = -1, k = 0;

        // if (i>0)              { north=cell_index(i-1,j); ++k; }
        // if (j<num_columns_-1) { east =cell_index(i,j+1); ++k; }
        // if (i<num_rows_-1)    { south=cell_index(i+1,j); ++k; }
        // if (j>0)              { west =cell_index(i,j-1); ++k; }

        // equivalent but more efficient:
        if (i > 0) {
            north = index - this.num_columns_;
            ++k;
        }
        if (j < this.num_columns_ - 1) {
            east = index + 1;
            ++k;
        }
        if (i < this.num_rows_ - 1) {
            south = index + this.num_columns_;
            ++k;
        }
        if (j > 0) {
            west = index - 1;
            ++k;
        }

        int[] nhd = new int[k];
        k = 0;

        final int[] tunnel_nhd = this.neighbors_from_tunnels(index);
        if (tunnel_nhd.length > 1) {
            // copy tunnels to nhd, but filter this cell index
            nhd = new int[nhd.length + tunnel_nhd.length - 1];
            for (int n = 0; n < tunnel_nhd.length; ++n) {
                if (tunnel_nhd[n] != index) {
                    nhd[k++] = tunnel_nhd[n];
                }
            }
        }

        if (north >= 0) nhd[k++] = north;
        if (east >= 0) nhd[k++] = east;
        if (south >= 0) nhd[k++] = south;
        if (west >= 0) nhd[k++] = west;

        return nhd;
    }

    /**
     * get neighbors of {@code index} from tunnels
     */
    protected int[] neighbors_from_tunnels(final int index) {
        final Cell cell = this.cell_[index];

        if (!cell.is_tunnel())
            return new int[0];

        if (this.tunnels_ == null)
          this.tunnels_ = new aud.HashMap<Character, int[]>();

        int[] nhd = this.tunnels_.find(cell.value);

        if (nhd == null) {
            nhd = this.create_tunnels_for(cell);
        }

        return nhd;
    }

    /**
     * create tunnels for specified entry at cell
     */
    protected int[] create_tunnels_for(final Cell cell) {
        assert cell.is_tunnel() : "expect tunnel entry";
        // count ...
        int n = 0;
        for (int i = 0; i < this.cell_.length; ++i) {
            if (this.cell_[i].value == cell.value)
                ++n;
        }
        final int[] nhd = new int[n];

        // ... save
      this.tunnels_.insert(cell.value, nhd);

        // .. and copy
        n = 0;
        for (int i = 0; i < this.cell_.length; ++i) {
            if (this.cell_[i].value == cell.value)
                nhd[n++] = i;
        }
        return nhd;
    }

    /**
     * Get weight for edge {@code (s,t)}.
     * <p>
     * We use a simple cost function:
     * <ul>
     * <li>each edge has weight 1, which is the distance between cells,</li>
     *
     * <li>an <b>unless</b> one cells has a <em>digit</em> {@code
     * 0,...,0} as value; in this case the absolute difference between
     * digits (with non-digit values counting as {@code 0}) is added.</li>
     * </ul>
     * <p>
     * You can think of the image as a height field with ground level
     * {@code 0} and height levels up to {@code 9}. Then the steeper
     * the ascend the larger the weight/cost. It makes no difference if
     * we are going uphill or downhill. (If you want to change this,
     * make sure that cost remain positive!)
     */
    double weight(final int s, final int t) {
        final char cs = this.cell_[s].value;
      final char ct = this.cell_[t].value;

      final double s_level = Character.isDigit(cs) ? Character.getNumericValue(cs) : 0.0;
        final double t_level = Character.isDigit(ct) ? Character.getNumericValue(ct) : 0.0;

        return Math.abs(t_level - s_level) + 1.0;
    }

    /**
     * find first cell in grid with {@code value}
     *
     * @return cell index or <b>exit program</b>.
     */
    public int find_first(final char value) {
        for (int i = 0; i < this.size(); ++i)
            if (this.cell_[i].value == value)
                return i;

        Terminal.instance.reset();
        System.err.println("Could not find a cell with value='" + value + "'.");
        System.exit(-1);

        return -1;
    }

    /**
     * display current grid
     */
    public void display() {
        final Terminal term = Terminal.instance;

        term.cls(this.frame_++ == 0);
        term.fg(Terminal.BLACK);
        term.hideCursor();

        for (int i = 0; i < this.num_rows(); ++i) {

            for (int j = 0; j < this.num_columns(); ++j) {
                final Cell c = this.cell(i, j);

                term.bg(c.is_free() ? c.color : Terminal.BLUE);

                char ch = c.value == '\000' ? ' ' : c.value;

                // display characters for colors
                if (term.dumb() && ch == ' ') {
                    switch (c.color) {
                        case Terminal.RED:
                            ch = '&';
                            break;
                        case Terminal.BLUE:
                            ch = '@';
                            break;
                        case Terminal.GREEN:
                            ch = '$';
                            break;
                        case Terminal.YELLOW:
                            ch = '~';
                            break;
                        case Terminal.CYAN:
                            ch = '*';
                            break;
                        case Terminal.MAGENTA:
                            ch = '.';
                            break;
                        case Terminal.BLACK:
                            ch = '%';
                            break;
                        case Terminal.WHITE:
                        default:
                            break;
                    }
                } else if (this.show_parent_) {
                    if (i > 0 && c.p == this.cell_index(i - 1, j)) ch = '↓';
                    else if (i < this.num_rows_ && c.p == this.cell_index(i + 1, j)) ch = '↑';
                    else if (j > 0 && c.p == this.cell_index(i, j - 1)) ch = '→';
                    else if (j < this.num_columns_ && c.p == this.cell_index(i, j + 1)) ch = '←';
                }

                term.out.print(ch);
            }
            term.out.println();
        }
        term.showCursor();
        term.reset();

        term.out.flush();
        try {
            Thread.sleep(this.delay);
        } catch (final InterruptedException e) {
        }
    }

    /**
     * reset all cells to default state but keep their values
     */
    public void reset_cells() {
        for (int i = 0; i < this.size(); ++i) {
            final Cell c = this.cell_[i];
          this.cell_[i] = new Cell();
          this.cell_[i].value = c.value;
        }
    }

    /**
     * recusive DFS
     */
    public void dfs_recursive(final int s) {
      this.cell_[s].color = Terminal.CYAN; // start processing s0
      this.cell_[s].d = 0.0;
      this.display();
        for (final int t : this.neighbors(s)) {
            if (this.cell_[t].color == Terminal.WHITE && this.cell_[t].is_free()) {

              this.cell_[t].d = this.cell_[s].d + this.weight(s, t);
              this.cell_[t].p = s;

              this.dfs_recursive(t);
            }
        }
      this.cell_[s].color = Terminal.MAGENTA; // done with s0
      this.display();
    }

    /**
     * iterative DFS
     */
    public void dfs_iterative(final int s0) {
        final Stack<Integer> open = new Stack<Integer>();

        open.push(s0);
      this.cell_[s0].color = Terminal.CYAN;  // start processing
      this.cell_[s0].d = 0.0;
      this.display();

        while (!open.isEmpty()) {
            final int s = open.pop();

            for (final int t : this.neighbors(s)) {
                if (this.cell_[t].color == Terminal.WHITE && this.cell_[t].is_free()) {

                  this.cell_[t].color = Terminal.CYAN; // start processing
                  this.cell_[t].d = this.cell_[s].d + this.weight(s, t);
                  this.cell_[t].p = s;

                    open.push(t);
                }
            }
          this.cell_[s].color = Terminal.MAGENTA;
          this.display();
        }
    }

    /**
     * BFS
     */
    public void bfs(final int s0) {
        final Queue<Integer> open = new Queue<Integer>();

        open.enqueue(s0);
      this.cell_[s0].color = Terminal.CYAN;  // start processing
      this.cell_[s0].d = 0.0;
      this.display();

        while (!open.isEmpty()) {
            final int s = open.dequeue();

            for (final int t : this.neighbors(s)) {
                if (this.cell_[t].color == Terminal.WHITE && this.cell_[t].is_free()) {

                  this.cell_[t].color = Terminal.CYAN; // start processing
                  this.cell_[t].d = this.cell_[s].d + this.weight(s, t);
                  this.cell_[t].p = s;

                    open.enqueue(t);
                }
            }
          this.cell_[s].color = Terminal.MAGENTA;
          this.display();
        }
    }

    /**
     * Dijkstra
     */
    public void dijkstra(final int s0, final int s1) {

        final PriorityQueue<Integer> open =
                new PriorityQueue<Integer>(this.size(), new CompareCellDistanceFromIndex(this));

        open.push(s0);
        ++this.num_opened_;
      this.cell_[s0].color = Terminal.CYAN;  // start processing
      this.cell_[s0].d = 0.0;
      this.display();

        while (!open.is_empty()) {
            final int s = open.pop();

            for (final int t : this.neighbors(s))
                if (this.cell_[t].is_free()) {

                    final double priority = this.cell_[s].d + this.weight(s, t);

                    if (Double.isInfinite(this.cell_[t].d)) {

                        assert (!open.contains(t));

                      this.cell_[t].color = Terminal.CYAN; // start processing
                      this.cell_[t].p = s;
                      this.cell_[t].d = priority;

                        open.push(t);
                        ++this.num_opened_;
                    } else if (this.cell_[t].d > priority && open.contains(t)) {
                      this.cell_[t].color = Terminal.YELLOW; // got updated
                      this.cell_[t].p = s;
                      this.cell_[t].d = priority;

                        open.lower(t); // adapt priority queue to new, lower priority
                    }
                }

          this.cell_[s].color = Terminal.MAGENTA;
          this.display();

            if (s == s1) {
              this.backtrack(s);
                break;
            }
        }
    }

    /**
     * estimate distance (we use city block norm due to 4-neighborhood)
     */
    double estimate_distance(final int s, final int t) {
        final int dx = this.get_column(s) - this.get_column(t);
        final int dy = this.get_row(s) - this.get_row(t);

        return Math.abs((double) dx) + Math.abs((double) dy);
    }

    /**
     * A*
     */
    public void astar(final int s0, final int s1) {

        final PriorityQueue<Integer> open =
                new PriorityQueue<Integer>
                        (this.size(), new CompareCellEstimatedDistanceFromIndex(this));

        open.push(s0);
        ++this.num_opened_;
      this.cell_[s0].color = Terminal.CYAN;  // start processing
      this.cell_[s0].d = 0.0;
      this.cell_[s0].f = this.estimate_distance(s0, s1); // initial guess
      this.display();

        while (!open.is_empty()) {
            final int s = open.pop();

            for (final int t : this.neighbors(s))
                if (this.cell_[t].is_free()) {

                    final double distance = this.cell_[s].d + this.weight(s, t);

                    final double priority = distance + this.estimate_distance(t, s1);

                    if (Double.isInfinite(this.cell_[t].d)) {

                        assert (!open.contains(t));

                      this.cell_[t].color = Terminal.CYAN; // start processing
                      this.cell_[t].p = s;
                      this.cell_[t].d = distance;
                      this.cell_[t].f = priority;

                        open.push(t);
                        ++this.num_opened_;
                    } else if (this.cell_[t].d > distance && open.contains(t)) {
                      this.cell_[t].color = Terminal.YELLOW; // got updated
                      this.cell_[t].p = s;
                      this.cell_[t].d = distance;
                      this.cell_[t].f = priority;

                        open.lower(t); // adapt priority queue to new, lower priority
                    }
                }

          this.cell_[s].color = Terminal.MAGENTA;
          this.display();

            if (s == s1) {
              this.backtrack(s);
                break;
            }
        }
    }

    /**
     * backtrack and {@link #display} path to source
     */
    public void backtrack(int s) {
        assert (this.cell_[s].p >= 0);
        final double d = this.cell_[s].d;
        while (s >= 0) {
          this.cell_[s].color = Terminal.GREEN;
            s = this.cell_[s].p;
          this.display();
        }
        Terminal.instance.out.println("distance = " + d);
        if (this.num_opened_ > 0)
            Terminal.instance.out.println("examined " + this.num_opened_ + " cells in total");
    }

    /**
     * Cell in a {@link Grid}.
     * <p>
     * Cells have a {@code char value} that is displayed. In addition,
     * cells with the following values can be accessed: white space,
     * letters, and digits. Any other values will be interpreted as
     * "obstacles".
     * <p>
     * The {@code color} attribute can be used for visualization and as
     * a flag (e.g., cell was processed). Note: Whether color can be
     * displayed depends on {@link Terminal}.
     * <p>
     * The remaining attributes are used during traversal (see
     * lecture).
     */
    public class Cell {
        /**
         * the value that is displayed
         */
        public char value = '\000';
        /**
         * indexed color, see {@link Terminal}
         */
        public int color = Terminal.WHITE;
        /**
         * distance to source
         */
        public double d = Double.POSITIVE_INFINITY;
        /**
         * priority used by  A* (distance + estimated distance to destination
         */
        public double f = Double.POSITIVE_INFINITY;
        /**
         * index of parent cell
         */
        public int p = -1;

        /**
         * Can we enter this cell?
         */
        public boolean is_free() {
            return this.value == '\000' || this.value == '.' || this.value == '*' ||
                    Character.isWhitespace(this.value) ||
                    Character.isLetterOrDigit(this.value);
        }

        /**
         * letters a-z denote entries to tunnels: e.g., all 'a' are connected
         */
        public boolean is_tunnel() {
            return 'a' <= this.value && this.value <= 'z';
        }
    }

    /**
     * comparator for {#link aud.PriorityQueue} compares {@link Cell#d}
     */
    class CompareCellDistanceFromIndex implements Comparator<Integer> {
        Grid grid;

        CompareCellDistanceFromIndex(final Grid g) {
          this.grid = g;
        }

        @Override
        public int compare(final Integer a, final Integer b) {
            final Cell ca = this.grid.cell_[a.intValue()];
          final Cell cb = this.grid.cell_[b.intValue()];
          final double d = ca.d - cb.d;
            return (d < 0.0) ? -1 : (d > 0.0 ? +1 : 0);
        }
    }

    /**
     * comparator for {#link aud.PriorityQueue} compares {@link Cell#f}
     */
    class CompareCellEstimatedDistanceFromIndex implements Comparator<Integer> {
        Grid grid;

        CompareCellEstimatedDistanceFromIndex(final Grid g) {
          this.grid = g;
        }

        @Override
        public int compare(final Integer a, final Integer b) {
            final Cell ca = this.grid.cell_[a.intValue()];
          final Cell cb = this.grid.cell_[b.intValue()];
          final double d = ca.f - cb.f;
            return (d < 0.0) ? -1 : (d > 0.0 ? +1 : 0);
        }
    }
}
