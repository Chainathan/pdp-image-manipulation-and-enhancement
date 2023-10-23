package Model;

import java.io.IOException;
import java.util.List;

import Exceptions.FileFormatNotSupportedException;

/**
 * The ImeModel interface defines the core functionality for working with image models.
 * Implementations of this interface provide methods to load, save, manipulate, and visualize image data.
 */
public interface ImeModel {

  /**
   * Load an image from the specified file path.
   *
   * @param filePath The path to the image file.
   * @return An ImageModel representing the loaded image.
   * @throws IOException If an I/O error occurs while loading the image.
   * @throws FileFormatNotSupportedException If the file format is not supported.
   */
  ImeModel load(String filePath) throws IOException, FileFormatNotSupportedException;

  /**
   * Save the image model to the specified file path.
   *
   * @param filePath The path to save the image to.
   * @throws FileFormatNotSupportedException If the file format is not supported.
   * @throws IOException If an I/O error occurs while saving the image.
   */
  void save(String filePath) throws FileFormatNotSupportedException, IOException;

  /**
   * Visualize the image model using the specified component.
   *
   * @param componentEnum The component to visualize.
   * @return An ImeModel representing the visualization.
   * @throws IllegalArgumentException If the component is not recognized.
   */
  ImeModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException;

  /**
   * Flip the image horizontally.
   *
   * @return An ImeModel representing the horizontally flipped image.
   */
  ImeModel horizontalFlip();

  /**
   * Flip the image vertically.
   *
   * @return An ImeModel representing the vertically flipped image.
   */
  ImeModel verticalFlip();

  /**
   * Brighten the image by the increment value.
   *
   * @param increment increment value to be applied to each pixel.
   * @return An ImeModel representing the brightened image.
   * @throws IllegalArgumentException If the increment argument is negative.
   */
  ImeModel brighten(int increment) throws IllegalArgumentException;

  /**
   * Darken the image by the decrement value.
   *
   * @param decrement decrement value to be applied to each pixel.
   * @return An ImeModel representing the darkened image.
   * @throws IllegalArgumentException If the decrement argument is negative.
   */
  ImeModel darken(int decrement) throws IllegalArgumentException;

  /**
   * Split the image into multiple grayscale-images based on the channels
   * of the color model.
   *
   * @return A list of ImeModels representing the split grayscale images.
   */
  List<ImeModel> split();

  /**
   * Combine multiple grayscale images each representing a channel of the
   * color model into a single image.
   *
   * @param imageModelList The list of Grayscale ImeModels to combine.
   * @return An ImeModel representing the combined image.
   * @throws IllegalArgumentException If one of the ImeModel is not grayscale images.
   */
  ImeModel combine(List<ImeModel> imageModelList) throws IllegalArgumentException;

  /**
   * Apply a blur effect to the image.
   *
   * @return An ImeModel representing the blurred image.
   */
  ImeModel blur();

  /**
   * Apply a sharpening effect to the image.
   *
   * @return An ImeModel representing the sharpened image.
   */
  ImeModel sharpen();

  /**
   * Apply a sepia tone effect to the image.
   *
   * @return An ImeModel representing the image with sepia tone.
   */
  ImeModel sepia();
}
