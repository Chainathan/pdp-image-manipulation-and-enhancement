package model;

/**
 * The ChannelModel interface defines methods for working with image channels.
 */
interface ChannelModel {
  ChannelModel createInstance(double[][] channelValues);

  /**
   * Get the array values of pixel data of the channel.
   *
   * @return A two-dimensional array of channel values.
   */
  double[][] getChannelValues();

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
   * @param x The column value.
   * @param y The row value.
   * @return The pixel value at the specified position.
   * @throws IllegalArgumentException If the provided coordinates are out of bounds.
   */
  double getValue(int x, int y) throws IllegalArgumentException;
}
