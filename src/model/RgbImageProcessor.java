package model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a rgb model that manages a set of rgb images associated with specific
 * and unique names and provides operations for performing various
 * manipulations on the available images.
 */
//public class RgbImageProcessor implements ImageProcessorModel<RgbImageModel> {
public class RgbImageProcessor implements ImageProcessorModel{
  final Map<String, RgbImageModel> imageList;
  /**
   * Constructs an empty set of rgb images with key as image name
   * and value as single rgb image.
   */
  public RgbImageProcessor() {
    imageList = new HashMap<>();
  }

  RgbImageModel createImageModel() {
    return new RgbImage();
  }

//  private RgbImageModel createImage() {
//    try {
//      // Assuming there is a default constructor for T
//      return (T) Class.forName("ConcreteRgbImage").getDeclaredConstructor().newInstance();
//    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
//      e.printStackTrace(); // Handle the exception according to your needs
//      return null;
//    }
//  }

  //  @Override
//  public RgbImageModel createModelInstance(){
//    return new RgbImage();
//  }

//  public T createModelInstance(){
//    return new RgbImage();
//  }
  @Override
  public void addImage(String destImageName, ImageData imageData)
          throws IllegalArgumentException {
    checkValidImageName(destImageName);
//    T image = imageList.get(destImageName);
//    if (image == null) {
//      image = getModel();
//    }
//    image.loadImageData(imageData);
//    imageList.put(destImageName, image);
//    T imageModel =  createModelInstance();

    RgbImageModel imageModel = this.createImageModel();//new RgbImage();
    imageModel.loadImageData(imageData);

    imageList.put(destImageName, imageModel);


//    RgbImageModel imageModel = create();
//    imageModel.loadImageData(imageData);
//    imageList.put(destImageName, imageModel);
  }

//  private T createInstance() {
//    try {
//      return (T) T.class.getDeclaredConstructor().newInstance();
//    } catch (Exception e) {
//      e.printStackTrace();
//      return null;
//    }
//  }

//  @Override
//  public T createModelInstance(){
//    return new RgbImage();
//  }
  @Override
  public ImageData getImageData(String imageName) throws IllegalArgumentException {
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
  public void horizontalFlip(String imageName, String destImageName)
          throws IllegalArgumentException {
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).horizontalFlip();
    imageList.put(destImageName, destImage);
  }

  @Override
  public void verticalFlip(String imageName, String destImageName)
          throws IllegalArgumentException {
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
  public void splitComponents(String imageName, List<String> destComponentImageList)
          throws IllegalArgumentException {
    destComponentImageList.forEach(this::checkValidImageName);
    checkImageNameExists(imageName);
    if (destComponentImageList.size() != 3) {
      throw new IllegalArgumentException("Invalid list of Destination images");
    }
    String redName = destComponentImageList.get(0);
    String greenName = destComponentImageList.get(1);
    String blueName = destComponentImageList.get(2);
    if (redName.equals(greenName) || redName.equals(blueName) || greenName.equals(blueName)) {
      throw new IllegalArgumentException("Invalid list of Destination images");
    }

    RgbImageModel rgbImageModel = imageList.get(imageName);
    RgbImageModel newRed = rgbImageModel.visualizeComponent(ComponentEnum.RED);
    RgbImageModel newGreen = rgbImageModel.visualizeComponent(ComponentEnum.GREEN);
    RgbImageModel newBlue = rgbImageModel.visualizeComponent(ComponentEnum.BLUE);

    imageList.put(redName, newRed);
    imageList.put(greenName, newGreen);
    imageList.put(blueName, newBlue);
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

    double[][][] newData = {red.getData()[0], green.getData()[1], blue.getData()[2]};
    ImageData newImageData = new ImageData(newData, red.getMaxValue());

    RgbImageModel newImage = this.createImageModel();
    newImage.loadImageData(newImageData);
    imageList.put(destImageName, newImage);
  }

  @Override
  public void blur(String imageName, String destImageName)
          throws IllegalArgumentException {
    double[][] blurKernel = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).applyFilter(blurKernel);
    imageList.put(destImageName, destImage);
  }

  @Override
  public void sharpen(String imageName, String destImageName)
          throws IllegalArgumentException {
    double[][] sharpenKernel = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).applyFilter(sharpenKernel);
    imageList.put(destImageName, destImage);
  }

  @Override
  public void sepia(String imageName, String destImageName) throws IllegalArgumentException {
    double[][] buffer = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    RgbImageModel destImage = imageList.get(imageName).applyTone(buffer);
    imageList.put(destImageName, destImage);
  }

  void checkValidImageName(String imageName) throws IllegalArgumentException {
    if (imageName.trim().isEmpty()) {
      throw new IllegalArgumentException("Invalid image name");
    }
  }

  void checkImageNameExists(String imageName) throws IllegalArgumentException {
    if (!imageList.containsKey(imageName)) {
      throw new IllegalArgumentException("Image does not exist");
    }
  }
}
