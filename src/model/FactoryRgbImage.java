package model;

public class FactoryRgbImage implements FactoryRgbImageModel{
  @Override
  public RgbImageModel createImageModel() {
    return new RgbImage();
  }
}
