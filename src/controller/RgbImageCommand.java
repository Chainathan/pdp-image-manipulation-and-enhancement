package controller;

import model.RgbImageModel;
import java.util.Map;

/**
 * The RgbImageCommand interface defines a contract for commands that manipulate RGB images.
 * Implementations of this interface should provide an execute method that performs the
 * specified image manipulation using the provided image list and command arguments.
 */
public interface RgbImageCommand {

  /**
   * Executes the RGB image manipulation command.
   *
   * @param imageList  A map containing named RGB images to be manipulated.
   * @param arguments  An array of command-specific arguments required for the execution.
   * @throws IllegalArgumentException If the command execution encounters invalid arguments.
   */
  void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException;

/*  static RgbImageModel splitHelper(RgbImageModel image,
          Function<RgbImageModel,RgbImageModel> fun,
          String split){
    double splitP = Double.parseDouble(split);
    RgbImageModel left = image.cropVertical(0,splitP);
    left = fun.apply(left);
    return image.overlapOnBase(left,0);
  }

  static void executeHelper(Function<RgbImageModel, RgbImageModel> fun,
                            int defaultArg, String[] arguments,
                            Map<String,RgbImageModel> imageList,
                            boolean supportSplit) {
    if ((supportSplit && arguments.length != defaultArg && arguments.length != defaultArg + 2) ||
            (!supportSplit && arguments.length != defaultArg)) {
      throw new IllegalArgumentException("Invalid arguments for the command "+arguments[0]);
    }

    String imageName = arguments[defaultArg-2];
    String destImageName = arguments[defaultArg-1];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);
    RgbImageModel destImage;

    if (arguments.length == defaultArg){
      destImage = fun.apply(image);
    } else {
      if (arguments[defaultArg].equals("split")){
        destImage = RgbImageCommand.splitHelper(image, fun, arguments[arguments.length-1]);
      } else {
        throw new IllegalArgumentException("Invalid arguments for the command: "+arguments[0]);
      }
    }
    imageList.put(destImageName, destImage);
  }*/
}
