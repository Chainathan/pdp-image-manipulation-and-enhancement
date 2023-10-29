package Model;

import DAO.ImageData;

/**
 * The ImeModel interface defines the core functionality for working with image models.
 * Implementations of this interface provide methods to load, save, manipulate, and visualize image data.
 */
public interface RgbImageModel {

  /**
   * Visualize the image model using the specified component.
   *
   * @param componentEnum The component to visualize.
   * @return An ImeModel representing the visualization.
   * @throws IllegalArgumentException If the component is not recognized.
   */
  RgbImageModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException;

  /**
   * Flip the image horizontally.
   *
   * @return An ImeModel representing the horizontally flipped image.
   */
  RgbImageModel horizontalFlip();

  /**
   * Flip the image vertically.
   *
   * @return An ImeModel representing the vertically flipped image.
   */
  RgbImageModel verticalFlip();

  /**
   * Brighten the image by the increment value.
   *
   * @param increment increment value to be applied to each pixel.
   * @return An ImeModel representing the brightened image.
   * @throws IllegalArgumentException If the increment argument is negative.
   */
  RgbImageModel brighten(int increment) throws IllegalArgumentException;

  /**
   * Darken the image by the decrement value.
   *
   * @param decrement decrement value to be applied to each pixel.
   * @return An ImeModel representing the darkened image.
   * @throws IllegalArgumentException If the decrement argument is negative.
   */
  RgbImageModel darken(int decrement) throws IllegalArgumentException;

  /**
   * Apply a blur effect to the image.
   *
   * @return An ImeModel representing the blurred image.
   */
  RgbImageModel blur();

  /**
   * Apply a sharpening effect to the image.
   *
   * @return An ImeModel representing the sharpened image.
   */
  RgbImageModel sharpen();

  /**
   * Apply a sepia tone effect to the image.
   *
   * @return An ImeModel representing the image with sepia tone.
   */
  RgbImageModel sepia();

  /**
   * Get the raw data of the image in the form of ImageData object.
   * @return ImageData object containing the raw data of the image.
   */
  ImageData getImageData();

  /**
   * Load the raw data from the ImageData data object into the RgbImageModel.
   * @param imageData
   */
  void loadImageData(ImageData imageData);
}
