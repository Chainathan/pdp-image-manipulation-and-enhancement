package model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.DoubleStream;

/**
 * The Channel class implements the ChannelModel interface
 * and represents a channel with pixel data.
 */
class Channel implements ChannelModel {
  final double[][] channelValues;

  @Override
  public ChannelModel createInstance(double[][] channelValues){
    return new Channel(channelValues);
  }
  /**
   * Constructs an empty Channel with zero height and width.
   */
  Channel() {
    channelValues = new double[0][0];
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
    channelValues = new double[height][width];
  }

  /**
   * Constructs a Channel with the provided channel values.
   *
   * @param channelValues The two-dimensional array representing the channel values.
   * @throws IllegalArgumentException If the provided array is not rectangular.
   */
  Channel(double[][] channelValues) throws IllegalArgumentException {
    checkRectangularArray(channelValues);
    if (channelValues.length == 0) {
      this.channelValues = new double[0][0];
    } else {
      this.channelValues = new double[channelValues.length][channelValues[0].length];
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
  static void checkRectangularArray(double[][] channelValues) throws IllegalArgumentException {
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
  public double[][] getChannelValues() {
    double[][] newValues = new double[getHeight()][getWidth()];
    for (int i = 0; i < getHeight(); i++) {
      System.arraycopy(channelValues[i], 0, newValues[i], 0, newValues[0].length);
    }
    return newValues;
  }

  @Override
  public ChannelModel getVerticalFlipChannel() {
    double[][] newValues = new double[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[getHeight() - 1 - y][x] = channelValues[y][x];
      }
    }
    return this.createInstance(newValues);
  }

  @Override
  public ChannelModel getHorizontalFlipChannel() {
    double[][] newValues = new double[getHeight()][getWidth()];
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        newValues[y][getWidth() - 1 - x] = channelValues[y][x];
      }
    }
    return this.createInstance(newValues);
  }

  @Override
  public ChannelModel addBuffer(int buffer, int maxPixelValue) {
    double[][] newValues = new double[getHeight()][getWidth()];
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
    double[][] newValues = new double[getHeight()][getWidth()];

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
  public double getValue(int y, int x) throws IllegalArgumentException {
    if (y >= getHeight() || y < 0 || x >= getWidth() || x < 0) {
      throw new IllegalArgumentException("Invalid pixel values for the image.");
    }
    return channelValues[y][x];
  }

  @Override
  public ChannelModel applyCompression(double compressionRatio) {
    double[][] padded = pad2DArray(getChannelValues());
    double[][] haarTransformed = haarTransform(padded);
    int nPixelsToCompress = (int)Math.round((compressionRatio
            * haarTransformed.length * haarTransformed.length)/100);
    double[][] compressed = applyCompressionRatio(haarTransformed,nPixelsToCompress);
    //Unpad method.
    //double[][] haarInverse = haarInverse(compressed);
    return createInstance(compressed);
  }

  private double[][] pad2DArray(double[][] originalChannel) {
    int originalChannelHeight = originalChannel.length;
    int originalChannelWidth = 512;//originalChannel[0].length;

    // Find the smallest power of 2 greater than or equal to the maximum of height and width
    int newSize = Math.max(originalChannelHeight, originalChannelWidth);
    int paddedSize = nextPowerOf2(newSize);
//    int paddedSize = 1;
//    while (paddedSize < newSize) {
//      paddedSize *= 2;
//    }
    // Create a new padded array with zeroes
    double[][] paddedChannel = new double[paddedSize][paddedSize];

    // Copy values from the original array to the padded array
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

  private double[][] haarTransform(double[][] X){
    int c = X.length;

    int test = 2;
    while (c > 1) {
      // Transform Rows
      for (int i = 0; i < c; i++) {
        //X[i] = transform(X[i]);
        double[] row = new double[c];
        for (int j = 0; j < c; j++) {
          row[j] = X[i][j];
        }
        row = transform(row);
        for (int j = 0; j < c; j++) {
          X[i][j] = row[j];
        }
      }

      // Transform Columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = X[i][j];
        }
        column = transform(column);
        for (int i = 0; i < c; i++) {
          X[i][j] = column[i];
        }
      }

      //c = c / 2;
      c=c/2;
      test-=1;
    }

    return X;
  }

  private double[] transform(double[] s){
    double[] avg = new double[s.length/2];
    double[] diff = new double[s.length/2];

    for (int i=0; i<s.length;i=i+2){
      double a = s[i];
      double b = s[i+1];
      avg[i/2] = (a+b) / Math.sqrt(2);
      diff[i/2] = (a-b) / Math.sqrt(2);
    }

    return DoubleStream.concat(Arrays.stream(avg), Arrays.stream(diff)).toArray();
  }

  private double[][] applyCompressionRatio(double[][] haarTransformed, int nSmallestNonZero) {
    if(nSmallestNonZero==0){
      return haarTransformed;
    }
    double[] flatMatrix = Arrays.stream(haarTransformed)
            .flatMapToDouble(Arrays::stream)
            .toArray();
    double[] absoluteMatrix = Arrays.stream(flatMatrix)
            .map(Math::abs)
            .toArray();
    int[] indices = findIndicesOfNSmallest(absoluteMatrix, nSmallestNonZero);
    double[] resettedMatrix = reset(flatMatrix, indices);

    // Reshape the flat array back to the matrix
    for (int i = 0; i < haarTransformed.length; i++) {
      for (int j = 0; j < haarTransformed[i].length; j++) {
        haarTransformed[i][j] = resettedMatrix[i * haarTransformed.length + j];
      }
    }
    return haarTransformed;
  }

  private int[] findIndicesOfNSmallest(double[] array, int n) {
    // Create an array of indices
    Integer[] ind = new Integer[array.length];
    for (int i = 0; i < array.length; i++) {
      ind[i] = i;
    }

    // Sort the indices based on the values in the original array
    Arrays.sort(ind, Comparator.comparingDouble(i -> array[i]));

    int[] nSmallest = new int[n];

    int count = 0;
    for (int i = 0; i < ind.length && count < n; i++) {
      int index = ind[i];
      if (array[index] != 0) {
        nSmallest[count++] = index;
      }
    }
    // If there are less than n non-zero elements, fill the remaining with -1
    while (count < n) {
      nSmallest[count++] = -1; // or any value that indicates no element found
    }
    return nSmallest;
  }

  private double[] reset(double[] flatTransform, int[] nSmallest){
    for(int i=0;i<nSmallest.length;i++){
      if(nSmallest[i]==-1){
          break;
      }
      flatTransform[nSmallest[i]]=0;
    }
    return flatTransform;
  }

//  private double[] transformAdv(double[] s){
//    int l = nextPowerOf2(s.length);
//    double[] newS = Arrays.copyOf(s, l);
//    int m = l;
//    while (m>1){
//      double[] temp = transform(Arrays.copyOf(newS,m));
//      System.arraycopy(temp,0,newS,0,m);
//      m = m/2;
//    }
//    return newS;
//  }


//
//  private double[] haarTransform(double[] s, double compressionRatio){
//    int l = (int) (s.length * compressionRatio/100);
//    double[] temp = transformAdv(s,l);
//    double[] res = Arrays.copyOf(s,s.length);
//    System.arraycopy(temp,0,res,0,l);
//    System.arraycopy(s,l+1,res,l+1,s.length-1-l);
//
//    int n = (int)Math.round(s.length * (compressionRatio/100));
//    int[] indices = findIndicesOfNSmallest(s,n);
//    double[] compressed = applyLossycompression(s,indices);
//    return res;
//  }

  private double[][] haarInverse(double[][] X){
    int c = 2;
    int test = 2;
    while (c <= X.length) {

      // Transform Columns
      for (int j = 0; j < c; j++) {
        double[] column = new double[c];
        for (int i = 0; i < c; i++) {
          column[i] = X[i][j];
        }
        column = inverse(column);
        for (int i = 0; i < c; i++) {
          X[i][j] = column[i];
        }
      }

      // Transform Rows
      for (int i = 0; i < c; i++) {
        //X[i] = transform(X[i]);
        double[] row = new double[c];
        for (int j = 0; j < c; j++) {
          row[j] = X[i][j];
        }
        row = inverse(row);
        for (int j = 0; j < c; j++) {
          X[i][j] = row[j];
        }
      }

      c=c*2;
      test-=1;
    }

    return X;
  }

  private double[] inverse(double[] s){
    double[] avg = new double[s.length/2];
    double[] diff = new double[s.length/2];

    for (int i=0; i<s.length/2;i++){
      double a = s[i];
      double b = s[i+s.length/2];
      avg[i] = (a+b) / Math.sqrt(2);
      diff[i] = (a-b) / Math.sqrt(2);
    }

    double[] res = new double[s.length];
    for (int i = 0; i < s.length/2; i++) {
      res[i*2] = avg[i];
      res[i*2+1] = diff[i];
    }
    return res;
  }

  private double[] inverseAdv(double[] s, int l, double compressionRatio){
    l = nextPowerOf2(l);
    s = Arrays.copyOf(s, l);
    int m =2;
    while (m<=l){
      double[] temp = inverse(Arrays.copyOf(s,m));
      System.arraycopy(temp,0,s,0,m);
      m = m*2;
    }
    return s;
  }
//  public void test(){
//    double[] t = {5,3,2,4,2,1,0,3};
//    double[] tr = transformAdv(t,t.length);
//    System.out.println(Arrays.toString(tr));
//    double[] ir = inverseAdv(tr,tr.length);
//    System.out.println(Arrays.toString(ir));
//  }

  private int[] getFrequencyOfPixels(){
    int[] pixelFreq = new int[256];
    int height = getHeight();
    int width = getWidth();

    for(int i=0;i<height;i++) {
      for (int j = 0; j < width; j++) {
        int x = (int)Math.round(getValue(i,j));
        pixelFreq[x]+=1;
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
  public ChannelModel adjustLevels(int b, int m, int w) throws IllegalArgumentException {
    int height = getHeight();
    int width = getWidth();
    if(b<0 || m < 0 || w<0
            || b > m || m > w || w> width){
      throw new IllegalArgumentException("Invalid arguments for adjust level");
    }
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
    double[][] leveled = new double[height][width];
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        double x = getValue(i,j);
        leveled[i][j] = Qa * Math.pow(x,2) + Qb * x + Qc;
      }
    }
    return this.createInstance(leveled);
  }

  @Override
  public ChannelModel cropVertical(int start, int end) throws IllegalArgumentException {
    int height = getHeight();
    int width = getWidth();
    if(start > width || end < 0 || end > width || start < end){
      throw new IllegalArgumentException("Invalid arguments for vtrim channel");
    }
    double[][] trimmed = new double[height][end-start];
    for(int i=0;i<height;i++){
      for(int j=start;j<end;j++){
        trimmed[i][j] = getValue(i,j);
      }
    }
    return this.createInstance(trimmed);
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

    double[][] overlappedChannel = getChannelValues();
    for(int i=0;i<minHeight;i++){
      for(int j=start;j<minWidth;j++){
        overlappedChannel[i][j] = otherChannel.getValue(i,j-start);
      }
    }
    return this.createInstance(overlappedChannel);
  }
}
