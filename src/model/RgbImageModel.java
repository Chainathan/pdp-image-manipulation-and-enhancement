package model;

import controller.ImageGraphics;

/**
 * The RgbImageModel interface defines the core functionality
 * for working with RGB image models. Implementations of this
 * interface provide methods to visualize, manipulate, and manage
 * RGB image data.
 */
public interface RgbImageModel {
  /**
   * Visualize the RGB image model using the specified component.
   *
   * @param componentEnum The component to visualize.
   * @return An RgbImageModel representing the visualization.
   * @throws IllegalArgumentException If the component is not recognized.
   */
  RgbImageModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException;

  /**
   * Flip the RGB image horizontally.
   *
   * @return An RgbImageModel representing the horizontally flipped image.
   */
  RgbImageModel horizontalFlip();

  /**
   * Flip the RGB image vertically.
   *
   * @return An RgbImageModel representing the vertically flipped image.
   */
  RgbImageModel verticalFlip();

  /**
   * Brighten the RGB image by the specified increment value.
   *
   * @param increment The increment value to be applied to each pixel.
   * @return An RgbImageModel representing the brightened image.
   * @throws IllegalArgumentException If the increment argument is negative.
   */
  RgbImageModel brighten(int increment) throws IllegalArgumentException;

  RgbImageModel blur();
  RgbImageModel sepia();
  RgbImageModel sharpen();

  /**
   * Get the raw data of the RGB image in the form of an ImageData object.
   *
   * @return ImageData object containing the raw data of the RGB image.
   */
  ImageData getImageData();

  /**
   * Load the raw data from the provided ImageData object into
   * the RgbImageModel.
   *
   * @param imageData The ImageData object to load.
   */
  void loadImageData(ImageData imageData);

  RgbImageModel applyCompression(double compressionRatio) throws IllegalArgumentException;
  RgbImageModel createHistogram(ImageGraphics graphics);
  RgbImageModel correctColor();
  RgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException;
  RgbImageModel cropVertical(double start, double end) throws IllegalArgumentException;
  RgbImageModel overlapOnBase(RgbImageModel otherImage, double start)
          throws IllegalArgumentException;
}
