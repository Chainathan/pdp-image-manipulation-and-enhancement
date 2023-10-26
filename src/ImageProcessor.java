import java.io.IOException;

import Controller.RgbController;
import Model.ImageProcessorModel;
import Model.RgbImageProcessor;

public class ImageProcessor {
  public static void main(String[] args) {
    ImageProcessorModel model = new RgbImageProcessor();
    RgbController controller = new RgbController(model);
    try {
      controller.run();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
