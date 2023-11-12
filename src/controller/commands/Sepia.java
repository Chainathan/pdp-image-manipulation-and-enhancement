package controller.commands;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class Sepia implements RgbImageCommand {
  private final double[][] buffer;
  private int defaultArg;
  private final Function<RgbImageModel,RgbImageModel> fun;
  public Sepia(){
    buffer = new double[][]{
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    defaultArg = 3;
    fun = (RgbImageModel rgb) -> rgb.applyTone(buffer);
  }

  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    if (arguments.length != defaultArg && arguments.length != defaultArg+2){
      throw new IllegalArgumentException("Invalid arguments for the command blur");
    }

    String imageName = arguments[defaultArg-2];
    String destImageName = arguments[defaultArg-1];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);
    RgbImageModel destImage;

    if (arguments.length == defaultArg){
      destImage = fun.apply(image);
    } else if (arguments[defaultArg].equals("split")){
      destImage = RgbImageCommand.splitHelper(image, fun, arguments[arguments.length-1]);
    } else {
      throw new IllegalArgumentException("Invalid arguments for the command blur");
    }
    imageList.put(destImageName, destImage);
  }
}
