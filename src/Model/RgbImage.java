package Model;

import DAO.ImageData;

public class RgbImage implements RgbImageModel {

  //private List<ChannelModel> channelList;
  private ChannelModel red;
  private ChannelModel green;
  private ChannelModel blue;
  private int maxPixelValue;

  public RgbImage() {
    red = new Channel();
    green = new Channel();
    blue = new Channel();
    maxPixelValue = 255;
  }

  RgbImage(ChannelModel red, ChannelModel green, ChannelModel blue, int maxPixelValue)
          throws IllegalArgumentException {
    if (red.getHeight() != green.getHeight() || red.getHeight() != blue.getHeight()
            || red.getWidth() != green.getWidth() || red.getWidth() != blue.getWidth()) {
      throw new IllegalArgumentException("Invalid Channel Size");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.maxPixelValue = maxPixelValue;
  }

  @Override
  public RgbImageModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException {
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
        TriFunction<RgbImage, Integer, Integer, Integer> funIntensity = (img, x, y) ->
                (red.getValue(x, y)
                        + green.getValue(x, y)
                        + blue.getValue(x, y)) / 3;
        return applyFunctionToChannels(funIntensity);
      case VALUE:
        TriFunction<RgbImage, Integer, Integer, Integer> funValue = (img, x, y) ->
                Math.max(Math.max(img.red.getValue(x, y)
                                , green.getValue(x, y))
                        , blue.getValue(x, y));
        return applyFunctionToChannels(funValue);
      default:
        throw new IllegalArgumentException("Invalid component to visualize");
    }
  }

  private RgbImage applyFunctionToChannels(TriFunction<RgbImage, Integer, Integer, Integer> fun) {
    int[][] values = new int[red.getHeight()][red.getWidth()];
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
  public RgbImageModel brighten(int increment) throws IllegalArgumentException {
    if (increment < 0) {
      throw new IllegalArgumentException("Increment should be non negative");
    }
    return new RgbImage(
            red.addBuffer(increment, maxPixelValue),
            green.addBuffer(increment, maxPixelValue),
            blue.addBuffer(increment, maxPixelValue),
            maxPixelValue);
  }

  @Override
  public RgbImageModel darken(int decrement) throws IllegalArgumentException {
    if (decrement < 0) {
      throw new IllegalArgumentException("Decrement should be non negative");
    }
    return new RgbImage(
            red.addBuffer(-decrement, maxPixelValue),
            green.addBuffer(-decrement, maxPixelValue),
            blue.addBuffer(-decrement, maxPixelValue),
            maxPixelValue);
  }

  @Override
  public RgbImageModel blur() throws IllegalArgumentException {
    double[][] kernel = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };
    return new RgbImage(
            red.applyConvolution(kernel, maxPixelValue),
            green.applyConvolution(kernel, maxPixelValue),
            blue.applyConvolution(kernel, maxPixelValue),
            maxPixelValue);
  }

  @Override
  public RgbImageModel sharpen() throws IllegalArgumentException {
    double[][] kernel = {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
    };
    return new RgbImage(
            red.applyConvolution(kernel, maxPixelValue),
            green.applyConvolution(kernel, maxPixelValue),
            blue.applyConvolution(kernel, maxPixelValue),
            maxPixelValue);
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

  private RgbImageModel applyTone(double[][] buffer) throws IllegalArgumentException {
    if (buffer.length != 3 || buffer[0].length != 3) {
      throw new IllegalArgumentException("Invalid tone buffer");
    }
    int[][] newRed = new int[red.getHeight()][red.getWidth()];
    int[][] newGreen = new int[red.getHeight()][red.getWidth()];
    int[][] newBlue = new int[red.getHeight()][red.getWidth()];

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
    int[][][] data = {red.getChannelValues(),
            green.getChannelValues(),
            blue.getChannelValues()
    };
    return new ImageData(data, maxPixelValue);
  }

  @Override
  public void loadImageData(ImageData imageData) throws IllegalArgumentException {
    checkValidImageData(imageData);
    red = new Channel(imageData.getData()[0]);
    green = new Channel(imageData.getData()[1]);
    blue = new Channel(imageData.getData()[2]);
    this.maxPixelValue = imageData.getMaxValue();
  }

  private void checkValidImageData(ImageData imageData) throws IllegalArgumentException {
    int[][][] data = imageData.getData();
    if (imageData.getMaxValue() < 0) {
      throw new IllegalArgumentException("Invalid Maximum pixel value");
    }
    if (data == null || data.length != 3) {
      throw new IllegalArgumentException("Invalid Channel Value Matrix");
    }
    for (int[][] channelValues : data) {
      Channel.checkRectangularArray(channelValues);
    }
  }
}
