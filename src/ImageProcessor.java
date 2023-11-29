import controller.RgbController;
import controller.GuiController;
import controller.CommandMapper;
import controller.CommandMapperAdv;
import controller.ImageController;
import model.FactoryRgbImage;
import model.FactoryRgbImageModel;
import view.GuiView;
import view.ImageProcessorView;
import view.JFrameView;
import view.TextView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * The ImageProcessor class represents the main entry point
 * for an image processing application. It initializes the necessary
 * components and runs the image processing controller.
 */
public class ImageProcessor {

  /**
   * The main method of the application that initializes the components
   * and runs the image processing controller.
   *
   * @param args Command-line arguments.
   */
  public static void main(String[] args) {
    Readable in;
    Appendable out = System.out;
    FactoryRgbImageModel factory = new FactoryRgbImage();
    ImageProcessorView textView = new TextView(out);
    CommandMapper commandMapper = new CommandMapperAdv();
    ImageController controller;
    try {
      if (args.length == 2 && args[0].equals("-file")) {
        in = new StringReader("run \"" + args[1] + "\"");
        controller = new RgbController(factory, textView, in, commandMapper);
        controller.run();
      } else if (args.length == 1 && args[0].equals("-text")) {
        in = new InputStreamReader(System.in);
        controller = new RgbController(factory, textView, in, commandMapper);
        controller.run();
      } else if (args.length == 0) {
        GuiView view = new JFrameView("Image Processor");
        controller = new GuiController(factory, view);
        controller.run();
      } else {
        System.out.println("Invalid arguments");
        System.exit(-1);
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}

