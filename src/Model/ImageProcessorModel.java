package Model;

import java.util.List;

import DAO.ImageData;

public interface ImageProcessorModel {

  /**
   * Load an image from the specified file path.
   *
   * @param imageData The path to the image file.
   * @throws IllegalArgumentException If the image does not exist.
   */
  void addImage(String destImageName, ImageData imageData) throws IllegalArgumentException;

  /**
   * Save the image model to the specified file path.
   *
   * @throws IllegalArgumentException If the image does not exist.
   */
  ImageData getImageData(String imageName) throws IllegalArgumentException;

  /**
   * Visualize the image model using the specified component.
   *
   * @param component The component to visualize.
   * @return An ImeModel representing the visualization.
   * @throws IllegalArgumentException If the component is not recognized.
   */
  void visualizeComponent(String imageName, String destImageName, String component) throws IllegalArgumentException;

  /**
   * Flip the image horizontally.
   *
   * @return An ImeModel representing the horizontally flipped image.
   */
  void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Flip the image vertically.
   *
   * @return An ImeModel representing the vertically flipped image.
   */
  void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Brighten the image by the increment value.
   *
   * @param increment increment value to be applied to each pixel.
   * @return An ImeModel representing the brightened image.
   * @throws IllegalArgumentException If the increment argument is negative.
   */
  void brighten(String imageName, String destImageName, int increment) throws IllegalArgumentException;

  /**
   * Darken the image by the decrement value.
   *
   * @param decrement decrement value to be applied to each pixel.
   * @return An ImeModel representing the darkened image.
   * @throws IllegalArgumentException If the decrement argument is negative.
   */
  void darken(String imageName, String destImageName, int decrement) throws IllegalArgumentException;

  /**
   * Split the image into multiple grayscale-images based on the channels
   * of the color model.
   *
   * @return A list of ImeModels representing the split grayscale images.
   */
  void splitComponents(String imageName, List<String> destComponentImageList) throws IllegalArgumentException;

  /**
   * Combine multiple grayscale images each representing a channel of the
   * color model into a single image.
   *
   * @param componentImageList The list of Grayscale ImeModels to combine.
   * @return An ImeModel representing the combined image.
   * @throws IllegalArgumentException If one of the ImeModel is not grayscale images.
   */
  void combineComponents(String destImageName, List<String> componentImageList) throws IllegalArgumentException;

  /**
   * Apply a blur effect to the image.
   *
   * @return An ImeModel representing the blurred image.
   */
  void blur(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Apply a sharpening effect to the image.
   *
   * @return An ImeModel representing the sharpened image.
   */
  void sharpen(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Apply a sepia tone effect to the image.
   *
   * @return An ImeModel representing the image with sepia tone.
   */
  void sepia(String imageName, String destImageName) throws IllegalArgumentException;

}
