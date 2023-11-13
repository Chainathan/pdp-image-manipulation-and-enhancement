package controller.commands.old;

import java.util.Map;
import java.util.function.Function;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class LevelAdjust implements RgbImageCommand {
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {

    int defaultArg = 6;

    Function<RgbImageModel,RgbImageModel> fun = (RgbImageModel rgb) -> {
      int b = Integer.parseInt(arguments[defaultArg-5]);
      int m = Integer.parseInt(arguments[defaultArg-4]);
      int w = Integer.parseInt(arguments[defaultArg-3]);
      return rgb.adjustLevels(b,m,w);
    };

    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList,true);
  }
}
