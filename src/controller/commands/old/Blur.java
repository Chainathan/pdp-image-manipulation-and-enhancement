package controller.commands.old;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;
import java.util.Map;
import java.util.function.Function;

public class Blur implements RgbImageCommand {
  @Override
  public void execute(Map<String,RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    int defaultArg = 3;
    Function<RgbImageModel,RgbImageModel> fun = RgbImageModel::blur;;
    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList,true);
  }
}
