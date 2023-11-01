import Controller.RgbController;
import Model.ImageProcessorModel;
import Model.RgbImageProcessor;
import View.ImeTextView;
import View.TextView;

import java.io.IOException;
import java.io.InputStreamReader;

public class ImageProcessor {
  public static void main(String[] args) {
    ImageProcessorModel model = new RgbImageProcessor();
    ImeTextView textView = new TextView(System.out);
    RgbController controller = new RgbController(model, textView, new InputStreamReader(System.in));
    try {
      controller.run();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
