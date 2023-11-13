package controller.commands.old;

import java.util.Map;
import java.util.function.Function;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class VerticalFlip implements RgbImageCommand {
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    int defaultArg = 3;
    Function<RgbImageModel,RgbImageModel> fun = RgbImageModel::verticalFlip;
    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList,false);
  }
}
