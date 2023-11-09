package model;

/**
 * The RgbImage class implements the RgbImageModel interface and
 * represents an RGB image composed of red, green, and blue channels.
 */
class RgbImage implements RgbImageModel {
  private ChannelModel red;
  private ChannelModel green;
  private ChannelModel blue;
  private int maxPixelValue;

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
  public RgbImageModel visualizeComponent(ComponentEnum componentEnum)
          throws IllegalArgumentException {
    switch (componentEnum) {
      case RED:
        return new RgbImage(
                red,
                new Channel(green.getHeight(), green.getWidth()),
                new Channel(blue.getHeight(), blue.getWidth()),
                maxPixelValue);
      case GREEN:
        return new RgbImage(
                new Channel(red.getHeight(), red.getWidth()),
                green,
                new Channel(blue.getHeight(), blue.getWidth()),
                maxPixelValue);
      case BLUE:
        return new RgbImage(
                new Channel(red.getHeight(), red.getWidth()),
                new Channel(green.getHeight(), green.getWidth()),
                blue,
                maxPixelValue);
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

  private RgbImage applyFunctionToChannels(TriFunction<RgbImage, Integer, Integer, Double> fun) {

    double[][] values = new double[red.getHeight()][red.getWidth()];

    for (int i = 0; i < red.getHeight(); i++) {
      for (int j = 0; j < red.getWidth(); j++) {
        values[i][j] = Math.max(Math.min(fun.apply(this, i, j), maxPixelValue), 0);
      }
    }
    return new RgbImage(new Channel(values),
            new Channel(values),
            new Channel(values),
            maxPixelValue);
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
  public RgbImageModel horizontalFlip() {
    return new RgbImage(
            red.getHorizontalFlipChannel(),
            green.getHorizontalFlipChannel(),
            blue.getHorizontalFlipChannel(),
            maxPixelValue);
  }

  @Override
  public RgbImageModel verticalFlip() {
    return new RgbImage(
            red.getVerticalFlipChannel(),
            green.getVerticalFlipChannel(),
            blue.getVerticalFlipChannel(),
            maxPixelValue);
  }

  @Override
  public RgbImageModel brighten(int increment) {
    return new RgbImage(
            red.addBuffer(increment, maxPixelValue),
            green.addBuffer(increment, maxPixelValue),
            blue.addBuffer(increment, maxPixelValue),
            maxPixelValue);
  }

  @Override
  public RgbImageModel applyFilter(double[][] kernel) throws IllegalArgumentException {
    return new RgbImage(
            red.applyConvolution(kernel, maxPixelValue),
            green.applyConvolution(kernel, maxPixelValue),
            blue.applyConvolution(kernel, maxPixelValue),
            maxPixelValue);
  }

  @Override
  public RgbImageModel applyTone(double[][] buffer) throws IllegalArgumentException {
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

    return new RgbImage(
            new Channel(newRed),
            new Channel(newGreen),
            new Channel(newBlue),
            maxPixelValue);
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

  private void checkValidRgbImageData(ImageData imageData) throws IllegalArgumentException {
    double[][][] data = imageData.getData();
    if (data.length != 3) {
      throw new IllegalArgumentException("Invalid Channel Value Matrix");
    }
  }
}
