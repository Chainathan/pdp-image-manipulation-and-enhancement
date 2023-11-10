package model;

import java.util.Map;

public class AdvRgbImageProcessor extends RgbImageProcessor<AdvRgbImageModel> implements AdvImageProcessorModel{
//  Map<String, AdvRgbImageModel> imageList;
  @Override
  public void compress(String imageName, String destImageName, double compressionRatio)
          throws IllegalArgumentException{
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    AdvRgbImageModel destImage = imageList.get(imageName).applyCompression(compressionRatio);
    imageList.put(destImageName, destImage);
  }

  @Override
  AdvRgbImageModel createImageModel() {
    return new AdvRgbImage();
  }

  @Override
  public void createHistogram(String imageName, String destImageName)
          throws IllegalArgumentException{
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    AdvRgbImageModel destImage = imageList.get(imageName).createHistogram();
    imageList.put(destImageName, destImage);
  }

  @Override
  public void correctColor(String imageName, String destImageName)
          throws IllegalArgumentException{
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    AdvRgbImageModel destImage = imageList.get(imageName).correctColor();
    imageList.put(destImageName, destImage);
  }

  @Override
  public void adjustLevels(String imageName, String destImageName, int b, int m, int w)
          throws IllegalArgumentException{
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    AdvRgbImageModel destImage = imageList.get(imageName).adjustLevels(b,m,w);
    imageList.put(destImageName, destImage);
  }

  @Override
  public void trimVertical(String imageName, String destImageName, double start, double end)
          throws IllegalArgumentException{
    checkValidImageName(destImageName);
    checkImageNameExists(imageName);
    AdvRgbImageModel destImage = imageList.get(imageName).trimVertical(start,end);
    imageList.put(destImageName, destImage);
  }

  @Override
  public void overlapOnBase(String imageNameOriginal, String imageNameAddon,
                            String destImageName, double start) throws IllegalArgumentException{
    checkValidImageName(destImageName);
    checkImageNameExists(imageNameOriginal);
    checkImageNameExists(imageNameAddon);
    AdvRgbImageModel imageOriginal = imageList.get(imageNameOriginal);
    AdvRgbImageModel imageAddon = imageList.get(imageNameAddon);
    AdvRgbImageModel destImage = imageOriginal.overlapOnBase(imageAddon, start);
    imageList.put(destImageName, destImage);
  }

  @Override
  public void removeImage(String imageName) throws IllegalArgumentException{
    checkImageNameExists(imageName);
    imageList.remove(imageName);
  }
}
