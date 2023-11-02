package controller;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import exceptions.FileFormatNotSupportedException;
import model.ImageData;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * The RgbImageFileIO class implements the ImageFileIO interface and
 * provides methods for loading and saving RGB image data.
 */
class RgbImageFileIO implements ImageFileIO {

  @Override
  public ImageData load(String filePath) throws IOException, FileFormatNotSupportedException {
    int startingIndex = filePath.lastIndexOf(".");
    String extension = filePath.substring(startingIndex == -1 ? 0 : startingIndex)
            .replace(".", "");
    try {
      FileFormatEnum fileFormatEnum = FileFormatEnum.valueOf(extension);
      switch (fileFormatEnum) {
        case png:
        case jpg:
          return loadGeneralFormat(filePath);
        case ppm:
          return loadPPM(filePath);
        default:
          throw new FileFormatNotSupportedException("Unsupported File format");
      }
    } catch (IllegalArgumentException e) {
      throw new FileFormatNotSupportedException("Unsupported File format");
    }
  }

  private ImageData loadPPM(String filePath) throws IOException {
    try {
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
      int[][][] imageData = new int[3][height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int red = sc.nextInt();
          int green = sc.nextInt();
          int blue = sc.nextInt();
          if (red < 0 || green < 0 || blue < 0
                  || red > maxValue || green > maxValue || blue > maxValue) {
            throw new IOException("Corrupted PPM file");
          }
          imageData[0][i][j] = red;
          imageData[1][i][j] = green;
          imageData[2][i][j] = blue;
        }
      }
      return new ImageData(imageData, maxValue);
    } catch (NoSuchElementException e) {
      throw new IOException("Corrupted PPM file.");
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File Not Found");
    }
  }

  private static ImageData loadGeneralFormat(String filePath) throws IOException {
    File imageFile = new File(filePath);
    try {
      BufferedImage image = ImageIO.read(imageFile);

      if (image != null) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][][] imageData = new int[3][height][width];

        ColorModel colorModel = image.getColorModel();
        if (colorModel.getColorSpace().getType() == ColorSpace.TYPE_RGB) {

          for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
              Color pixelColor = new Color(image.getRGB(j, i));

              int red = pixelColor.getRed();
              int green = pixelColor.getGreen();
              int blue = pixelColor.getBlue();
              imageData[0][i][j] = red;
              imageData[1][i][j] = green;
              imageData[2][i][j] = blue;
            }
          }

          int bitDepth = image.getColorModel().getPixelSize();
          int numOfChannels = image.getColorModel().getComponentSize().length;
          int bitDepthPerChannel = bitDepth / numOfChannels;
          return new ImageData(imageData, (int) Math.pow(2, bitDepthPerChannel) - 1);
        } else {
          throw new IOException("Invalid Image");
        }
      } else {
        throw new IOException("Invalid Image.");
      }
    } catch (IOException e) {
      throw new IOException("Invalid Image.");
    }
  }

  @Override
  public void save(String filePath, ImageData imageModel)
          throws IOException, FileFormatNotSupportedException {
    int startingIndex = filePath.lastIndexOf(".");
    String extension = filePath.substring(startingIndex == -1 ? 0 : startingIndex)
            .replace(".", "");

    try {
      FileFormatEnum fileFormatEnum = FileFormatEnum.valueOf(extension);
      switch (fileFormatEnum) {
        case png:
        case jpg:
          saveGeneralFormat(fileFormatEnum.toString().toUpperCase(), filePath, imageModel);
          break;
        case ppm:
          savePPM(filePath, imageModel);
          break;
        default:
          throw new FileFormatNotSupportedException("Unsupported File format");
      }
    } catch (IllegalArgumentException e) {
      throw new FileFormatNotSupportedException("Unsupported File format");
    }
  }

  private void savePPM(String filePath, ImageData imageData) throws IOException {
    int[][][] data = imageData.getData();
    int width = data[0][0].length;
    int height = data[0].length;

    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
    writer.write("P3\n");
    writer.write(width + " " + height + "\n");
    writer.write(imageData.getMaxValue() + "\n");
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = data[0][y][x];
        int green = data[1][y][x];
        int blue = data[2][y][x];
        writer.write(red + "\n" + green + "\n" + blue + "\n");
      }
    }
    writer.close();
  }

  private void saveGeneralFormat(String imageFormat, String destinationPath, ImageData imageData)
          throws IOException {
    int[][][] pixelValues = imageData.getData();
    int width = pixelValues[0][0].length;
    int height = pixelValues[0].length;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = pixelValues[0][y][x] << 16 | pixelValues[1][y][x] << 8 | pixelValues[2][y][x];
        image.setRGB(x, y, rgb);
      }
    }
    File outputFile = new File(destinationPath);
    ImageIO.write(image, imageFormat, outputFile);
  }
}
