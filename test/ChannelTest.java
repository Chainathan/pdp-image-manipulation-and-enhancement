import org.junit.Test;

import Model.Channel;
import Model.ChannelModel;

import static org.junit.Assert.*;

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
  public void testPositiveAddBuffer() {
    int[][] values = {
            {100, 200, 120},
            {150, 245, 250},
            {0, 110, 255}
    };
    int buffer = 10;
    int maxPixelValue = 255;
    int[][] expectedValues = {
            {11, 12, 13},
            {14, 15, 16},
            {17, 18, 19}
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
    ChannelModel result = channel.applyConvolution(kernel,255);
    int[][] expectedOutput = {{42,48,54},
            {72,78,84},
            {102,108,114},
    };
    int[][] actualOutput = result.getChannelValues();
    int height = inputChannel.length - kernel.length + 1;
    int width = inputChannel[0].length - kernel[0].length + 1;
    System.out.print("{");
      for (int i = 0; i < height; i++) {
        System.out.print("{");
        for (int j = 0; j < width; j++) {
          System.out.print(actualOutput[i][j]+",");
        }
        System.out.println("},");
      }
    System.out.println("}");
    assertArrayEquals(expectedOutput, actualOutput);
  }

  //Check for 0 width and height all methods
  @Test
  public void testVerticalFlipForEmptyChannel(){
    int[][] inputChannel = new int[0][0];
    Channel channel = new Channel(inputChannel);
    ChannelModel actual = channel.getVerticalFlipChannel();
    assertEquals(0,actual.getWidth());
    assertEquals(0,actual.getHeight());
  }

  @Test
  public void testHorizontalFlipForEmptyChannel(){
    int[][] inputChannel = new int[0][0];
    Channel channel = new Channel(inputChannel);
    ChannelModel actual = channel.getHorizontalFlipChannel();
    assertEquals(0,actual.getWidth());
    assertEquals(0,actual.getHeight());
  }

  @Test
  public void testBufferEmptyChannel(){
    int[][] inputChannel = new int[0][0];
    Channel channel = new Channel(inputChannel);
    ChannelModel actual = channel.addBuffer(10, 255);
    assertEquals(0,actual.getWidth());
    assertEquals(0,actual.getHeight());
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
  public void testGetHeight(){
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
  public void testGetWidth(){
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
  public void testGetValue(){
    int[][] inputChannel = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    Channel channel = new Channel(inputChannel);
    assertEquals(18, channel.getValue(3,2));
  }

  @Test
  public void testInitializingEmptyChannel(){
    Channel channel = new Channel();
    assertEquals(0,channel.getWidth());
    assertEquals(0,channel.getHeight());
  }

  @Test
  public void testZeroChannel(){
    Channel channel = new Channel(5,4);
    assertEquals(4,channel.getWidth());
    assertEquals(5,channel.getHeight());
  }

  @Test
  public void testValidChannel(){
    int[][] inputChannel = {
            {1, 2, 3, 4},
            {6, 7, 8, 9},
            {11, 12, 13, 14},
            {16, 17, 18, 19},
            {21, 22, 23, 24}
    };
    Channel channel = new Channel(inputChannel);
    for(int i=0;i< channel.getHeight();i++){
      for(int j=0;j< channel.getWidth();j++){
        assertEquals(inputChannel[i][j],channel.getValue(i,j));
      }
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidChannel(){
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
  public void testChannelIsNull(){
    new Channel(null);
  }

  @Test
  public void testEmptyChannel(){
    int[][] inputChannel = new int[0][0];
    Channel channel = new Channel(inputChannel);
    assertEquals(0,channel.getWidth());
    assertEquals(0,channel.getHeight());
  }
}