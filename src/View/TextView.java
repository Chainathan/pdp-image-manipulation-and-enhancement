package View;

import java.io.IOException;
import java.io.PrintStream;

public class TextView implements ImeTextView {

  private final Appendable out;

  public TextView(Appendable out) {
    this.out = out;
  }

  @Override
  public void display(String msg) throws IOException {
    out.append(msg).append("\n");
  }

  @Override
  public void displayInLine(String msg) throws IOException {
    out.append(msg);
  }
}
