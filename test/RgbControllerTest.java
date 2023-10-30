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

    class MockImageDataDAO implements DataDAO{

        @Override
        public ImageData load(String filePath) throws IOException {
            return null;
        }

        @Override
        public void save(String filePath, ImageData imageModel) throws IOException {

        }
    }
    class MockRgbImageProcess implements ImageProcessorModel{

        @Override
        public void addImage(String destImageName, ImageData imageData) throws IllegalArgumentException {

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
        Reader in = new StringReader("load filePath imageName\nexit");
        ImageProcessorModel rgbImageProcess = new MockRgbImageProcess();
        DataDAO imageDataDAO = new MockImageDataDAO();
        ImeTextView textView = new TextView(out);
        ImageController rgbController = new RgbController(rgbImageProcess, textView, imageDataDAO, in);

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
    }
}