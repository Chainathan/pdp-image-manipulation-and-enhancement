package view;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Junit test class for TextView.
 */
public class TextViewTest {

  @Test
  public void display() {
    //GIVEN
    StringBuilder out = new StringBuilder();
    ImageProcessorView textView = new TextView(out);

    //WHEN
    try {
      textView.display("Display message");
    } catch (IOException e) {
      fail("Should not have thrown error");
    }

    //THEN
    assertEquals("Display message\n", out.toString());
  }
}