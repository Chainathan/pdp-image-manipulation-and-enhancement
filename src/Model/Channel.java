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

  @Override
  public ChannelModel getHorizontalFlipChannel() {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[getWidth() - 1 - x][y] = channelValues[x][y];
      }
    }
    return new Channel(newValues);
  }

  @Override
  public ChannelModel getVerticalFlipChannel() {
    int[][] newValues = new int[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[x][getHeight() - 1 - y] = channelValues[x][y];
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
  public ChannelModel applyConvolution(double[][] kernel) {
    /*int[][] newValues = new int[getHeight()][getWidth()];
    int kernelSize = kernel.length;
    int kernelRadius = kernelSize / 2;

    for (int y = kernelRadius; y < getHeight() - kernelRadius; y++) {
      for (int x = kernelRadius; x < getWidth() - kernelRadius; x++) {
        int pixel = 0;

        for (int ky = 0; ky < kernelSize; ky++) {
          for (int kx = 0; kx < kernelSize; kx++) {
            pixel += channelValues[x + kx - kernelRadius][y + ky - kernelRadius];
          }
        }
        newValues[x][y] = pixel;
      }
    }
    return new Channel(newValues);*/
    return null;
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
