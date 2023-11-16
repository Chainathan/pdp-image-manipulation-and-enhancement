package controller.commands;

import java.util.Map;
import java.util.function.Function;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class GenericCommand implements RgbImageCommand {
  private final int defaultArg;
  private final Function<RgbImageModel,RgbImageModel> fun;
  private final boolean supportSplit;
  public GenericCommand(int defaultArg, Function<RgbImageModel, RgbImageModel> fun,
                  boolean supportSplit) throws IllegalArgumentException{
    if (defaultArg<=0){
      throw new IllegalArgumentException("Invalid Default arguments for the Command");
    }
    this.defaultArg = defaultArg;
    this.fun = fun;
    this.supportSplit = supportSplit;
  }

  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    if ((supportSplit && arguments.length != defaultArg && arguments.length != defaultArg + 2) ||
            (!supportSplit && arguments.length != defaultArg)) {
      throw new IllegalArgumentException("Invalid arguments for the command "+arguments[0]);
    }

    String imageName = arguments[defaultArg-2];
    String destImageName = arguments[defaultArg-1];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);
    RgbImageModel destImage;

    if (arguments.length == defaultArg){
      destImage = fun.apply(image);
    } else {
      if (arguments[defaultArg].equals("split")){
        destImage = splitHelper(image, fun, arguments[arguments.length-1]);
      } else {
        throw new IllegalArgumentException("Invalid arguments for the command: "+arguments[0]);
      }
    }
    imageList.put(destImageName, destImage);
  }
  private RgbImageModel splitHelper(RgbImageModel image,
                                   Function<RgbImageModel,RgbImageModel> fun,
                                   String split){
    double splitP = Double.parseDouble(split);
    RgbImageModel left = image.cropVertical(0,splitP);
    left = fun.apply(left);
    return image.overlapOnBase(left,0);
  }
}
