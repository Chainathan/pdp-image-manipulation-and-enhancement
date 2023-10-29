package sample;

/**
 * The Image interface represents a generic image containing pixel data.
 */
public interface Image {

  /**
   * Get the pixel data of the image.
   *
   * @return A three-dimensional array representing the pixel data of the image.
   * The dimensions are [channels][height][width], where channels typically
   * represent color components like red, green, and blue.
   */
  public int[][][] getData();

  /**
   * Get the maximum pixel value in the image.
   *
   * @return The maximum pixel value, which is often used to represent the
   *     maximum intensity value in the image.
   */
  public int getMaxValue();
}