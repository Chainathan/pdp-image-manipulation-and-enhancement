package view;

import java.io.IOException;

/**
 * The TextView class implements the ImageProcessorView interface
 * and represents a text-based view for displaying image processing results.
 */
public class TextView implements ImageProcessorView {
  private final Appendable out;

  /**
   * Constructs a TextView with the specified output destination.
   *
   * @param out The output destination (e.g., a stream or writer)
   *            for displaying text-based results.
   */
  public TextView(Appendable out) {
    this.out = out;
  }

  @Override
  public void display(String msg) throws IOException {
    out.append(msg).append("\n");
  }
}
