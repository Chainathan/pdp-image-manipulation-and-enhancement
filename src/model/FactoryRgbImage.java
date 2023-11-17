package model;

/**
 * The FactoryRgbImage class is a factory class that implements the FactoryRgbImageModel interface
 * to provide a method for creating instances of RgbImageModel.
 */
public class FactoryRgbImage implements FactoryRgbImageModel{

  /**
   * Create and return a new instance of RgbImage.
   *
   * @return A new RgbImage instance.
   */
  @Override
  public RgbImageModel createImageModel() {
    return new RgbImage();
  }
}
