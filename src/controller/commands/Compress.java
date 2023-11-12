package controller.commands;

import java.util.Map;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.RgbImageModel;

public class Compress implements RgbImageCommand {
  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {
    int defaultArg = 4;
    if (arguments.length != defaultArg){
      throw new IllegalArgumentException("Invalid arguments for the command compress");
    }

    String imageName = arguments[defaultArg-2];
    String destImageName = arguments[defaultArg-1];

    AdvRgbController.checkImageExists(imageList, imageName);
    RgbImageModel image = imageList.get(imageName);

    double compressRatio = Double.parseDouble(arguments[defaultArg-3]);

    RgbImageModel destImage = imageList.get(imageName).applyCompression(compressRatio);
    imageList.put(destImageName, destImage);
  }
}
