package Controller;

import Model.ImageData;
import Model.ImageProcessorModel;
import View.ImeTextView;
import View.TextView;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;

public class RgbControllerTest {

//  class MockImageImageFileIO implements ImageFileIO {
//
//      private StringBuilder log;
//
//      public MockImageImageFileIO(StringBuilder log){
//          this.log = log;
//      }
//    @Override
//    public ImageData load(String filePath) throws IOException {
//          int[][][] data = {
//                  {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}},
//                  {{5,6,7,8}, {1,2,3,4}, {9,10,11,12}},
//                  {{1,2,3,4}, {9,10,11,12}, {5,6,7,8}}
//          };
//          int maxValue = 255;
//          ImageData imageData = new ImageData(data, maxValue);
//          log.append("FilePath : "+filePath);
//      return imageData;
//    }
//
//    @Override
//    public void save(String filePath, ImageData imageModel) throws IOException {
//
//    }
//  }

  class MockRgbImageProcess implements ImageProcessorModel {

      private StringBuilder log;

    public MockRgbImageProcess(StringBuilder log){
        this.log = log;
    }
    @Override
    public void addImage(String destImageName, ImageData imageData) throws IllegalArgumentException {
      log.append("Dest Image Name : "+destImageName+"\nImage Data : ");
      int width = imageData.getData()[0][0].length;
      int height = imageData.getData()[0].length;
      for(int k=0;k<3;k++) {
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            log.append(imageData.getData()[k][i][j] + " ");
          }
        }
      }
        log.append("\nMax Pixel Size : "+imageData.getMaxValue());
      //log for imageModel also.
    }

    @Override
    public ImageData getImageData(String imageName) throws IllegalArgumentException {
      return null;
    }

    @Override
    public void visualizeComponent(String imageName, String destImageName, String component) throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName)
              .append(", Component : ")
              .append(component);
    }

    @Override
    public void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName);
    }

    @Override
    public void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName);
    }

    @Override
    public void brighten(String imageName, String destImageName, int increment) throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image Name : ")
              .append(destImageName)
              .append(", Increment : ")
              .append(increment);
    }

    @Override
    public void splitComponents(String imageName, List<String> destComponentImageList) throws IllegalArgumentException {
      log.append("Image Name : ")
              .append(imageName)
              .append(", Dest Image List : [ ");
      for(String destImg : destComponentImageList){
        log.append(destImg).append(" ");
      }
      log.append("]");
    }

    @Override
    public void combineComponents(String destImageName, List<String> componentImageList) throws IllegalArgumentException {
      log.append("Dest Image Name : ")
              .append(destImageName)
              .append(", Image List : [ ");
      for(String imgComponents : componentImageList){
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
  ImeTextView textView;
  @Before
  public void setUp(){
    //GIVEN
    out = new StringBuffer();
    modelLog = new StringBuilder();
    rgbImageProcess = new MockRgbImageProcess(modelLog);
    textView = new TextView(out);
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);
  }
  /*
    @Test
    public void testRun() {
        //GIVEN
        StringBuffer out = new StringBuffer();
        StringBuilder modelLog = new StringBuilder();
        StringBuilder daoLog = new StringBuilder();
        Reader in = new StringReader("load filePath imageName\nexit");
        ImageProcessorModel rgbImageProcess = new MockRgbImageProcess(modelLog);
        ImageFileIO imageImageFileIO = new MockImageImageFileIO(daoLog);
        ImeTextView textView = new TextView(out);
        ImageController rgbController = new RgbController(rgbImageProcess, textView, imageImageFileIO, in);
        StringBuilder expected = new StringBuilder();
        int[][][] expectedData = {
                {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}},
                {{5,6,7,8}, {1,2,3,4}, {9,10,11,12}},
                {{1,2,3,4}, {9,10,11,12}, {5,6,7,8}}
        };
        int expectedMaxValue = 255;
      expected.append("Dest Image Name : imageName\nImage Data : ");
      int width = expectedData[0][0].length;
      int height = expectedData[0].length;
      for(int k=0;k<3;k++) {
        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            expected.append(expectedData[k][i][j] + " ");
          }
        }
      }
      expected.append("\nMax Pixel Size : 255");

        //WHEN
        try{
            rgbController.run();
        }
        catch (IOException e){
            fail("The above method call should not have thrown an exception");
        }

        //THEN
        assertEquals("Image Processing Program\n> \n" +
                "Operation performed successfully\n> \nProgram Terminated\n",out.toString());
        assertEquals("FilePath : filePath",daoLog.toString());
//        System.out.println("Expected :"+expected);
//        System.out.println("Model :"+modelLog.toString());
        assertEquals(expected.toString(),modelLog.toString());
    }

   */

  @Test
  public void testRedComponentCommand(){
    //GIVEN
    Reader in = new StringReader("red-component image red-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : red-image, " +
            "Component : red-component",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "red-component Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testGreenComponentCommand(){
    //GIVEN
    Reader in = new StringReader("green-component image green-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : green-image, " +
            "Component : green-component",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "green-component Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testBlueComponentCommand(){
    //GIVEN
    Reader in = new StringReader("blue-component image blue-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : blue-image, " +
            "Component : blue-component",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "blue-component Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testValueComponentCommand(){
    //GIVEN
    Reader in = new StringReader("value-component image value-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : value-image, " +
            "Component : value-component",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "value-component Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testLumaComponentCommand(){
    //GIVEN
    Reader in = new StringReader("luma-component image luma-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : luma-image, " +
            "Component : luma-component",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "luma-component Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testIntensityComponentCommand(){
    //GIVEN
    Reader in = new StringReader("intensity-component image intensity-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : intensity-image, " +
            "Component : intensity-component",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "intensity-component Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testVerticalFlipCommand(){
    //GIVEN
    Reader in = new StringReader("vertical-flip image flipped-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : flipped-image",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "vertical-flip Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testHorizontalFlipCommand(){
    //GIVEN
    Reader in = new StringReader("horizontal-flip image flipped-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : flipped-image",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "horizontal-flip Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testBlurCommand(){
    //GIVEN
    Reader in = new StringReader("blur image blurred-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : blurred-image",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "blur Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testSharpenCommand(){
    //GIVEN
    Reader in = new StringReader("sharpen image sharpened-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : sharpened-image",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "sharpen Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testSepiaCommand(){
    //GIVEN
    Reader in = new StringReader("sepia image sepia-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : sepia-image",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "sepia Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testDefaultFor3Args(){
    //GIVEN
    Reader in = new StringReader("command arg1 arg2\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown command: command\nProgram Terminated\n",out.toString());
  }

  // 4 Args Command
  @Test
  public void testBrightenCommand(){
    //GIVEN
    Reader in = new StringReader("brighten 50 image brightened-image\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Name : image, " +
            "Dest Image Name : brightened-image, "+
            "Increment : 50",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "brighten Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testDefaultFor4Arg(){
    //GIVEN
    Reader in = new StringReader("command arg1 arg2 arg3\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown command: command\nProgram Terminated\n",out.toString());
  }

  // 5 Arg Command
  @Test
  public void testRgbSplitCommand(){
    //GIVEN
    Reader in = new StringReader("rgb-split image img1 img2 img3\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN

    assertEquals("Image Name : image, " +
            "Dest Image List : [ img1 img2 img3 ]",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "rgb-split Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testRgbCombineCommand(){
    //GIVEN
    Reader in = new StringReader("rgb-combine image img1 img2 img3\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN

    assertEquals("Dest Image Name : image, " +
            "Image List : [ img1 img2 img3 ]",modelLog.toString());
    assertEquals("Image Processing program started\n" +
            "rgb-combine Operation performed successfully\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testDefaultFor5Args(){
    //GIVEN
    Reader in = new StringReader("command arg1 arg2 arg3 arg4\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown command: command\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testRandomOperation(){
    //GIVEN
    Reader in = new StringReader("random\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown Operation: random\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testOperationWithHash(){
    //GIVEN
    Reader in = new StringReader("random    #this is a cmd\nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Processing program started\n" +
            "Unknown Operation: random\nProgram Terminated\n",out.toString());
  }

  @Test
  public void testBlankOperation(){
    //GIVEN
    Reader in = new StringReader("   \nexit");
    ImageController rgbController = new RgbController(rgbImageProcess, textView, in);

    //WHEN
    try{
      rgbController.run();
    }
    catch (IOException ignored){}

    //THEN
    assertEquals("Image Processing program started\n" +
            "Program Terminated\n",out.toString());
  }
}