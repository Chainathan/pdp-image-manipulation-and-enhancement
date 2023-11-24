import controller.*;
import model.FactoryRgbImage;
import model.FactoryRgbImageModel;
import view.*;

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
   * @param args Command-line arguments (not used in this example).
   */
  public static void main(String[] args) {
    runGUI();
  }
  private static void runGUI(){
    FactoryRgbImageModel factory = new FactoryRgbImage();
    GuiView view = new JFrameViewSplit("Image Processor");
    GuiControllerSplit controller = new GuiControllerSplit(factory, view);
    try {
      controller.run();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
  private static void runText(String[] args){
    Readable in = new InputStreamReader(System.in);
    Appendable out = System.out;
    FactoryRgbImageModel factory = new FactoryRgbImage();
    ImageProcessorView textView = new TextView(out);
    CommandMapper commandMapper = new CommandMapperAdv();
    try {
      if (args.length == 2 && args[0].equals("-file")) {
        in = new StringReader("run \"" + args[1] + "\"");
      } else if (args.length != 0) {
        textView.display("Invalid arguments");
        System.exit(-1);
      } else {
        in = new InputStreamReader(System.in);
      }

      RgbController controller = new RgbController(factory, textView, in, commandMapper);
      controller.run();

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}

