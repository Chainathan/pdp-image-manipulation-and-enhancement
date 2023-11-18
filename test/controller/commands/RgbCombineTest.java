package controller.commands;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import model.FactoryRgbImage;
import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class RgbCombineTest {
  @Test
  public void testExecute() {
    FactoryRgbImageModel factory = new FactoryRgbImage();
    RgbCombine rgbCombine = new RgbCombine(factory);

    // Create mock image data
    int[][][] mockImageData = {
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
    int[][][] mockImageDataRed = new int[3][4][5];
    int[][][] mockImageDataGreen = new int[3][4][5];
    int[][][] mockImageDataBlue = new int[3][4][5];
    mockImageDataRed[0] = mockImageData[0];
    mockImageDataGreen[1] = mockImageData[1];
    mockImageDataBlue[2] = mockImageData[2];

    ImageData redImageData = new ImageData(mockImageDataRed, 255);
    ImageData greenImageData = new ImageData(mockImageDataGreen, 255);
    ImageData blueImageData = new ImageData(mockImageDataBlue, 255);

    Map<String, RgbImageModel> imageList = new HashMap<>();
    RgbImageModel image = factory.createImageModel();
    image.loadImageData(redImageData);
    imageList.put("red", image);

    image = factory.createImageModel();
    image.loadImageData(greenImageData);
    imageList.put("green", image);

    image = factory.createImageModel();
    image.loadImageData(blueImageData);
    imageList.put("blue", image);

    String[] arguments = {"rgb-combine", "destImage", "red", "green", "blue"};

    try {
      rgbCombine.execute(imageList, arguments);
      assertTrue(imageList.containsKey("destImage"));

      ImageData res = imageList.get("destImage").getImageData();
      ImageData exp = new ImageData(mockImageData, 255);
      assertEquals(exp, res);
    } catch (IllegalArgumentException e) {
      fail("IllegalArgumentException not expected");
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testExecuteWithInvalidArguments() {
    Map<String, RgbImageModel> imageList = new HashMap<>();
    RgbCombine rgbCombine = new RgbCombine(new FactoryRgbImage());

    String[] invalidArguments = {"command", "image1", "image2"};
    rgbCombine.execute(imageList, invalidArguments);
  }
}

