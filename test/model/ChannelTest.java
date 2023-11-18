package model;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test class for Channel class.
 */
public class ChannelTest {

  @Test
  public void testGetChannelValues() {
    int[][] values = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    ChannelModel channel = new Channel(values);
    int[][] channelValues = channel.getChannelValues();
    assertArrayEquals(values, channelValues);
  }

  @Test
  public void testGetVerticalFlipChannel() {
    int[][] values = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    int[][] expectedValues = {
            {7, 8, 9},
            {4, 5, 6},
            {1, 2, 3}
    };
    ChannelModel channel = new Channel(values);
    ChannelModel flippedChannel = channel.getVerticalFlipChannel();
    int[][] flippedValues = flippedChannel.getChannelValues();
    assertArrayEquals(expectedValues, flippedValues);
  }

  @Test
  public void testGetHorizontalFlipChannel() {
    int[][] values = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    int[][] expectedValues = {
            {3, 2, 1},
            {6, 5, 4},
            {9, 8, 7}
    };
    ChannelModel channel = new Channel(values);
    ChannelModel flippedChannel = channel.getHorizontalFlipChannel();
    int[][] flippedValues = flippedChannel.getChannelValues();

    assertArrayEquals(expectedValues, flippedValues);
  }

  @Test
  public void testPositiveAddBuffer() {
    int[][] values = {
            {100, 200, 120},
            {150, 245, 250},
            {0, 110, 255}
    };
    int buffer = 10;
    int maxPixelValue = 255;
    int[][] expectedValues = {
            {110, 210, 130},
            {160, 255, 255},
            {10, 120, 255}
    };
    ChannelModel channel = new Channel(values);
    ChannelModel bufferedChannel = channel.addBuffer(buffer, maxPixelValue);
    int[][] bufferedValues = bufferedChannel.getChannelValues();

    assertArrayEquals(expectedValues, bufferedValues);
  }

  @Test
  public void testNegativeAddBuffer() {
    int[][] values = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    int buffer = -5;
    int maxPixelValue = 255;
    int[][] expectedValues = {
            {0, 0, 0},
            {0, 0, 1},
            {2, 3, 4}
    };
    ChannelModel channel = new Channel(values);
    ChannelModel bufferedChannel = channel.addBuffer(buffer, maxPixelValue);
    int[][] bufferedValues = bufferedChannel.getChannelValues();

    assertArrayEquals(expectedValues, bufferedValues);
  }

  //Check for Convolution when Kernel > Image Size
  @Test
  public void testApplyConvolution() {
    int[][] inputChannel = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
    };
    double[][] kernel = {
            {0.0, 1.0, 0.0},
            {1.0, 2.0, 1.0},
            {0.0, 1.0, 0.0}
    };
    Channel channel = new Channel(inputChannel);
    ChannelModel result = channel.applyConvolution(kernel, 255);
    int[][] expectedOutput = {
            {10, 15, 20, 25, 24},
            {31, 42, 48, 54, 49},
            {56, 72, 78, 84, 74},
            {81, 102, 108, 114, 99},
            {80, 105, 110, 115, 94}
    };
    int[][] actualOutput = result.getChannelValues();
    assertArrayEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testApplyConvolutionLargeKernel() {
    double[][] kernel = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25}
    };
    int[][] inputChannel = {
            {0, 1, 0},
            {1, 2, 1},
            {0, 1, 0}
    };
    Channel channel = new Channel(inputChannel);
    ChannelModel result = channel.applyConvolution(kernel, 255);
    int[][] expectedOutput = {
            {114, 108, 102},
            {84, 78, 72},
            {54, 48, 42}
    };
    int[][] actualOutput = result.getChannelValues();
    assertArrayEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testVerticalFlipForEmptyChannel() {
    int[][] inputChannel = new int[0][0];
    Channel channel = new Channel(inputChannel);
    ChannelModel actual = channel.getVerticalFlipChannel();
    assertEquals(0, actual.getWidth());
    assertEquals(0, actual.getHeight());
  }

  @Test
  public void testHorizontalFlipForEmptyChannel() {
    int[][] inputChannel = new int[0][0];
    Channel channel = new Channel(inputChannel);
    ChannelModel actual = channel.getHorizontalFlipChannel();
    assertEquals(0, actual.getWidth());
    assertEquals(0, actual.getHeight());
  }

  @Test
  public void testBufferEmptyChannel() {
    int[][] inputChannel = new int[0][0];
    Channel channel = new Channel(inputChannel);
    ChannelModel actual = channel.addBuffer(10, 255);
    assertEquals(0, actual.getWidth());
    assertEquals(0, actual.getHeight());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidKernel() {
    double[][] invalidKernel = {
            {1.0, 2.0},
            {3.0, 4.0}
    };

    Channel channel = new Channel();
    channel.applyConvolution(invalidKernel, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInvalidValue() {
    Channel channel = new Channel();
    channel.getValue(0, 0);
  }

  @Test
  public void testGetHeight() {
    int[][] inputChannel = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    Channel channel = new Channel(inputChannel);
    assertEquals(5, channel.getHeight());
  }

  @Test
  public void testGetWidth() {
    int[][] inputChannel = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    Channel channel = new Channel(inputChannel);
    assertEquals(4, channel.getWidth());
  }

  @Test
  public void testGetValue() {
    int[][] inputChannel = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    Channel channel = new Channel(inputChannel);
    assertEquals(18, channel.getValue(3, 2));
  }

  @Test
  public void testInitializingEmptyChannel() {
    Channel channel = new Channel();
    assertEquals(0, channel.getWidth());
    assertEquals(0, channel.getHeight());
  }

  @Test
  public void testZeroChannel() {
    Channel channel = new Channel(5, 4);
    assertEquals(4, channel.getWidth());
    assertEquals(5, channel.getHeight());
  }

  @Test
  public void testValidChannel() {
    int[][] inputChannel = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    Channel channel = new Channel(inputChannel);
    for (int i = 0; i < channel.getHeight(); i++) {
      for (int j = 0; j < channel.getWidth(); j++) {
        assertEquals(inputChannel[i][j], channel.getValue(i, j));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidChannel() {
    int[][] inputChannel = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    new Channel(inputChannel);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testChannelIsNull() {
    new Channel(null);
  }

  @Test
  public void testEmptyChannel() {
    int[][] inputChannel = new int[0][0];
    Channel channel = new Channel(inputChannel);
    assertEquals(0, channel.getWidth());
    assertEquals(0, channel.getHeight());
  }

  @Test
  public void testOverlapOnBase() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    int[][] values2 = {
            {7, 8},
            {4, 5},
            {1, 2}
    };

    int[][] expectedValues = {
            {7, 8, 3, 4},
            {4, 5, 8, 9},
            {1, 2, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel channel2 = new Channel(values2);
    ChannelModel overlappedChannel = channel1.overlapOnBase(channel2, 0);
    int[][] overlappedValues = overlappedChannel.getChannelValues();
    assertArrayEquals(expectedValues, overlappedValues);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseNegativeStart() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    int[][] values2 = {
            {7, 8},
            {4, 5},
            {1, 2}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel channel2 = new Channel(values2);
    channel1.overlapOnBase(channel2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseStartGreaterThanWidth() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    int[][] values2 = {
            {7, 8},
            {4, 5},
            {1, 2}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel channel2 = new Channel(values2);
    channel1.overlapOnBase(channel2, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseNullChannel() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    channel1.overlapOnBase(null, 0);
  }

  @Test
  public void testCropVertical() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    int[][] expectedValues = {
            {2, 3},
            {7, 8},
            {12, 13},
            {17, 18},
            {22, 23}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel overlappedChannel = channel1.cropVertical(1, 3);
    int[][] overlappedValues = overlappedChannel.getChannelValues();
    assertArrayEquals(expectedValues, overlappedValues);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalNegativeStart() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    channel1.cropVertical(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalStartGreaterThanEnd() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    channel1.cropVertical(3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalEndGreaterThanWidth() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    channel1.cropVertical(0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalNegativeEnd() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    channel1.cropVertical(1, -1);
  }

  @Test
  public void adjustLevels() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    int[][] expectedValues = {
            {7, 14, 20, 27},
            {40, 47, 53, 60},
            {73, 79, 85, 92},
            {104, 110, 116, 122},
            {134, 140, 146, 151}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel actual = channel1.adjustLevels(0, 20, 255);
    assertArrayEquals(expectedValues, actual.getChannelValues());
  }

  @Test
  public void testMaxFreqPixel() {
    int[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 8},
            {11, 22, 13, 14},
            {16, 16, 22, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    int actual = channel1.getMaxFreqPixel();
    assertEquals(22, actual);
  }

  @Test
  public void testMaxFreqPixelForEdgeCase() {
    int[][] values1 = {
            {9, 9, 9, 9},
            {6, 7, 8, 8},
            {11, 22, 13, 14},
            {16, 22, 22, 19},
            {255, 255, 255, 255}
    };
    ChannelModel channel1 = new Channel(values1);
    int actual = channel1.getMaxFreqPixel();
    assertEquals(22, actual);
  }

  @Test
  public void testGetFrequencyValues() {
    int[][] values1 = {
            {9, 9, 9, 9},
            {22, 22, 22, 22},
            {22, 22, 22, 22},
            {255, 255, 255, 255}
    };
    int[] expected = new int[256];
    expected[9] = 4;
    expected[22] = 8;
    expected[255] = 4;
    ChannelModel channel1 = new Channel(values1);
    int[] actual = channel1.getFrequencyValues();
    assertArrayEquals(expected, actual);
  }

  @Test
  public void testCreateInstance() {
    int[][] values = {
            {9, -9, 9, 9},
            {6, 7, -8, 8},
            {11, -22, 13, 14},
            {16, 22, 22, -19}
    };
    ChannelModel channel1 = new Channel();
    ChannelModel actual = channel1.createInstance(values);
    assertArrayEquals(values, actual.getChannelValues());
  }
}