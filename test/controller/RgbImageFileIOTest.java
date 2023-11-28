package controller;

import org.junit.Test;

import java.io.IOException;

import exceptions.FileFormatNotSupportedException;
import model.ImageData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for RGB Image FIle IO class tests.
 */
public class RgbImageFileIOTest {
  @Test
  public void testLoadForInvalidFileFormat() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("file");
      fail("Above line should throw an exception");
    } catch (FileFormatNotSupportedException e) {
      assertEquals("Unsupported File format", e.getMessage());
    } catch (IOException e) {
      fail("This exception should not be thrown");
    }
  }

  @Test
  public void testLoadPPMForFileNotFound() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("file.ppm");
      fail("Above line should throw an exception");
    } catch (FileFormatNotSupportedException e) {
      fail("This exception should not be thrown");
    } catch (IOException e) {
      //Exception expected
      assertEquals("File Not Found", e.getMessage());
    }
  }

  @Test
  public void testLoadPPMInvalidToken() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("images/test/testInvalidToken.ppm");
      fail("Above line should throw an exception");
    } catch (FileFormatNotSupportedException e) {
      fail("This exception should not be thrown");
    } catch (IOException e) {
      //Exception expected
      assertEquals("Invalid PPM file: plain RAW file should begin with P3", e.getMessage());
    }
  }

  @Test
  public void testLoadCorruptedPPMWithNegativeValues() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("images/test/testCorrupted.ppm");
      fail("Above line should throw an exception");
    } catch (IOException e) {
      //Exception expected
      assertEquals("Corrupted PPM file", e.getMessage());
    }
  }

  @Test
  public void testLoadCorruptedPPMWithValuesGreaterThanMaxPixel() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("images/test/testCorruptedMaxValue.ppm");
      fail("Above line should throw an exception");
    } catch (IOException e) {
      //Exception expected
      assertEquals("Corrupted PPM file", e.getMessage());
    }
  }

  @Test
  public void testLoadCorruptedPPMWithMissingValues() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("images/test/testCorruptedMissingData.ppm");
      fail("Above line should throw an exception");
    } catch (IOException e) {
      //Exception expected
      assertEquals("Corrupted PPM file.", e.getMessage());
    }
  }

  private void assertImageData(int[][][] expectedData, ImageData image) {
    int[][][] actual = image.getData();
    for (int i = 0; i < actual.length; i++) {
      for (int j = 0; j < actual[0].length; j++) {
        for (int k = 0; k < actual[0][0].length; k++) {
          assertEquals(expectedData[i][j][k], actual[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testLoadPPM() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();
    int[][][] expectedData = {
            {{151, 153, 160, 154, 154},
                    {154, 158, 150, 153, 155},
                    {151, 153, 156, 153, 155},
                    {153, 155, 161, 160, 154}},
            {{90, 92, 95, 91, 91},
                    {93, 94, 86, 90, 91},
                    {89, 91, 92, 86, 91},
                    {91, 91, 97, 96, 93}},
            {{58, 62, 66, 59, 59},
                    {63, 65, 57, 56, 64},
                    {63, 65, 66, 66, 64},
                    {65, 64, 70, 69, 65}}
    };
    //WHEN
    try {
      ImageData image = imageFileIO.load("images/test/test.ppm");
      assertImageData(expectedData, image);
      assertEquals(250, image.getMaxValue());
    } catch (IOException e) {
      fail("Expection should not be thrown");
    }
  }

  @Test
  public void testLoadPNGForFileNotFound() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("file.png");
      fail("Above line should throw an exception");
    } catch (IOException e) {
      //Exception expected
      assertEquals("Invalid Image.", e.getMessage());
    }
  }

  @Test
  public void testLoadPNGNullImage() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("images/test/testEmptyImage.png");
      fail("Above line should throw an exception");
    } catch (IOException e) {
      //Exception expected
      assertEquals("Invalid Image.", e.getMessage());
    }
  }

  //Check for Color Space
  //Check for image null by ImageIO.read()
  @Test
  public void testLoadPNG() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();
    int[][][] expectedData = {
            {{151, 153, 160, 154, 154},
                    {154, 158, 150, 153, 155},
                    {151, 153, 156, 153, 155},
                    {153, 155, 161, 160, 154}},
            {{90, 92, 95, 91, 91},
                    {93, 94, 86, 90, 91},
                    {89, 91, 92, 86, 91},
                    {91, 91, 97, 96, 93}},
            {{58, 62, 66, 59, 59},
                    {63, 65, 57, 56, 64},
                    {63, 65, 66, 66, 64},
                    {65, 64, 70, 69, 65}}
    };

    //WHEN
    try {
      ImageData image = imageFileIO.load("images/test/test.png");
      assertImageData(expectedData, image);
      assertEquals(255, image.getMaxValue());
    } catch (IOException e) {
      fail("Expection should not be thrown");
    }
  }

  @Test
  public void testLoadJPGForFileNotFound() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("file.jpg");
      fail("Above line should throw an exception");
    } catch (IOException e) {
      //Exception expected
      assertEquals("Invalid Image.", e.getMessage());
    }
  }

  @Test
  public void testLoadJPGForEmptyImage() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.load("images/test/testEmptyImage.jpg");
      fail("Above line should throw an exception");
    } catch (IOException e) {
      //Exception expected
      assertEquals("Invalid Image.", e.getMessage());
    }
  }

  @Test
  public void testLoadJPG() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();
    int[][][] expectedData = {
            {{155, 156, 156, 155, 153},
                    {154, 156, 156, 155, 154},
                    {154, 156, 157, 156, 155},
                    {154, 156, 157, 157, 155}},
            {{91, 92, 92, 91, 89},
                    {90, 92, 92, 91, 90},
                    {90, 92, 93, 92, 91},
                    {90, 92, 93, 93, 91}},
            {{64, 65, 65, 64, 62},
                    {63, 65, 65, 64, 63},
                    {63, 65, 66, 65, 64},
                    {63, 65, 66, 66, 64}}
    };
    try {
      ImageData image = imageFileIO.load("images/test/test.jpg");
      assertImageData(expectedData, image);
      assertEquals(255, image.getMaxValue());
    } catch (IOException e) {
      fail("Expection should not be thrown");
    }
  }

  @Test
  public void testSaveForUnsupportedFormat() {
    //GIVEN
    ImageFileIO imageFileIO = new RgbImageFileIO();

    //WHEN
    try {
      imageFileIO.save("file", new ImageData(new int[3][10][10], 255));
      fail("Above line should throw an exception");
    } catch (FileFormatNotSupportedException e) {
      //Exception catched.
      assertEquals("Unsupported File format", e.getMessage());
    } catch (IOException e) {
      fail("This exception should not be thrown");
    }
  }
}