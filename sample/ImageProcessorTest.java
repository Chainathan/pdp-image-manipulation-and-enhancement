/*
package sample;

import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.imageio.ImageIO;

import Controller.RgbController;
import Model.ImageProcessorModel;
import Model.RgbImageProcessor;
import View.ImageProcessorView;
import View.TextView;

import static org.junit.Assert.fail;

public class ImageProcessorTest {

  ImageProcessorModel model;
  Appendable appendable;
  ImageProcessorView textView;
  @Before
  public void setup(){
    model = new RgbImageProcessor();
    appendable = new StringBuilder();
    textView = new TextView(appendable);
  }
  @Test
  public void testSaveLoadPPM() {
    ImageProcessorModel model = new RgbImageProcessor();
    Appendable appendable = new StringBuilder();
    ImageProcessorView textView = new TextView(appendable);

    StringBuilder input = new StringBuilder();
    input.append("load images/testSrc/koala.ppm koala\n");
    input.append("save images/testTemp/koala.ppm koala\n");
    input.append("save images/testTemp/koala.png koala\n");
    input.append("save images/testTemp/koala.jpg koala\n");
    input.append("exit\n");
    Readable reader = new StringReader(input.toString());

    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(appendable);
    String src = "images/testSrc/";
    String temp = "images/testTemp/";
    String[] fileNames = {"koala.ppm","koala.png","koala.jpg"};
    try{
//      for (String fileName: fileNames) {
//        BufferedImage image2 = ImageIO.read(new File(src+fileName));
//        BufferedImage image1 = ImageIO.read(new File(temp+fileName));

      BufferedImage image2 = ImageIO.read(new File("images/testSrc/Koala.ppm"));
      BufferedImage image1 = ImageIO.read(new File("images/testTemp/koala.ppm"));

//        System.out.println(fileName);
//        assertTrue(areImagesEqual(image1, image2));
//      }
    } catch (IOException e) {
      e.printStackTrace();
//      deleteFiles("images/testTemp");
      Assert.fail("Should not have throw error");
    }
//    deleteFiles("images/testTemp");
  }
  @Test
  public void testSourcePPM(){
    ImageProcessorModel model = new RgbImageProcessor();
    Appendable appendable = new StringBuilder();
    ImageProcessorView textView = new TextView(appendable);

    StringBuilder input = new StringBuilder();
    input.append("load images/koala.ppm koala\n");
    input.append("save images/test/koalaTest.png koala\n");
    input.append("save images/test/koalaTest.jpg koala\n");
    input.append("save images/test/koalaTest.ppm koala\n");
    input.append("brighten 50 koala koala-brighter\n");
    input.append("save images/test/koalaTestBright.png koala-brighter\n");
    input.append("brighten -50 koala koala-darker\n");
    input.append("save images/test/koalaTestDark.png koala-darker\n");
    input.append("brighten -50 koala-brighter koala-brighter-darker\n");
    input.append("save images/test/koalaTestBrightDark.png koala-brighter-darker\n");
    input.append("vertical-flip koala koala-vertical\n");
    input.append("save images/test/koalaTestVertical.png koala-vertical\n");
    input.append("horizontal-flip koala-vertical koala-vertical-horizontal\n");
    input.append("save images/test/koalaTestVerticalHorizontal.png koala-vertical-horizontal\n");
    input.append("value-component koala koala-value\n");
    input.append("save images/test/koalaTestValue.png koala-value\n");
    input.append("intensity-component koala koala-intensity\n");
    input.append("save images/test/koalaTestIntensity.png koala-intensity\n");
    input.append("luma-component koala koala-luma\n");
    input.append("save images/test/koalaTestLuma.png koala-luma\n");
    input.append("red-component koala koala-red\n");
    input.append("save images/test/koalaTestRed.png koala-red\n");
    input.append("green-component koala koala-green\n");
    input.append("save images/test/koalaTestGreen.png koala-green\n");
    input.append("blue-component koala koala-blue\n");
    input.append("save images/test/koalaTestBlue.png koala-blue\n");
    input.append("rgb-split koala koala-red koala-green koala-blue\n");
    input.append("save images/test/koalaTestSplitRed.png koala-red\n");
    input.append("save images/test/koalaTestSplitGreen.png koala-green\n");
    input.append("save images/test/koalaTestSplitBlue.png koala-blue\n");
    input.append("#combine them back, but by using the brightened red we get a red tint\n");
    input.append("rgb-combine koala-combine koala-red koala-green koala-blue\n");
    input.append("save images/test/koalaTestCombine.png koala-combine\n");

    input.append("#brighten just the red image\n");
    input.append("brighten 50 koala-red koala-red-bright\n");
    input.append("save images/test/koalaTestSplitRedBr.png koala-red-bright\n");
    input.append("rgb-combine koala-red-tint koala-red-bright koala-green koala-blue\n");
    input.append("save images/test/koalaTestTint.png koala-red-tint\n");
    input.append("save \"images/test blank/koalaTestTint.ppm\" koala-red-tint\n");
    input.append("sepia koala koala-sepia\n");
    input.append("save images/test/koalaTestSepia.png koala-sepia\n");
    input.append("blur koala koala-blur\n");
    input.append("save images/test/koalaTestBlur.png koala-blur\n");
    input.append("sharpen koala koala-sharpen\n");
    input.append("save images/test/koalaTestSharpen.png koala-sharpen\n");
    input.append("load \"images/test blank/koalaTestTint.ppm\" koala\n");
    input.append("save \"images/test/koalaTestOverwriteBlankNameSpace.png\" koala\n");
    input.append("\n");
    input.append("exit\n");

    Readable reader = new StringReader(input.toString());

    RgbController controller = new RgbController(model, textView, reader);
    try {
      controller.run();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(appendable);
//    deleteFiles("images/test");
  }
}*/
