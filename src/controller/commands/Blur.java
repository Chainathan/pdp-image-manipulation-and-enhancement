package controller.commands;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;
import java.util.Map;
import java.util.function.Function;

public class Blur implements RgbImageCommand {
  private final int defaultArg;
  private final Function<RgbImageModel,RgbImageModel> fun;
  public Blur(){
    double[][] kernel = new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
    defaultArg = 3;
    fun = (RgbImageModel rgb) -> rgb.applyFilter(kernel);
  }

  @Override
  public void execute(Map<String,RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
//    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList);
//    if (arguments.length != defaultArg && arguments.length != defaultArg+2){
//      throw new IllegalArgumentException("Invalid arguments for the command blur");
//    }
//
//    String imageName = arguments[defaultArg-2];
//    String destImageName = arguments[defaultArg-1];
//
//    AdvRgbController.checkImageExists(imageList, imageName);
//    RgbImageModel image = imageList.get(imageName);
//    RgbImageModel destImage;
//
//    if (arguments.length == defaultArg){
//      destImage = fun.apply(image);
//    } else if (arguments[defaultArg].equals("split")){
//      destImage = RgbImageCommand.splitHelper(image, fun, arguments[arguments.length-1]);
//    } else {
//      throw new IllegalArgumentException("Invalid arguments for the command blur");
//    }
//    imageList.put(destImageName, destImage);
  }
}
