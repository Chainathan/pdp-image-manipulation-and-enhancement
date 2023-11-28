package view;

/**
 * The FeatureEnum enum represents a set of basic image processing features that can be applied to
 * an image. This is primarily used to maintain consistency over the features and operations
 * allowed from the view.
 */
public enum FeatureEnum {
  /**
   * Applies a blur effect to the image.
   */
  BLUR,

  /**
   * Applies a sharpening effect to the image.
   */
  SHARPEN,

  /**
   * Applies a sepia tone effect to the image.
   */
  SEPIA,

  /**
   * Converts the image to greyscale.
   */
  GREYSCALE,

  /**
   * Applies color correction to the image.
   */
  COLOR_CORRECT,

  /**
   * Adjusts the levels of the image.
   */
  LEVELS_ADJUST,

  /**
   * Flips the image horizontally.
   */
  HORIZONTAL_FLIP,

  /**
   * Flips the image vertically.
   */
  VERTICAL_FLIP,

  /**
   * Applies compression to the image.
   */
  COMPRESS,

  /**
   * Visualizes the red component of the image.
   */
  RED,

  /**
   * Visualizes the green component of the image.
   */
  GREEN,

  /**
   * Visualizes the blue component of the image.
   */
  BLUE;
}