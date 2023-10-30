import Controller.ImageController;
import Controller.RgbController;
import DAO.DataDAO;
import DAO.ImageData;
import DAO.ImageDataDAO;
import Model.ImageProcessorModel;
import Model.RgbImageProcessor;
import View.ImeTextView;
import View.TextView;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static org.junit.Assert.*;

public class RgbControllerTest {

  class MockImageDataDAO implements DataDAO {

      private StringBuilder log;

      public MockImageDataDAO(StringBuilder log){
          this.log = log;
      }
    @Override
    public ImageData load(String filePath) throws IOException {
          int[][][] data = {
                  {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}},
                  {{5,6,7,8}, {1,2,3,4}, {9,10,11,12}},
                  {{1,2,3,4}, {9,10,11,12}, {5,6,7,8}}
          };
          int maxValue = 255;
          ImageData imageData = new ImageData(data, maxValue);
          log.append("FilePath : "+filePath);
      return imageData;
    }

    @Override
    public void save(String filePath, ImageData imageModel) throws IOException {

    }
  }

  class MockRgbImageProcess implements ImageProcessorModel {

      private StringBuilder log;

    public MockRgbImageProcess(StringBuilder log){
        this.log = log;
    }
    @Override
    public void addImage(String destImageName, ImageData imageData) throws IllegalArgumentException {
        log.append("Dest Image Name : "+destImageName+"\nImage Data : "+imageData.getData()
        +"\nMax Pixel Size : "+imageData.getMaxValue());
    }

    @Override
    public ImageData getImageData(String imageName) throws IllegalArgumentException {
      return null;
    }

    @Override
    public void visualizeComponent(String imageName, String destImageName, String component) throws IllegalArgumentException {

    }

    @Override
    public void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException {

    }

    @Override
    public void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException {

    }

    @Override
    public void brighten(String imageName, String destImageName, int increment) throws IllegalArgumentException {

    }

    @Override
    public void darken(String imageName, String destImageName, int decrement) throws IllegalArgumentException {

    }

    @Override
    public void splitComponents(String imageName, List<String> destComponentImageList) throws IllegalArgumentException {

    }

    @Override
    public void combineComponents(String destImageName, List<String> componentImageList) throws IllegalArgumentException {

    }

    @Override
    public void blur(String imageName, String destImageName) throws IllegalArgumentException {

    }

    @Override
    public void sharpen(String imageName, String destImageName) throws IllegalArgumentException {

    }
    @Override
    public void sepia(String imageName, String destImageName) throws IllegalArgumentException {

    }
  }

    @Test
    public void testRun() {
        //GIVEN
        StringBuffer out = new StringBuffer();
        StringBuilder modelLog = new StringBuilder();
        StringBuilder daoLog = new StringBuilder();
        Reader in = new StringReader("load filePath imageName\nexit");
        ImageProcessorModel rgbImageProcess = new MockRgbImageProcess(modelLog);
        DataDAO imageDataDAO = new MockImageDataDAO(daoLog);
        ImeTextView textView = new TextView(out);
        ImageController rgbController = new RgbController(rgbImageProcess, textView, imageDataDAO, in);
        int[][][] expectedData = {
                {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}},
                {{5,6,7,8}, {1,2,3,4}, {9,10,11,12}},
                {{1,2,3,4}, {9,10,11,12}, {5,6,7,8}}
        };
        int expectedMaxValue = 255;

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
        assertEquals("Dest Image Name : imageName\nImage Data : "+expectedData
                +"\nMax Pixel Size : 255",modelLog.toString());
    }
}