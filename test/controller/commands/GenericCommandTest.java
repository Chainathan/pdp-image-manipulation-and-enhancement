package controller.commands;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import controller.ComponentEnum;
import controller.ImageGraphics;
import model.ImageData;
import model.RgbImageModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Test class for GenericCommand class.
 */
public class GenericCommandTest {

  StringBuilder log;

  @Before
  public void setup() {
    log = new StringBuilder();
  }

  static class MockRgbImage implements RgbImageModel {
    private final String imageRef;
    private final StringBuilder log;

    MockRgbImage(StringBuilder log, String imageRef) {
      this.imageRef = imageRef;
      this.log = log;
    }

    @Override
    public RgbImageModel visualizeComponent(ComponentEnum componentEnum)
            throws IllegalArgumentException {
      return null;
    }

    @Override
    public RgbImageModel horizontalFlip() {
      return null;
    }

    @Override
    public RgbImageModel verticalFlip() {
      return null;
    }

    @Override
    public RgbImageModel brighten(int increment) throws IllegalArgumentException {
      return null;
    }

    @Override
    public RgbImageModel blur() {
      return null;
    }

    @Override
    public RgbImageModel sepia() {
      return null;
    }

    @Override
    public RgbImageModel sharpen() {
      return null;
    }

    @Override
    public ImageData getImageData() {
      return null;
    }

    @Override
    public void loadImageData(ImageData imageData) {
      //mock
    }

    @Override
    public RgbImageModel applyCompression(double compressionRatio) throws IllegalArgumentException {
      return null;
    }

    @Override
    public RgbImageModel createHistogram(ImageGraphics graphics) {
      return null;
    }

    @Override
    public RgbImageModel correctColor() {
      return null;
    }

    @Override
    public RgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException {
      return null;
    }

    @Override
    public RgbImageModel cropVertical(double start, double end) throws IllegalArgumentException {
      log.append(imageRef).append(" method crop ").append(start)
              .append(" ").append(end).append("\n");
      return new MockRgbImage(log, "croppedImg");
    }

    @Override
    public RgbImageModel overlapOnBase(RgbImageModel otherImage, double start)
            throws IllegalArgumentException {
      log.append(imageRef).append(" method overlap ").append(start).append("\n");
      return new MockRgbImage(log, "overlappedImg");
    }
  }

  private Map<String, RgbImageModel> imageList;
  private RgbImageModel testImage;
  Function<RgbImageModel, RgbImageModel> testFunction;

  @Before
  public void setUp() {
    imageList = new HashMap<>();
    testImage = new MockRgbImage(log, "TestImage");
    imageList.put("TestImage", testImage);
    testFunction = rgb -> {
      log.append("testFunc called\n");
      return rgb;
    };
  }

  @Test
  public void testExecuteWithValidArguments() {
    GenericCommand genericCommand = new GenericCommand(2, testFunction, false);

    String[] arguments = {"TestImage", "DestImage"};

    genericCommand.execute(imageList, arguments);

    assertEquals(2, imageList.size());
    assertEquals(testImage, imageList.get("DestImage"));
  }

  @Test
  public void testExecuteWithValidArgumentsAndSplit() {
    GenericCommand genericCommand = new GenericCommand(3, testFunction, true);

    String[] arguments = {"test-command", "TestImage", "DestImage", "split", "0.35"};

    genericCommand.execute(imageList, arguments);
    System.out.println(log);
    assertEquals("TestImage method crop 0.0 0.35\n" +
            "testFunc called\n" +
            "TestImage method overlap 0.0\n", log.toString());

    assertEquals(2, imageList.size());
    assertTrue(imageList.containsKey("DestImage"));
  }

  @Test
  public void testExecuteWithInvalidArguments() {
    Function<RgbImageModel, RgbImageModel> testFunction = Function.identity();
    GenericCommand genericCommand = new GenericCommand(2, testFunction, false);

    String[] arguments = {"TestImage", "DestImage", "extraArg"};

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            genericCommand.execute(imageList, arguments));

    assertEquals("Invalid arguments for the command TestImage", exception.getMessage());
  }

  @Test
  public void testExecuteWithInvalidArgumentsAndSplit() {
    Function<RgbImageModel, RgbImageModel> testFunction = Function.identity();
    GenericCommand genericCommand = new GenericCommand(4, testFunction, true);

    String[] arguments = {"TestImage", "DestImage", "split", "0.5", "extraArg"};

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            genericCommand.execute(imageList, arguments));

    assertEquals("Invalid arguments for the command TestImage", exception.getMessage());
  }
}
