package controller.commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

import controller.ImageGraphics;
import controller.ComponentEnum;
import model.ImageData;
import model.RgbImageModel;

public class RgbSplitTest {
  StringBuilder log;
  @Before
  public void setup(){
    log = new StringBuilder();
  }
  class MockRgbImage implements RgbImageModel{
    private StringBuilder log;
    MockRgbImage(StringBuilder log){
      this.log = log;
    }
    MockRgbImage(){
      this.log = new StringBuilder();
    }
    @Override
    public RgbImageModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException {
      log.append(componentEnum.toString()).append(" ");
      return new MockRgbImage();
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
      return null;
    }

    @Override
    public RgbImageModel overlapOnBase(RgbImageModel otherImage, double start) throws IllegalArgumentException {
      return null;
    }
  }

  @Test
  public void testExecute() {
    // Create test data
    Map<String, RgbImageModel> imageList = new HashMap<>();
    RgbImageModel originalImage = new MockRgbImage(log);

    imageList.put("originalImage", originalImage);

    String[] arguments = {"command", "originalImage", "redImage", "greenImage", "blueImage"};

    RgbSplit rgbSplit = new RgbSplit();
    try {
      rgbSplit.execute(imageList, arguments);
      assertEquals("RED GREEN BLUE ",log.toString());
      assertTrue(imageList.containsKey("redImage"));
      assertTrue(imageList.containsKey("greenImage"));
      assertTrue(imageList.containsKey("blueImage"));

    } catch (IllegalArgumentException e) {
      fail("Unexpected exception: " + e.getMessage());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteWithInvalidArguments() {
    Map<String, RgbImageModel> imageList = new HashMap<>();
    RgbSplit rgbSplit = new RgbSplit();

    String[] invalidArguments = {"command", "image1", "image2"};
    rgbSplit.execute(imageList, invalidArguments);
  }
}
