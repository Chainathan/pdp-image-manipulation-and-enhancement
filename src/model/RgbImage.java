package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.DoubleStream;

import controller.ComponentEnum;
import controller.ImageGraphics;

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
   *                                  mismatched dimensions or if maxPixelValue is negative.
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

  public RgbImageModel createInstance(ChannelModel... channelModels)
          throws IllegalArgumentException {
    if (channelModels.length != 3) {
      throw new IllegalArgumentException("Invalid channels to create rgb image model");
    }
    return new RgbImage(channelModels[0], channelModels[1], channelModels[2], maxPixelValue);
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
        TriFunction<RgbImage, Integer, Integer, Integer> funIntensity = (img, i, j)
                -> (red.getValue(i, j) + green.getValue(i, j) + blue.getValue(i, j)) / 3;
        return applyFunctionToChannels(funIntensity);
      case VALUE:
        TriFunction<RgbImage, Integer, Integer, Integer> funValue = (img, i, j)
                -> Math.max(Math.max(img.red.getValue(i, j), green.getValue(i, j)),
                blue.getValue(i, j));
        return applyFunctionToChannels(funValue);
      default:
        throw new IllegalArgumentException("Invalid component to visualize");
    }
  }

  private RgbImageModel applyFunctionToChannels(
          TriFunction<RgbImage, Integer, Integer, Integer> fun) {

    int[][] values = new int[red.getHeight()][red.getWidth()];

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

    int[][] newRed = new int[red.getHeight()][red.getWidth()];
    int[][] newGreen = new int[red.getHeight()][red.getWidth()];
    int[][] newBlue = new int[red.getHeight()][red.getWidth()];

    for (int i = 0; i < red.getHeight(); i++) {
      for (int j = 0; j < red.getWidth(); j++) {
        int redValue = red.getValue(i, j);
        int greenValue = green.getValue(i, j);
        int blueValue = blue.getValue(i, j);

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
    int[][][] data = {red.getChannelValues(),
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
    if (compressionRatio < 0 || compressionRatio > 100) {
      throw new IllegalArgumentException("Invalid percentage value for compression ratio");
    }
    double[][] redTransformed = applyHaarTransform(applyPadding(red.getChannelValues()));
    double[][] greenTransformed = applyHaarTransform(applyPadding(green.getChannelValues()));
    double[][] blueTransformed = applyHaarTransform(applyPadding(blue.getChannelValues()));
    double threshold = getThreshold(compressionRatio, redTransformed,
            greenTransformed, blueTransformed);
    applyThreshold(redTransformed, threshold);
    applyThreshold(greenTransformed, threshold);
    applyThreshold(blueTransformed, threshold);
    double[][] redInverse = applyUnpad(applyHaarInverse(redTransformed),
            red.getHeight(), red.getWidth());
    double[][] greenInverse = applyUnpad(applyHaarInverse(greenTransformed),
            green.getHeight(), green.getWidth());
    double[][] blueInverse = applyUnpad(applyHaarInverse(blueTransformed),
            blue.getHeight(), blue.getWidth());
    ChannelModel compressedRed = transformAndCreateChannel(red, redInverse);
    ChannelModel compressedGreen = transformAndCreateChannel(green, greenInverse);
    ChannelModel compressedBlue = transformAndCreateChannel(blue, blueInverse);

    return createInstance(compressedRed, compressedGreen, compressedBlue);
  }

  private ChannelModel transformAndCreateChannel(ChannelModel c, double[][] array) {
    return c.createInstance(Arrays.stream(array)
            .map(row -> Arrays.stream(row)
                    .mapToInt(this::roundAndClipPixelValue).toArray())
            .toArray(int[][]::new));
  }

  private int roundAndClipPixelValue(double value) {
    int i = (int) Math.round(value);
    i = Math.max(0, i);
    i = Math.min(255, i);
    return i;
  }

  private double getThreshold(double compressionRatio, double[][] redTransformed,
                              double[][] greenTransformed, double[][] blueTransformed) {
    double[] flatRed = getFlatArray(redTransformed);
    double[] flatGreen = getFlatArray(greenTransformed);
    double[] flatBlue = getFlatArray(blueTransformed);
    double[] channelList = getFlatArray(flatRed, flatGreen, flatBlue);
    double[] absChannelList = Arrays.stream(channelList)
            .map(Math::abs)
            .toArray();
    double[] uniqueChannelList = getUniqueValues(absChannelList);
    int compressValue = (int) Math.round((compressionRatio
            * uniqueChannelList.length) / 100);
    Arrays.sort(uniqueChannelList);
    double nthSmallest = uniqueChannelList[compressValue - 1];
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

  private int[][] applyPadding(int[][] originalChannel) {
    int originalChannelHeight = originalChannel.length;
    int originalChannelWidth = originalChannel[0].length;

    int newSize = Math.max(originalChannelHeight, originalChannelWidth);
    int paddedSize = nextPowerOf2(newSize);

    int[][] paddedChannel = new int[paddedSize][paddedSize];

    for (int i = 0; i < originalChannelHeight; i++) {
      paddedChannel[i] = Arrays.copyOf(originalChannel[i], paddedSize);
    }

    return paddedChannel;
  }

  private int nextPowerOf2(int number) {
    if (number <= 0) {
      return 1;
    }

    number--;
    number |= number >> 1;
    number |= number >> 2;
    number |= number >> 4;
    number |= number >> 8;
    number |= number >> 16;
    number++;

    return number;
  }


  private double[][] applyHaarTransform(int[][] originalChannel) {
    double[][] channel = Arrays.stream(originalChannel)
            .map(row -> Arrays.stream(row).asDoubleStream().toArray())
            .toArray(double[][]::new);
    int c = channel.length;
    while (c > 1) {
      // Transform Rows
      for (int i = 0; i < c; i++) {
        double[] row = new double[c];
        for (int j = 0; j < c; j++) {
          row[j] = channel[i][j];
        }
        row = transform(row);
        for (int j = 0; j < c; j++) {
          channel[i][j] = row[j];
        }
      }

      // Transform Columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = channel[i][j];
        }
        column = transform(column);
        for (int i = 0; i < c; i++) {
          channel[i][j] = column[i];
        }
      }

      c = c / 2;
    }

    return channel;
  }


  private double[] transform(double[] s) {
    double[] avg = new double[s.length / 2];
    double[] diff = new double[s.length / 2];

    for (int i = 0; i < s.length; i = i + 2) {
      double a = s[i];
      double b = s[i + 1];
      avg[i / 2] = (a + b) / Math.sqrt(2);
      diff[i / 2] = (a - b) / Math.sqrt(2);
    }

    return DoubleStream.concat(Arrays.stream(avg), Arrays.stream(diff)).toArray();
  }

  private double[][] applyHaarInverse(double[][] channel) {
    int c = 2;
    while (c <= channel.length) {

      // Transform Columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = channel[i][j];
        }
        column = inverse(column);
        for (int i = 0; i < c; i++) {
          channel[i][j] = column[i];
        }
      }

      // Transform Rows
      for (int i = 0; i < c; i++) {
        double[] row = new double[c];
        for (int j = 0; j < c; j++) {
          row[j] = channel[i][j];
        }
        row = inverse(row);
        for (int j = 0; j < c; j++) {
          channel[i][j] = row[j];
        }
      }

      c = c * 2;
    }
    return channel;
  }

  private double[] inverse(double[] s) {
    double[] avg = new double[s.length / 2];
    double[] diff = new double[s.length / 2];

    for (int i = 0; i < s.length / 2; i++) {
      double a = s[i];
      double b = s[i + s.length / 2];
      avg[i] = (a + b) / Math.sqrt(2);
      diff[i] = (a - b) / Math.sqrt(2);
    }

    double[] res = new double[s.length];
    for (int i = 0; i < s.length / 2; i++) {
      res[i * 2] = avg[i];
      res[i * 2 + 1] = diff[i];
    }
    return res;
  }

  private double[][] applyUnpad(double[][] paddedChannel, int originalHeight, int originalWidth)
          throws IllegalArgumentException {
    if (originalHeight < 0 || originalWidth < 0) {
      throw new IllegalArgumentException("Height or width cannot be negative");
    }
    double[][] image = new double[originalHeight][originalWidth];
    for (int i = 0; i < originalHeight; i++) {
      for (int j = 0; j < originalWidth; j++) {
        image[i][j] = paddedChannel[i][j];
      }
    }
    return image;
  }

  private void applyThreshold(double[][] transform, double threshold) {
    for (int i = 0; i < transform.length; i++) {
      for (int j = 0; j < transform[0].length; j++) {
        if (Math.abs(transform[i][j]) <= threshold) {
          transform[i][j] = 0;
        }
      }
    }
  }

  // Advanced Features

  @Override
  public RgbImageModel correctColor() {
    int redMax = red.getMaxFreqPixel();
    int greenMax = green.getMaxFreqPixel();
    int blueMax = blue.getMaxFreqPixel();

    int avgMaxFrqPixel = Math.round((float) (redMax + greenMax + blueMax) / 3);
    return this.createInstance(
            red.addBuffer(avgMaxFrqPixel - redMax, maxPixelValue),
            green.addBuffer(avgMaxFrqPixel - greenMax, maxPixelValue),
            blue.addBuffer(avgMaxFrqPixel - blueMax, maxPixelValue));
  }

  @Override
  public RgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException {
    if (b < 0 || m < 0 || w < 0
            || b >= m || m >= w || w > 255) {
      throw new IllegalArgumentException("Invalid arguments for adjust level");
    }
    return this.createInstance(
            red.adjustLevels(b, m, w),
            green.adjustLevels(b, m, w),
            blue.adjustLevels(b, m, w));
  }

  @Override
  public RgbImageModel cropVertical(double start, double end) throws IllegalArgumentException {
    if (start < 0 || start > 100 || end < 0 || end > 100) {
      throw new IllegalArgumentException("Invalid start/end percentage for trimming");
    }
    int startWidth = (int) Math.round(red.getWidth() * start / 100);
    int endWidth = (int) Math.round(red.getWidth() * end / 100);
    return this.createInstance(
            red.cropVertical(startWidth, endWidth),
            green.cropVertical(startWidth, endWidth),
            blue.cropVertical(startWidth, endWidth)
    );
  }

  @Override
  public RgbImageModel overlapOnBase(RgbImageModel otherImage, double start)
          throws IllegalArgumentException {
    if (start < 0 || start > 100 || otherImage == null) {
      throw new IllegalArgumentException("Invalid start percentage for Overlap");
    }
    int startWidth = (int) Math.round(red.getWidth() * start / 100);

    int[][][] imageData = otherImage.getImageData().getData();
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
    int[][][] data = imageData.getData();
    if (data.length != 3) {
      throw new IllegalArgumentException("Invalid Channel Value Matrix");
    }
  }

  @Override
  public RgbImageModel createHistogram(ImageGraphics graphics) {
    int[][] freqData = {
            red.getFrequencyValues(),
            green.getFrequencyValues(),
            blue.getFrequencyValues()
    };
    int maxCount = Math.max(Math.max(getMaxValue(freqData[0]),
            getMaxValue(freqData[1])), getMaxValue(freqData[2]));

    int numComponents = freqData.length;
    int binCount = freqData[0].length;
    ColorEnum[] colorList = {ColorEnum.RED, ColorEnum.GREEN, ColorEnum.BLUE};
    int histogramWidth = graphics.getImageData().getData()[0][0].length;
    int histogramHeight = graphics.getImageData().getData()[0].length;
    for (int component = 0; component < numComponents; component++) {
      graphics.setColor(colorList[component]);
      for (int i = 0; i < binCount - 1; i++) {
        int x1 = (int) (((double) i * histogramWidth / binCount));
        int x2 = (int) (((double) (i + 1) * histogramWidth / binCount));
        int y1 = (int) (((double) freqData[component][i] / maxCount) * histogramHeight);
        int y2 = (int) (((double) freqData[component][i + 1] / maxCount) * histogramHeight);
        graphics.drawLine(x1 + 1, binCount - y1 - 1, x2 + 1, binCount - y2 - 1);
      }
    }
    int[][][] data = graphics.getImageData().getData();
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
}
