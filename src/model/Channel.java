package model;

import java.util.Arrays;
import java.util.stream.DoubleStream;

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

//  @Override
//  public ChannelModel applyCompression(double compressionRatio) {
//    //Check if compressionRatio value is more than 100.
//    double[][] padded = pad2DArray(getChannelValues());
//    double[][] haarTransformed = haarTransform(padded);
//
//    double[][] compressed = applyCompressionRatio(haarTransformed,compressionRatio);
//    double[][] haarInverse = haarInverse(compressed);
//    double[][] unpadded = unpad2DArray(haarInverse, getWidth(),getHeight());
//    return createInstance(unpadded);
//  }

//  @Override
//  public ChannelModel applyPadding() {
//    int[][] originalChannel = getChannelValues();
//    int originalChannelHeight = originalChannel.length;
//    int originalChannelWidth = originalChannel[0].length;
//
//    int newSize = Math.max(originalChannelHeight, originalChannelWidth);
//    int paddedSize = nextPowerOf2(newSize);
//
//    int[][] paddedChannel = new int[paddedSize][paddedSize];
//
//    for (int i = 0; i < originalChannelHeight; i++) {
//      paddedChannel[i] = Arrays.copyOf(originalChannel[i], paddedSize);
//    }
//
//    return createInstance(paddedChannel);
//  }
//
//  private int nextPowerOf2(int number) {
//    if (number <= 0) {
//      return 1;
//    }
//
//    number--;
//    number |= number >> 1;
//    number |= number >> 2;
//    number |= number >> 4;
//    number |= number >> 8;
//    number |= number >> 16;
//    number++;
//
//    return number;
//  }
//
//
//  @Override
//  public double[][] applyHaarTransform() {
//    int[][] original = getChannelValues();
//    double[][] channel = Arrays.stream(original)
//            .map(row -> Arrays.stream(row).asDoubleStream().toArray())
//            .toArray(double[][]::new);
//    int c = channel.length;
//    while (c > 1) {
//      // Transform Rows
//      for (int i = 0; i < c; i++) {
//        //X[i] = transform(X[i]);
//        double[] row = new double[c];
//        for (int j = 0; j < c; j++) {
//          row[j] = channel[i][j];
//        }
//        row = transform(row);
//        for (int j = 0; j < c; j++) {
//          channel[i][j] = row[j];
//        }
//      }
//
//      // Transform Columns
//      for (int j = 0; j < c; j++) {
//        double[] column = new double[c];
//        for (int i = 0; i < c; i++) {
//          column[i] = channel[i][j];
//        }
//        column = transform(column);
//        for (int i = 0; i < c; i++) {
//          channel[i][j] = column[i];
//        }
//      }
//
//      c=c/2;
//    }
//
//    return channel;
//  }
//
//
//  private double[] transform(double[] s){
//    double[] avg = new double[s.length/2];
//    double[] diff = new double[s.length/2];
//
//    for (int i=0; i<s.length;i=i+2){
//      double a = s[i];
//      double b = s[i+1];
//      avg[i/2] = (a+b) / Math.sqrt(2);
//      diff[i/2] = (a-b) / Math.sqrt(2);
//    }
//
//    return DoubleStream.concat(Arrays.stream(avg), Arrays.stream(diff)).toArray();
//  }
//
//  @Override
//  public ChannelModel applyHaarInverse(double[][] haarTransformed) {
//    double[][] channel = haarTransformed;
//    int c = 2;
//    //int test = 2;
//    while (c <= channel.length) {
//
//      // Transform Columns
//      for (int j = 0; j < c; j++) {
//        double[] column = new double[c];
//        for (int i = 0; i < c; i++) {
//          column[i] = channel[i][j];
//        }
//        column = inverse(column);
//        for (int i = 0; i < c; i++) {
//          channel[i][j] = column[i];
//        }
//      }
//
//      // Transform Rows
//      for (int i = 0; i < c; i++) {
//        //X[i] = transform(X[i]);
//        double[] row = new double[c];
//        for (int j = 0; j < c; j++) {
//          row[j] = channel[i][j];
//        }
//        row = inverse(row);
//        for (int j = 0; j < c; j++) {
//          channel[i][j] = row[j];
//        }
//      }
//
//      c=c*2;
//      //test-=1;
//    }
//    return createInstance(channel);
//  }
//
//  @Override
//  public ChannelModel applyUnpad(int originalHeight, int originalWidth) throws IllegalArgumentException{
//    if(originalHeight < 0 || originalWidth < 0){
//      throw new IllegalArgumentException("Height or width cannot be negative");
//    }
//    double[][] paddedImage = getChannelValues();
//    double[][] image = new double[originalHeight][originalWidth];
//    for(int i=0;i<originalHeight;i++){
//      for(int j=0;j<originalWidth;j++){
//        image[i][j] = paddedImage[i][j];
//      }
//    }
//    return createInstance(image);
//  }

//  private double[][] haarTransform(){
//    double[][] X = getChannelValues();
//    int c = X.length;
//
//    //int test = 2;
//    while (c > 1) {
//      // Transform Rows
//      for (int i = 0; i < c; i++) {
//        //X[i] = transform(X[i]);
//        double[] row = new double[c];
//        for (int j = 0; j < c; j++) {
//          row[j] = X[i][j];
//        }
//        row = transform(row);
//        for (int j = 0; j < c; j++) {
//          X[i][j] = row[j];
//        }
//      }
//
//      // Transform Columns
//      for (int j = 0; j < c; j++) {
//        double[] column = new double[c];
//        for (int i = 0; i < c; i++) {
//          column[i] = X[i][j];
//        }
//        column = transform(column);
//        for (int i = 0; i < c; i++) {
//          X[i][j] = column[i];
//        }
//      }
//
//      c=c/2;
//      //test-=1;
//    }
//
//    return X;
//  }


//  @Override
//  public ChannelModel applyThreshold(double threshold){
//    double[][] channel = getChannelValues();
//    int height = getHeight();
//    int width = getWidth();
//    for(int i=0;i<height;i++){
//      for(int j=0;j<width;j++){
//        if(Math.abs(channel[i][j]) <= threshold){
//          channel[i][j]=0;
//        }
//      }
//    }
//    return createInstance(channel);
//  }


//  private double[] reset(double[] array, double threshold){
//    for(int i=0;i<array.length;i++){
//      if(Math.abs(array[i]) <= threshold){
//        array[i]=0;
//      }
//    }
//    return array;
//  }


//  private int[] findIndicesOfNSmallest(double[] array, int n) {
//    // Create an array of indices
//    Integer[] ind = new Integer[array.length];
//    for (int i = 0; i < array.length; i++) {
//      ind[i] = i;
//    }
//
//    // Sort the indices based on the values in the original array
//    Arrays.sort(ind, Comparator.comparingDouble(i -> array[i]));
//
//    int[] nSmallest = new int[n];
//
//    int count = 0;
//    for (int i = 0; i < ind.length && count < n; i++) {
//      int index = ind[i];
//      if (array[index] != 0) {
//        nSmallest[count++] = index;
//      }
//    }
//    // If there are less than n non-zero elements, fill the remaining with -1
//    while (count < n) {
//      nSmallest[count++] = -1; // or any value that indicates no element found
//    }
//    return nSmallest;
//  }
//
//  private double[] reset(double[] flatTransform, int[] nSmallest){
//    for(int i=0;i<nSmallest.length;i++){
//      if(nSmallest[i]==-1){
//          break;
//      }
//      flatTransform[nSmallest[i]]=0;
//    }
//    return flatTransform;
//  }

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

//  private double[][] haarInverse(double[][] X){
//    int c = 2;
//    //int test = 2;
//    while (c <= X.length) {
//
//      // Transform Columns
//      for (int j = 0; j < c; j++) {
//        double[] column = new double[c];
//        for (int i = 0; i < c; i++) {
//          column[i] = X[i][j];
//        }
//        column = inverse(column);
//        for (int i = 0; i < c; i++) {
//          X[i][j] = column[i];
//        }
//      }
//
//      // Transform Rows
//      for (int i = 0; i < c; i++) {
//        //X[i] = transform(X[i]);
//        double[] row = new double[c];
//        for (int j = 0; j < c; j++) {
//          row[j] = X[i][j];
//        }
//        row = inverse(row);
//        for (int j = 0; j < c; j++) {
//          X[i][j] = row[j];
//        }
//      }
//
//      c=c*2;
//      //test-=1;
//    }
//
//    return X;
//  }

//  private double[] inverse(double[] s){
//    double[] avg = new double[s.length/2];
//    double[] diff = new double[s.length/2];
//
//    for (int i=0; i<s.length/2;i++){
//      double a = s[i];
//      double b = s[i+s.length/2];
//      avg[i] = (a+b) / Math.sqrt(2);
//      diff[i] = (a-b) / Math.sqrt(2);
//    }
//
//    double[] res = new double[s.length];
//    for (int i = 0; i < s.length/2; i++) {
//      res[i*2] = avg[i];
//      res[i*2+1] = diff[i];
//    }
//    return res;
//  }

//  private double[][] unpad2DArray(double[][] paddedArray){
//    int height = getHeight();
//    int width = getWidth();
//    double[][] image = new double[height][width];
//    for(int i=0;i<height;i++){
//      for(int j=0;j<width;j++){
//        image[i][j] = paddedArray[i][j];
//      }
//    }
//    return image;
//  }

//  private double[] inverseAdv(double[] s, int l, double compressionRatio){
//    l = nextPowerOf2(l);
//    s = Arrays.copyOf(s, l);
//    int m =2;
//    while (m<=l){
//      double[] temp = inverse(Arrays.copyOf(s,m));
//      System.arraycopy(temp,0,s,0,m);
//      m = m*2;
//    }
//    return s;
//  }
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
        int x = (int)Math.round(getValue(i,j));//Math.round(getValue(i,j));
        pixelFreq[x]++;
      }
    }
    return pixelFreq;
  }

//  private int[] normalizeFreq(int[] freq) {
//    int minValue = Integer.MAX_VALUE;
//    int maxValue = Integer.MIN_VALUE;
//
//    for (int value : freq) {
//      minValue = Math.min(minValue, value);
//      maxValue = Math.max(maxValue, value);
//    }
//    int range = maxValue - minValue;
//    int[] normalizedFreq = new int[freq.length];
//
//    for (int i = 0; i < freq.length; i++) {
//      //normalizedFreq[i] = (int)(((freq[i] - minValue) / range) * 255.0);
//      normalizedFreq[i] = (int)((double)freq[i]/maxValue * 256.0);
//    }
//    return normalizedFreq;
//  }

  @Override
  public int[] getFrequencyValues() {
//    return normalizeFreq(getFrequencyOfPixels());
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
//      System.out.println("},");
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
