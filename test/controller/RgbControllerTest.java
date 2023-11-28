package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;
import view.ImageProcessorView;
import view.TextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test class for RgbController class.
 */
public class RgbControllerTest {
  class MockFactory implements FactoryRgbImageModel {
    @Override
    public RgbImageModel createImageModel() {
      return new MockRgbImage(modelLog);
    }
  }

  static class MockRgbImage implements RgbImageModel {
    private final StringBuilder log;

    public MockRgbImage(StringBuilder log) {
      this.log = log;
    }

    public MockRgbImage() {
      log = new StringBuilder();
    }

    @Override
    public RgbImageModel visualizeComponent(ComponentEnum componentEnum)
            throws IllegalArgumentException {
      log.append("Component : ").append(componentEnum.getComponentString());
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel horizontalFlip() {
      log.append("Horizontal Flip : Called");
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel verticalFlip() {
      log.append("Vertical Flip : Called");
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel brighten(int increment) throws IllegalArgumentException {
      log.append("increment : ").append(increment);
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel blur() {
      log.append("Blur : Called");
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel sepia() {
      log.append("Sepia : Called");
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel sharpen() {
      log.append("Sharpen : Called");
      return new MockRgbImage();
    }

    @Override
    public ImageData getImageData() {
      log.append("imagedata called");
      return new ImageData(new int[3][5][5], 255);
    }

    @Override
    public void loadImageData(ImageData imageData) {
      //mock
    }

    @Override
    public RgbImageModel applyCompression(double compressionRatio)
            throws IllegalArgumentException {
      log.append("compressionRatio : ").append(compressionRatio);
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel createHistogram(ImageGraphics graphics) {
      log.append("Histogram : Called");
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel correctColor() {
      log.append("Color Correct : Called");
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException {
      log.append("b : ").append(b).append(" m : ").append(m).append(" w : ").append(w);
      return new MockRgbImage();
    }

    @Override
    public RgbImageModel cropVertical(double start, double end) throws IllegalArgumentException {
      log.append("\n").append("crop start : ")
              .append(start).append(" end : ").append(end).append("\n");
      return new MockRgbImage(log);
    }

    @Override
    public RgbImageModel overlapOnBase(RgbImageModel otherImage, double start)
            throws IllegalArgumentException {
      log.append("\n").append("overlap start : ").append(start).append("\n");
      return new MockRgbImage();
    }
  }

  StringBuffer out;
  StringBuilder modelLog;
  ImageProcessorView textView;
  FactoryRgbImageModel factory;
  CommandMapper commandMapper;
  String load;
  String defaultStart;
  String defaultEnd;

  @Before
  public void setUp() {
    //GIVEN
    out = new StringBuffer();
    modelLog = new StringBuilder();
    textView = new TextView(out);
    factory = new MockFactory();
    commandMapper = new CommandMapperAdv();
    load = "load images/test/test.png image\n";
    defaultStart = load + "%s image destImage\n";
    defaultEnd = "Image Processing program started\n" +
            "load Operation performed successfully\n" +
            "%s Operation performed successfully\n";

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

  @Test
  public void testRedComponentCommand() {
    //GIVEN
    String command = "red-component";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Component : " + command, modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testGreenComponentCommand() {
    //GIVEN
    String command = "green-component";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Component : " + command, modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testBlueComponentCommand() {
    //GIVEN
    String command = "blue-component";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Component : " + command, modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testValueComponentCommand() {
    //GIVEN
    String command = "value-component";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Component : " + command, modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testLumaComponentCommand() {
    //GIVEN
    String command = "luma-component";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Component : " + command, modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testLumaComponentCommandSplit() {
    //GIVEN
    String command = "luma-component";
    defaultStart = load + "%s image destImage split 25";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("\n" +
            "crop start : 0.0 end : 25.0\n" +
            "Component : " + command + "\n" +
            "overlap start : 0.0\n", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testIntensityComponentCommand() {
    //GIVEN
    String command = "intensity-component";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Component : " + command, modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testVerticalFlipCommand() {
    //GIVEN
    String command = "vertical-flip";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Vertical Flip : Called", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testHorizontalFlipCommand() {
    //GIVEN
    String command = "horizontal-flip";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Horizontal Flip : Called", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testBlurCommand() {
    //GIVEN
    String command = "blur";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Blur : Called", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testBlurSplitCommand() {
    //GIVEN
    String command = "blur";
    defaultStart = load + "%s image destImage split 25";

    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("\n" +
            "crop start : 0.0 end : 25.0\n" +
            "Blur : Called\n" +
            "overlap start : 0.0\n", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testSharpenCommand() {
    //GIVEN
    String command = "sharpen";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Sharpen : Called", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testSharpenSplitCommand() {
    //GIVEN
    String command = "sharpen";
    defaultStart = load + "%s image destImage split 25";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("\n" +
            "crop start : 0.0 end : 25.0\n" +
            "Sharpen : Called\n" +
            "overlap start : 0.0\n", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testSepiaCommand() {
    //GIVEN
    String command = "sepia";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Sepia : Called", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testSepiaSplitCommand() {
    //GIVEN
    String command = "sepia";
    defaultStart = load + "%s image destImage split 25";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("\n" +
            "crop start : 0.0 end : 25.0\n" +
            "Sepia : Called\n" +
            "overlap start : 0.0\n", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testHistogramCommand() {
    //GIVEN
    String command = "histogram";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Histogram : Called", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testColorCorrectCommand() {
    //GIVEN
    String command = "color-correct";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("Color Correct : Called", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testColorCorrectCommandSplit() {
    //GIVEN
    String command = "color-correct";
    defaultStart = load + "%s image destImage split 25";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("\n" +
            "crop start : 0.0 end : 25.0\n" +
            "Color Correct : Called\n" +
            "overlap start : 0.0\n", modelLog.toString());
    assertEquals(String.format(defaultEnd, command), out.toString());
  }

  @Test
  public void testDefaultFor3Args() {
    //GIVEN
    runCommand("command arg1 arg2");
    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n", out.toString());
  }

  @Test
  public void testBrightenCommand() {
    //GIVEN
    String command = "brighten 50";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("increment : 50", modelLog.toString());
    assertEquals(String.format(defaultEnd, "brighten"), out.toString());
  }

  @Test
  public void testCompressCommand() {
    //GIVEN
    String command = "compress 20";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("compressionRatio : 20.0", modelLog.toString());
    assertEquals(String.format(defaultEnd, "compress"), out.toString());
  }

  @Test
  public void testDefaultFor4Arg() {
    //GIVEN
    runCommand("command arg1 arg2 arg3");

    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n", out.toString());
  }

  @Test
  public void testAdjustLevelsCommand() {
    //GIVEN
    String command = "levels-adjust 25 55 241";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("b : 25 m : 55 w : 241", modelLog.toString());
    assertEquals(String.format(defaultEnd, "levels-adjust"), out.toString());
  }

  @Test
  public void testAdjustLevelsCommandSplit() {
    //GIVEN
    String command = "levels-adjust 25 55 241";
    defaultStart = load + "%s image destImage split 25";
    // WHEN
    runCommand(String.format(defaultStart, command));

    //THEN
    assertEquals("\n" +
            "crop start : 0.0 end : 25.0\n" +
            "b : 25 m : 55 w : 241\n" +
            "overlap start : 0.0\n", modelLog.toString());
    assertEquals(String.format(defaultEnd, "levels-adjust"), out.toString());
  }

  @Test
  public void testRgbSplitCommand() {
    //GIVEN
    String command = load + "rgb-split image img1 img2 img3";

    //WHEN
    runCommand(command);

    //THEN
    assertEquals("Component : red-component" +
            "Component : green-component" +
            "Component : blue-component", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n" +
            "rgb-split Operation performed successfully\n", out.toString());
  }

  @Test
  public void testRgbCombineCommand() {
    //GIVEN
    String command = load + "rgb-combine dest image image image";

    //WHEN
    runCommand(command);

    //THEN
    assertEquals("imagedata called" +
            "imagedata called" +
            "imagedata called", modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n" +
            "rgb-combine Operation performed successfully\n", out.toString());
  }

  @Test
  public void testDefaultFor5Args() {
    //GIVEN
    runCommand("command arg1 arg2 arg3 arg4");
    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n", out.toString());
  }

  @Test
  public void testRandomOperation() {
    //GIVEN
    runCommand("random");
    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n", out.toString());
  }

  @Test
  public void testOperationWithHashWithNoComment() {
    //GIVEN
    runCommand("random    #this is a cmd");
    //THEN
    assertEquals("Image Processing program started\n" +
            "Invalid Command\n", out.toString());
  }

  @Test
  public void testOperationWithHashForValidCommand() {
    //GIVEN
    runCommand("load images/test/test.ppm test    #this is a cmd");
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n", out.toString());
  }

  @Test
  public void testOperationWithHash() {
    //GIVEN
    runCommand("#this is a cmd");
    //THEN
    assertEquals("Image Processing program started\n", out.toString());
  }

  @Test
  public void testBlankOperation() {
    //GIVEN
    runCommand("   ");
    //THEN
    assertEquals("Image Processing program started\n", out.toString());
  }

  @Test
  public void testloadPPM() {
    //GIVEN
    runCommand("load images/test/test.ppm koala");
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n", out.toString());
  }

  @Test
  public void testloadPPMWithQuotes() {
    //GIVEN
    runCommand("load \"images/test/test.ppm\" koala\n");
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n", out.toString());
  }

  @Test
  public void testloadPPMWithQuotesForInvalidCommand() {
    //GIVEN
    runCommand("load \"images/test/test.ppm koala\n");
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Invalid Command\n", out.toString());
  }

  @Test
  public void testloadPPMForInvalidCommand() {
    //GIVEN
    runCommand("load \"images/test/test.ppm\" \n");
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Invalid Command\n", out.toString());
  }

  @Test
  public void testloadPPMWhenFileNotPresent() {
    //GIVEN
    runCommand("load images/random.ppm koala\n");
    //THEN
    assertEquals("Image Processing program started\n" +
            "File Not Found\n", out.toString());
  }

  @Test
  public void testloadPPMInvalidImageName() {
    //GIVEN
    runCommand("load images/test/test.ppm \" \"\n");
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Invalid Command\n", out.toString());
  }

  @Test
  public void testSave() {
    String command = "load images/test/test.ppm image\n"
            + "save images/temp/image.png image";
    //GIVEN
    runCommand(command);
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n" +
            "save Operation performed successfully\n", out.toString());
  }

  @Test
  public void testSaveWithQuotes() {
    String command = "load images/test/test.ppm image\n"
            + "save \"images/temp/image.png\" image";
    //GIVEN
    runCommand(command);
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n" +
            "save Operation performed successfully\n", out.toString());
  }

  @Test
  public void testSaveWithQuotesInvalid() {
    String command = "load images/test/test.ppm image\n"
            + "save \"images/temp/image.png image";
    //GIVEN
    runCommand(command);
    //THEN
    assertEquals("Image Processing program started\n" +
            "load Operation performed successfully\n" +
            "save Invalid Command\n", out.toString());
  }

  @Test
  public void testSavePPMForInvalidCommand() {
    String command = "save images/test/test.ppm";
    //GIVEN
    runCommand(command);
    //THEN
    assertEquals("Image Processing program started\n" +
            "save Invalid Command\n", out.toString());
  }

  @Test
  public void testSavePPMForInvalidCommandWithoutLoad() {
    String command = "save images/test/test.ppm image";
    //GIVEN
    runCommand(command);
    //THEN
    assertEquals("Image Processing program started\n" +
            "Image does not exist: image\n", out.toString());
  }
}
