package controller.commands;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;

import static org.junit.Assert.*;

public class RgbCombineTest {

  private static class MockFactoryRgbImageModel implements FactoryRgbImageModel {
    @Override
    public RgbImageModel createImageModel() {
//      return new RgbImageModel(new ImageData(new int[1][1][1], 255));
      return null;
    }
  }

  @Test
  public void testExecute() {
    // Create an instance of RgbCombine with the mocked dependencies
    RgbCombine rgbCombine = new RgbCombine(new MockFactoryRgbImageModel());

    // Create mock image data
    int[][][] mockImageData = new int[1][1][1];
    ImageData redImageData = new ImageData(mockImageData, 255);
    ImageData greenImageData = new ImageData(mockImageData, 255);
    ImageData blueImageData = new ImageData(mockImageData, 255);

    // Create mock image list
    Map<String, RgbImageModel> imageList = new HashMap<>();
//    imageList.put("red", new RgbImageModel(redImageData));
//    imageList.put("green", new RgbImageModel(greenImageData));
//    imageList.put("blue", new RgbImageModel(blueImageData));

    // Mock arguments
    String[] arguments = {"arg1", "arg2", "red", "green", "blue"};

    // Call the execute method
    try {
      rgbCombine.execute(imageList, arguments);

      // Verify that the new image was added to the image list
      assertTrue(imageList.containsKey("arg2"));

      // Add more assertions as needed to verify the behavior of the execute method
    } catch (IllegalArgumentException e) {
      fail("IllegalArgumentException not expected");
    }
  }
}

