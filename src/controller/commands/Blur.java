package controller.commands;

import controller.AdvRgbController;
import controller.RgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;
import java.util.Map;

public class Blur implements RgbImageCommand {
  private final double[][] blurKernel;

  public Blur(){
    blurKernel = new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
  }

  @Override
  public void execute(Map<String,RgbImageModel> imageList, String input)
          throws IllegalArgumentException {
    String[] arguments = input.split("\\s+");
    AdvRgbController.checkImageExists(imageList, arguments[1]);
    RgbImageModel image = imageList.get(arguments[1]);
    RgbImageModel destImage;

    if (arguments.length == 3){
      destImage = imageList.get(arguments[1]).applyFilter(blurKernel);
    } else if (arguments.length == 5 && arguments[3].equals("split")){
      double splitP = Double.parseDouble(arguments[4]);
      RgbImageModel left = image.cropVertical(0,splitP);
      left = left.applyFilter(blurKernel);
      destImage = image.overlapOnBase(left,0);
    } else {
      throw new IllegalArgumentException("Invalid arguments for the command blur");
    }
    imageList.put(arguments[2], destImage);
  }
}
