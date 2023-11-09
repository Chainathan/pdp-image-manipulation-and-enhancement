package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for Image Data class.
 */
public class ImageDataTest {

  @Test(expected = IllegalArgumentException.class)
  public void testImageDataForNoData() {
    new ImageData(null, 25);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImageDataForNegativeMaxValue() {
    new ImageData(new double[3][3][3], -25);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testImageDataForDifferentChannelHeights() {
    double[][][] values = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
    };
    new ImageData(values, 255);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testImageDataForDifferentChannelWidth() {
    double[][][] values = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10}}
    };
    new ImageData(values, 255);
  }

  @Test
  public void testImageDataValidImage() {
    try {
      double[][][] values = {
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
      };
      new ImageData(values, 255);
    } catch (IllegalArgumentException e) {
      fail("Above line should not throw an exception");
    }
  }

  @Test
  public void getData() {
    //GIVEN
    double[][][] values = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
    };
    ImageData imageData = new ImageData(values, 255);
    double[][][] actual = imageData.getData();
    for (int i = 0; i < values.length; i++) {
      for (int j = 0; j < values[0].length; j++) {
        for (int k = 0; k < values[0][0].length; k++) {
          assertEquals(values[i][j][k], actual[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testGetMaxValue() {
    double[][][] values = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
    };
    ImageData imageData = new ImageData(values, 255);
    assertEquals(255, imageData.getMaxValue());
  }
}