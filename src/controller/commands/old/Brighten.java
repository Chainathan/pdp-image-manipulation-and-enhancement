package controller.commands.old;

import java.util.Map;
import java.util.function.Function;

import controller.RgbImageCommand;
import model.RgbImageModel;

public class Brighten implements RgbImageCommand {
  private final int defaultArg;
  private final Function<RgbImageModel,RgbImageModel> fun;
  private final boolean supportSplit;
  public Brighten(int defaultArg, Function<RgbImageModel, RgbImageModel> fun,
                  boolean supportSplit) {
    this.defaultArg = defaultArg;
    this.fun = fun;
    this.supportSplit = supportSplit;
  }
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {

//    int defaultArg = 4;
//    Function<RgbImageModel,RgbImageModel> fun = (RgbImageModel rgb) -> {
//      int inc = Integer.parseInt(arguments[defaultArg-3]);
//      return rgb.brighten(inc);
//    };
    RgbImageCommand.executeHelper(fun,defaultArg,arguments,imageList,supportSplit);
  }
}
