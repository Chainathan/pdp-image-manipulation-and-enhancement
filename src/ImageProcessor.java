import Controller.RgbController;
import DAO.DataDAO;
import DAO.ImageDataDAO;
import Model.ImageProcessorModel;
import Model.RgbImageProcessor;
import View.ImeTextView;
import View.TextView;

import java.io.IOException;
import java.io.InputStreamReader;

public class ImageProcessor {
  public static void main(String[] args) {
    ImageProcessorModel model = new RgbImageProcessor();
    ImeTextView textView = new TextView(System.out);
    DataDAO imageDAO = new ImageDataDAO();
    RgbController controller = new RgbController(model, textView, imageDAO, new InputStreamReader(System.in));
    try{
      controller.run();
    }
    catch (IOException e){
      System.out.println(e.getMessage());
    }
  }
}
