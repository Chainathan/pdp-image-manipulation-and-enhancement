package controller;

import model.RgbImageModel;
import java.util.Map;
import java.util.function.Function;

public interface RgbImageCommand {
  void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException;

  static RgbImageModel splitHelper(RgbImageModel image,
          Function<RgbImageModel,RgbImageModel> fun,
          String split){
    double splitP = Double.parseDouble(split);
    RgbImageModel left = image.cropVertical(0,splitP);
    left = fun.apply(left);
    return image.overlapOnBase(left,0);
  }

  static void executeHelper(Function<RgbImageModel, RgbImageModel> fun,
                            int defaultArg, String[] arguments,
                            Map<String,RgbImageModel> imageList,
                            boolean supportSplit) {
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
        destImage = RgbImageCommand.splitHelper(image, fun, arguments[arguments.length-1]);
      } else {
        throw new IllegalArgumentException("Invalid arguments for the command: "+arguments[0]);
      }
    }
    imageList.put(destImageName, destImage);
  }
}
