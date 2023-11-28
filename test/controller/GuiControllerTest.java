package controller;

import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;

import org.junit.Before;
import org.junit.Test;

import view.GuiView;

import java.io.IOException;

import static org.junit.Assert.*;

public class GuiControllerTest {

  static class JFrameViewMock implements GuiView {

    private StringBuilder viewLog;

    public JFrameViewMock(StringBuilder log) {
      this.viewLog = log;
    }


    @Override
    public void togglePreview(boolean isEnabled) {
      viewLog.append("Preview toggled : " + isEnabled + "\n");
    }


    @Override
    public void showWindow() {
      viewLog.append("Show window");
    }

    @Override
    public void showDiscardConfirmation() {
      viewLog.append("Show discard confirmation");
    }

    @Override
    public void showLoadMenu() {
      viewLog.append("Show load menu");
    }

    @Override
    public void showSaveMenu() {
      viewLog.append("Show save menu");
    }

    @Override
    public void displayImage(ImageData imageData) {
      viewLog.append("Image to display : \n");
      viewLog.append("Max Value : ").append(imageData.getMaxValue());
      int[][][] data = imageData.getData();
      viewLog.append("{");
      for (int i = 0; i < data.length; i++) {
        viewLog.append("{");
        for (int j = 0; j < data[0].length; j++) {
          viewLog.append("{ ");
          for (int k = 0; k < data[0][0].length; k++) {
            viewLog.append(data[i][j][k]).append(" ");
          }
          viewLog.append("} ");
        }
        viewLog.append("} ");
      }
      viewLog.append("}\n");
    }

    @Override
    public void displayHistogram(ImageData imageData) {
      viewLog.append("Histogram of Image : \n");
      viewLog.append("Max Value : ").append(imageData.getMaxValue());
      int[][][] data = imageData.getData();
      viewLog.append("{");
      for (int i = 0; i < data.length; i++) {
        viewLog.append("{");
        for (int j = 0; j < data[0].length; j++) {
          viewLog.append("{ ");
          for (int k = 0; k < data[0][0].length; k++) {
            viewLog.append(data[i][j][k]).append(" ");
          }
          viewLog.append("} ");
        }
        viewLog.append("} ");
      }
      viewLog.append("}\n");
    }

    @Override
    public void addFeatures(Features features) {

    }

    @Override
    public void showCompressMenu() {
      viewLog.append("Show compress menu");
    }

    @Override
    public void showLvlAdjMenu() {
      viewLog.append("Show level adjust menu");
    }


    @Override
    public void displayError(String message) {
      viewLog.append(message);
    }

  }

  class RgbImageMock implements RgbImageModel {
    private StringBuilder modelLog;

    public RgbImageMock(StringBuilder log) {
      this.modelLog = log;
    }

    @Override
    public RgbImageModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException {
      modelLog.append("Visualization operation called : " + componentEnum.getComponentString());
      return new RgbImageMock(new StringBuilder("Visualization completed."));
    }

    @Override
    public RgbImageModel horizontalFlip() {
      modelLog.append("Horizontal Flip operation called");
      return new RgbImageMock(new StringBuilder("Horizontal Flip completed."));
    }

    @Override
    public RgbImageModel verticalFlip() {
      modelLog.append("Vertical Flip operation called");
      return new RgbImageMock(new StringBuilder("Vertical Flip completed."));
    }

    @Override
    public RgbImageModel brighten(int increment) throws IllegalArgumentException {
      modelLog.append("Brighten operation called with increment : " + increment);
      return new RgbImageMock(new StringBuilder("Brighten completed."));
    }

    @Override
    public RgbImageModel blur() {
      modelLog.append("Blur operation called");
      return new RgbImageMock(new StringBuilder("Blur completed."));
    }

    @Override
    public RgbImageModel sepia() {
      modelLog.append("Sepia operation called");
      return new RgbImageMock(new StringBuilder("Sepia completed."));
    }

    @Override
    public RgbImageModel sharpen() {
      modelLog.append("Sharpen operation called");
      return new RgbImageMock(new StringBuilder("Sharpen completed."));
    }

    @Override
    public ImageData getImageData() {
      modelLog.append("GetImageData called\n");
      int[][][] data = {
              {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}},
              {{2, 3, 4}, {4, 5, 6}, {1, 2, 4}},
              {{1, 2, 3}, {3, 4, 5}, {1, 2, 4}}
      };
      ImageData dummy = new ImageData(data, 255);
      return dummy;
    }

    @Override
    public void loadImageData(ImageData imageData) {
      modelLog.append("Image Data :");
      int[][][] data = imageData.getData();
      modelLog.append("{");
      for (int i = 0; i < data.length; i++) {
        modelLog.append("{");
        for (int j = 0; j < data[0].length; j++) {
          modelLog.append("{ ");
          for (int k = 0; k < data[0][0].length; k++) {
            modelLog.append(data[i][j][k]).append(" ");
          }
          modelLog.append("} ");
        }
        modelLog.append("} ");
      }
      modelLog.append("}\n");
    }

    @Override
    public RgbImageModel applyCompression(double compressionRatio) throws IllegalArgumentException {
      modelLog.append("Compress operation called with compression ratio : " + compressionRatio);
      return new RgbImageMock(new StringBuilder("Compress completed."));
    }

    @Override
    public RgbImageModel createHistogram(ImageGraphics graphics) {
      modelLog.append("Create Histogram called\n");
      return new RgbImageMock(new StringBuilder("Histogram created"));
    }

    @Override
    public RgbImageModel correctColor() {
      modelLog.append("Color Correct operation called");
      return new RgbImageMock(new StringBuilder("Color Correct completed."));
    }

    @Override
    public RgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException {
      if (b < 0 || m < 0 || w < 0
              || b > m || m > w || w > 255) {
        throw new IllegalArgumentException("Invalid arguments for adjust level");
      }
      modelLog.append("Level Adjust operation called with b:" + b + " m:" + m + " w:" + w);
      return new RgbImageMock(new StringBuilder("Level Adjust completed."));
    }

    @Override
    public RgbImageModel cropVertical(double start, double end) throws IllegalArgumentException {
      modelLog.append("Crop Vertical operation called start: " + start + " end: " + end);
      return new RgbImageMock(new StringBuilder("Crop Vertical completed."));
    }

    @Override
    public RgbImageModel overlapOnBase(RgbImageModel otherImage, double start) throws IllegalArgumentException {
      modelLog.append("Overlap operation called start : " + start);
      modelLog.append("Max Value : ").append(otherImage.getImageData().getMaxValue());
      int[][][] data = otherImage.getImageData().getData();
      modelLog.append("{");
      for (int i = 0; i < data.length; i++) {
        modelLog.append("{");
        for (int j = 0; j < data[0].length; j++) {
          modelLog.append("{ ");
          for (int k = 0; k < data[0][0].length; k++) {
            modelLog.append(data[i][j][k]).append(" ");
          }
          modelLog.append("} ");
        }
        modelLog.append("} ");
      }
      modelLog.append("}\n");
      return new RgbImageMock(new StringBuilder("Overlap completed."));
    }
  }

  class FactoryRgbImageMock implements FactoryRgbImageModel {
    private StringBuilder factoryLog;

    public FactoryRgbImageMock(StringBuilder log) {
      this.factoryLog = log;
    }

    @Override
    public RgbImageModel createImageModel() {
      return new RgbImageMock(factoryLog);
    }
  }

  private void getexpViewLog() {
    expViewLog.append("Max Value : 255");
    int[][][] data = {
            {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}},
            {{2, 3, 4}, {4, 5, 6}, {1, 2, 4}},
            {{1, 2, 3}, {3, 4, 5}, {1, 2, 4}}
    };
    expViewLog.append("{");
    for (int i = 0; i < data.length; i++) {
      expViewLog.append("{");
      for (int j = 0; j < data[0].length; j++) {
        expViewLog.append("{ ");
        for (int k = 0; k < data[0][0].length; k++) {
          expViewLog.append(data[i][j][k]).append(" ");
        }
        expViewLog.append("} ");
      }
      expViewLog.append("} ");
    }
    expViewLog.append("}\n");
  }

  private void generateExpViewLogs() {
    expViewLog.append("Image to display : \n");
    getexpViewLog();
    expViewLog.append("Histogram of Image : \n");
    getexpViewLog();
    expViewLog.append("Preview toggled : ");
  }

  StringBuilder modelLog;
  StringBuilder viewLog;
  StringBuilder expViewLog;
  StringBuilder expModelLog;
  FactoryRgbImageModel factory;
  GuiView view;
  GuiControllerSplit controller;

  @Before
  public void setUp() {
    modelLog = new StringBuilder();
    viewLog = new StringBuilder();
    factory = new FactoryRgbImageMock(modelLog);
    view = new JFrameViewMock(viewLog);
    controller = new GuiControllerSplit(factory, view);
    expViewLog = new StringBuilder();
    expModelLog = new StringBuilder();
    generateExpViewLogs();
    generateExpModelLogs();
  }

  private void generateExpModelLogs() {
    expModelLog.append("GetImageData called\n");
    expModelLog.append("GetImageData called\n");
    expModelLog.append("Create Histogram called\n");
    expModelLog.append("GetImageData called\n");
  }

  @Test
  public void testBlur() {
    //WHEN
    controller.blur();
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testSharpen() {
    //WHEN
    controller.sharpen();
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testSepia() {
    //WHEN
    controller.sepia();
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testGreyscale() {
    //WHEN
    controller.greyscale();
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testRed() {
    //WHEN
    controller.red();
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testBlue() {
    //WHEN
    controller.blue();
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testGreen() {
    //WHEN
    controller.green();
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testVertical() {
    //WHEN
    controller.vertical();
    //THEN
    expViewLog.append("false\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testHorizontal() {
    //WHEN
    controller.horizontal();
    //THEN
    expViewLog.append("false\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testColorCorrect() {
    //WHEN
    controller.colorCorrect();
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testCompress() {
    //WHEN
    controller.compress(30);
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testLvlAdjust() {
    //WHEN
    controller.levelsAdjust(10, 20, 200);
    //THEN
    expViewLog.append("true\n");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  private StringBuilder getLogForApplyOperation(boolean isPreview) {
    StringBuilder finalLog = new StringBuilder();
    finalLog.append(expViewLog).append(isPreview + "\n").append(expViewLog).append("false\n");
    return finalLog;
  }

  @Test
  public void testApplyForBlur() {
    //GIVEN
    controller.blur();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Blur operation called");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForSharpen() {
    //GIVEN
    controller.sharpen();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Sharpen operation called");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForSepia() {
    //GIVEN
    controller.sepia();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Sepia operation called");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForGreyscale() {
    //GIVEN
    controller.greyscale();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Visualization operation called : luma-component");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForRed() {
    //GIVEN
    controller.red();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Visualization operation called : red-component");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForBlue() {
    //GIVEN
    controller.blue();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Visualization operation called : blue-component");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForGreen() {
    //GIVEN
    controller.green();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Visualization operation called : green-component");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForHorizontalFlip() {
    //GIVEN
    controller.horizontal();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Horizontal Flip operation called");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(false).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForVerticalFlip() {
    //GIVEN
    controller.vertical();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Vertical Flip operation called");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(false).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForCompress() {
    //GIVEN
    controller.compress(60);

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Compress operation called with compression ratio : 60.0");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForColorCorrect() {
    //GIVEN
    controller.colorCorrect();

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Color Correct operation called");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForLvlAdjust() {
    //GIVEN
    controller.levelsAdjust(20, 30, 200);

    //WHEN
    controller.apply();

    //THEN
    expModelLog.append("Level Adjust operation called with b:20 m:30 w:200");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  @Test
  public void testApplyForLvlAdjustForInvalidInputs() {
    //GIVEN
    controller.levelsAdjust(20, 10, 200);

    //WHEN
    controller.apply();

    //THEN
    expViewLog.append("true\n").append("Invalid arguments for adjust level");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testApplyForInvalidOperation() {
    //WHEN
    controller.apply();

    //THEN
    assertEquals("", modelLog.toString());
    assertEquals("Invalid Operation", viewLog.toString());
  }

  @Test
  public void testRun() {
    //WHEN
    try {
      controller.run();
    } catch (IOException e) {
      fail("Exception not expected");
    }
    //THEN
    assertEquals("", modelLog.toString());
    assertEquals("Show window", viewLog.toString());
  }

  //Check
  @Test
  public void exitProgramBeforeSave() {
    //GIVEN
    controller.blur();
    controller.apply();

    //WHEN
    controller.exitProgram();

    //THEN
    expModelLog.append("Blur operation called");
    StringBuilder exp = getLogForApplyOperation(true).append("Show discard confirmation");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(exp.toString(), viewLog.toString());
  }

  //Check.
  @Test
  public void exitProgramAfterSave() {
    //GIVEN
    controller.blur();
    controller.apply();
    controller.saveImage("images/temp/dummy.png");

    //WHEN
    controller.exitProgram();

    //THEN
    expModelLog.append("Blur operation called");
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(getLogForApplyOperation(true).toString(), viewLog.toString());
  }

  //Load
  @Test
  public void testLoadImage() {
    //GIVEN
    expViewLog = new StringBuilder();
    expViewLog.append("Image to display : \n");
    getexpViewLog();
    expViewLog.append("Histogram of Image : \n");
    getexpViewLog();

    expModelLog = new StringBuilder();
    expModelLog.append("GetImageData called\n");
    expModelLog.append("Image Data :");
    int[][][] data = {{{151, 153, 160, 154, 154},
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
    expModelLog.append("{");
    for (int i = 0; i < data.length; i++) {
      expModelLog.append("{");
      for (int j = 0; j < data[0].length; j++) {
        expModelLog.append("{ ");
        for (int k = 0; k < data[0][0].length; k++) {
          expModelLog.append(data[i][j][k]).append(" ");
        }
        expModelLog.append("} ");
      }
      expModelLog.append("} ");
    }
    expModelLog.append("}\n");
    expModelLog.append("GetImageData called\n");
    expModelLog.append("GetImageData called\n");
    expModelLog.append("Create Histogram called\n");
    //WHEN
    controller.loadImage("images/test/test.ppm");

    //THEN
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testLoadImageIfImageIsNotSavedAfterOperation() {
    //GIVEN
    controller.blur();
    controller.apply();
    expViewLog = getLogForApplyOperation(true);
    expModelLog.append("Blur operation called");
    expViewLog.append("Show discard confirmation");
    expViewLog.append("Image to display : \n");
    getexpViewLog();
    expViewLog.append("Histogram of Image : \n");
    getexpViewLog();

    //WHEN
    controller.loadImage("images/test/test.ppm");

    //THEN
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }

  @Test
  public void testLoadImageForUnsupportedFileFormat() {
    //WHEN
    controller.loadImage("images/test/testInvalidFormat.txt");

    //THEN
    assertEquals("GetImageData called\n", modelLog.toString());
    assertEquals("Unsupported File format", viewLog.toString());
  }

  //Invalid Image or File not found?
  @Test
  public void testLoadImageForImageNotPresent() {
    //WHEN
    controller.loadImage("images/test/sample.png");

    //THEN
    assertEquals("GetImageData called\n", modelLog.toString());
    assertEquals("Invalid Image.", viewLog.toString());
  }

  @Test
  public void testSaveImage() {
    //WHEN
    controller.saveImage("images/temp/dummy.png");

    //THEN
    assertEquals("GetImageData called\n", modelLog.toString());
    assertEquals("", viewLog.toString());
  }

  @Test
  public void testSaveImageForUnsupportedFileFormat() {
    //WHEN
    controller.saveImage("images/temp/dummy.txt");

    //THEN
    assertEquals("GetImageData called\n", modelLog.toString());
    assertEquals("Unsupported File format", viewLog.toString());
  }

  @Test
  public void testHandleLoadButton() {
    //WHEN
    controller.handleLoadButton();

    //THEN
    assertEquals("", modelLog.toString());
    assertEquals("Show load menu", viewLog.toString());
  }

  @Test
  public void testHandleSaveButton() {
    //WHEN
    controller.handleSaveButton();

    //THEN
    assertEquals("GetImageData called\n", modelLog.toString());
    assertEquals("Show save menu", viewLog.toString());
  }

  @Test
  public void testHandleLevelsAdjust() {
    //WHEN
    controller.handleLevelsAdjust();

    //THEN
    assertEquals("", modelLog.toString());
    assertEquals("Show level adjust menu", viewLog.toString());
  }

  @Test
  public void testHandleCompress() {
    //WHEN
    controller.handleCompress();

    //THEN
    assertEquals("", modelLog.toString());
    assertEquals("Show compress menu", viewLog.toString());
  }

  @Test
  public void testPreview() {
    //GIVEN
    controller.blur();
    expModelLog.append("Blur operation called")
            .append("Overlap operation called start : 0.0");
    expModelLog.append("Max Value : 255");
    int[][][] data = {
            {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}},
            {{2, 3, 4}, {4, 5, 6}, {1, 2, 4}},
            {{1, 2, 3}, {3, 4, 5}, {1, 2, 4}}
    };
    expModelLog.append("{");
    for (int i = 0; i < data.length; i++) {
      expModelLog.append("{");
      for (int j = 0; j < data[0].length; j++) {
        expModelLog.append("{ ");
        for (int k = 0; k < data[0][0].length; k++) {
          expModelLog.append(data[i][j][k]).append(" ");
        }
        expModelLog.append("} ");
      }
      expModelLog.append("} ");
    }
    expModelLog.append("}\n");
    expModelLog.append("GetImageData called\n");
    expModelLog.append("Create Histogram called\n");
    expViewLog.append("true\n");
    expViewLog.append("Image to display : \n");
    getexpViewLog();
    expViewLog.append("Histogram of Image : \n");
    getexpViewLog();

    //WHEN
    controller.preview(50);

    //THEN
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());

  }

  @Test
  public void testnoOperation() {
    //WHEN
    controller.noOperation();

    //THEN
    assertEquals("", modelLog.toString());
    assertEquals("Preview toggled : false\n", viewLog.toString());
  }

  @Test
  public void testCancel() {
    //WHEN
    controller.cancel();
    expModelLog = new StringBuilder();
    expModelLog.append("GetImageData called\n")
            .append("GetImageData called\n")
            .append("Create Histogram called\n");
    expViewLog = new StringBuilder();
    expViewLog.append("Image to display : \n");
    getexpViewLog();
    expViewLog.append("Histogram of Image : \n");
    getexpViewLog();
    //THEN
    assertEquals(expModelLog.toString(), modelLog.toString());
    assertEquals(expViewLog.toString(), viewLog.toString());
  }
}
