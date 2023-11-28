package model;

import controller.ComponentEnum;
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

  /**
   * Apply blur effect to the RGB image using a predefined kernel.
   *
   * @return An RgbImageModel representing the blurred image.
   */
  RgbImageModel blur();

  /**
   * Apply sepia tone effect to the RGB image.
   *
   * @return An RgbImageModel representing the sepia-toned image.
   */
  RgbImageModel sepia();

  /**
   * Apply sharpening effect to the RGB image using a predefined kernel.
   *
   * @return An RgbImageModel representing the sharpened image.
   */
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

  /**
   * Apply compression to the RGB image based on the specified compression ratio.
   *
   * @param compressionRatio The compression ratio is the percentage of compression to be applied.
   * @return An RgbImageModel representing the compressed image.
   * @throws IllegalArgumentException If the compression ratio percentage is negative
   *                                  or greater than 100.
   */
  RgbImageModel applyCompression(double compressionRatio) throws IllegalArgumentException;

  /**
   * Create a histogram for the RGB image using the provided ImageGraphics object.
   *
   * @param graphics The ImageGraphics object to draw the histogram.
   * @return An RgbImageModel representing the image with the histogram.
   */
  RgbImageModel createHistogram(ImageGraphics graphics);

  /**
   * Correct the color of the RGB image by aligning the histogram peaks of individual channels.
   *
   * @return An RgbImageModel representing the image with corrected colors.
   */
  RgbImageModel correctColor();

  /**
   * Adjust the levels of the RGB image based on the provided black, mid, and white pixel values.
   *
   * @param b The black pixel value.
   * @param m The mid pixel value.
   * @param w The white pixel value.
   * @return An RgbImageModel with adjusted levels.
   * @throws IllegalArgumentException If black/mid/white is negative or if balck,mid and white
   *                                  are not in ascending order or if white is greater than 255.
   */
  RgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException;

  /**
   * Crop the RGB image vertically based on the provided start and end percentage
   * of width of the image.
   *
   * @param start The start percentage of width for cropping.
   * @param end   The end percentage of width for cropping.
   * @return An RgbImageModel with the cropped region.
   * @throws IllegalArgumentException If start/end is negative or if start/end is greater than 100.
   */
  RgbImageModel cropVertical(double start, double end) throws IllegalArgumentException;

  /**
   * Overlap another RGB image horizontally on the image based on the given percentage for overlap.
   *
   * @param otherImage The RGB image to use for overlap.
   * @param start      The percentage of overlapping.
   * @return An RgbImageModel with the overlapped values.
   * @throws IllegalArgumentException If otherImage is null or if start percentage is negative or
   *                                  greater than 100.
   */
  RgbImageModel overlapOnBase(RgbImageModel otherImage, double start)
          throws IllegalArgumentException;
}
