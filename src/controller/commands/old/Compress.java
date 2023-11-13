package controller.commands.old;

import java.util.Map;
import java.util.function.Function;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class Compress implements RgbImageCommand {
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    int defaultArg = 4;
    Function<RgbImageModel,RgbImageModel> fun = (RgbImageModel rgb) -> {
      double compressRatio = Double.parseDouble(arguments[defaultArg-3]);
      return rgb.applyCompression(compressRatio);
    };
    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList,false);
  }
}
