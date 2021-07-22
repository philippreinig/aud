package aud.example.dp;

import aud.Vector;
import java.io.*;
import java.awt.Color;
import java.awt.image.*;
import javax.imageio.*;

/** Simple implementation of <em>seam crving</em>.<p>

    Seam carving is a method for resizing images such that (important)
    content is not scaled or distorted. Instead, unimportant content
    is removed, by removing seams of connected pixels in vertical or
    horizontal direction. The computation of "optimal" seams, i.e.,
    the decision which pixels are to be removed, is based on <strong>
    Dynamic Programming</strong>.<p>

    This demo implements part of the original article

    Shai Avidan and Ariel Shamir.
    <em>Seam carving for content-aware image resizing</em>,
    SIGGRAPH 2007

    with the following main restrictions:

    <ul>
    <li>We remove only vertical seams, i.e., reduce the width
    of the image.</li>
    <li>We remove seams one by one. There are more efficient
    implementations. (Hint: Use the {@code index_map_}.)</li>
    <li>The importance map is generated from gradient magnitude
    using a Sobel operator. (This is not part of the core
    algorithm.)</li>
    </ul>
 */
public class SeamCarvingDemo {
  /** the input image (will not be changed) */
  final BufferedImage image_;
  /** input width */
  final int width_;
  /** input height*/
  final int height_;

  /** number if seams that were computed and removed */
  int ncuts_ = 0;

  /** Assign importance value (low 0, high: 1) to each pixel.
      Array uses same indexing as {@code index_map_}.
   */
  float[] importance_ = null;

  /** linear indices of pixels in input image that remain after removing seams
   */
  int[] index_map_ = null;

  /** Store linear indices of removed pixels for visualizing seams.
      Seams are stored in order, any value {@code -1} indicates the start of a new seam.
  */
  Vector<Integer> seams_ = new Vector<Integer>();

  /** Initialize seam carving on input {@code image}.
   */
  SeamCarvingDemo(BufferedImage image) {
    image_ = image;
    width_ = image.getWidth();
    height_ = image.getHeight();

    compute_importance();

    // index_map_ stores indices of "remaining" pixels
    index_map_ = new int[width_ * height_];
    for (int i=0; i < width_*height_; ++i) {
      index_map_[i] = i;
    }
  }

  /** Apply {@code ncuts} iterations of seam carving.
      This reduces the width of the output by {@code ncuts}.
   */
  void apply(int ncuts) {
    seams_.reserve((height_+1) * ncuts_);

    for (int n=0; n < ncuts; ++n) {
      compute_seam();
      remove_seam();
    }
  }

  /** Compute a single seam.

      This is the <strong>Dynamic Programming</strong> part with
      backtracking.
   */
  void compute_seam() {
    // This simple version assume that remove_cuts() has been called!

    int current_width = width_ - ncuts_;

    // Note: Not "reusing" parent and importance arrays for multiple cuts is
    //       simple but inefficient.
    int size = current_width * height_;
    int[] parent = new int[size];
    for (int k=0; k<size; ++k)
      parent[k] = -2;
    float[] sums = new float[size];
    for (int i=0; i<size; ++i)
      sums[i] = importance_[i];

    //
    // compute sums bottom up
    //
    for (int j=1; j < height_; ++j) {
      for (int i=0; i < current_width; ++i) {
        int index = j * current_width + i;
        assert index_map_[index] >= 0 : "assume earlier are removed";
        int up = index - current_width;
        float[] values = {
          i > 0 ? sums[up-1] : Float.POSITIVE_INFINITY,
          sums[up],
          i < current_width-1 ? sums[up+1] : Float.POSITIVE_INFINITY
        };
        int imin = values[0] < values[1] ? 0 : 1;
        imin = values[imin] <= values[2] ? imin : 2;

        int direction = imin - 1; // -1: left, 0: center, +1: right
        parent[index] = direction;
        sums[index] += sums[up + direction];
      }
    }

    //
    // find minimum ...
    //

    int imin = sums.length - current_width;
    for (int k = imin + 1; k<sums.length; ++k) {
      if (sums[imin] > sums[k])
        imin = k;
    }

    //
    // ... and backtrack
    //

    int index = imin;
    while (parent[index] != -2) {
      seams_.push_back(index_map_[index]);
      index_map_[index] = -1;
      int from = parent[index];

      index -= current_width;
      index += from;
    }
    seams_.push_back(index_map_[index]);
    index_map_[index] = -1;
    seams_.push_back(-1); // next seam
  }

  /** Remove seam and reduce importance_ and index_map_.

      Note that removing seamss one by one is inefficient due
      to streaming the whole data. -- But it simplifies the algorithm.
  */
  void remove_seam() {
    int current_width = width_ - ncuts_;

    int next_width = current_width - 1;

    float[] next_importance = new float[next_width * height_];
    int[] next_index_map = new int[next_width * height_];

    int j = 0;
    for (int i=0; i < index_map_.length; ++i) {
      if (index_map_[i] >= 0) {
        next_index_map[j] = index_map_[i];
        next_importance[j] = importance_[i];
        ++j;
      }
    }

    importance_ = next_importance;
    index_map_ = next_index_map;
    ++ncuts_;
  }

  /** Compute importance matrix from edge detection on image_.
      Uses Sobel filter for edge detection.
   */
  void compute_importance() {
    importance_ = new float[width_ * height_];

    float[] sobel_x = { -1.0f,  0.0f,  1.0f,
                        -2.0f,  0.0f,  2.0f,
                        -1.0f,  0.0f,  1.0f };

    float[] sobel_y = { -1.0f, -2.0f, -1.0f,
                         0.0f,  0.0f,  0.0f,
                         1.0f,  2.0f,  1.0f };

    Kernel dx = new Kernel(3, 3, sobel_x);
    Kernel dy = new Kernel(3, 3, sobel_y);

    BufferedImage image_dx = new ConvolveOp(dx).filter(image_, null);
    BufferedImage image_dy = new ConvolveOp(dy).filter(image_, null);

    float[] components = { 0.0f, 0.0f, 0.0f };
    int index = 0;
    for (int j=0; j < height_; ++j) {
      for (int i=0; i < width_; ++i) {
        float vx = gray(image_dx.getRGB(i, j), components);
        float vy = gray(image_dy.getRGB(i, j), components);
        float value = (float) Math.sqrt(vx*vx + vy*vy);

        importance_[index++] = Math.min(1.0f, value);
      }
    }
  }

  /** Convert integer RGB value to gray scale.
      Use preallocated storage rgb.
   */
  static float gray(int irgb, float[] rgb) {
    new Color(irgb).getColorComponents(rgb);
    float value =  0.2126f*rgb[0] + 0.7152f*rgb[1] + 0.0722f*rgb[2];
    return Math.min(1.0f, value);
  }

  /** Write output image to file {@code path}.
   */
  void write_result(String path) {
    int width_result = width_ - ncuts_;

    BufferedImage result =
        new BufferedImage(width_result, height_, BufferedImage.TYPE_INT_RGB);

    int index = 0;
    for (int k=0; k<index_map_.length; ++k) {
      if (index_map_[k] >= 0) {
        int si = index_map_[k] % width_;    // source pixel
        int sj = index_map_[k] / width_;
        int di = index % width_result;      // destination pixel
        int dj = index / width_result;
        ++index;

        result.setRGB(di, dj, image_.getRGB(si, sj));
      }
    }

    write_image(result, path);
  }

  /** Write an image file {@code path} with color coded seams.
   */
  void dump_seams(String path) {
    BufferedImage result =
        new BufferedImage(width_, height_, BufferedImage.TYPE_INT_RGB);

    for (int j=0; j<height_; ++j) {
      for (int i=0; i<width_; ++i) {
        result.setRGB(i, j, image_.getRGB(i, j));
      }
    }

    Color color = Color.RED;
    for (int index : seams_) {
      if (index >= 0) {
        int i = index % width_;
        int j = index / width_;
        result.setRGB(i, j, color.getRGB());
      }
      else {
        // color code seams in their order
        // red -> darkred -> green -> darkgreen -> blue -> drakblue
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        if (g == 63 && b > 63)
          b -= 1;
        if (g == 63 && b == 0)
          b = 255;
        if (r == 63 && g > 63)
          g -= 1;
        if (r == 63 && g == 0)
          g = 255;
        if (r > 63)
          r -= 1;

        color = new Color(r, g, b);
      }
    }

    write_image(result, path);
  }

  void dump_importance(String path) {
    BufferedImage dump =
        new BufferedImage(width_, height_, BufferedImage.TYPE_INT_RGB);
    int index = 0;
    for (int j=0; j < height_; ++j) {
      for (int i=0; i < width_; ++i) {
        float v = importance_[index++];
        int rgb = new Color(v, v, v).getRGB();
        dump.setRGB(i, j, rgb);
      }
    }
    write_image(dump, path);
  }

  /** Write {@code image} to file {@code path}.
   */
  void write_image(BufferedImage image, String path) {
    try {
      File ofile = new File(path);
      String format = "png"; // PNG is default

      if (path.toLowerCase().endsWith(".jpg"))
        format = "jpg";
      else if (path.toLowerCase().endsWith(".gif"))
        format = "gif";

      ImageIO.write(image, format, ofile);
    } catch (IOException e) {
      System.err.println(e);
      System.exit(-1);
    }
  }

  /** print help message and exit */
  protected static void usage() {
    System.err.println
      ("usage: java aud.example.dp.SeamCarvingDemo IMAGE N OUTPUT " +
       "[DUMP-SEAMS] [DUMP-IMPORTANCE]]\n\n" +
       "Read IMAGE and shrink horizontally by removing N vertical seams.\n" +
       "If the filename DUMP-SEAMS is specified, write an image that " +
       "shows the generated seams.\n" +
       "If the filename DUMP-IMPORTANCE is specified, the generated " +
       "importance map is saved to this file.\n"
       );
    System.exit(-1);
  }

  public static void main(String[] args) {
    if (args.length < 3)
      usage();

    try {
      BufferedImage image = ImageIO.read(new File(args[0]));
      int n = Integer.parseInt(args[1]);

      int nmax = image.getWidth() - 1;
      n = Math.max(0, Math.min(nmax, n));
      SeamCarvingDemo seam_carving = new SeamCarvingDemo(image);

      if (args.length > 4)
        seam_carving.dump_importance(args[4]);

      seam_carving.apply(n);
      seam_carving.write_result(args[2]);

      if (args.length > 3)
        seam_carving.dump_seams(args[3]);

    } catch(IOException e) {
      System.err.println(e);
      System.exit(-1);
    }
  }
}
