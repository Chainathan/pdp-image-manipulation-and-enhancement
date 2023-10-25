package Model;

import java.io.IOException;
import java.util.List;

import Exceptions.FileFormatNotSupportedException;

/**
 * The ImeModel interface defines the core functionality for working with image models.
 * Implementations of this interface provide methods to load, save, manipulate, and visualize image data.
 */
interface RgbImeModel {

  /**
   * Visualize the image model using the specified component.
   *
   * @param componentEnum The component to visualize.
   * @return An ImeModel representing the visualization.
   * @throws IllegalArgumentException If the component is not recognized.
   */
  RgbImeModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException;

  /**
   * Flip the image horizontally.
   *
   * @return An ImeModel representing the horizontally flipped image.
   */
  RgbImeModel horizontalFlip();

  /**
   * Flip the image vertically.
   *
   * @return An ImeModel representing the vertically flipped image.
   */
  RgbImeModel verticalFlip();

  /**
   * Brighten the image by the increment value.
   *
   * @param increment increment value to be applied to each pixel.
   * @return An ImeModel representing the brightened image.
   * @throws IllegalArgumentException If the increment argument is negative.
   */
  RgbImeModel brighten(int increment) throws IllegalArgumentException;

  /**
   * Darken the image by the decrement value.
   *
   * @param decrement decrement value to be applied to each pixel.
   * @return An ImeModel representing the darkened image.
   * @throws IllegalArgumentException If the decrement argument is negative.
   */
  RgbImeModel darken(int decrement) throws IllegalArgumentException;

  /**
   * Apply a blur effect to the image.
   *
   * @return An ImeModel representing the blurred image.
   */
  RgbImeModel blur();

  /**
   * Apply a sharpening effect to the image.
   *
   * @return An ImeModel representing the sharpened image.
   */
  RgbImeModel sharpen();

  /**
   * Apply a sepia tone effect to the image.
   *
   * @return An ImeModel representing the image with sepia tone.
   */
  RgbImeModel sepia();
}
