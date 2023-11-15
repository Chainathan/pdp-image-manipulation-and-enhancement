package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import controller.ImageGraphics;
import controller.ImageGraphicsImpl;
import controller.RgbImageFileIO;

/**
 * The RgbImage class implements the RgbImageModel interface and
 * represents an RGB image composed of red, green, and blue channels.
 */
class RgbImage implements RgbImageModel {
  ChannelModel red;
  ChannelModel green;
  ChannelModel blue;
  int maxPixelValue;

  /**
   * Constructs an empty RgbImage with default values for red, green,
   * and blue channels, and a maximum pixel value of 255.
   */
  RgbImage() {
    red = new Channel();
    green = new Channel();
    blue = new Channel();
    maxPixelValue = 255;
  }
  /**
   * Constructs an RgbImage with the specified red, green,
   * and blue channels, and a maximum pixel value.
   *
   * @param red           The red channel of the image.
   * @param green         The green channel of the image.
   * @param blue          The blue channel of the image.
   * @param maxPixelValue The maximum pixel value for the image.
   * @throws IllegalArgumentException If the provided channels have
   *               mismatched dimensions or if maxPixelValue is negative.
   */
  RgbImage(ChannelModel red, ChannelModel green, ChannelModel blue, int maxPixelValue)
          throws IllegalArgumentException {
    if (red.getHeight() != green.getHeight() || red.getHeight() != blue.getHeight()
            || red.getWidth() != green.getWidth() || red.getWidth() != blue.getWidth()
            || maxPixelValue < 0) {
      throw new IllegalArgumentException("Invalid Channel Size");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.maxPixelValue = maxPixelValue;
  }

  @Override
  public RgbImageModel createInstance(ChannelModel...channelModels)
          throws IllegalArgumentException{
    if (channelModels.length!=3){
      throw new IllegalArgumentException("Invalid channels to create image model");
    }
    return new RgbImage(channelModels[0],channelModels[1],channelModels[2],maxPixelValue);
  }
  @Override
  public RgbImageModel visualizeComponent(ComponentEnum componentEnum)
          throws IllegalArgumentException {
    switch (componentEnum) {
      case RED:
        return createInstance(
                red,
                new Channel(green.getHeight(), green.getWidth()),
                new Channel(blue.getHeight(), blue.getWidth()));
      case GREEN:
        return createInstance(
                new Channel(red.getHeight(), red.getWidth()),
                green,
                new Channel(blue.getHeight(), blue.getWidth()));
      case BLUE:
        return createInstance(
                new Channel(red.getHeight(), red.getWidth()),
                new Channel(green.getHeight(), green.getWidth()),
                blue);
      case LUMA:
        return getLumaComponent();
      case INTENSITY:
        TriFunction<RgbImage, Integer, Integer, Double> funIntensity = (img, x, y)
            -> (red.getValue(x, y) + green.getValue(x, y) + blue.getValue(x, y)) / 3;
        return applyFunctionToChannels(funIntensity);
      case VALUE:
        TriFunction<RgbImage, Integer, Integer, Double> funValue = (img, x, y)
            -> Math.max(Math.max(img.red.getValue(x, y), green.getValue(x, y)),
                blue.getValue(x, y));
        return applyFunctionToChannels(funValue);
      default:
        throw new IllegalArgumentException("Invalid component to visualize");
    }
  }

  private RgbImageModel applyFunctionToChannels(TriFunction<RgbImage, Integer, Integer, Double> fun) {

    double[][] values = new double[red.getHeight()][red.getWidth()];

    for (int i = 0; i < red.getHeight(); i++) {
      for (int j = 0; j < red.getWidth(); j++) {
        values[i][j] = Math.max(Math.min(fun.apply(this, i, j), maxPixelValue), 0);
      }
    }
    return createInstance(new Channel(values),
            new Channel(values),
            new Channel(values));
  }

  private RgbImageModel getLumaComponent() throws IllegalArgumentException {
    double[][] buffer = {
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
            {0.2126, 0.7152, 0.0722},
    };
    return applyTone(buffer);
  }

  @Override
  public RgbImageModel sepia() throws IllegalArgumentException {
    double[][] buffer = {
            {0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168},
            {0.272, 0.534, 0.131}
    };
    return applyTone(buffer);
  }

  @Override
  public RgbImageModel horizontalFlip() {
    return createInstance(
            red.getHorizontalFlipChannel(),
            green.getHorizontalFlipChannel(),
            blue.getHorizontalFlipChannel());
  }

  @Override
  public RgbImageModel verticalFlip() {
    return createInstance(
            red.getVerticalFlipChannel(),
            green.getVerticalFlipChannel(),
            blue.getVerticalFlipChannel());
  }

  @Override
  public RgbImageModel brighten(int increment) {
    return createInstance(
            red.addBuffer(increment, maxPixelValue),
            green.addBuffer(increment, maxPixelValue),
            blue.addBuffer(increment, maxPixelValue));
  }

  @Override
  public RgbImageModel blur() {
    double[][] kernel = new double[][]{
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
    return applyFilter(kernel);
  }

  @Override
  public RgbImageModel sharpen() {
    double[][] kernel = new double[][]{
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
    return applyFilter(kernel);
  }

  private RgbImageModel applyFilter(double[][] kernel) throws IllegalArgumentException {
    return createInstance(
            red.applyConvolution(kernel, maxPixelValue),
            green.applyConvolution(kernel, maxPixelValue),
            blue.applyConvolution(kernel, maxPixelValue));
  }

  private RgbImageModel applyTone(double[][] buffer) throws IllegalArgumentException {
    if (buffer.length != 3) {
      throw new IllegalArgumentException("Invalid tone buffer");
    }
    for (double[] doubles : buffer) {
      if (doubles.length != 3) {
        throw new IllegalArgumentException("Invalid tone buffer");
      }
    }
    double[][] newRed = new double[red.getHeight()][red.getWidth()];
    double[][] newGreen = new double[red.getHeight()][red.getWidth()];
    double[][] newBlue = new double[red.getHeight()][red.getWidth()];

    for (int i = 0; i < red.getHeight(); i++) {
      for (int j = 0; j < red.getWidth(); j++) {
        double redValue = red.getValue(i, j);
        double greenValue = green.getValue(i, j);
        double blueValue = blue.getValue(i, j);

        newRed[i][j] = (int) Math.round(
                buffer[0][0] * redValue + buffer[0][1] * greenValue + buffer[0][2] * blueValue
        );
        newRed[i][j] = Math.max(Math.min(newRed[i][j], maxPixelValue), 0);
        newGreen[i][j] = (int) Math.round(
                buffer[1][0] * redValue + buffer[1][1] * greenValue + buffer[1][2] * blueValue
        );
        newGreen[i][j] = Math.max(Math.min(newGreen[i][j], maxPixelValue), 0);
        newBlue[i][j] = (int) Math.round(
                buffer[2][0] * redValue + buffer[2][1] * greenValue + buffer[2][2] * blueValue
        );
        newBlue[i][j] = Math.max(Math.min(newBlue[i][j], maxPixelValue), 0);

      }
    }
    return createInstance(new Channel(newRed), new Channel(newGreen), new Channel(newBlue));
  }

  @Override
  public ImageData getImageData() {
    double[][][] data = {red.getChannelValues(),
            green.getChannelValues(),
            blue.getChannelValues()
    };
    return new ImageData(data, maxPixelValue);
  }

  @Override
  public void loadImageData(ImageData imageData) throws IllegalArgumentException {
    checkValidRgbImageData(imageData);
    red = new Channel(imageData.getData()[0]);
    green = new Channel(imageData.getData()[1]);
    blue = new Channel(imageData.getData()[2]);
    this.maxPixelValue = imageData.getMaxValue();
  }

  @Override
  public RgbImageModel applyCompression(double compressionRatio) throws IllegalArgumentException {
//    return this.createInstance(
//            red.applyCompression(compressionRatio),
//            green.applyCompression(compressionRatio),
//            blue.applyCompression(compressionRatio));
    ChannelModel redTransformed = red.applyPadding().applyHaarTransform();
    ChannelModel greenTransformed = green.applyPadding().applyHaarTransform();
    ChannelModel blueTransformed = blue.applyPadding().applyHaarTransform();
    double threshold = getThreshold(compressionRatio, redTransformed, greenTransformed, blueTransformed);
    ChannelModel redInverse = redTransformed.applyThreshold(threshold)
            .applyHaarInverse()
            .applyUnpad(red.getHeight(), red.getWidth());
    ChannelModel greenInverse = greenTransformed.applyThreshold(threshold)
            .applyHaarInverse()
            .applyUnpad(green.getHeight(), green.getWidth());
    ChannelModel blueInverse = blueTransformed.applyThreshold(threshold)
            .applyHaarInverse()
            .applyUnpad(blue.getHeight(), blue.getWidth());
    return createInstance(redInverse, greenInverse, blueInverse);
  }

  private double getThreshold(double compressionRatio, ChannelModel redTransformed,
                              ChannelModel greenTransformed, ChannelModel blueTransformed) {
    double[] flatRed = getFlatArray(redTransformed.getChannelValues());
    double[] flatGreen = getFlatArray(greenTransformed.getChannelValues());
    double[] flatBlue = getFlatArray(blueTransformed.getChannelValues());
    double[] channelList = getFlatArray(flatRed,flatGreen,flatBlue);
    double[] absChannelList = Arrays.stream(channelList)
            .map(Math::abs)
            .toArray();
    double[] uniqueChannelList = getUniqueValues(absChannelList);
    int compressValue = (int)Math.round((compressionRatio
            *uniqueChannelList.length)/100);
    Arrays.sort(uniqueChannelList);
    double nthSmallest = uniqueChannelList[compressValue-1];
    return nthSmallest;
  }

  private double[] getFlatArray(double[]... arrays) {
    return Arrays.stream(arrays)
            .flatMapToDouble(Arrays::stream)
            .toArray();
  }

  private double[] getUniqueValues(double[] channels) {

    Set<Double> uniqueElements = new HashSet<>();

    for (double element : channels) {
      uniqueElements.add(element);
    }

    return uniqueElements.stream().mapToDouble(Double::doubleValue).toArray();
  }

  // Advanced Features

  @Override
  public RgbImageModel correctColor() {
    int redMax = red.getMaxFreqPixel();
    int greenMax = green.getMaxFreqPixel();
    int blueMax = blue.getMaxFreqPixel();

    int avgMaxFrqPixel = Math.round((float) (redMax+greenMax+blueMax)/3);
    return this.createInstance(
            red.addBuffer(avgMaxFrqPixel-redMax,maxPixelValue),
            green.addBuffer(avgMaxFrqPixel-greenMax,maxPixelValue),
            blue.addBuffer(avgMaxFrqPixel-blueMax,maxPixelValue));
  }

  @Override
  public RgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException {
    return this.createInstance(
            red.adjustLevels(b,m,w),
            green.adjustLevels(b,m,w),
            blue.adjustLevels(b,m,w));
  }

  @Override
  public RgbImageModel cropVertical(double start, double end) throws IllegalArgumentException {
    if (start < 0 || start > 100 || end < 0 || end > 100 ) {
      throw new IllegalArgumentException("Invalid start/end percentage for trimming");
    }
    int startWidth = (int) Math.round(red.getWidth()*start / 100);
    int endWidth = (int) Math.round(red.getWidth()*end / 100);
    return this.createInstance(
            red.cropVertical(startWidth,endWidth),
            green.cropVertical(startWidth,endWidth),
            blue.cropVertical(startWidth,endWidth)
    );
  }

  @Override
  public RgbImageModel overlapOnBase(RgbImageModel otherImage, double start)
          throws IllegalArgumentException {
    if (start < 0 || start > 100) {
      throw new IllegalArgumentException("Invalid start percentage for Overlap");
    }
    int startWidth = (int) Math.round(red.getWidth()*start / 100);

    double[][][] imageData = otherImage.getImageData().getData();
    ChannelModel otherRed = new Channel(imageData[0]);
    ChannelModel otherGreen = new Channel(imageData[1]);
    ChannelModel otherBlue = new Channel(imageData[2]);

    return this.createInstance(
            red.overlapOnBase(otherRed, startWidth),
            green.overlapOnBase(otherGreen, startWidth),
            blue.overlapOnBase(otherBlue, startWidth)
    );
  }

  void checkValidRgbImageData(ImageData imageData) throws IllegalArgumentException {
    double[][][] data = imageData.getData();
    if (data.length != 3) {
      throw new IllegalArgumentException("Invalid Channel Value Matrix");
    }
  }

  @Override
  public RgbImageModel createHistogram() {
    int[][] freqData = {
            red.getFrequencyValues(),
            green.getFrequencyValues(),
            blue.getFrequencyValues()
    };
    int maxCount = Math.max(Math.max(getMaxValue(freqData[0]),
            getMaxValue(freqData[1])), getMaxValue(freqData[2]));

    int numComponents = freqData.length;
    int binCount = freqData[0].length;

    ImageGraphics graphics = new ImageGraphicsImpl();
    graphics.initBlankPlane(binCount,binCount);
    graphics.drawGrid(20);
    ColorEnum[] colorList = {ColorEnum.RED, ColorEnum.GREEN, ColorEnum.BLUE};

    for (int component = 0; component < numComponents; component++) {
      graphics.setColor(colorList[component]);
      for (int i = 0; i < binCount - 1; i++) {
        int x1 = i;
        int x2 = i + 1;
        int y1 = (int) (((double) freqData[component][i] / maxCount) * binCount);
        int y2 = (int) (((double) freqData[component][i+1] / maxCount) * binCount);
        graphics.drawLine(x1, binCount-y1-1, x2, binCount-y2-1);
      }
    }
    double[][][] data = graphics.getImageData().getData();
    ChannelModel newRed = red.createInstance(data[0]);
    ChannelModel newGreen = green.createInstance(data[1]);
    ChannelModel newBlue = blue.createInstance(data[2]);

    return this.createInstance(newRed, newGreen, newBlue);
  }
  private static int getMaxValue(int[] array) {
    int max = array[0];
    for (int value : array) {
      if (value > max) {
        max = value;
      }
    }
    return max;
  }
//  private static BufferedImage createHistogramLineGraphs(int[][] histograms) {
//    int numComponents = histograms.length;
//    int binCount = histograms[0].length;
//
//    BufferedImage histogramImages = new BufferedImage(binCount, binCount,
//            BufferedImage.TYPE_INT_RGB);
//
//    Graphics2D g = histogramImages.createGraphics();
//    g.setBackground(Color.WHITE);
//    g.clearRect(0, 0, binCount, binCount);
//    //int gridSize = 20;
//    //Color gridColor = Color.GRAY;
//
//    // Draw the grid
//    //g.setColor(gridColor);
//
//    Color[] colorList = {Color.RED, Color.GREEN, Color.BLUE};
////    int red = new Color(255, 0, 0).getRGB();
////    int green = new Color(0, 255, 0).getRGB();
////    int blue = new Color(0, 0, 255).getRGB();
////    int[] colorList = {red,green,blue};
//    int scale = 1;
//    for (int component = 0; component < numComponents; component++) {
//      g.setColor(colorList[component]);
//      for (int i = 0; i < binCount - 1; i++) {
//        int x1 = i;
//        int x2 = i + 1;
//        int y1 = 256 - histograms[component][i] * scale;
//        int y2 = 256 - histograms[component][i+1] * scale;
//        g.drawLine(x1, y1, x2, y2);
////        g.fillRect(i,histograms[component][i],1,1);
//      }
//    }
//    //g.dispose();
//    return histogramImages;
//  }
}
