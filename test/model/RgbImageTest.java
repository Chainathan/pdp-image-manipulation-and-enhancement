package model;

import controller.ComponentEnum;

import org.junit.Before;
import org.junit.Test;

import controller.ImageGraphicsImpl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for RGB Image class.
 */
public class RgbImageTest {

  RgbImageModel rgbImage;

  @Before
  public void setUp() {
    //GIVEN
    int[][][] imageValues = new int[][][]{
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
            {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
    };
    ChannelModel channel1 = new Channel(imageValues[0]);
    ChannelModel channel2 = new Channel(imageValues[1]);
    ChannelModel channel3 = new Channel(imageValues[2]);
    rgbImage = new RgbImage(channel1, channel2, channel3, 255);
  }

  private boolean assertImages(int[][][] expectedImage, RgbImageModel rgbImageModel) {
    int[][][] actual = rgbImageModel.getImageData().getData();
    int height = actual[0].length;
    int width = actual[0][0].length;
    //THEN
    if (rgbImageModel.getImageData().getMaxValue() != 255) {
      return false;
    }
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (expectedImage[0][i][j] != actual[0][i][j]
                || expectedImage[1][i][j] != actual[1][i][j]
                || expectedImage[2][i][j] != actual[2][i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  @Test
  public void testBrightenWithPositiveIncrement() {
    //GIVEN
    int[][][] expectedImage = {
            {{51, 52, 53},
                    {54, 55, 56},
                    {57, 58, 59}},
            {{54, 55, 56},
                    {51, 52, 53},
                    {57, 58, 59}},
            {{51, 52, 53},
                    {57, 58, 59},
                    {54, 55, 56}}
    };
    //WHEN
    RgbImageModel brightened = rgbImage.brighten(50);
    //THEN
    assertTrue(assertImages(expectedImage, brightened));
  }

  @Test
  public void testBrightenWithNegativeIncrement() {
    //GIVEN
    int[][][] expectedImage = {
            {{0, 0, 1},
                    {2, 3, 4},
                    {5, 6, 7}},
            {{2, 3, 4},
                    {0, 0, 1},
                    {5, 6, 7}},
            {{0, 0, 1},
                    {5, 6, 7},
                    {2, 3, 4}}
    };
    //WHEN
    RgbImageModel darkened = rgbImage.brighten(-2);
    //THEN
    assertTrue(assertImages(expectedImage, darkened));
  }

  @Test
  public void testHorizontalFlip() {
    int[][][] expectedImage = {
            {{3, 2, 1},
                    {6, 5, 4},
                    {9, 8, 7}},
            {{6, 5, 4},
                    {3, 2, 1},
                    {9, 8, 7}},
            {{3, 2, 1},
                    {9, 8, 7},
                    {6, 5, 4}}
    };
    RgbImageModel flipped = rgbImage.horizontalFlip();
    //THEN
    assertTrue(assertImages(expectedImage, flipped));
  }

  @Test
  public void testVerticalFlip() {
    //WHEN
    int[][][] expectedImage = {
            {{7, 8, 9},
                    {4, 5, 6},
                    {1, 2, 3}},
            {{7, 8, 9},
                    {1, 2, 3},
                    {4, 5, 6}},
            {{4, 5, 6},
                    {7, 8, 9},
                    {1, 2, 3}}
    };
    RgbImageModel flipped = rgbImage.verticalFlip();
    //THEN
    assertTrue(assertImages(expectedImage, flipped));
  }

  @Test
  public void testBlur() {
    //WHEN
    int[][][] expectedImage = {
            {{1, 2, 2},
                    {4, 5, 5},
                    {4, 5, 4}},
            {{2, 3, 3},
                    {3, 4, 4},
                    {3, 4, 3}},
            {{2, 3, 3},
                    {4, 5, 4},
                    {4, 5, 5}}
    };
    RgbImageModel blurred = rgbImage.blur();
    //THEN
    assertTrue(assertImages(expectedImage, blurred));
  }

  @Test
  public void testSharpen() {
    //WHEN
    int[][][] expectedImage = {
            {{0, 4, 4},
                    {8, 16, 12},
                    {9, 16, 13}},
            {{2, 7, 6},
                    {6, 13, 10},
                    {7, 12, 10}},
            {{3, 7, 5},
                    {10, 18, 15},
                    {7, 14, 10}}
    };
    RgbImageModel sharpened = rgbImage.sharpen();
    //THEN
    assertTrue(assertImages(expectedImage, sharpened));
  }

  @Test
  public void testSepia() {
    //GIVEN

    int[][][] expectedImage = {
            {{4, 5, 6},
                    {4, 5, 6},
                    {9, 10, 12}},
            {{3, 4, 6},
                    {3, 4, 6},
                    {8, 9, 10}},
            {{3, 3, 4},
                    {3, 3, 4},
                    {6, 7, 8}}
    };
    //WHEN
    RgbImageModel sepiaImage = rgbImage.sepia();
    assertTrue(assertImages(expectedImage, sepiaImage));
  }

  @Test
  public void visualizeComponentForRed() {
    //GIVEN
    int[][][] expectedImage = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    //WHEN
    RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.RED);

    //THEN
    assertTrue(assertImages(expectedImage, visualizedImage));
  }

  @Test
  public void visualizeComponentForGreen() {
    //GIVEN
    int[][][] expectedImage = {
            {{0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}},
            {{4, 5, 6},
                    {1, 2, 3},
                    {7, 8, 9}},
            {{0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}}
    };
    //WHEN
    RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.GREEN);
    //THEN
    assertTrue(assertImages(expectedImage, visualizedImage));
  }

  @Test
  public void visualizeComponentForBlue() {
    //GIVEN
    int[][][] expectedImage = {
            {{0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}},
            {{0, 0, 0},
                    {0, 0, 0},
                    {0, 0, 0}},
            {{1, 2, 3},
                    {7, 8, 9},
                    {4, 5, 6}}
    };
    //WHEN
    RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.BLUE);
    //THEN
    assertTrue(assertImages(expectedImage, visualizedImage));
  }

  @Test
  public void visualizeComponentForLUMA() {
    //GIVEN
    int[][][] expectedImage = {
            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}},
            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}},
            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}}
    };
    //WHEN
    RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.LUMA);

    //THEN
    assertTrue(assertImages(expectedImage, visualizedImage));
  }

  @Test
  public void visualizeComponentForIntensity() {
    //GIVEN
    int[][][] expectedImage = {
            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}},
            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}},
            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}}
    };
    //WHEN
    RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.INTENSITY);

    //THEN
    assertTrue(assertImages(expectedImage, visualizedImage));
  }

  @Test
  public void visualizeComponentForValue() {
    //GIVEN
    int[][][] expectedImage = {
            {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}},
            {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}},
            {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}}
    };
    //WHEN
    RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.VALUE);
    //THEN
    assertTrue(assertImages(expectedImage, visualizedImage));
  }

  @Test
  public void testRgbImageForInvalidChannelSize() {
    try {
      RgbImageModel rgbImageModel = new RgbImage(
              new Channel(),
              new Channel(new int[3][4]),
              new Channel(),
              255
      );
      fail("Above line should throw an exception.");
    } catch (IllegalArgumentException e) {
      //Exception catched.
    }
  }

  @Test
  public void testRgbImageForInvalidMaxPixelSize() {
    try {
      RgbImageModel rgbImageModel = new RgbImage(
              new Channel(),
              new Channel(),
              new Channel(),
              -2
      );
      fail("Above line should throw an exception.");
    } catch (IllegalArgumentException e) {
      //Exception catched.
    }
  }

  @Test
  public void testRgbImageForValidArguments() {
    try {
      RgbImageModel rgbImageModel = new RgbImage(
              new Channel(),
              new Channel(),
              new Channel(),
              255
      );
    } catch (IllegalArgumentException e) {
      fail("Above line should not throw an exception.");
    }
  }

  @Test
  public void testRgbImageEmptyConstructor() {
    try {
      new RgbImage();
    } catch (IllegalArgumentException e) {
      fail("Above line should not throw an exception.");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateInstanceForInvalidRgbChannelLength() {
    //GIVEN
    ChannelModel channel1 = new Channel();
    RgbImage rgbImage = new RgbImage();

    //WHEN
    rgbImage.createInstance(channel1);
  }

  @Test
  public void testCreateInstanceForValidChannelLength() {
    //GIVEN
    ChannelModel channel1 = new Channel();
    RgbImage rgbImage = new RgbImage();

    //WHEN
    try {
      rgbImage.createInstance(channel1, channel1, channel1);
    } catch (IllegalArgumentException e) {
      fail("Above line should not throw an exception");
    }
  }

  @Test
  public void testGetImageData() {
    //GIVEN
    int[][] values = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
    ChannelModel channel = new Channel(values);
    RgbImageModel rgbImage = new RgbImage(channel, channel, channel, 255);
    int[][][] expectedImage = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
    };
    //THEN
    assertTrue(assertImages(expectedImage, rgbImage));

  }

  @Test
  public void testLoadImageDataForInvalidRgb() {
    try {
      RgbImageModel rgbImageModel = new RgbImage();
      int[][][] values = {
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
      };
      ImageData imageData = new ImageData(values, 255);
      rgbImageModel.loadImageData(imageData);
      fail("Above line should throw an exception.");
    } catch (IllegalArgumentException e) {
      //Exception catched.
    }
  }

  @Test
  public void testLoadImageDataForValidRgb() {
    try {
      RgbImageModel rgbImageModel = new RgbImage();
      int[][][] values = {
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
      };
      ImageData imageData = new ImageData(values, 255);
      rgbImageModel.loadImageData(imageData);
    } catch (IllegalArgumentException e) {
      fail("Above line should not throw an exception.");
    }
  }

  @Test
  public void testCheckValidRgbImageData() {
    //GIVEN
    StringBuilder log = new StringBuilder();
    RgbImage rgbImage = new RgbImage();
    int[][][] image = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
    };
    ImageData imageData = new ImageData(image, 255);

    //WHEN
    try {
      rgbImage.checkValidRgbImageData(imageData);
      //Above line won't throw an exception.
    } catch (IllegalArgumentException e) {
      fail("Above line should not throw an exception");
    }
  }

  @Test
  public void testCheckInValidRgbImageData() {
    //GIVEN
    RgbImage rgbImage = new RgbImage();
    int[][][] image = {
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
    };
    ImageData imageData = new ImageData(image, 255);

    //WHEN
    try {
      rgbImage.checkValidRgbImageData(imageData);
      fail("Above line should throw an exception");
    } catch (IllegalArgumentException e) {
      //exception catched.
    }
  }

  @Test
  public void testCreateHistogram() {
    //GIVEN
    int[][] red = {{255, 228, 46}, {255, 255, 255}, {198, 255, 255}, {255, 255, 255}};
    int[][] green = {{255, 228, 46}, {100, 255, 200}, {198, 200, 50}, {255, 10, 25}};
    int[][] blue = {{60, 0, 70}, {0, 0, 80}, {0, 100, 0}, {0, 110, 255}};

    ChannelModel channel1 = new Channel(red);
    ChannelModel channel2 = new Channel(green);
    ChannelModel channel3 = new Channel(blue);
    RgbImageModel rgbImage = new RgbImage(
            channel1, channel2, channel3,
            255
    );
    int[][][] expectedImage = {
            {{255, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192}},
            {{255, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192}},
            {{255, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192},
                    {192, 192, 192, 192, 192}}
    };
    //WHEN
    RgbImageModel histogram = rgbImage.createHistogram(new ImageGraphicsImpl(5, 5, 5));

    //THEN
    assertTrue(assertImages(expectedImage, histogram));
  }

  @Test
  public void testOverlapOnBase() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    RgbImageModel otherImage = new RgbImage(
            channel, channel, channel,
            255
    );
    int[][] expectedChannel = {
            {1, 2, 1},
            {4, 5, 4},
            {7, 8, 7},
            {10, 11, 10}
    };
    int[][][] expectedImage = {expectedChannel, expectedChannel, expectedChannel};
    //WHEN
    RgbImageModel overlappedImage = rgbImage.overlapOnBase(otherImage, 50);

    //THEN
    assertTrue(assertImages(expectedImage, overlappedImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseForNegativeStart() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    RgbImageModel otherImage = new RgbImage(
            channel, channel, channel,
            255
    );

    //WHEN
    rgbImage.overlapOnBase(otherImage, -50);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseForStartGreaterThan100() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    RgbImageModel otherImage = new RgbImage(
            channel, channel, channel,
            255
    );

    //WHEN
    rgbImage.overlapOnBase(otherImage, 150);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapOnBaseForNullImage() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );

    //WHEN
    rgbImage.overlapOnBase(null, 50);
  }

  @Test
  public void testCropVertical() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    int[][] expectedChannel = {
            {2},
            {5},
            {8},
            {11}
    };
    int[][][] expectedImage = {expectedChannel, expectedChannel, expectedChannel};
    //WHEN
    RgbImageModel croppedImage = rgbImage.cropVertical(20, 50);

    //THEN
    assertTrue(assertImages(expectedImage, croppedImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalForNegativeStart() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    //WHEN
    rgbImage.cropVertical(-20, 50);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalForStartGreaterThan100() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    //WHEN
    rgbImage.cropVertical(120, 50);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalNegativeEnd() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    //WHEN
    rgbImage.cropVertical(20, -50);


  }

  @Test(expected = IllegalArgumentException.class)
  public void testCropVerticalEndGreaterThan100() {
    //GIVEN
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    //WHEN
    rgbImage.cropVertical(20, 150);
  }

  @Test
  public void testAdjustLevels() {
    //GIVEN
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    int[][][] expectedImage = {
            {{255, 228, 46},
                    {255, 255, 255},
                    {198, 255, 255},
                    {255, 255, 255}},
            {{255, 228, 46},
                    {255, 255, 255},
                    {198, 255, 255},
                    {255, 255, 255}},
            {{255, 228, 46},
                    {255, 255, 255},
                    {198, 255, 255},
                    {255, 255, 255}}
    };
    //WHEN
    RgbImageModel adjustedImage = rgbImage.adjustLevels(20, 50, 255);
    //THEN
    assertTrue(assertImages(expectedImage, adjustedImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForNegativeBlack() {
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(-20, 50, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForNegativeMid() {
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(0, -20, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForNegativeWhite() {
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(0, 20, -255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForBlackGreatherThanMid() {
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(30, 20, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForMidGreatherThanWhite() {
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(30, 255, 200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForWhiteGreatherThan255() {
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(0, 20, 260);
  }


  @Test
  public void testCorrectColor() {
    //GIVEN
    int[][] red = {
            {241, 80, 30},
            {220, 158, 158},
            {70, 100, 100},
            {100, 110, 120}
    };
    int[][] green = {
            {158, 80, 30},
            {220, 158, 158},
            {70, 100, 100},
            {158, 110, 120}
    };
    int[][] blue = {
            {241, 80, 30},
            {220, 158, 158},
            {70, 100, 100},
            {100, 110, 120}
    };
    ChannelModel channel1 = new Channel(red);
    ChannelModel channel2 = new Channel(green);
    ChannelModel channel3 = new Channel(blue);
    RgbImageModel rgbImage = new RgbImage(
            channel1, channel2, channel3,
            255
    );
    int[][][] expectedImage = {
            {{255, 99, 49},
                    {239, 177, 177},
                    {89, 119, 119},
                    {119, 129, 139}},
            {{119, 41, 0},
                    {181, 119, 119},
                    {31, 61, 61},
                    {119, 71, 81}},
            {{255, 99, 49},
                    {239, 177, 177},
                    {89, 119, 119},
                    {119, 129, 139}}
    };
    //WHEN
    RgbImageModel colorCorrected = rgbImage.correctColor();
    //THEN
    assertTrue(assertImages(expectedImage, colorCorrected));
  }


  @Test
  public void testApplyCompression() {
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    int[][][] expectedImage = {
            {{3, 3, 4},
                    {3, 3, 4},
                    {9, 9, 10},
                    {9, 9, 10}},
            {{3, 3, 4},
                    {3, 3, 4},
                    {9, 9, 10},
                    {9, 9, 10}},
            {{3, 3, 4},
                    {3, 3, 4},
                    {9, 9, 10},
                    {9, 9, 10}}
    };
    //WHEN
    RgbImageModel compressedImage = rgbImage.applyCompression(50);

    //THEN
    assertTrue(assertImages(expectedImage, compressedImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyCompressionForNegativeCompression() {
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    //WHEN
    rgbImage.applyCompression(-50);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyCompressionForCompressionGreaterThan100() {
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    //WHEN
    rgbImage.applyCompression(150);

  }

}