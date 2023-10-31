package Model;

import DAO.ImageData;
import Exceptions.FileFormatNotSupportedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RgbImageProcessor implements ImageProcessorModel {
  private final Map<String, RgbImageModel> imageList;

  public RgbImageProcessor(){
    imageList = new HashMap<>();
  }
  @Override
  public void addImage(String destImageName, ImageData imageData)
          throws IllegalArgumentException {
    checkValidImageName(destImageName);
    RgbImageModel imageModel = new RgbImage();
    imageModel.loadImageData(imageData);
    imageList.put(destImageName, imageModel);
    System.out.println(imageData.getMaxValue());
  }

  @Override
  public ImageData getImageData(String imageName) throws IllegalArgumentException,
          FileFormatNotSupportedException {
    checkImageNameExists(imageName);
    RgbImageModel imageModel = imageList.get(imageName);
    return imageModel.getImageData();
  }

  @Override
  public void visualizeComponent(String imageName, String destImageName, String component)
          throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    ComponentEnum componentEnum = ComponentEnum.fromString(component);

    RgbImageModel destImage = imageList.get(imageName).visualizeComponent(componentEnum);
    imageList.put(destImageName, destImage);
  }

  @Override
  public void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).horizontalFlip();
    imageList.put(destImageName, destImage);
  }

  @Override
  public void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).verticalFlip();
    imageList.put(destImageName, destImage);
  }

  @Override
  public void brighten(String imageName, String destImageName, int increment)
          throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).brighten(increment);
    imageList.put(destImageName, destImage);
  }

  @Override
  public void darken(String imageName, String destImageName, int decrement)
          throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).darken(decrement);
    imageList.put(destImageName, destImage);
  }

  @Override
  public void splitComponents(String imageName, List<String> destComponentImageList)
          throws IllegalArgumentException {
    destComponentImageList.forEach(RgbImageProcessor::checkValidImageName);
    checkImageNameExists(imageName);
    if (destComponentImageList.size() != 3) {
      throw new IllegalArgumentException("Invalid list of Destination images");
    }
    RgbImageModel rgbImageModel = imageList.get(imageName);
    ImageData imageData = rgbImageModel.getImageData();
    ChannelModel red = new Channel(imageData.getData()[0]);
    ChannelModel green = new Channel(imageData.getData()[1]);
    ChannelModel blue = new Channel(imageData.getData()[2]);

    RgbImageModel newRed = new RgbImage(
            red,
            new Channel(red.getHeight(), red.getWidth()),
            new Channel(red.getHeight(), red.getWidth()),
            imageData.getMaxValue());
    RgbImageModel newGreen = new RgbImage(
            new Channel(green.getHeight(), green.getWidth()),
            green,
            new Channel(green.getHeight(), green.getWidth()),
            imageData.getMaxValue());
    RgbImageModel newBlue = new RgbImage(
            new Channel(blue.getHeight(), blue.getWidth()),
            new Channel(blue.getHeight(), blue.getWidth()),
            blue,
            imageData.getMaxValue());

    imageList.put(destComponentImageList.get(0), newRed);
    imageList.put(destComponentImageList.get(1), newGreen);
    imageList.put(destComponentImageList.get(2), newBlue);
  }

  @Override
  public void combineComponents(String destImageName, List<String> componentImageList)
          throws IllegalArgumentException {
    componentImageList.forEach(this::checkImageNameExists);
    checkValidImageName(destImageName);
    if (componentImageList.size() != 3) {
      throw new IllegalArgumentException("Invalid list of Component images");
    }
    ImageData red = imageList.get(componentImageList.get(0)).getImageData();
    ImageData green = imageList.get(componentImageList.get(1)).getImageData();
    ImageData blue = imageList.get(componentImageList.get(2)).getImageData();

    int[][][] newData = {red.getData()[0], green.getData()[1], blue.getData()[2]};
    ImageData newImageData = new ImageData(newData, red.getMaxValue());

    RgbImageModel newImage = new RgbImage();
    newImage.loadImageData(newImageData);
    imageList.put(destImageName, newImage);
  }

  @Override
  public void blur(String imageName, String destImageName) throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).blur();
    imageList.put(destImageName, destImage);
  }

  @Override
  public void sharpen(String imageName, String destImageName) throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).sharpen();
    imageList.put(destImageName, destImage);
  }

  @Override
  public void sepia(String imageName, String destImageName) throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).sepia();
    imageList.put(destImageName, destImage);
  }

  private static void checkValidImageName(String imageName) throws IllegalArgumentException {
    if (imageName.trim().isEmpty()) {
      throw new IllegalArgumentException("Invalid image name");
    }
  }

  private void checkImageNameExists(String imageName) throws IllegalArgumentException {
    if (!imageList.containsKey(imageName)) {
      throw new IllegalArgumentException("Image does not exist");
    }
  }
}
