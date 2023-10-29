package Model;

public interface ChannelModel {
  /**
   * Get the array values of pixel data of the channel.
   * @return
   */
  int[][] getChannelValues();

  /**
   * Creates a channel with values that are horizontal flipped from
   * the original channel.
   * @return
   */
  ChannelModel getHorizontalFlipChannel();

  /**
   * Creates a channel with values that are vertically flipped from
   * the original channel.
   * @return
   */
  ChannelModel getVerticalFlipChannel();

  /**
   * Create a channel with values added buffer amount from the
   * original channel.
   * @param buffer
   * @return
   */
  ChannelModel addBuffer(int buffer);

  /**
   * Apply convolution with the given kernel on the channel values and
   * return a new channel with the resultant values.
   * @param kernel
   * @return
   */
  ChannelModel applyConvolution(double[][] kernel);

  /**
   * Get the channel height.
   * @return
   */
  int getHeight();

  /**
   * Get the channel width.
   * @return
   */
  int getWidth();

  /**
   * Get the pixel value.
   * @param x column value.
   * @param y row value.
   * @return
   */
  int getValue(int x, int y);
}
