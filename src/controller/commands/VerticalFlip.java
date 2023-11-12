package controller.commands;

import java.util.Map;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class VerticalFlip implements RgbImageCommand {
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    int defaultArg = 3;

    if (arguments.length != defaultArg){
      throw new IllegalArgumentException("Invalid arguments for the command Vertical Flip");
    }

    String imageName = arguments[defaultArg-2];
    String destImageName = arguments[defaultArg-1];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);

    RgbImageModel destImage = imageList.get(imageName).verticalFlip();

    imageList.put(destImageName, destImage);
  }
}
