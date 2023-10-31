package DAO;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferDouble;
import java.awt.image.DataBufferFloat;
import java.awt.image.DataBufferInt;
import java.awt.image.DataBufferShort;
import java.awt.image.DataBufferUShort;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import Exceptions.FileFormatNotSupportedException;
import Model.FileFormatEnum;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ImageDataDAO implements DataDAO {

  public ImageData load(String filePath) throws IOException {
    String extension = filePath.substring(filePath.lastIndexOf('.')).replace(".", "");
    ;
    //Check if extension is present in Enum.
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
    int[][][] imageData = new int[3][height][width];
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
    return new ImageData(imageData, maxValue); //new RGBModel(image, maxValue);
  }

  private static ImageData loadGeneralFormat(String filePath) throws IOException {
    File imageFile = new File(filePath);

    BufferedImage image = ImageIO.read(imageFile);
    int width = image.getWidth();
    int height = image.getHeight();
    int[][][] imageData = new int[3][height][width];

    if (image != null) {
//      return new RGBModel(image);
//      int numColorComponents = colorModel.getNumColorComponents();
//      int[] bitsPerComponent = colorModel.getComponentSize();
//
//      System.out.println("Number of color components: " + numColorComponents);
//      for (int i = 0; i < bitsPerComponent.length; i++) {
//        System.out.println("Bit depth of component " + i + ": " + bitsPerComponent[i]);
//      }
//      if (colorModel.getColorSpace().getType() == ColorSpace.TYPE_RGB) {
//        String[] componentNames = new String[0];// = colorModel.getComponentNames();
//
//        System.out.println("Component Names:");
//        for (int i = 0; i < componentNames.length; i++) {
//          System.out.println("Component " + i + ": " + componentNames[i]);
//        }
//      } else {
//        System.out.println("This image is not in the RGB color space.");
//      }
      /* Note - if the color space is not RGB, we ignore the precision of bit depth
      * of the components since manually converting high depth non-rgb values requires
      * third party libraries or complex calculations depending on the source color space type.
      * So we take advantage of the BufferedImage's getRGB() method that can convert other color
      * space values to rgb and give us directly (con - the returned values are only upto 8 bit
      * per component)
      * */
      ColorModel colorModel = image.getColorModel();
      if (colorModel.getColorSpace().getType() == ColorSpace.TYPE_RGB) {
//        int redBitDepth = colorModel.getComponentSize(0);
//        int greenBitDepth = colorModel.getComponentSize(1);
//        int blueBitDepth = colorModel.getComponentSize(2);

        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            Color pixelColor = new Color(image.getRGB(j, i));

            int red = pixelColor.getRed();
            int green = pixelColor.getGreen();
            int blue = pixelColor.getBlue();
            //image.setRGB(i, j, rgb);
            imageData[0][i][j] = red;
            imageData[1][i][j] = green;
            imageData[2][i][j] = blue;
          }
        }

        int bitDepth = image.getColorModel().getPixelSize();
        int numOfChannels = image.getColorModel().getComponentSize().length;
        int bitDepthPerChannel = bitDepth / numOfChannels;
        return new ImageData(imageData, (int) Math.pow(2, bitDepthPerChannel) - 1);
      }
      else {

      }
      throw new IOException("Failed to load the image. Unsupported Color Model");
    }
//      else {
//        //BufferedImage image = ImageIO.read(imageFile);
//        Raster raster = image.getRaster();
//        DataBuffer dataBuffer = raster.getDataBuffer();
//        int bitDepth = DataBuffer.getDataTypeSize(dataBuffer.getDataType());
//        int numChannels = raster.getNumBands();
//        int bitDepthPerChannel = bitDepth / numChannels;
//
//        int dataType = dataBuffer.getDataType();
//        int[][] bankData = null;
//
//        switch (dataType) {
//          *//*case DataBuffer.TYPE_BYTE:
//            // Convert to DataBufferByte (8-bit signed byte)
//            bankData = ((DataBufferByte) dataBuffer).getBankData();
//            break;
//
//          case DataBuffer.TYPE_USHORT:
//            // Convert to DataBufferUShort (16-bit unsigned short)
//            bankData = ((DataBufferUShort) dataBuffer).getBankData();
//            break;
//
//          case DataBuffer.TYPE_SHORT:
//            // Convert to DataBufferShort (16-bit signed short)
//            bankData = ((DataBufferShort) dataBuffer).getBankData();
//            break;
//
//          case DataBuffer.TYPE_INT:
//            // Convert to DataBufferInt (32-bit signed integer)
//            bankData = ((DataBufferInt) dataBuffer).getBankData();
//            break;
//
//          case DataBuffer.TYPE_FLOAT:
//            // Convert to DataBufferFloat (32-bit floating-point)
//            float[][] floatArray = ((DataBufferFloat) dataBuffer).getBankData();
//            bankData = new int[width][height];
//            for (int i = 0; i < floatArray.length; i++) {
//              for (int j = 0; j < floatArray[0].length; j++) {
//                bankData[i][j] = Math.round(floatArray[i][j]);
//              }
//            }
//            break;
//
//          case DataBuffer.TYPE_DOUBLE:
//            // Convert to DataBufferDouble (64-bit floating-point)
//            bankData = ((DataBufferDouble) dataBuffer).getBankData();
//            break;
//          default:
//            throw new IOException("Error reading the Image");
//        }
//
//        int[][] pixelData = ((DataBufferUShort)dataBuffer).getBankData();
//
//        // Access the pixel values directly
//        for (int y = 0; y < height; y++) {
//          for (int x = 0; x < width; x++) {
//            int red = pixelData[0][y * width + x];
//            int green = pixelData[1][y * width + x];
//            int blue = pixelData[2][y * width + x];
//            // Work with the original red, green, and blue values, which may have the full bit depth
//          }
//        }
//      }
    else {
      throw new IOException("Failed to load the image.");
    }
  }

  public void save(String filePath, ImageData imageModel) throws IOException, FileFormatNotSupportedException {
    String extension = filePath.substring(filePath.lastIndexOf('.')).replace(".", "");

    //Check if extension is present in Enum.
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
      ;
    } catch (IllegalArgumentException e) {
      throw new FileFormatNotSupportedException("Unsupported File format");
    }
  }

  public static void savePPM(String filePath, ImageData imageData) throws IOException {
    int width = imageData.getData()[0].length;
    int height = imageData.getData().length;

    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
    // Write the PPM header
    writer.write("P3\n"); // P3 indicates the PPM format
    writer.write(width + " " + height + "\n"); // Image dimensions
    writer.write(imageData.getMaxValue() + "\n"); // Maximum color value

    // Write pixel values
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = imageData.getData()[0][y][x];
        int green = imageData.getData()[1][y][x];
        int blue = imageData.getData()[2][y][x];
        writer.write(red + "\n" + green + "\n" + blue + "\n");
      }
    }
  }


  public static void saveGeneralFormat(String imageFormat, String destinationPath, ImageData imageData)
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
//    System.out.println("Image saved as " + imageFormat + " : " + destinationPath);
  }
}
