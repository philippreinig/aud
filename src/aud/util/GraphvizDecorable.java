package aud.util;

/** Interface for decorating items of {@link Graphvizable} objects.
    @see Graphvizable
*/
public interface GraphvizDecorable {

  /** get decoration or {@code null} */
  GraphvizDecorator getDecorator();
}
