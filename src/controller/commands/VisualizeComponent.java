package controller.commands;

import java.util.Map;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.ComponentEnum;
import model.RgbImageModel;

public class VisualizeComponent implements RgbImageCommand {
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {

    if (arguments.length != 3){
      throw new IllegalArgumentException("Invalid arguments for the command visualize component");
    }

    String imageName = arguments[arguments.length-2];
    String destImageName = arguments[arguments.length-1];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);

    ComponentEnum component = ComponentEnum.fromString(arguments[0]);
    RgbImageModel destImage = imageList.get(imageName).visualizeComponent(component);

    imageList.put(destImageName, destImage);
  }
}
