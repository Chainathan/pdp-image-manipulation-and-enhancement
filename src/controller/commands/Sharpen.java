package controller.commands;

import java.util.Map;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class Sharpen implements RgbImageCommand {
  private final double[][] kernel;

  public Sharpen(){
    kernel = new double[][]{
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
  }

  @Override
  public void execute(Map<String,RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    String imageName = arguments[1];
    String destImageName = arguments[2];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);
    RgbImageModel destImage;

    if (arguments.length == 3){
      destImage = imageList.get(imageName).applyFilter(kernel);
    } else if (arguments.length == 5 && arguments[3].equals("split")){
      double splitP = Double.parseDouble(arguments[4]);
      RgbImageModel left = image.cropVertical(0,splitP);
      left = left.applyFilter(kernel);
      destImage = image.overlapOnBase(left,0);
    } else {
      throw new IllegalArgumentException("Invalid arguments for the command blur");
    }
    imageList.put(destImageName, destImage);
  }
}
