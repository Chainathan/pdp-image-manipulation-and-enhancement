package DAO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import Exceptions.FileFormatNotSupportedException;
import Model.FileFormatEnum;
import java.awt.Color;

public class ImageDataDAO {

  public ImageData load(String filePath) throws IOException {
    String extension = filePath.substring(filePath.lastIndexOf('.'));
    FileFormatEnum fileFormatEnum = FileFormatEnum.valueOf(extension);
    return switch (fileFormatEnum) {
      case PNG, JPG -> loadGeneralFormat(filePath);
      case PPM -> loadPPM(filePath);
      default -> throw new FileFormatNotSupportedException("Unsupported File format");
    };
  }

  private ImageData loadPPM(String filePath) throws IOException {
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
    //BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    int[][][] imageData = new int[3][width][height];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
//        int rgb = (red << 16) | (green << 8) | blue;
        //image.setRGB(i, j, rgb);
        imageData[0][i][j] = red;
        imageData[1][i][j] = green;
        imageData[2][i][j] = blue;
      }
    }
    return new ImageData(imageData,maxValue); //new RGBModel(image, maxValue);
  }
  private static ImageData loadGeneralFormat(String filePath) throws IOException {
    File imageFile = new File(filePath);

    BufferedImage image = ImageIO.read(imageFile);
    int width = image.getWidth();
    int height = image.getHeight();
    int[][][] imageData = new int[3][width][height];
    if (image != null) {
//      return new RGBModel(image);
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          Color pixelColor = new Color(image.getRGB(i, j));

          // Extract red, green, and blue components
          int red = pixelColor.getRed();
          int green = pixelColor.getGreen();
          int blue = pixelColor.getBlue();
          //image.setRGB(i, j, rgb);
          imageData[0][i][j] = red;
          imageData[1][i][j] = green;
          imageData[2][i][j] = blue;
        }
      }
      return new ImageData(imageData,(int) Math.pow(2,image.getColorModel().getPixelSize())-1);
    } else {
      throw new IOException("Failed to load the image.");
    }
  }
  public void save(String filePath, ImageData imageModel) throws IOException{
    // save the image - TODO
  }
}
