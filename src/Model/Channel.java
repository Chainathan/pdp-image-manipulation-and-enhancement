package Model;

public class Channel implements ChannelModel {
  private int[][] channelValues;

  public Channel(){
    channelValues = new int[0][0];
  }
  public Channel(int height, int width){
    channelValues = new int[height][width];
  }
  public Channel(int[][] channelValues) {
    checkRectangularArray(channelValues);
    this.channelValues = new int[channelValues.length][channelValues[0].length];
    for (int i=0;i<channelValues.length;i++){
      System.arraycopy(channelValues[i], 0, this.channelValues[i], 0, channelValues[0].length);
    }
  }

  static void checkRectangularArray(int[][] channelValues) throws IllegalArgumentException{
    if (channelValues == null) {
      throw new IllegalArgumentException("Invalid Channel Value Matrix");
    }
    if (channelValues.length == 0){
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
    for (int i=0;i<getHeight();i++){
      System.arraycopy(channelValues[i], 0, newValues[i], 0, newValues[0].length);
    }
    return newValues;
  }

  //Create a Function for flip and buffer.
  @Override
  public ChannelModel getHorizontalFlipChannel() {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[getHeight() - 1 - y][x] = channelValues[y][x];
      }
    }
    return new Channel(newValues);
  }

  @Override
  public ChannelModel getVerticalFlipChannel() {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[y][getWidth() - 1 - x] = channelValues[y][x];
      }
    }
    return new Channel(newValues);
  }

  @Override
  public ChannelModel addBuffer(int buffer) {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[x][y] = channelValues[x][y]+buffer;
      }
    }
    return new Channel(newValues);
  }

  @Override
  public ChannelModel applyConvolution(double[][] kernel) throws IllegalArgumentException{
    //Check if kernel is odd dimension .
    isKernelValid(kernel);
    int kernelHeight = kernel.length;
    int kernelWidth = kernel[0].length;
    int kernelWidthRadius = kernelWidth/2;
    int kernelHeightRadius = kernelHeight/2;
    int[][] newValues = new int[getHeight()-kernelHeight+1][getWidth()-kernelWidth+1];

    for (int y = kernelHeightRadius; y < getHeight() - kernelHeightRadius; y++) {
      for (int x = kernelWidthRadius; x < getWidth() - kernelWidthRadius; x++) {
        int pixel = 0;

        for (int ky = 0; ky < kernelHeight; ky++) {
          for (int kx = 0; kx < kernelWidth; kx++) {
            pixel += (int) (channelValues[y + ky - kernelHeightRadius][x + kx - kernelWidthRadius]
                    *kernel[kx][ky]);
          }
        }
        newValues[y-kernelHeightRadius][x-kernelWidthRadius] = pixel;
      }
    }
    return new Channel(newValues);
  }

  private void isKernelValid(double[][] kernel) throws IllegalArgumentException{
    if(kernel.length%2==0 || kernel[0].length%2==0){
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
  public int getValue(int x, int y) {
    return channelValues[x][y];
  }
}
