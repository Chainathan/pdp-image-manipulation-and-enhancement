package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * The ImageData class represents a data object or wrapper for image data,
 * storing pixel information about an image.
 * This class is often used as an entity for passing image data to and from the model,
 * controller, and view components.
 */
public class ImageData {
  private final double[][][] data;
  private final int maxValue;

  /**
   * Constructs an ImageData object with the provided raw image data and the maximum pixel value.
   *
   * @param data     A three-dimensional array representing the pixel data of the image.
   *                 The dimensions are [channels][height][width], where channels typically
   *                 represent color components like red, green, and blue.
   * @param maxValue The maximum pixel value, often used to represent the maximum
   *                 intensity value in the image.
   * @throws IllegalArgumentException If the provided data is null, if maxValue is negative,
   *                 or if the channel heights are not the same.
   */
  public ImageData(double[][][] data, int maxValue) throws IllegalArgumentException {
    if (data == null) {
      throw new IllegalArgumentException("Raw image data cannot be null");
    }
    if (maxValue < 0) {
      throw new IllegalArgumentException("Max value of an image cannot be negative");
    }
    for (int i = 1; i < data.length; i++) {
      if (data[i].length != data[0].length) {
        throw new IllegalArgumentException("Channel Heights are not the same");
      }
    }
    for (double[][] channelValues : data) {
      Channel.checkRectangularArray(channelValues);
    }
    this.data = data;
    this.maxValue = maxValue;
  }

  /**
   * Get the pixel data of the image.
   *
   * @return A three-dimensional array representing the pixel data of the image.
   *         The dimensions are [channels][height][width], where channels typically
   *         represent color components like red, green, and blue.
   */
  public double[][][] getData() {
    double[][][] copy = new double[data.length][data[0].length][data[0][0].length];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        System.arraycopy(data[i][j], 0, copy[i][j], 0, data[i][j].length);
      }
    }
    return copy;
  }

  /**
   * Get the maximum pixel value in the image.
   *
   * @return The maximum pixel value, which is often used to represent the
   *        maximum intensity value in the image.
   */
  public int getMaxValue() {
    return maxValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageData imageData = (ImageData) o;
    return getMaxValue() == imageData.getMaxValue()
            && areArraysEqual(getData(), imageData.getData(),1);
  }

  private boolean areArraysEqual(double[][][] array1, double[][][] array2, double tolerance) {
    if (array1 == null || array2 == null || array1.length != array2.length) {
      return false;
    }

    for (int i = 0; i < array1.length; i++) {
      if (!areArraysEqual(array1[i], array2[i], tolerance)) {
        return false;
      }
    }

    return true;
  }

  private boolean areArraysEqual(double[][] array1, double[][] array2, double tolerance) {
    if (array1 == null || array2 == null || array1.length != array2.length) {
      return false;
    }

    for (int i = 0; i < array1.length; i++) {
      if (!areArraysEqual(array1[i], array2[i], tolerance)) {
        return false;
      }
    }

    return true;
  }

  private boolean areArraysEqual(double[] array1, double[] array2, double tolerance) {
    if (array1 == null || array2 == null || array1.length != array2.length) {
      return false;
    }
    for (int i = 0; i < array1.length; i++) {
      if (Math.abs(array1[i] - array2[i]) > tolerance) {
        return false;
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getMaxValue());
    result = 31 * result + Arrays.deepHashCode(getData());
    return result;
  }
}
