package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import model.ImageData;
import model.ImageProcessorModel;
import model.RgbImageProcessor;
import view.ImageProcessorView;
import view.TextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for Integration test of RGB controller.
 */
public class RgbControllerIntegrationTest {
  String imageName1;
  String imageName2;
  Appendable appendable;
  ImageProcessorView textView;
  ImageData imageData;

  @Before
  public void setup() {
    imageName1 = "image1";
    imageName2 = "image2";
    appendable = new StringBuilder();
    textView = new TextView(appendable);
    double[][][] init = {
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
    imageData = new ImageData(init, 250);
  }

  @Test
  public void testCompress(){
   //String command = "load ImagesRef/compression/koala-square.png koala";
   String command = "load ImagesRef/manhattan/manhattan-small.png manhattan";
    Readable reader = new StringReader(command);
    ImageProcessorModel model = new RgbImageProcessor();
    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
    model.compress("manhattan","manhattan-compress-new-90",90);
    String command1 = "save ImagesRef/manhattan-compress-new-90.png manhattan-compress-new-90";
    Readable reader1 = new StringReader(command1);
    RgbController controller1 = new RgbController(model, textView, reader1);
    try {
      controller1.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testLoad() {
    String command = "load images/test/test.ppm " + imageName1;
    Readable reader = new StringReader(command);
    ImageProcessorModel model = new RgbImageProcessor();
    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
    ImageData res = model.getImageData(imageName1);
    assertEquals(imageData, res);
  }

  @Test
  public void testSavePPM() {
    ImageProcessorModel model = new RgbImageProcessor();
    model.addImage(imageName1, imageData);
    String command = "save images/temp/test.ppm " + imageName1;
    Readable reader = new StringReader(command);
    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
    try {
      ImageFileIO fileIO = new RgbImageFileIO();
      ImageData res = fileIO.load("images/temp/test.ppm");
      assertEquals(imageData, res);
      deleteFiles("images/temp");
    } catch (Exception e) {
      deleteFiles("images/temp");
      //e.printStackTrace();
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testSavePNG() {
    ImageProcessorModel model = new RgbImageProcessor();
    model.addImage(imageName1, imageData);
    String command = "save images/temp/test.png " + imageName1;
    Readable reader = new StringReader(command);
    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
    try {
      ImageFileIO fileIO = new RgbImageFileIO();
      ImageData res = fileIO.load("images/temp/test.png");
      ImageData exp = new ImageData(imageData.getData(), 255);
      assertEquals(exp, res);
      deleteFiles("images/temp");
    } catch (Exception e) {
      deleteFiles("images/temp");
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testSaveJPG() {
    ImageProcessorModel model = new RgbImageProcessor();
    model.addImage(imageName1, imageData);
    String command = "save images/temp/test.jpg " + imageName1;
    Readable reader = new StringReader(command);
    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
    try {
      ImageFileIO fileIO = new RgbImageFileIO();
      ImageData res = fileIO.load("images/temp/test.jpg");
      ImageData exp = new ImageData(imageData.getData(), 255);
      assertEquals(exp.getMaxValue(), res.getMaxValue());
      assertTrue(compare3DArrays(exp.getData(), res.getData(), 10));
      deleteFiles("images/temp");
    } catch (Exception e) {
      deleteFiles("images/temp");
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testBrighten() {
    double[][][] expected = {
            {{201, 203, 210, 204, 204},
                    {204, 208, 200, 203, 205},
                    {201, 203, 206, 203, 205},
                    {203, 205, 211, 210, 204}},
            {{140, 142, 145, 141, 141},
                    {143, 144, 136, 140, 141},
                    {139, 141, 142, 136, 141},
                    {141, 141, 147, 146, 143}},
            {{108, 112, 116, 109, 109},
                    {113, 115, 107, 106, 114},
                    {113, 115, 116, 116, 114},
                    {115, 114, 120, 119, 115}}
    };
    String command = "brighten 50";
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testDarken() {
    double[][][] expected = {
            {{101, 103, 110, 104, 104},
                    {104, 108, 100, 103, 105},
                    {101, 103, 106, 103, 105},
                    {103, 105, 111, 110, 104}},
            {{40, 42, 45, 41, 41},
                    {43, 44, 36, 40, 41},
                    {39, 41, 42, 36, 41},
                    {41, 41, 47, 46, 43}},
            {{8, 12, 16, 9, 9},
                    {13, 15, 7, 6, 14},
                    {13, 15, 16, 16, 14},
                    {15, 14, 20, 19, 15}}
    };
    String command = "brighten -50";
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testBlur() {
    double[][][] expected = {
            {{86, 116, 117, 116, 87},
                    {117, 154, 157, 154, 116},
                    {115, 155, 156, 155, 116},
                    {86, 116, 119, 118, 88}},
            {{53, 69, 70, 68, 51},
                    {69, 94, 92, 91, 67},
                    {68, 92, 92, 91, 69},
                    {51, 69, 70, 71, 51}},
            {{35, 47, 47, 45, 34},
                    {47, 63, 61, 60, 46},
                    {48, 64, 65, 65, 48},
                    {36, 49, 51, 50, 37}}
    };
    String command = "blur";
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testSharpen() {
    double[][][] expected = {
            {{172, 234, 182, 233, 174},
                    {230, 250, 249, 250, 231},
                    {229, 250, 250, 250, 233},
                    {171, 232, 183, 240, 175}},
            {{104, 142, 110, 139, 104},
                    {140, 198, 146, 192, 135},
                    {137, 196, 153, 192, 138},
                    {101, 137, 110, 144, 106}},
            {{67, 94, 71, 87, 65},
                    {94, 136, 97, 125, 92},
                    {96, 140, 109, 140, 98},
                    {73, 100, 83, 107, 76}}
    };
    String command = "sharpen";
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testSepia() {
    double[][][] expected = {
            {{140, 143, 148, 142, 142},
                    {144, 147, 136, 140, 143},
                    {140, 142, 145, 139, 143},
                    {142, 143, 151, 150, 144}},
            {{124, 127, 132, 126, 126},
                    {128, 131, 121, 125, 127},
                    {124, 127, 129, 123, 127},
                    {127, 127, 134, 133, 128}},
            {{97, 99, 103, 98, 98},
                    {100, 102, 94, 97, 99},
                    {97, 99, 100, 96, 99},
                    {99, 99, 105, 104, 100}}
    };
    String command = "sepia";
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testRed() {
    double[][][] expected = {
            {{151, 153, 160, 154, 154},
                    {154, 158, 150, 153, 155},
                    {151, 153, 156, 153, 155},
                    {153, 155, 161, 160, 154}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}}
    };
    String command = "red-component";
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testBlue() {
    String command = "blue-component";
    double[][][] expected = {
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{58, 62, 66, 59, 59},
                    {63, 65, 57, 56, 64},
                    {63, 65, 66, 66, 64},
                    {65, 64, 70, 69, 65}}
    };
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testGreen() {
    String command = "green-component";
    double[][][] expected = {
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{90, 92, 95, 91, 91},
                    {93, 94, 86, 90, 91},
                    {89, 91, 92, 86, 91},
                    {91, 91, 97, 96, 93}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}}
    };
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testValue() {
    String command = "value-component";
    double[][][] expected = {
            {{151, 153, 160, 154, 154},
                    {154, 158, 150, 153, 155},
                    {151, 153, 156, 153, 155},
                    {153, 155, 161, 160, 154}},
            {{151, 153, 160, 154, 154},
                    {154, 158, 150, 153, 155},
                    {151, 153, 156, 153, 155},
                    {153, 155, 161, 160, 154}},
            {{151, 153, 160, 154, 154},
                    {154, 158, 150, 153, 155},
                    {151, 153, 156, 153, 155},
                    {153, 155, 161, 160, 154}}
    };
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testIntensity() {
    String command = "intensity-component";
    double[][][] expected = {
            {{99, 102, 107, 101, 101},
                    {103, 105, 97, 99, 103},
                    {101, 103, 104, 101, 103},
                    {103, 103, 109, 108, 104}},
            {{99, 102, 107, 101, 101},
                    {103, 105, 97, 99, 103},
                    {101, 103, 104, 101, 103},
                    {103, 103, 109, 108, 104}},
            {{99, 102, 107, 101, 101},
                    {103, 105, 97, 99, 103},
                    {101, 103, 104, 101, 103},
                    {103, 103, 109, 108, 104}}
    };
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testLuma() {
    String command = "luma-component";
    double[][][] expected = {
            {{101, 103, 107, 102, 102},
                    {104, 106, 98, 101, 103},
                    {100, 102, 104, 99, 103},
                    {102, 103, 109, 108, 104}},
            {{101, 103, 107, 102, 102},
                    {104, 106, 98, 101, 103},
                    {100, 102, 104, 99, 103},
                    {102, 103, 109, 108, 104}},
            {{101, 103, 107, 102, 102},
                    {104, 106, 98, 101, 103},
                    {100, 102, 104, 99, 103},
                    {102, 103, 109, 108, 104}}
    };
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testVerticalFlip() {
    String command = "horizontal-flip";
    double[][][] expected = {
            {{154, 154, 160, 153, 151},
                    {155, 153, 150, 158, 154},
                    {155, 153, 156, 153, 151},
                    {154, 160, 161, 155, 153}},
            {{91, 91, 95, 92, 90},
                    {91, 90, 86, 94, 93},
                    {91, 86, 92, 91, 89},
                    {93, 96, 97, 91, 91}},
            {{59, 59, 66, 62, 58},
                    {64, 56, 57, 65, 63},
                    {64, 66, 66, 65, 63},
                    {65, 69, 70, 64, 65}}
    };
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testHorizontalFlip() {
    String command = "vertical-flip";
    double[][][] expected = {
            {{153, 155, 161, 160, 154},
                    {151, 153, 156, 153, 155},
                    {154, 158, 150, 153, 155},
                    {151, 153, 160, 154, 154}},
            {{91, 91, 97, 96, 93},
                    {89, 91, 92, 86, 91},
                    {93, 94, 86, 90, 91},
                    {90, 92, 95, 91, 91}},
            {{65, 64, 70, 69, 65},
                    {63, 65, 66, 66, 64},
                    {63, 65, 57, 56, 64},
                    {58, 62, 66, 59, 59}}
    };
    assertTrue(testHelper(command, expected));
  }

  @Test
  public void testSplit() {
    String command = "rgb-split";
    ImageProcessorModel model = new RgbImageProcessor();
    model.addImage(imageName1, imageData);
    command += " " + imageName1 + " red green blue";
    runCommand(command, model);
    double[][][] expected = {
            {{151, 153, 160, 154, 154},
                    {154, 158, 150, 153, 155},
                    {151, 153, 156, 153, 155},
                    {153, 155, 161, 160, 154}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}}
    };
    ImageData exp = new ImageData(expected, 250);
    ImageData res = model.getImageData("red");
    assertEquals(exp, res);
    expected = new double[][][]{
            {{0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}},
            {{90, 92, 95, 91, 91},
                    {93, 94, 86, 90, 91},
                    {89, 91, 92, 86, 91},
                    {91, 91, 97, 96, 93}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}}
    };
    exp = new ImageData(expected, 250);
    res = model.getImageData("green");
    assertEquals(exp, res);
    expected = new double[][][]{
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{58, 62, 66, 59, 59},
                    {63, 65, 57, 56, 64},
                    {63, 65, 66, 66, 64},
                    {65, 64, 70, 69, 65}}
    };
    exp = new ImageData(expected, 250);
    res = model.getImageData("blue");
    assertEquals(exp, res);
  }

  @Test
  public void testCombine() {
    ImageProcessorModel model = new RgbImageProcessor();
    double[][][] initV = {
            {{151, 153, 160, 154, 154},
                    {154, 158, 150, 153, 155},
                    {151, 153, 156, 153, 155},
                    {153, 155, 161, 160, 154}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 50, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 100, 0, 0, 0},
                    {0, 0, 0, 0, 0}}
    };
    ImageData init = new ImageData(initV, 250);
    model.addImage("red", init);
    initV = new double[][][]{
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 40, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{90, 92, 95, 91, 91},
                    {93, 94, 86, 90, 91},
                    {89, 91, 92, 86, 91},
                    {91, 91, 97, 96, 93}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 60, 0},
                    {0, 0, 0, 0, 0}}
    };
    init = new ImageData(initV, 250);
    model.addImage("green", init);
    initV = new double[][][]{
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 10, 0, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 20, 0, 0},
                    {0, 0, 0, 0, 0}},
            {{58, 62, 66, 59, 59},
                    {63, 65, 57, 56, 64},
                    {63, 65, 66, 66, 64},
                    {65, 64, 70, 69, 65}}
    };
    init = new ImageData(initV, 250);
    model.addImage("blue", init);

    String command = "rgb-combine " + imageName1 + " red green blue";
    runCommand(command, model);
    ImageData res = model.getImageData(imageName1);
    assertEquals(imageData, res);
  }

  @Test
  public void testRunScript() {
    String command = "run \"images/test/script folder/scriptTest.txt\"";
    ImageProcessorModel model = new RgbImageProcessor();
    Readable reader = new StringReader(command);
    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
    String[] imageNames = {
        "koalaTest.jpg",
        "koalaTest.png",
        "koalaTest.ppm",
        "koalaTestBlue.png",
        "koalaTestBlur.png",
        "koalaTestBright.png",
        "koalaTestBrightDark.png",
        "koalaTestCombine.png",
        "koalaTestDark.png",
        "koalaTestGreen.png",
        "koalaTestIntensity.png",
        "koalaTestLuma.png",
        "koalaTestRed.png",
        "koalaTestSepia.png",
        "koalaTestSharpen.png",
        "koalaTestSplitBlue.png",
        "koalaTestSplitGreen.png",
        "koalaTestSplitRed.png",
        "koalaTestSplitRedBr.png",
        "koalaTestTint.png",
        "koalaTestValue.png",
        "koalaTestVertical.png",
        "koalaTestVerticalHorizontal.png"
    };
    String src = "images/scriptRef/";
    String res = "images/temp/";
    ImageFileIO fileIO = new RgbImageFileIO();
    ImageData exp;
    ImageData act;
    try {
      for (String imageName :
              imageNames) {
        exp = fileIO.load(src + imageName);
        act = fileIO.load(res + imageName);
        assertEquals(exp, act);
      }
      deleteFiles("images/temp");
    } catch (Exception e) {
      deleteFiles("images/temp");
      fail("Should not have thrown error");
    }
  }

  private void deleteFiles(String folderPath) {
    try {
      Path directory = Paths.get(folderPath);
      if (Files.exists(directory) && Files.isDirectory(directory)) {
        Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
            new SimpleFileVisitor<Path>() {
              @Override
              public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                      throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
              }
            });
      }
    } catch (IOException e) {
      // delete fail but test pass
    }
  }

  private boolean compare3DArrays(double[][][] array1, double[][][] array2, int buffer) {
    if (array1.length != array2.length) {
      return false;
    }

    for (int i = 0; i < array1.length; i++) {
      if (array1[i].length != array2[i].length || array1[i][0].length != array2[i][0].length) {
        return false;
      }

      for (int j = 0; j < array1[i].length; j++) {
        for (int k = 0; k < array1[i][j].length; k++) {
          double diff = Math.abs(array1[i][j][k] - array2[i][j][k]);
          if (diff > buffer) {
            return false;
          }
        }
      }
    }

    return true;
  }

  private void runCommand(String command, ImageProcessorModel model) {
    Readable reader = new StringReader(command);
    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
  }

  private boolean testHelper(String command, double[][][] expected) {
    ImageProcessorModel model = new RgbImageProcessor();
    model.addImage(imageName1, imageData);
    command += " " + imageName1 + " " + imageName2;
    runCommand(command, model);
    ImageData exp = new ImageData(expected, 250);
    ImageData res = model.getImageData(imageName2);
    return exp.equals(res);
  }
}
