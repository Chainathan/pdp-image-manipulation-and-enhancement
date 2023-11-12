package controller.commands;

import java.util.Map;
import java.util.function.Function;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class ColorCorrect implements RgbImageCommand {
  private final int defaultArg;
  private final Function<RgbImageModel,RgbImageModel> fun;
  public ColorCorrect(){
    defaultArg = 3;
    fun = (RgbImageModel rgb) -> rgb.correctColor();
  }
  @Override
  public void execute(Map<String,RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {

    if (arguments.length != defaultArg && arguments.length != defaultArg+2){
      throw new IllegalArgumentException("Invalid arguments for the command color correct");
    }

    String imageName = arguments[defaultArg-2];
    String destImageName = arguments[defaultArg-1];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);
    RgbImageModel destImage;

    if (arguments.length == defaultArg){
      destImage = imageList.get(imageName).correctColor();
    } else if (arguments[defaultArg].equals("split")){
      destImage = RgbImageCommand.splitHelper(image, fun, arguments[arguments.length-1]);
    } else {
      throw new IllegalArgumentException("Invalid arguments for the command color correct");
    }
    imageList.put(destImageName, destImage);
  }
}
