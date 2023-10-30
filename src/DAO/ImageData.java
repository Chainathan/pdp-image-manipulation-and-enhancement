package DAO;

/**
 * The ImageData class represents a generic image containing pixel data.
 */
public class ImageData {

  private final int[][][] data;
  private final int maxValue;

  public ImageData(int[][][] data, int maxValue) {
    this.data = data;
    this.maxValue = maxValue;
  }

  /**
   * Get the pixel data of the image.
   *
   * @return A three-dimensional array representing the pixel data of the image.
   * The dimensions are [channels][height][width], where channels typically
   * represent color components like red, green, and blue.
   */
  public int[][][] getData() {
    int[][][] copy = new int[data.length][data[0].length][data[0][0].length];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        System.arraycopy(data[i][j], 0, copy[i][j], 0, data[0][0].length);
      }
    }
    return copy;
  }

  /**
   * Get the maximum pixel value in the image.
   *
   * @return The maximum pixel value, which is often used to represent the
   * maximum intensity value in the image.
   */
  public int getMaxValue() {
    return maxValue;
  }

  public void printImageData(){
    System.out.printf("Channels: %d, red: height: %d\n" +
            "width: %d\n green: height: %d, width: %d\n" +
            "blue: height: %d, width: %d\n" +
            "max pixel value: %d\n",
            data.length,
            data[0].length, data[0][0].length,
            data[1].length, data[1][0].length,
            data[2].length, data[2][0].length,
            maxValue);
  }
}
