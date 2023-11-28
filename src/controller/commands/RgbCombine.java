package controller.commands;

import java.util.Map;

import controller.RgbController;
import controller.RgbImageCommand;
import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;

/**
 * The RgbCombine class implements the RgbImageCommand interface and represents a command
 * to combine multiple RGB images into a single RGB image using a provided factory for
 * creating new image models.
 */
public class RgbCombine implements RgbImageCommand {
  private final FactoryRgbImageModel factoryRgbImageModel;

  /**
   * Constructs an RgbCombine command with the specified factory for creating new image models.
   *
   * @param factoryRgbImageModel The factory for creating new RGB image models.
   */
  public RgbCombine(FactoryRgbImageModel factoryRgbImageModel) {
    this.factoryRgbImageModel = factoryRgbImageModel;
  }

  /**
   * Executes the RGB combine command on the provided image list with the given arguments.
   * Combines multiple RGB images into a single RGB image using the provided factory.
   *
   * @param imageList The map of image names to corresponding RGB image models.
   * @param arguments The arguments for the command.
   * @throws IllegalArgumentException If the execution encounters invalid number of arguments
   *                                  for combine command.
   */
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {

    if (arguments.length != 5) {
      throw new IllegalArgumentException("Invalid arguments for the command rgb combine");
    }

    String[] imageNameList = new String[3];
    System.arraycopy(arguments, 2, imageNameList, 0, 3);
    for (String imageName : imageNameList) {
      RgbController.checkImageExists(imageList, imageName);
    }

    String destImage = arguments[arguments.length - 4];
    ImageData red = imageList.get(imageNameList[0]).getImageData();
    ImageData green = imageList.get(imageNameList[1]).getImageData();
    ImageData blue = imageList.get(imageNameList[2]).getImageData();

    int[][][] newData = {red.getData()[0], green.getData()[1], blue.getData()[2]};
    ImageData newImageData = new ImageData(newData, red.getMaxValue());

    RgbImageModel newImage = factoryRgbImageModel.createImageModel();
    newImage.loadImageData(newImageData);
    imageList.put(destImage, newImage);
  }
}
