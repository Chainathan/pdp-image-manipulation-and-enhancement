package model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

/**
 * Test class for RGB Image Processor class.
 */
public class RgbImageProcessorTest {

  ImageProcessorModel rgbImageProcess;
  double[][][] imageValues1;
  double[][][] imageValues2;
  ImageData imageData1;
  ImageData imageData2;
  String imageName1;
  String imageName2;

  @Before
  public void setup() {
    rgbImageProcess = new RgbImageProcessor();
    imageValues1 = new double[][][]{
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
            {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
    };
    imageValues2 = new double[][][]{
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{10, 11, 12}, {13, 14, 15}, {16, 17, 18}},
            {{19, 20, 21}, {22, 23, 24}, {25, 26, 27}}
    };
    imageData1 = new ImageData(imageValues1, 255);
    imageData2 = new ImageData(imageValues2, 200);
    imageName1 = "image1";
    imageName2 = "image2";
  }

  private void assertImages(int[][][] expectedImage, RgbImageModel rgbImageModel) {
    double[][][] actual = rgbImageModel.getImageData().getData();
    int height = actual[0].length;
    int width = actual[0][0].length;
    //THEN
    assertEquals(255, rgbImageModel.getImageData().getMaxValue());
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        assertEquals(expectedImage[0][i][j], actual[0][i][j]);
        assertEquals(expectedImage[1][i][j], actual[1][i][j]);
        assertEquals(expectedImage[2][i][j], actual[2][i][j]);
      }
    }
  }

  @Test
  public void testValidAddAndGetImage() {
    rgbImageProcess.addImage(imageName1, imageData1);
    rgbImageProcess.addImage(imageName2, imageData2);
    assertEquals(imageData1, rgbImageProcess.getImageData(imageName1));
    assertEquals(imageData2, rgbImageProcess.getImageData(imageName2));
    try {
      rgbImageProcess.addImage(imageName1, imageData2);
    } catch (Exception e) {
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testInvalidImageNameGet() {
    assertThrows(IllegalArgumentException.class, () -> rgbImageProcess.getImageData(imageName1));
    rgbImageProcess.addImage(imageName2, imageData1);
    assertThrows(IllegalArgumentException.class, ()
        -> rgbImageProcess.getImageData(imageName1));
    assertThrows(IllegalArgumentException.class, ()
        -> rgbImageProcess.getImageData(""));
    assertThrows(IllegalArgumentException.class, ()
        -> rgbImageProcess.getImageData(" "));
    assertThrows(IllegalArgumentException.class, ()
        -> rgbImageProcess.getImageData("\t"));
    assertThrows(IllegalArgumentException.class, ()
        -> rgbImageProcess.getImageData("\n"));
  }

  @Test
  public void testInvalidImageName() {
    rgbImageProcess.addImage(imageName1, imageData1);
    String[] randomStrings = {"", "\t", " ", "\n"};

    for (String image : randomStrings) {
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent(image, "destImageName",
              "red-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent(image, "destImageName",
              "green-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent(image, "destImageName",
              "blue-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent(image, "destImageName",
              "value-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent(image, "destImageName",
              "intensity-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent(image, "destImageName",
              "luma-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.horizontalFlip(image, "destImageName"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.verticalFlip(image, "destImageName"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.brighten(image, "destImageName", 50));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.splitComponents(image, List.of("First", "Second", "Third")));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.combineComponents("image",
              List.of(image, image, image)));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.blur(image, "destImageName"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.sharpen(image, "destImageName"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.sepia(image, "destImageName"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.getImageData(image));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.addImage(image, imageData2));
    }
  }

  @Test
  public void testInvalidDestImageName() {
    rgbImageProcess.addImage(imageName1, imageData1);
    String[] randomStrings = {"", "\t", " ", "\n"};

    for (String image : randomStrings) {
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent("imageName", image,
              "red-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent("imageName", image,
              "green-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent("imageName", image,
                      "blue-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent("imageName", image,
              "value-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent("imageName", image,
              "intensity-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.visualizeComponent("imageName", image,
              "luma-component"));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.horizontalFlip("imageName", image));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.verticalFlip("imageName", image));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.brighten("imageName", image, 50));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.splitComponents(imageName1, List.of(image, image, image)));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.combineComponents("imageName",
              List.of("First", "Second", "Third")));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.blur("imageName", image));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.sharpen("imageName", image));
      assertThrows(IllegalArgumentException.class, ()
          -> rgbImageProcess.sepia("imageName", image));
    }
  }

  @Test
  public void testValidImageName() {
    rgbImageProcess.addImage(imageName1, imageData1);
    try {
      rgbImageProcess.visualizeComponent(imageName1, "destImageName",
              "red-component");
      rgbImageProcess.visualizeComponent(imageName1, "destImageName",
              "green-component");
      rgbImageProcess.visualizeComponent(imageName1, "destImageName",
              "blue-component");
      rgbImageProcess.visualizeComponent(imageName1, "destImageName",
              "value-component");
      rgbImageProcess.visualizeComponent(imageName1, "destImageName",
              "intensity-component");
      rgbImageProcess.visualizeComponent(imageName1, "destImageName",
              "luma-component");
      rgbImageProcess.horizontalFlip(imageName1, "destImageName");
      rgbImageProcess.verticalFlip(imageName1, "destImageName");
      rgbImageProcess.brighten(imageName1, "destImageName", 50);
      rgbImageProcess.splitComponents(imageName1, List.of("First", "Second", "Third"));
      rgbImageProcess.combineComponents("destImageName",
              List.of(imageName1, imageName1, imageName1));
      rgbImageProcess.blur(imageName1, "destImageName");
      rgbImageProcess.sharpen(imageName1, "destImageName");
      rgbImageProcess.sepia(imageName1, "destImageName");
    } catch (Exception e) {
      fail("Should not have thrown error");
    }
  }

  @Test
  public void testInvalidComponentWithRandomStrings() {
    String[] randomStrings = {"abc123", "xyz", "invalid123", "testing",
        "invalid-component!", "", "\t", " ", "\n"};

    for (String component : randomStrings) {
      assertThrows(IllegalArgumentException.class,
          () -> rgbImageProcess.visualizeComponent(imageName1, "destImageName", component));
    }
  }

  @Test
  public void testVisCompRed() {
    rgbImageProcess.addImage(imageName1, imageData2);
    rgbImageProcess.visualizeComponent(imageName1, imageName2, "red-component");
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    ImageData exp = new ImageData(expected, 200);
    assertEquals(exp, res);
  }

  @Test
  public void testVisCompGreen() {
    rgbImageProcess.addImage(imageName1, imageData2);
    rgbImageProcess.visualizeComponent(imageName1, imageName2, "green-component");
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{10, 11, 12}, {13, 14, 15}, {16, 17, 18}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    ImageData exp = new ImageData(expected, 200);
    assertEquals(exp, res);
  }

  @Test
  public void testVisCompBlue() {
    rgbImageProcess.addImage(imageName1, imageData2);
    rgbImageProcess.visualizeComponent(imageName1, imageName2, "blue-component");
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{19, 20, 21}, {22, 23, 24}, {25, 26, 27}}
    };
    ImageData exp = new ImageData(expected, 200);
    assertEquals(exp, res);
  }

  @Test
  public void testVisCompValue() {
    rgbImageProcess.addImage(imageName1, imageData1);
    rgbImageProcess.visualizeComponent(imageName1, imageName2, "value-component");
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}},
            {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}},
            {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}}
    };
    ImageData exp = new ImageData(expected, 255);
    assertEquals(exp, res);
  }

  @Test
  public void testVisCompIntensity() {
    rgbImageProcess.addImage(imageName1, imageData1);
    rgbImageProcess.visualizeComponent(imageName1, imageName2, "intensity-component");
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}},
            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}},
            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}}
    };
    ImageData exp = new ImageData(expected, 255);
    assertEquals(exp, res);
  }

  @Test
  public void testVisCompLuma() {
    rgbImageProcess.addImage(imageName1, imageData1);
    rgbImageProcess.visualizeComponent(imageName1, imageName2, "luma-component");
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}},
            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}},
            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}}
    };
    ImageData exp = new ImageData(expected, 255);
    assertEquals(exp, res);
  }

  @Test
  public void testVerFlip() {
    rgbImageProcess.addImage(imageName1, imageData2);
    rgbImageProcess.verticalFlip(imageName1, imageName2);
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{7, 8, 9}, {4, 5, 6}, {1, 2, 3}},
            {{16, 17, 18}, {13, 14, 15}, {10, 11, 12}},
            {{25, 26, 27}, {22, 23, 24}, {19, 20, 21}}
    };
    ImageData exp = new ImageData(expected, 200);
    assertEquals(exp, res);
  }

  @Test
  public void testHorFlip() {
    rgbImageProcess.addImage(imageName1, imageData2);
    rgbImageProcess.horizontalFlip(imageName1, imageName2);
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{3, 2, 1}, {6, 5, 4}, {9, 8, 7}},
            {{12, 11, 10}, {15, 14, 13}, {18, 17, 16}},
            {{21, 20, 19}, {24, 23, 22}, {27, 26, 25}}
    };
    ImageData exp = new ImageData(expected, 200);
    assertEquals(exp, res);
  }

  @Test
  public void testBrighten() {
    rgbImageProcess.addImage(imageName1, imageData1);
    rgbImageProcess.brighten(imageName1, imageName2, 50);
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = {
            {{51, 52, 53}, {54, 55, 56}, {57, 58, 59}},
            {{54, 55, 56}, {51, 52, 53}, {57, 58, 59}},
            {{51, 52, 53}, {57, 58, 59}, {54, 55, 56}}
    };
    ImageData exp = new ImageData(expected, 255);
    assertEquals(exp, res);
    rgbImageProcess.brighten(imageName1, imageName2, 250);
    res = rgbImageProcess.getImageData(imageName2);
    expected = new double[][][]{
            {{251, 252, 253}, {254, 255, 255}, {255, 255, 255}},
            {{254, 255, 255}, {251, 252, 253}, {255, 255, 255}},
            {{251, 252, 253}, {255, 255, 255}, {254, 255, 255}}
    };
    exp = new ImageData(expected, 255);
    assertEquals(exp, res);
  }

  @Test
  public void testDarken() {
    double[][][] initV = {
            {{251, 252, 253}, {254, 255, 255}, {255, 255, 255}},
            {{254, 255, 255}, {251, 252, 253}, {255, 255, 255}},
            {{251, 252, 253}, {255, 255, 255}, {254, 255, 255}}
    };
    ImageData init = new ImageData(initV, 255);
    rgbImageProcess.addImage(imageName1, init);
    rgbImageProcess.brighten(imageName1, imageName2, -200);
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = {
            {{51, 52, 53}, {54, 55, 55}, {55, 55, 55}},
            {{54, 55, 55}, {51, 52, 53}, {55, 55, 55}},
            {{51, 52, 53}, {55, 55, 55}, {54, 55, 55}}
    };
    ImageData exp = new ImageData(expected, 255);
    assertEquals(exp, res);

    rgbImageProcess.brighten(imageName2, imageName2, -53);
    res = rgbImageProcess.getImageData(imageName2);
    expected = new double[][][]{
            {{0, 0, 0}, {1, 2, 2}, {2, 2, 2}},
            {{1, 2, 2}, {0, 0, 0}, {2, 2, 2}},
            {{0, 0, 0}, {2, 2, 2}, {1, 2, 2}}
    };
    exp = new ImageData(expected, 255);
    assertEquals(exp, res);
  }

  @Test
  public void testSplit() {
    rgbImageProcess.addImage(imageName1, imageData1);
    rgbImageProcess.splitComponents(imageName1, List.of("red", "green", "blue"));
    ImageData res = rgbImageProcess.getImageData("red");
    double[][][] expected = new double[][][]{
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    ImageData exp = new ImageData(expected, 255);
    assertEquals(exp, res);

    res = rgbImageProcess.getImageData("green");
    expected = new double[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    exp = new ImageData(expected, 255);
    assertEquals(exp, res);

    res = rgbImageProcess.getImageData("blue");
    expected = new double[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
    };
    exp = new ImageData(expected, 255);
    assertEquals(exp, res);
  }

  @Test
  public void testInvalidSplit() {
    rgbImageProcess.addImage(imageName1, imageData1);
    assertThrows(IllegalArgumentException.class,
        () -> rgbImageProcess.splitComponents(imageName1, List.of("red", "red", "blue")));
    assertThrows(IllegalArgumentException.class,
        () -> rgbImageProcess.splitComponents(imageName1, List.of("red", "blue", "blue")));
    assertThrows(IllegalArgumentException.class,
        () -> rgbImageProcess.splitComponents(imageName1, List.of("red", "blue", "red")));
    assertThrows(IllegalArgumentException.class,
        () -> rgbImageProcess.splitComponents(imageName1, List.of("red", "red", "red")));
  }

  @Test
  public void testCombine() {
    double[][][] initV = new double[][][]{
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    ImageData init = new ImageData(initV, 255);
    rgbImageProcess.addImage("red", init);

    initV = new double[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    init = new ImageData(initV, 255);
    rgbImageProcess.addImage("green", init);

    initV = new double[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
    };
    init = new ImageData(initV, 255);
    rgbImageProcess.addImage("blue", init);

    rgbImageProcess.combineComponents(imageName1, List.of("red", "green", "blue"));
    ImageData res = rgbImageProcess.getImageData(imageName1);
    assertEquals(imageData1, res);
  }

  @Test
  public void testBlur() {
    double[][][] initV = new double[][][]{
            {{16, 32, 48}, {64, 80, 96}, {112, 128, 144}},
            {{32, 64, 96}, {128, 160, 192}, {224, 256, 288}},
            {{48, 96, 144}, {192, 240, 288}, {336, 384, 432}}
    };
    ImageData init = new ImageData(initV, 255);
    rgbImageProcess.addImage(imageName1, init);

    rgbImageProcess.blur(imageName1, imageName2);
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{21, 36, 33}, {52, 80, 68}, {57, 84, 69}},
            {{42, 72, 66}, {104, 160, 136}, {114, 168, 138}},
            {{63, 108, 99}, {156, 240, 204}, {171, 252, 207}}
    };
    ImageData exp = new ImageData(expected, 255);
    assertEquals(exp, res);
  }

  @Test
  public void testBlurRounded() {
    rgbImageProcess.addImage(imageName1, imageData2);
    rgbImageProcess.blur(imageName1, imageName2);
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{1, 2, 2}, {4, 5, 5}, {4, 5, 4}},
            {{7, 10, 7}, {10, 15, 12}, {9, 12, 10}},
            {{12, 16, 12}, {17, 24, 18}, {13, 19, 14}}
    };
    ImageData exp = new ImageData(expected, 200);
    assertEquals(exp, res);
  }

  @Test
  public void testSharpen() {
    rgbImageProcess.addImage(imageName1, imageData2);
    rgbImageProcess.sharpen(imageName1, imageName2);
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{0, 4, 4}, {8, 16, 12}, {9, 16, 13}},
            {{11, 22, 14}, {26, 43, 29}, {20, 34, 23}},
            {{21, 39, 24}, {42, 70, 46}, {31, 50, 33}}
    };
    ImageData exp = new ImageData(expected, 200);
    assertEquals(exp, res);
  }

  @Test
  public void testSepia() {
    rgbImageProcess.addImage(imageName1, imageData2);
    rgbImageProcess.sepia(imageName1, imageName2);
    ImageData res = rgbImageProcess.getImageData(imageName2);
    double[][][] expected = new double[][][]{
            {{12, 13, 14}, {16, 17, 18}, {20, 21, 22}},
            {{10, 12, 13}, {14, 15, 16}, {18, 19, 20}},
            {{8, 9, 10}, {11, 12, 13}, {14, 15, 16}}
    };
    ImageData exp = new ImageData(expected, 200);
    assertEquals(exp, res);
  }

}