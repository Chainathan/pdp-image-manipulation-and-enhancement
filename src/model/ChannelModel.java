package model;

/**
 * The ChannelModel interface defines methods for working with image channels.
 */
interface ChannelModel {
  ChannelModel createInstance(int[][] channelValues);

  /**
   * Get the array values of pixel data of the channel.
   *
   * @return A two-dimensional array of channel values.
   */
  int[][] getChannelValues();

  /**
   * Creates a channel with values that are horizontally flipped from the original channel.
   *
   * @return A new ChannelModel with horizontally flipped values.
   */
  ChannelModel getHorizontalFlipChannel();

  /**
   * Creates a channel with values that are vertically flipped from the original channel.
   *
   * @return A new ChannelModel with vertically flipped values.
   */
  ChannelModel getVerticalFlipChannel();

  /**
   * Create a channel with values added buffer amount from the original channel.
   *
   * @param buffer         The amount to add to the channel values.
   * @param maxPixelValues The maximum pixel values allowed in the channel.
   * @return A new ChannelModel with the added buffer values.
   */
  ChannelModel addBuffer(int buffer, int maxPixelValues);

  /**
   * Apply convolution with the given kernel on the channel values and return a
   * new channel with the resultant values.
   *
   * @param kernel         The convolution kernel to apply.
   * @param maxPixelValues The maximum pixel values allowed in the channel.
   * @return A new ChannelModel with the convolution result.
   */
  ChannelModel applyConvolution(double[][] kernel, int maxPixelValues);

  /**
   * Get the channel height.
   *
   * @return The height of the channel.
   */
  int getHeight();

  /**
   * Get the channel width.
   *
   * @return The width of the channel.
   */
  int getWidth();

  /**
   * Get the pixel value at the specified position.
   *
   * @param y The row value.
   * @param x The column value.
   * @return The pixel value at the specified position.
   * @throws IllegalArgumentException If the provided coordinates are out of bounds.
   */
  int getValue(int y, int x) throws IllegalArgumentException;

  /**
   * Get the frequency values of pixel data in the channel.
   *
   * @return An array containing frequency values.
   */
  int[] getFrequencyValues();

  /**
   * Get the maximum frequency pixel value in the channel.
   *
   * @return The maximum frequency pixel value.
   */
  int getMaxFreqPixel();

  /**
   * Adjust the levels of the channel based on the provided parameters. The adjustment is restricted to
   * horizontal movements of the three points (black, mid and white).
   *
   * @param b The black pixel value.
   * @param m The mid pixel value.
   * @param w The white pixel value.
   * @return A new ChannelModel with adjusted levels.
   */
  ChannelModel adjustLevels(int b, int m, int w);

  /**
   * Crop the channel vertically based on the provided start and end positions.
   *
   * @param start The starting x-position for cropping.
   * @param end   The ending x-position for cropping.
   * @return A new ChannelModel with the cropped region.
   * @throws IllegalArgumentException If start/end is negative or if start/end is greater than channel width.
   */
  ChannelModel cropVertical(int start, int end) throws IllegalArgumentException;

  /**
   * Overlap another channel horizontally on the channel starting from the provided position.
   *
   * @param otherChannel The channel to use for overlap.
   * @param start        The starting x-coordinate for overlapping.
   * @return A new ChannelModel with the overlapped values.
   * @throws IllegalArgumentException If otherChannel is null or if start value is negative or
   *     greater than the channel width.
   */
  ChannelModel overlapOnBase(ChannelModel otherChannel, int start)
          throws IllegalArgumentException;
}
