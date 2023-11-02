import controller.RgbController;
import model.ImageProcessorModel;
import model.RgbImageProcessor;
import view.ImageProcessorView;
import view.TextView;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The ImageProcessor class represents the main entry point
 * for an image processing application. It initializes the necessary
 * components and runs the image processing controller.
 */
public abstract class ImageProcessor implements ImageProcessorModel {

  /**
   * The main method of the application that initializes the components
   * and runs the image processing controller.
   *
   * @param args Command-line arguments (not used in this example).
   */
  public static void main(String[] args) {
    ImageProcessorModel model = new RgbImageProcessor();
    ImageProcessorView textView = new TextView(System.out);
    RgbController controller = new RgbController(model, textView,
            new InputStreamReader(System.in));

    try {
      controller.run();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}

