package Model;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import Exceptions.FileFormatNotSupportedException;

public class RgbImageDAO {
  static RgbImeModel load(String filePath) throws IOException {
    String extension = filePath.substring(filePath.lastIndexOf('.'));
    FileFormatEnum fileFormatEnum = FileFormatEnum.valueOf(extension);
    if (fileFormatEnum.equals(FileFormatEnum.PNG)
            || fileFormatEnum.equals(FileFormatEnum.JPG)){
      return loadGeneralFormat(filePath);
    }
    else if (fileFormatEnum.equals(FileFormatEnum.PPM)) {
      return loadPPM(filePath);
    }
    else {
      throw new FileFormatNotSupportedException("Unsupported File format");
    }
  }

  static RgbImeModel loadPPM(String filePath) throws IOException {
    Scanner sc = new Scanner(new FileInputStream(filePath));
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token = sc.next();
    if (!token.equals("P3")) {
      throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        int rgb = (red << 16) | (green << 8) | blue;
        image.setRGB(i, j, rgb);
      }
    }
    return new RGBModel(image, maxValue);
  }
  static RgbImeModel loadGeneralFormat(String filePath) throws IOException {
    File imageFile = new File(filePath);

    BufferedImage image = ImageIO.read(imageFile);
    if (image != null) {
      return new RGBModel(image);
    } else {
      throw new IOException("Failed to load the image.");
    }
  }
  static void save(String filePath, RgbImeModel imageModel) throws IOException{
    // save the image - TODO
  }
}
