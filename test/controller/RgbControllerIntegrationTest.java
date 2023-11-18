package controller;

import model.ImageData;
import model.FactoryRgbImageModel;
import model.FactoryRgbImage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import view.ImageProcessorView;
import view.TextView;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Paths;
import java.nio.file.FileVisitOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.EnumSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test class for RgbControllerIntegration class.
 */
public class RgbControllerIntegrationTest {
  String originalImage;
  String destImage;
  Appendable appendable;
  ImageProcessorView textView;
  ImageData imageData;
  FactoryRgbImageModel factory;
  CommandMapper commandMapper;

  @Before
  public void setup() {
    appendable = new StringBuilder();
    textView = new TextView(appendable);
    factory = new FactoryRgbImage();
    commandMapper = new CommandMapperAdv();
    originalImage = "test";
    destImage = "result";
  }

  @Test
  public void testCompress() {
    int[][][] exp = {
            {{155, 155, 155, 155, 116},
                    {155, 155, 155, 155, 116},
                    {155, 155, 155, 155, 116},
                    {155, 155, 155, 155, 116}},
            {{57, 57, 57, 57, 57},
                    {57, 57, 57, 57, 57},
                    {57, 57, 57, 57, 57},
                    {57, 57, 57, 57, 57}},
            {{40, 40, 40, 40, 40},
                    {40, 40, 40, 40, 40},
                    {40, 40, 40, 40, 40},
                    {40, 40, 40, 40, 40}}
    };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("compress 90 %s %s", imageData));
  }

  @Test
  public void testColorCorrect() {
    int[][][] exp = {
            {{101, 103, 110, 104, 104},
                    {104, 108, 100, 103, 105},
                    {101, 103, 106, 103, 105},
                    {103, 105, 111, 110, 104}},
            {{102, 104, 107, 103, 103},
                    {105, 106, 98, 102, 103},
                    {101, 103, 104, 98, 103},
                    {103, 103, 109, 108, 105}},
            {{96, 100, 104, 97, 97},
                    {101, 103, 95, 94, 102},
                    {101, 103, 104, 104, 102},
                    {103, 102, 108, 107, 103}}
    };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("color-correct %s %s", imageData));
  }

  @Test
  public void testLevelAdjust() {
    int[][][] exp = {
            {{187, 189, 196, 190, 190},
                    {190, 194, 186, 189, 191},
                    {187, 189, 192, 189, 191},
                    {189, 191, 197, 196, 190}},
            {{114, 117, 121, 116, 116},
                    {118, 120, 109, 114, 116},
                    {113, 116, 117, 109, 116},
                    {116, 116, 124, 123, 118}},
            {{66, 73, 79, 68, 68},
                    {74, 77, 64, 63, 76},
                    {74, 77, 79, 79, 76},
                    {77, 76, 85, 83, 77}}
    };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("levels-adjust 20 100 255 %s %s", imageData));
  }

  @Test
  public void testGreyScaleSplit() {
    int[][][] exp = {
            {{101, 153, 160, 154, 154},
                    {104, 158, 150, 153, 155},
                    {100, 153, 156, 153, 155},
                    {102, 155, 161, 160, 154}},
            {{101, 92, 95, 91, 91},
                    {104, 94, 86, 90, 91},
                    {100, 91, 92, 86, 91},
                    {102, 91, 97, 96, 93}},
            {{101, 62, 66, 59, 59},
                    {104, 65, 57, 56, 64},
                    {100, 65, 66, 66, 64},
                    {102, 64, 70, 69, 65}}
            };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("luma-component %s %s split 20", imageData));
  }

  @Test
  public void testBlurSplit() {
    int[][][] exp = {
            {{57, 153, 160, 154, 154},
                    {77, 158, 150, 153, 155},
                    {76, 153, 156, 153, 155},
                    {57, 155, 161, 160, 154}},
            {{35, 92, 95, 91, 91},
                    {45, 94, 86, 90, 91},
                    {45, 91, 92, 86, 91},
                    {34, 91, 97, 96, 93}},
            {{23, 62, 66, 59, 59},
                    {31, 65, 57, 56, 64},
                    {32, 65, 66, 66, 64},
                    {24, 64, 70, 69, 65}}
    };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("blur %s %s split 20", imageData));
  }

  @Test
  public void testSepiaSplit() {
    int[][][] exp = {
            {{140, 153, 160, 154, 154},
                {144, 158, 150, 153, 155},
                {140, 153, 156, 153, 155},
                {142, 155, 161, 160, 154}},
            {{124, 92, 95, 91, 91},
                    {128, 94, 86, 90, 91},
                    {124, 91, 92, 86, 91},
                    {127, 91, 97, 96, 93}},
            {{97, 62, 66, 59, 59},
                    {100, 65, 57, 56, 64},
                    {97, 65, 66, 66, 64},
                    {99, 64, 70, 69, 65}}
    };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("sepia %s %s split 20", imageData));
  }

  @Test
  public void testSharpenSplit() {
    int[][][] exp = {{{171, 153, 160, 154, 154},
            {211, 158, 150, 153, 155},
            {209, 153, 156, 153, 155},
            {172, 155, 161, 160, 154}},
            {{102, 92, 95, 91, 91},
                    {127, 94, 86, 90, 91},
                    {124, 91, 92, 86, 91},
                    {101, 91, 97, 96, 93}},
            {{66, 62, 66, 59, 59},
                    {86, 65, 57, 56, 64},
                    {88, 65, 66, 66, 64},
                    {73, 64, 70, 69, 65}}
    };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("sharpen %s %s split 20", imageData));
  }

  @Test
  public void testColorCorrectSplit() {
    int[][][] exp = {{{101, 153, 160, 154, 154},
            {104, 158, 150, 153, 155},
            {101, 153, 156, 153, 155},
            {103, 155, 161, 160, 154}},
            {{102, 92, 95, 91, 91},
                    {105, 94, 86, 90, 91},
                    {101, 91, 92, 86, 91},
                    {103, 91, 97, 96, 93}},
            {{96, 62, 66, 59, 59},
                    {101, 65, 57, 56, 64},
                    {101, 65, 66, 66, 64},
                    {103, 64, 70, 69, 65}}
    };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("color-correct %s %s split 20", imageData));
  }

  @Test
  public void testLevelAdjustSplit() {
    int[][][] exp = {{{187, 189, 160, 154, 154},
            {190, 194, 150, 153, 155},
            {187, 189, 156, 153, 155},
            {189, 191, 161, 160, 154}},
            {{114, 117, 95, 91, 91},
                    {118, 120, 86, 90, 91},
                    {113, 116, 92, 86, 91},
                    {116, 116, 97, 96, 93}},
            {{66, 73, 66, 59, 59},
                    {74, 77, 57, 56, 64},
                    {74, 77, 66, 66, 64},
                    {77, 76, 70, 69, 65}}
    };
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper("levels-adjust 20 100 255 %s %s split 40", imageData));
  }

  @Test
  public void testBrighten() {
    int[][][] exp = {
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
    imageData = new ImageData(exp, 255);
    String command = "brighten 50";
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testDarken() {
    int[][][] exp = {
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
    imageData = new ImageData(exp, 255);
    String command = "brighten -50";
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testBlur() {
    int[][][] exp = {
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
    imageData = new ImageData(exp, 255);
    String command = "blur";
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testSharpen() {
    int[][][] exp = {{{172, 234, 182, 233, 174},
            {230, 255, 249, 255, 231},
            {229, 255, 255, 255, 233},
            {171, 232, 183, 240, 175}},
            {{104, 142, 110, 139, 104},
                    {140, 198, 146, 192, 135},
                    {137, 196, 153, 192, 138},
                    {101, 137, 110, 144, 106}},
            {{67, 94, 71, 87, 65},
                    {94, 136, 97, 125, 92},
                    {96, 140, 109, 140, 98},
                    {73, 100, 83, 107, 76}}};
    String command = "sharpen";
    imageData = new ImageData(exp, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testSepia() {
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testRed() {
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testBlue() {
    String command = "blue-component";
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testGreen() {
    String command = "green-component";
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testValue() {
    String command = "value-component";
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testIntensity() {
    String command = "intensity-component";
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testLuma() {
    String command = "luma-component";
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testVerticalFlip() {
    String command = "horizontal-flip";
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testHorizontalFlip() {
    String command = "vertical-flip";
    int[][][] expected = {
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
    imageData = new ImageData(expected, 255);
    assertTrue(testHelper(command + " %s %s", imageData));
  }

  @Test
  public void testCombine() {
    String red = "red";
    String green = "green";
    String blue = "blue";
    String command = "rgb-combine %s %s %s %s";
    String load = "load images/test/" + originalImage + ".png " + red;
    load = load + "\n" + "load images/test/" + originalImage + ".png " + green;
    load = load + "\n" + "load images/test/" + originalImage + ".png " + blue;
    String save = "save images/temp/" + destImage + ".png " + destImage;
    command = load + "\n" + String.format(command, destImage, red, green, blue) + "\n" + save;
    runCommand(command);

    String res = "images/temp/";
    String expDir = "images/test/";
    ImageFileIO fileIO = new RgbImageFileIO();

    try {
      ImageData act = fileIO.load(res + destImage + ".png");
      ImageData exp = fileIO.load(expDir + originalImage + ".png");
      assertEquals(exp, act);
    } catch (Exception e) {
      System.out.println("Issue while loading the files " + e.getMessage());
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testSplit() {
    String red = "red";
    String green = "green";
    String blue = "blue";
    String command = "rgb-split %s %s %s %s";
    String load = "load images/test/" + originalImage + ".png " + originalImage;
    String save = "save images/temp/" + red + ".png " + red;
    save = save + "\n" + "save images/temp/" + green + ".png " + green;
    save = save + "\n" + "save images/temp/" + blue + ".png " + blue;
    command = load + "\n" + String.format(command, originalImage, red, green, blue)
            + "\n" + save;

    runCommand(command);

    String res = "images/temp/";
    ImageFileIO fileIO = new RgbImageFileIO();
    int[][][] expected = {
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
    ImageData expR = new ImageData(expected, 255);
    expected = new int[][][]{
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
    ImageData expG = new ImageData(expected, 255);
    expected = new int[][][]{
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
    ImageData expB = new ImageData(expected, 255);
    try {
      ImageData actR = fileIO.load(res + red + ".png");
      ImageData actG = fileIO.load(res + green + ".png");
      ImageData actB = fileIO.load(res + blue + ".png");
      assertEquals(expR, actR);
      assertEquals(expG, actG);
      assertEquals(expB, actB);
    } catch (Exception e) {
      System.out.println("Issue while loading the files " + e.getMessage());
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testHistogram() {
    String command = "histogram %s %s";
    String load = "load images/test/" + originalImage + ".png " + originalImage;
    String save = "save images/temp/" + destImage + ".png " + destImage;
    command = load + "\n" + String.format(command, originalImage, destImage) + "\n" + save;
    runCommand(command);
    String res = "images/temp/";
    String testDir = "images/test/histogramTest.png";
    ImageFileIO fileIO = new RgbImageFileIO();
    try {
      ImageData act = fileIO.load(res + destImage + ".png");
      ImageData exp = fileIO.load(testDir);
      assertEquals(exp, act);
    } catch (Exception e) {
      System.out.println("Issue while loading the files " + e.getMessage());
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testLoadAndSavePPM() {
    String load = "load images/test/" + originalImage + ".png " + originalImage;
    String save = "save images/temp/" + destImage + ".ppm " + originalImage;
    String command = load + "\n" + save;
    runCommand(command);
    String res = "images/temp/";
    String testDir = "images/test/" + originalImage + ".png";
    ImageFileIO fileIO = new RgbImageFileIO();
    try {
      ImageData act = fileIO.load(res + destImage + ".ppm");
      ImageData exp = fileIO.load(testDir);
      assertEquals(exp, act);
    } catch (Exception e) {
      System.out.println("Issue while loading the files " + e.getMessage());
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testLoadAndSavePNG() {
    String load = "load images/test/" + originalImage + ".png " + originalImage;
    String save = "save images/temp/" + destImage + ".png " + originalImage;
    String command = load + "\n" + save;
    runCommand(command);
    String res = "images/temp/";
    String testDir = "images/test/" + originalImage + ".png";
    ImageFileIO fileIO = new RgbImageFileIO();
    try {
      ImageData act = fileIO.load(res + destImage + ".png");
      ImageData exp = fileIO.load(testDir);
      assertEquals(exp, act);
    } catch (Exception e) {
      System.out.println("Issue while loading the files " + e.getMessage());
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testLoadAndSaveJPG() {
    String load = "load images/test/" + originalImage + ".png " + originalImage;
    String save = "save images/temp/" + destImage + ".jpg " + originalImage;
    String command = load + "\n" + save;
    runCommand(command);
    String res = "images/temp/";
    String testDir = "images/test/" + originalImage + ".jpg";
    ImageFileIO fileIO = new RgbImageFileIO();
    try {
      ImageData act = fileIO.load(res + destImage + ".jpg");
      ImageData exp = fileIO.load(testDir);
      assertTrue(Arrays.deepEquals(exp.getData(), act.getData()));
    } catch (Exception e) {
      System.out.println("Issue while loading the files " + e.getMessage());
      fail("Should not have thrown error");
    }
  }

  private void runCommand(String command) {
    Readable reader = new StringReader(command);
    ImageController controller = new RgbController(factory, textView, reader, commandMapper);
    try {
      controller.run();
    } catch (IOException e) {
      fail("Should not have thrown error");
    }
  }

  private boolean testHelper(String command, ImageData exp) {
    String load = "load images/test/" + originalImage + ".png " + originalImage;
    String save = "save images/temp/" + destImage + ".png " + destImage;
    command = load + "\n" + String.format(command, originalImage, destImage) + "\n" + save;
    runCommand(command);

    String res = "images/temp/";
    ImageFileIO fileIO = new RgbImageFileIO();
    ImageData act;
    try {
      act = fileIO.load(res + destImage + ".png");
      return exp.equals(act);
    } catch (Exception e) {
      System.out.println("Issue while loading the files " + e.getMessage());
      return false;
    }
  }

  @Test
  public void testRunScript() {
    String command = "run \"images/test/script folder/scriptTest.txt\"";
    runCommand(command);
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
            "koalaTestVerticalHorizontal.png",
            "koala-blur-sp.png",
            "koala-cc-histogram.png",
            "koala-color-correct.png",
            "koala-color-correct-sp.png",
            "koala-compress.png",
            "koala-histogram.png",
            "koala-la-histogram.png",
            "koala-levels-adjust.png",
            "koala-levels-adjust-sp.png",
            "koala-sepia-sp.png",
            "koala-sharpen-sp.png"
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
    } catch (Exception e) {
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

  @After
  public void tearDown() {
    deleteFiles("images/temp/");
  }
}