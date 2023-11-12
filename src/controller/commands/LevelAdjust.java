package controller.commands;

import java.util.Map;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class LevelAdjust implements RgbImageCommand {
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {

    if (arguments.length != 6 && arguments.length != 8){
      throw new IllegalArgumentException("Invalid arguments for the command levels adjust");
    }

    String imageName = arguments[4];
    String destImageName = arguments[5];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);
    RgbImageModel destImage;

    int b = Integer.parseInt(arguments[1]);
    int m = Integer.parseInt(arguments[2]);
    int w = Integer.parseInt(arguments[3]);

    if (arguments.length == 6){
      destImage = imageList.get(imageName).adjustLevels(b,m,w);
    } else if (arguments[6].equals("split")){
      double splitP = Double.parseDouble(arguments[7]);
      RgbImageModel left = image.cropVertical(0,splitP);
      left = left.adjustLevels(b,m,w);
      destImage = image.overlapOnBase(left,0);
    } else {
      throw new IllegalArgumentException("Invalid arguments for the command levels adjust");
    }
    imageList.put(destImageName, destImage);
  }
}
