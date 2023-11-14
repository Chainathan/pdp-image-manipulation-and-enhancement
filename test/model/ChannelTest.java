package model;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Test class of Channel class.
 */
public class ChannelTest {
//  @Test
//  public void testTransform(){
//    double[][] values = {
//            {1, 2, 3},
//            {4, 5, 6},
//            {7, 8, 9}
//    };
//    AdvChannel c = new AdvChannel(values);
//    c.test();
//  }

  //Kernel > image.
  @Test
  public void testGetChannelValues() {
    double[][] values = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    ChannelModel channel = new Channel(values);
    double[][] channelValues = channel.getChannelValues();
    assertArrayEquals(values, channelValues);
  }

  @Test
  public void testGetVerticalFlipChannel() {
    double[][] values = {
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
    double[][] flippedValues = flippedChannel.getChannelValues();
    assertArrayEquals(expectedValues, flippedValues);
  }

  @Test
  public void testGetHorizontalFlipChannel() {
    double[][] values = {
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
    double[][] flippedValues = flippedChannel.getChannelValues();

    assertArrayEquals(expectedValues, flippedValues);
  }

  @Test
  public void testPositiveAddBuffer() {
    double[][] values = {
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
    double[][] bufferedValues = bufferedChannel.getChannelValues();

    assertArrayEquals(expectedValues, bufferedValues);
  }

  @Test
  public void testNegativeAddBuffer() {
    double[][] values = {
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
    double[][] bufferedValues = bufferedChannel.getChannelValues();

    assertArrayEquals(expectedValues, bufferedValues);
  }

  //Check for Convolution when Kernel > Image Size
  @Test
  public void testApplyConvolution() {
    double[][] inputChannel = {
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
    double[][] actualOutput = result.getChannelValues();
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
    double[][] inputChannel = {
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
    double[][] actualOutput = result.getChannelValues();
    assertArrayEquals(expectedOutput, actualOutput);
  }

  //Check for 0 width and height all methods
  @Test
  public void testVerticalFlipForEmptyChannel() {
    double[][] inputChannel = new double[0][0];
    Channel channel = new Channel(inputChannel);
    ChannelModel actual = channel.getVerticalFlipChannel();
    assertEquals(0, actual.getWidth());
    assertEquals(0, actual.getHeight());
  }

  @Test
  public void testHorizontalFlipForEmptyChannel() {
    double[][] inputChannel = new double[0][0];
    Channel channel = new Channel(inputChannel);
    ChannelModel actual = channel.getHorizontalFlipChannel();
    assertEquals(0, actual.getWidth());
    assertEquals(0, actual.getHeight());
  }

  @Test
  public void testBufferEmptyChannel() {
    double[][] inputChannel = new double[0][0];
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
    double[][] inputChannel = {
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
    double[][] inputChannel = {
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
    double[][] inputChannel = {
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
    double[][] inputChannel = {
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
    double[][] inputChannel = {
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
    double[][] inputChannel = new double[0][0];
    Channel channel = new Channel(inputChannel);
    assertEquals(0, channel.getWidth());
    assertEquals(0, channel.getHeight());
  }

  @Test
  public void testOverlapOnBase(){
    double[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    double[][] values2 = {
            {7, 8},
            {4, 5},
            {1, 2}
    };

    double[][] expectedValues = {
            {7, 8, 3, 4},
            {4, 5, 8, 9},
            {1, 2, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel channel2 = new Channel(values2);
    ChannelModel overlappedChannel = channel1.overlapOnBase(channel2, 0);
    double[][] overlappedValues = overlappedChannel.getChannelValues();
    assertArrayEquals(expectedValues, overlappedValues);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseNegativeStart(){
    double[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    double[][] values2 = {
            {7, 8},
            {4, 5},
            {1, 2}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel channel2 = new Channel(values2);
    ChannelModel overlappedChannel = channel1.overlapOnBase(channel2, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseStartGreaterThanWidth(){
    double[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    double[][] values2 = {
            {7, 8},
            {4, 5},
            {1, 2}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel channel2 = new Channel(values2);
    ChannelModel overlappedChannel = channel1.overlapOnBase(channel2, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseNullChannel(){
    double[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel overlappedChannel = channel1.overlapOnBase(null, 0);
  }

  @Test
  public void testCropVertical(){
    double[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    double[][] expectedValues = {
            {2, 3},
            {7, 8},
            {12, 13},
            {17, 18},
            {22, 23}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel overlappedChannel = channel1.cropVertical(1, 3);
    double[][] overlappedValues = overlappedChannel.getChannelValues();
    assertArrayEquals(expectedValues, overlappedValues);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalNegativeStart(){
    double[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    double[][] expectedValues = {
            {2, 3},
            {7, 8},
            {12, 13},
            {17, 18},
            {22, 23}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel overlappedChannel = channel1.cropVertical(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalStartGreaterThanEnd(){
    double[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    double[][] expectedValues = {
            {2, 3},
            {7, 8},
            {12, 13},
            {17, 18},
            {22, 23}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel overlappedChannel = channel1.cropVertical(3, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalEndGreaterThanWidth(){
    double[][] values1 = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    double[][] expectedValues = {
            {2, 3},
            {7, 8},
            {12, 13},
            {17, 18},
            {22, 23}
    };
    ChannelModel channel1 = new Channel(values1);
    ChannelModel overlappedChannel = channel1.cropVertical(0, 5);
  }

}