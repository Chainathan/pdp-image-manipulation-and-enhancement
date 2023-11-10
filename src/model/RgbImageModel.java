package model;

/**
 * The RgbImageModel interface defines the core functionality
 * for working with RGB image models. Implementations of this
 * interface provide methods to visualize, manipulate, and manage
 * RGB image data.
 */
interface RgbImageModel {
  //T getModel();

  RgbImageModel createInstance(ChannelModel red,
                                    ChannelModel green,
                                    ChannelModel blue,
                                    int maxPixelValue);
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
   * Apply a filter to the RGB image using the provided kernel.
   *
   * @param kernel The 2D kernel for the filter.
   * @return An RgbImageModel representing the image with the applied filter.
   * @throws IllegalArgumentException If the kernel is invalid or
   *     the filter operation fails.
   */
  RgbImageModel applyFilter(double[][] kernel) throws IllegalArgumentException;

  /**
   * Apply a sepia tone effect to the RGB image using the specified buffer.
   *
   * @param buffer The buffer for applying the sepia tone effect.
   * @return An RgbImageModel representing the image with sepia tone.
   * @throws IllegalArgumentException If the buffer is invalid or
   *     the sepia tone effect fails.
   */
  RgbImageModel applyTone(double[][] buffer) throws IllegalArgumentException;

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
}
