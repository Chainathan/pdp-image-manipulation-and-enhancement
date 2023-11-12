package controller.commands;

import java.util.Map;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.ComponentEnum;
import model.RgbImageModel;

public class RgbSplit implements RgbImageCommand {
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

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);

    RgbImageModel newRed = image.visualizeComponent(ComponentEnum.RED);
    RgbImageModel newGreen = image.visualizeComponent(ComponentEnum.GREEN);
    RgbImageModel newBlue = image.visualizeComponent(ComponentEnum.BLUE);

    imageList.put(redName, newRed);
    imageList.put(greenName, newGreen);
    imageList.put(blueName, newBlue);
  }
}
