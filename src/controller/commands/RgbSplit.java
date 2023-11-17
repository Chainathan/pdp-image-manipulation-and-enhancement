package controller.commands;

import java.util.Map;

import controller.RgbController;
import controller.RgbImageCommand;
import controller.ComponentEnum;
import model.RgbImageModel;

/**
 * The RgbSplit class implements the RgbImageCommand interface and represents a command
 * to split an RGB image into its red, green, and blue components, creating new images
 * for each component in the provided image list.
 */
public class RgbSplit implements RgbImageCommand {

  /**
   * Executes the RGB split command on the provided image list with the given arguments.
   * Splits an RGB image into its red, green, and blue components, creating new images
   * for each component in the image list.
   *
   * @param imageList The map of image names to corresponding RGB image models.
   * @param arguments The arguments for the command, including the input image name and
   *                  names for the new red, green, and blue component images.
   * @throws IllegalArgumentException If the execution encounters invalid number of arguments for split command.
   */
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {

    if (arguments.length != 5){
      throw new IllegalArgumentException("Invalid arguments for the command rgb split");
    }

    String imageName = arguments[arguments.length-4];

    String redName = arguments[arguments.length-3];
    String greenName = arguments[arguments.length-2];
    String blueName = arguments[arguments.length-1];

    RgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);

    RgbImageModel newRed = image.visualizeComponent(ComponentEnum.RED);
    RgbImageModel newGreen = image.visualizeComponent(ComponentEnum.GREEN);
    RgbImageModel newBlue = image.visualizeComponent(ComponentEnum.BLUE);

    imageList.put(redName, newRed);
    imageList.put(greenName, newGreen);
    imageList.put(blueName, newBlue);
  }
}
