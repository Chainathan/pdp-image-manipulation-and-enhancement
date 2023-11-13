package controller.commands.old;

import java.util.Map;
import java.util.function.Function;

import controller.RgbImageCommand;
import model.RgbImageModel;

public class Sepia implements RgbImageCommand {
  private final int defaultArg;
  private final Function<RgbImageModel,RgbImageModel> fun;
  private final boolean supportSplit;
  public Sepia(int defaultArg, Function<RgbImageModel, RgbImageModel> fun, boolean supportSplit) {
    this.defaultArg = defaultArg;
    this.fun = fun;
    this.supportSplit = supportSplit;
  }

  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList,supportSplit);
  }
}
