package View;

import java.io.IOException;

/**
 * The interface represents a view for displaying messages.
 * Classes implementing this interface should provide a way to display messages.
 */
public interface ImeTextView {

  /**
   * Displays the given message to the user.
   *
   * @param msg The message to display.
   * @throws IOException If an error occurs during the display process.
   */
  void display(String msg) throws IOException;

  /**
   * Displays the given message to the user without line break.
   *
   * @param msg The message to display.
   * @throws IOException If an error occurs during the display process.
   */
  void displayInLine(String msg) throws IOException;
}
