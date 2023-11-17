package model;


/**
 * The Channel class implements the ChannelModel interface
 * and represents a channel with pixel data.
 */
class Channel implements ChannelModel {
  final int[][] channelValues;

  @Override
  public ChannelModel createInstance(int[][] channelValues){
    return new Channel(channelValues);
  }
  /**
   * Constructs an empty Channel with zero height and width.
   */
  Channel() {
    channelValues = new int[0][0];
  }

  /**
   * Constructs a Channel with the specified height and width.
   *
   * @param height The height of the channel.
   * @param width  The width of the channel.
   * @throws IllegalArgumentException If the provided height or width is negative.
   */
  Channel(int height, int width) throws IllegalArgumentException {
    if (height < 0 || width < 0) {
      throw new IllegalArgumentException("Invalid height and width for Channel");
    }
    channelValues = new int[height][width];
  }

  /**
   * Constructs a Channel with the provided channel values.
   *
   * @param channelValues The two-dimensional array representing the channel values.
   * @throws IllegalArgumentException If the provided array is not rectangular.
   */
  Channel(int[][] channelValues) throws IllegalArgumentException {
    checkRectangularArray(channelValues);
    if (channelValues.length == 0) {
      this.channelValues = new int[0][0];
    } else {
      this.channelValues = new int[channelValues.length][channelValues[0].length];
      for (int i = 0; i < channelValues.length; i++) {
        System.arraycopy(channelValues[i], 0, this.channelValues[i], 0, channelValues[0].length);
      }
    }
  }

  /**
   * Checks if the provided two-dimensional array is rectangular,
   * meaning that all rows have the same length.
   *
   * @param channelValues The two-dimensional array to be checked for rectangularity.
   * @throws IllegalArgumentException If the array is not rectangular or if it is null.
   */
  static void checkRectangularArray(int[][] channelValues) throws IllegalArgumentException {
    if (channelValues == null) {
      throw new IllegalArgumentException("Invalid Channel Value Matrix");
    }
    if (channelValues.length == 0) {
      return;
    }
    int numRows = channelValues.length;
    int numCols = channelValues[0].length;

    for (int i = 1; i < numRows; i++) {
      if (channelValues[i].length != numCols) {
        throw new IllegalArgumentException("Invalid Channel Value Matrix");
      }
    }
  }

  @Override
  public int[][] getChannelValues() {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int i = 0; i < getHeight(); i++) {
      System.arraycopy(channelValues[i], 0, newValues[i], 0, newValues[0].length);
    }
    return newValues;
  }

  @Override
  public ChannelModel getVerticalFlipChannel() {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[getHeight() - 1 - y][x] = channelValues[y][x];
      }
    }
    return this.createInstance(newValues);
  }

  @Override
  public ChannelModel getHorizontalFlipChannel() {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[y][getWidth() - 1 - x] = channelValues[y][x];
      }
    }
    return this.createInstance(newValues);
  }

  @Override
  public ChannelModel addBuffer(int buffer, int maxPixelValue) {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[y][x] = Math.max(Math.min(channelValues[y][x] + buffer, maxPixelValue), 0);
      }
    }
    return this.createInstance(newValues);
  }

  @Override
  public ChannelModel applyConvolution(double[][] kernel, int maxPixelValue)
          throws IllegalArgumentException {
    isKernelValid(kernel);
    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    int kernelWidthRadius = kernelWidth / 2;
    int kernelHeightRadius = kernelHeight / 2;
    int[][] newValues = new int[getHeight()][getWidth()];

    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        int pixel = 0;

        for (int ky = 0; ky < kernelHeight; ky++) {
          for (int kx = 0; kx < kernelWidth; kx++) {
            int inputX = x - kernelWidthRadius + kx;
            int inputY = y - kernelHeightRadius + ky;
            if (inputX >= 0 && inputX < getWidth() && inputY >= 0 && inputY < getHeight()) {
              pixel += (int) Math.round(channelValues[inputY][inputX] * kernel[ky][kx]);
            }
          }
        }
        newValues[y][x] = Math.max(Math.min(pixel, maxPixelValue), 0);
      }
    }
    return this.createInstance(newValues);
  }

  private void isKernelValid(double[][] kernel) throws IllegalArgumentException {
    if (kernel.length % 2 == 0 || kernel[0].length % 2 == 0) {
      throw new IllegalArgumentException("Invalid Kernel");
    }
  }

  @Override
  public int getHeight() {
    return channelValues.length;
  }

  @Override
  public int getWidth() {
    return getHeight() == 0 ? 0 : channelValues[0].length;
  }

  @Override
  public int getValue(int y, int x) throws IllegalArgumentException {
    if (y >= getHeight() || y < 0 || x >= getWidth() || x < 0) {
      throw new IllegalArgumentException("Invalid pixel values for the image.");
    }
    return channelValues[y][x];
  }

  private int[] getFrequencyOfPixels(){
    int[] pixelFreq = new int[256];
    int height = getHeight();
    int width = getWidth();

    for(int i=0;i<height;i++) {
      for (int j = 0; j < width; j++) {
        int x = getValue(i,j);
        pixelFreq[x]++;
      }
    }
    return pixelFreq;
  }

  @Override
  public int[] getFrequencyValues() {
    return getFrequencyOfPixels();
  }

  @Override
  public int getMaxFreqPixel() {
    int[] pixelFreq = getFrequencyOfPixels();
    int maxPixel = 10;
    int maxFreq = pixelFreq[maxPixel];

    for (int i = maxPixel; i < pixelFreq.length-10; i++) {
      if (pixelFreq[i] > maxFreq) {
        maxFreq = pixelFreq[i];
        maxPixel = i;
      }
    }
    return maxPixel;
  }

  @Override
  public ChannelModel adjustLevels(int b, int m, int w){
    int height = getHeight();
    int width = getWidth();
    double b2 = Math.pow(b,2);
    double m2 = Math.pow(m,2);
    double w2 = Math.pow(w,2);
    double A = (b2*(m-w))-(b*(m2-w2))+(w*m2 - m*w2);
    double Aa = (127)*b + (128*w - 255 * m);
    double Ab = (-127)*b2 + (255*m2 - 128*w2);
    double Ac = b2 *(255 * m - 128*w) - (b * (255 * m2 - 128 * w2));
    double Qa = Aa/A ;
    double Qb = Ab/A;
    double Qc = Ac/A;
    int[][] leveled = new int[height][width];
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        double x = getValue(i,j);
        double y = Qa * Math.pow(x,2) + Qb * x + Qc;
        y = Math.max(y,0);
        y = Math.min(y,255);
        leveled[i][j] = (int) Math.round(y);
      }
    }
    return this.createInstance(leveled);
  }

  @Override
  public ChannelModel cropVertical(int start, int end) throws IllegalArgumentException {
    int height = getHeight();
    int width = getWidth();
    if(start > end || start < 0 || end > width){
      throw new IllegalArgumentException("Invalid arguments for vtrim channel");
    }
    int[][] cropped = new int[height][end-start];
    for(int i=0;i<height;i++){
      for(int j=start;j<end;j++){
        cropped[i][j-start] = getValue(i,j);
      }
    }
    return this.createInstance(cropped);
  }

  @Override
  public ChannelModel overlapOnBase(ChannelModel otherChannel, int start)
          throws IllegalArgumentException{
    int height = getHeight();
    int width = getWidth();
    if(start < 0 || start > width || otherChannel==null){
      throw new IllegalArgumentException("Invalid arguments for Overlap Channel");
    }
    int minWidth = Math.min(width, start+otherChannel.getWidth());
    int minHeight = Math.min(height, otherChannel.getHeight());

    int[][] overlappedChannel = getChannelValues();
    for(int i=0;i<minHeight;i++){
      for(int j=start;j<minWidth;j++){
        overlappedChannel[i][j] = otherChannel.getValue(i,j-start);
      }
    }
    return this.createInstance(overlappedChannel);
  }
}
