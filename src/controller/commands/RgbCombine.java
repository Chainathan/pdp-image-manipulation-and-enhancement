package controller.commands;

import java.util.Map;

import controller.AdvRgbController;
import controller.RgbImageCommand;
import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;

public class RgbCombine implements RgbImageCommand {
  private final FactoryRgbImageModel factoryRgbImageModel;

  public RgbCombine(FactoryRgbImageModel factoryRgbImageModel) {
    this.factoryRgbImageModel = factoryRgbImageModel;
  }

  @Override
  public void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException {

    if (arguments.length != 5){
      throw new IllegalArgumentException("Invalid arguments for the command rgb combine");
    }

    String[] imageNameList = new String[3];
    System.arraycopy(arguments,2,imageNameList,0,3);
    for (String imageName : imageNameList) {
      AdvRgbController.checkImageExists(imageList, imageName);
    }

    String destImage = arguments[arguments.length-4];
    ImageData red = imageList.get(imageNameList[0]).getImageData();
    ImageData green = imageList.get(imageNameList[1]).getImageData();
    ImageData blue = imageList.get(imageNameList[2]).getImageData();

    int[][][] newData = {red.getData()[0], green.getData()[1], blue.getData()[2]};
    ImageData newImageData = new ImageData(newData, red.getMaxValue());

    RgbImageModel newImage = factoryRgbImageModel.createImageModel();
    newImage.loadImageData(newImageData);
    imageList.put(destImage, newImage);
  }
}
