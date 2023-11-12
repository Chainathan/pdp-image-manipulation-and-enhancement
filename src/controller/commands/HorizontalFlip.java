package controller.commands;

import java.util.Map;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class HorizontalFlip implements RgbImageCommand {
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    if (arguments.length != 3){
      throw new IllegalArgumentException("Invalid arguments for the command horizontal flip");
    }

    String imageName = arguments[arguments.length-2];
    String destImageName = arguments[arguments.length-1];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);

    RgbImageModel destImage = imageList.get(imageName).horizontalFlip();

    imageList.put(destImageName, destImage);
  }
}
