package model;

import java.util.List;

/**
 * The ImageProcessorModel interface represents a model
 * that manages a set of images associated with specific and unique names
 * and provides operations for performing various manipulations on the available images.
 */
public interface ImageProcessorModel {
  /**
   * Add an image to the image processor with the specified
   * name and the provided image data.
   *
   * @param destImageName The unique name to associate with the added image.
   * @param imageData     The image data to be added.
   * @throws IllegalArgumentException If the destImageName is invalid
   *                                  (contains whitespace, line breaks, or tabs).
   */
  void addImage(String destImageName, ImageData imageData) throws IllegalArgumentException;

  /**
   * Retrieve the image data associated with the given image name.
   *
   * @param imageName The name of the image to retrieve.
   * @return The ImageData object representing the image.
   * @throws IllegalArgumentException If the image with the specified name does not exist.
   */
  ImageData getImageData(String imageName) throws IllegalArgumentException;

  /**
   * Visualize the image using the specified component and associate it with a new name.
   *
   * @param imageName     The name of the image to visualize.
   * @param destImageName The name to associate with the visualization.
   *                      Must not contain whitespace, line breaks, or tabs.
   * @param component     The component to visualize.
   * @throws IllegalArgumentException If the image or component is not recognized or
   *                      if the destImageName is invalid.
   */
  void visualizeComponent(String imageName, String destImageName, String component)
          throws IllegalArgumentException;

  /**
   * Flip the image horizontally and associate the result with a new name.
   *
   * @param imageName     The name of the image to flip horizontally.
   * @param destImageName The name to associate with the flipped image. Must not contain
   *                      whitespace, line breaks, or tabs.
   * @throws IllegalArgumentException If the image does not exist or if the destImageName
   *                      is invalid.
   */
  void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Flip the image vertically and associate the result with a new name.
   *
   * @param imageName     The name of the image to flip vertically.
   * @param destImageName The name to associate with the flipped image.
   *                      Must not contain whitespace, line breaks, or tabs.
   * @throws IllegalArgumentException If the image does not exist or if the destImageName is
   *                      invalid.
   */
  void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Brighten the image by the specified increment value and associate the result with a new name.
   *
   * @param imageName     The name of the image to brighten.
   * @param destImageName The name to associate with the brightened image.
   *                      Must not contain whitespace, line breaks, or tabs.
   * @param increment     The increment value to be applied to each pixel.
   * @throws IllegalArgumentException If the image does not exist, if the
   *                      increment argument is negative, or if the destImageName is invalid.
   */
  void brighten(String imageName, String destImageName, int increment)
          throws IllegalArgumentException;

  /**
   * Split the image into multiple grayscale images based on the channels of
   * the color model and associate them with new names.
   *
   * @param imageName              The name of the image to split.
   * @param destComponentImageList A list of unique names for the resulting
   *                               grayscale images. Each name must not contain
   *                               whitespace, line breaks, or tabs.
   * @throws IllegalArgumentException If the image does not exist or if the
   *                               destComponentImageList contains invalid names.
   */
  void splitComponents(String imageName, List<String> destComponentImageList)
          throws IllegalArgumentException;

  /**
   * Combine multiple grayscale images, each representing a channel of the color
   * model, into a single image and associate it with a new name.
   *
   * @param destImageName      The name to associate with the combined image.
   *                           Must not contain whitespace, line breaks, or tabs.
   * @param componentImageList The list of grayscale images to combine.
   * @throws IllegalArgumentException If one of the images is not a grayscale
   *                           image or if the destImageName is invalid.
   */
  void combineComponents(String destImageName, List<String> componentImageList)
          throws IllegalArgumentException;

  /**
   * Apply a blur effect to the image and associate the result with a new name.
   *
   * @param imageName     The name of the image to apply the blur effect.
   * @param destImageName The name to associate with the blurred image.
   *                      Must not contain whitespace, line breaks, or tabs.
   * @throws IllegalArgumentException If the image does not exist or if the
   *                      destImageName is invalid.
   */
  void blur(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Apply a sharpening effect to the image and associate the result with a new name.
   *
   * @param imageName     The name of the image to apply the sharpening effect.
   * @param destImageName The name to associate with the sharpened image.
   *                      Must not contain whitespace, line breaks, or tabs.
   * @throws IllegalArgumentException If the image does not exist or if the destImageName
   *                      is invalid.
   */
  void sharpen(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Apply a sepia tone effect to the image and associate the result with a new name.
   *
   * @param imageName     The name of the image to apply the sepia tone effect.
   * @param destImageName The name to associate with the sepia-toned image.
   *                      Must not contain whitespace, line breaks, or tabs.
   * @throws IllegalArgumentException If the image does not exist or if the destImageName
   *                      is invalid.
   */
  void sepia(String imageName, String destImageName) throws IllegalArgumentException;

  void compress(String imageName, String destImageName, double compressionRatio)
          throws IllegalArgumentException;
  void createHistogram(String imageName, String destImageName)
          throws IllegalArgumentException;
  void correctColor(String imageName, String destImageName)
          throws IllegalArgumentException;
  void adjustLevels(String imageName, String destImageName, int b, int m, int w)
          throws IllegalArgumentException;
  void cropVertical(String imageName, String destImageName,
                    double start, double end) throws IllegalArgumentException;
  void overlapOnBase(String imageNameOriginal, String imageNameAddon,
                     String destImageName, double start) throws IllegalArgumentException;
  void removeImage(String imageName) throws IllegalArgumentException;

}
