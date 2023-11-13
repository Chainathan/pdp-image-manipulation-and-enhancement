package controller.commands.old;

import java.util.Map;
import java.util.function.Function;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.ComponentEnum;
import model.RgbImageModel;

public class VisualizeComponent implements RgbImageCommand {
  private final int defaultArg;
  private final Function<RgbImageModel,RgbImageModel> fun;
  private final boolean supportSplit;
  public VisualizeComponent(int defaultArg, Function<RgbImageModel, RgbImageModel> fun,
                            boolean supportSplit) {
    this.defaultArg = defaultArg;
    this.fun = fun;
    this.supportSplit = supportSplit;
  }

  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList,supportSplit);

    int defaultArg = 3;
    Function<RgbImageModel,RgbImageModel> fun = (RgbImageModel rgb) -> {
      ComponentEnum component = ComponentEnum.fromString(arguments[0]);
      return rgb.visualizeComponent(component);
    };
    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList,false);
  }
}
