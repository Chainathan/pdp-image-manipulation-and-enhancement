package model;

import java.util.Arrays;
import java.util.Objects;

/**
 * The ImageData class represents a generic image containing pixel data.
 */
public class ImageData {

  private final int[][][] data;
  private final int maxValue;

  public ImageData(int[][][] data, int maxValue) throws IllegalArgumentException{
    if(data==null){
      throw new IllegalArgumentException("Raw image data cannot be null");
    }
    if(maxValue<0){
      throw new IllegalArgumentException("Max value of an image cannot be negative");
    }
    for(int i=1;i<data.length;i++){
      if(data[i].length!=data[0].length){
        throw new IllegalArgumentException("Channel Heights are not same");
      }
    }
    for (int[][] channelValues : data) {
      Channel.checkRectangularArray(channelValues);
    }
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
        System.arraycopy(data[i][j], 0, copy[i][j], 0, data[i][j].length);
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

  public void printImageArray2(){
    System.out.print("{\n");
    for (int i = 0; i < data.length; i++) {
      System.out.print("    {");
      for (int j = 0; j < data[i].length; j++) {
        System.out.print("{");
        for (int k = 0; k < data[i][j].length; k++) {
          System.out.print(data[i][j][k]);
          if (k < data[i][j].length - 1) {
            System.out.print(", ");
          }
        }
        System.out.print("}");
        if (j < data[i].length - 1) {
          System.out.print(", ");
        }
      }
      System.out.print("}");
      if (i < data.length - 1) {
        System.out.print(", ");
        System.out.println();
      }
    }
    System.out.println("};");
  }
  public void printArray(){
    System.out.print("{");
    for (int i = 0; i < data.length; i++) {
      if (i > 0) {
        System.out.print(",\n ");
      }

      System.out.print("{");
      for (int j = 0; j < data[i].length; j++) {
        if (j > 0) {
          System.out.print(",\n ");
        }

        System.out.print("{");
        for (int k = 0; k < data[i][j].length; k++) {
          if (k > 0) {
            System.out.print(", ");
          }
          System.out.print(data[i][j][k]);
        }
        System.out.print("}");
      }
      System.out.print("}");
    }
    System.out.print("};\n");
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ImageData imageData = (ImageData) o;
    return getMaxValue() == imageData.getMaxValue() && Arrays.deepEquals(getData(), imageData.getData());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getMaxValue());
    result = 31 * result + Arrays.hashCode(getData());
    return result;
  }
}
