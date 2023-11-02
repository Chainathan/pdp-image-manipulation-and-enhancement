package controller;

import model.ImageData;
import model.ImageProcessorModel;
import view.ImageProcessorView;
import view.TextView;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test class for Mock test of RGB controller.
 */
public class RgbControllerTest {
  class MockRgbImageProcess implements ImageProcessorModel {
    private StringBuilder log;

    public MockRgbImageProcess(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void addImage(String destImageName, ImageData imageData)
            throws IllegalArgumentException {
      log.append("Dest Image Name : " + destImageName + "\nImage Data : {");
      int[][][] data = imageData.getData();
      int width = data[0][0].length;
      int height = data[0].length;
      for (int k = 0; k < 3; k++) {
        log.append("{");
        for (int i = 0; i < height; i++) {
          log.append("{");
          for (int j = 0; j < width; j++) {
            log.append(data[k][i][j] + " ");
          }
          log.append("}");
        }
        log.append("}");
      }
      log.append("}\nMax Pixel Size : " + imageData.getMaxValue());
    }

    @Override
    public ImageData getImageData(String imageName) throws IllegalArgumentException {
      log.append("Image Name : ").append(imageName);
      int[][][] data = {
              {{11, 12, 13},
                      {14, 15, 16},
                      {17, 18, 19},
                      {20, 21, 22}},
              {{11, 12, 13},
                      {14, 15, 16},
                      {17, 18, 19},
                      {20, 21, 22}},
              {{11, 12, 13},
                      {14, 15, 16},
                      {17, 18, 19},
                      {20, 21, 22}}
      };
      return new ImageData(data, 255);
    }

    @Override
    public void visualizeComponent(String imageName, String destImageName, String component)
            throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName)
              .append(", Component : ")
              .append(component);
    }

    @Override
    public void horizontalFlip(String imageName, String destImageName)
            throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName);
    }

    @Override
    public void verticalFlip(String imageName, String destImageName)
            throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName);
    }

    @Override
    public void brighten(String imageName, String destImageName, int increment)
            throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName)
              .append(", Increment : ")
              .append(increment);
    }

    @Override
    public void splitComponents(String imageName, List<String> destComponentImageList)
            throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image List : [ ");
      for (String destImg : destComponentImageList) {
        log.append(destImg).append(" ");
      }
      log.append("]");
    }

    @Override
    public void combineComponents(String destImageName, List<String> componentImageList)
            throws IllegalArgumentException {
      log.append("Dest Image Name : ")
              .append(destImageName)
              .append(", Image List : [ ");
      for (String imgComponents : componentImageList) {
        log.append(imgComponents).append(" ");
      }
      log.append("]");
    }

    @Override
    public void blur(String imageName, String destImageName) throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName);
    }

    @Override
    public void sharpen(String imageName, String destImageName) throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName);
    }

    @Override
    public void sepia(String imageName, String destImageName) throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName);
    }
  }

  StringBuffer out;
  StringBuilder modelLog;
  Reader in;
  ImageProcessorModel rgbImageProcess;
  ImageProcessorView textView;

  @Before
  public void setUp() {
    //GIVEN
    out = new StringBuffer();
    modelLog = new StringBuilder();
    rgbImageProcess = new MockRgbImageProcess(modelLog);
    textView = new TextView(out);
  }

  @Test
  public void testRedComponentCommand() {
    //GIVEN
    Reader in = new StringReader("red-component image red-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : red-image, " +
            "Component : red-component", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "red-component Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testGreenComponentCommand() {
    //GIVEN
    Reader in = new StringReader("green-component image green-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : green-image, " +
            "Component : green-component", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "green-component Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testBlueComponentCommand() {
    //GIVEN
    Reader in = new StringReader("blue-component image blue-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : blue-image, " +
            "Component : blue-component", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "blue-component Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testValueComponentCommand() {
    //GIVEN
    Reader in = new StringReader("value-component image value-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : value-image, " +
            "Component : value-component", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "value-component Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testLumaComponentCommand() {
    //GIVEN
    Reader in = new StringReader("luma-component image luma-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : luma-image, " +
            "Component : luma-component", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "luma-component Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testIntensityComponentCommand() {
    //GIVEN
    Reader in = new StringReader("intensity-component image intensity-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : intensity-image, " +
            "Component : intensity-component", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "intensity-component Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testVerticalFlipCommand() {
    //GIVEN
    Reader in = new StringReader("vertical-flip image flipped-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : flipped-image", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "vertical-flip Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testHorizontalFlipCommand() {
    //GIVEN
    Reader in = new StringReader("horizontal-flip image flipped-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : flipped-image", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "horizontal-flip Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testBlurCommand() {
    //GIVEN
    Reader in = new StringReader("blur image blurred-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : blurred-image", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "blur Operation performed successfully\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testSharpenCommand() {
    //GIVEN
    Reader in = new StringReader("sharpen image sharpened-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : sharpened-image", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "sharpen Operation performed successfully\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testSepiaCommand() {
    //GIVEN
    Reader in = new StringReader("sepia image sepia-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : sepia-image", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "sepia Operation performed successfully\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testDefaultFor3Args() {
    //GIVEN
    Reader in = new StringReader("command arg1 arg2\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown command: command\nProgram Terminated\n", out.toString());
  }

  // 4 Args Command
  @Test
  public void testBrightenCommand() {
    //GIVEN
    Reader in = new StringReader("brighten 50 image brightened-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : brightened-image, " +
            "Increment : 50", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "brighten Operation performed successfully\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testDefaultFor4Arg() {
    //GIVEN
    Reader in = new StringReader("command arg1 arg2 arg3\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown command: command\nProgram Terminated\n", out.toString());
  }

  // 5 Arg Command
  @Test
  public void testRgbSplitCommand() {
    //GIVEN
    Reader in = new StringReader("rgb-split image img1 img2 img3\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN

    assertEquals("Image Name : image, " +
            "Dest Image List : [ img1 img2 img3 ]", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "rgb-split Operation performed successfully\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testRgbCombineCommand() {
    //GIVEN
    Reader in = new StringReader("rgb-combine image img1 img2 img3\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN

    assertEquals("Dest Image Name : image, " +
            "Image List : [ img1 img2 img3 ]", modelLog.toString());
    assertEquals("Image Processing program started\n" +
                    "rgb-combine Operation performed successfully\nProgram Terminated\n",
            out.toString());
  }

  @Test
  public void testDefaultFor5Args() {
    //GIVEN
    Reader in = new StringReader("command arg1 arg2 arg3 arg4\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown command: command\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testRandomOperation() {
    //GIVEN
    Reader in = new StringReader("random\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown Operation: random\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testOperationWithHashWithNoComment() {
    //GIVEN
    Reader in = new StringReader("random    #this is a cmd\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown Operation: random\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testOperationWithHashForValidCommand() {
    //GIVEN
    Reader in = new StringReader("load images/test/test.ppm test    #this is a cmd\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\nProgram Terminated\n", out.toString());
  }

  @Test
  public void testOperationWithHash() {
    //GIVEN
    Reader in = new StringReader("#this is a cmd");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n", out.toString());
  }

  @Test
  public void testBlankOperation() {
    //GIVEN
    Reader in = new StringReader("   \nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Program Terminated\n", out.toString());
  }

  @Test
  public void testloadPPM() {
    //GIVEN
    Reader in = new StringReader("load images/test/test.ppm koala\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    ImageFileIO imageFileIO = new RgbImageFileIO();
    StringBuilder expectedlog = new StringBuilder();
    ImageData expectedImageData = new ImageData(new int[0][0][0], 255);
    //WHEN
    try {
      expectedImageData = imageFileIO.load("images/test/test.ppm");
      rgbController.run();
    } catch (IOException ignored) {
    }

    expectedlog.append("Dest Image Name : koala" + "\nImage Data : {");
    int[][][] data = expectedImageData.getData();
    int width = data[0][0].length;
    int height = data[0].length;
    for (int k = 0; k < 3; k++) {
      expectedlog.append("{");
      for (int i = 0; i < height; i++) {
        expectedlog.append("{");
        for (int j = 0; j < width; j++) {
          expectedlog.append(data[k][i][j] + " ");
        }
        expectedlog.append("}");
      }
      expectedlog.append("}");
    }
    expectedlog.append("}\nMax Pixel Size : 250");
    //THEN
    assertEquals(expectedlog.toString(), modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n" + "Program Terminated\n", out.toString());
  }

  @Test
  public void testloadPPMWithQuotes() {
    //GIVEN
    Reader in = new StringReader("load \"images/test/test.ppm\" koala\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    ImageFileIO imageFileIO = new RgbImageFileIO();
    StringBuilder expectedlog = new StringBuilder();
    ImageData expectedImageData = new ImageData(new int[0][0][0], 255);
    //WHEN
    try {
      expectedImageData = imageFileIO.load("images/test/test.ppm");
      rgbController.run();
    } catch (IOException ignored) {
    }

    expectedlog.append("Dest Image Name : koala" + "\nImage Data : {");
    int[][][] data = expectedImageData.getData();
    int width = data[0][0].length;
    int height = data[0].length;
    for (int k = 0; k < 3; k++) {
      expectedlog.append("{");
      for (int i = 0; i < height; i++) {
        expectedlog.append("{");
        for (int j = 0; j < width; j++) {
          expectedlog.append(data[k][i][j] + " ");
        }
        expectedlog.append("}");
      }
      expectedlog.append("}");
    }
    expectedlog.append("}\nMax Pixel Size : 250");
    //THEN
    assertEquals(expectedlog.toString(), modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n" + "Program Terminated\n", out.toString());
  }

  @Test
  public void testloadPPMWithQuotesForInvalidCommand() {
    //GIVEN
    Reader in = new StringReader("load \"images/test/test.ppm koala\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n" + "Program Terminated\n", out.toString());
  }

  @Test
  public void testloadPPMForInvalidCommand() {
    //GIVEN
    Reader in = new StringReader("load \"images/test/test.ppm\" \nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n" + "Program Terminated\n", out.toString());
  }

  @Test
  public void testloadPPMWhenFileNotPresent() {
    //GIVEN
    Reader in = new StringReader("load images/random.ppm koala\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "File Not Found\n" + "Program Terminated\n", out.toString());
  }

  @Test
  public void testloadPPMInvalidImageName() {
    //GIVEN
    Reader in = new StringReader("load images/test/test.ppm \" \"\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n" + "Program Terminated\n", out.toString());
  }

  @Test
  public void testSave() {
    //GIVEN
    Reader in = new StringReader("save images/temp/image.png image");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "save Operation performed successfully\n", out.toString());
    deleteFiles("images/temp");
  }

  @Test
  public void testSaveWithQuotes() {
    //GIVEN
    Reader in = new StringReader("save \"images/temp/image.png\" image");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Name : image", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "save Operation performed successfully\n", out.toString());
    deleteFiles("images/temp");
  }

  @Test
  public void testSaveWithQuotesInvalid() {
    //GIVEN
    Reader in = new StringReader("save \"images/temp/image.png image");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n", out.toString());
    deleteFiles("images/temp");
  }

  @Test
  public void testSavePPMForInvalidCommand() {
    //GIVEN
    Reader in = new StringReader("save images/test/test.ppm");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n", out.toString());
    deleteFiles("images/temp");
  }

  @Test
  public void testSavePPMInvalidImageName() {
    //GIVEN
    Reader in = new StringReader("save images/test/test.ppm \" \"");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
    //WHEN
    try {
      rgbController.run();
    } catch (IOException ignored) {
    }

    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n", out.toString());
    deleteFiles("images/temp");
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

}