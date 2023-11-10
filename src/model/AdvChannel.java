package model;

import java.util.Arrays;
import java.util.stream.DoubleStream;

class AdvChannel extends Channel implements AdvChannelModel{
  AdvChannel(double[][] channel) {
    super(channel);
  }
  AdvChannel() {
    super();
  }
  @Override
  public AdvChannelModel applyCompression(double compressionRatio) {
    return new AdvChannel();
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

  private double[] transformAdv(double[] s, int l){
    l = nextPowerOf2(l);
    double[] newS = Arrays.copyOf(s, l);
    int m = l;
    while (m>1){
      double[] temp = transform(Arrays.copyOf(newS,m));
      System.arraycopy(temp,0,newS,0,m);
      m = m/2;
    }
    return newS;
  }
  private double[] haarTransform(double[] s, double compressionRatio){
    int l = (int) (s.length * compressionRatio/100);
    double[] temp = transformAdv(s,l);
    double[] res = Arrays.copyOf(s,s.length);
    System.arraycopy(temp,0,res,0,l);
    System.arraycopy(s,l+1,res,l+1,s.length-1-l);
    return res;
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
  private double[] inverseAdv(double[] s, int l){
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
    int maxPixel = 0;
    double maxValue = pixelFreq[0];

    for (int i = 1; i < pixelFreq.length; i++) {
      if (pixelFreq[i] > maxValue) {
        maxValue = pixelFreq[i];
        maxPixel = i;
      }
    }
    return maxPixel;
  }

  @Override
  public AdvChannelModel adjustLevels(int b, int m, int w) throws IllegalArgumentException {
    int height = getHeight();
    int width = getWidth();
    if(b<0 || m < 0 || w<0
            || b > m || m > w || w> width){
      throw new IllegalArgumentException("Invalid arguments");
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
    return new AdvChannel(leveled);
  }

  @Override
  public AdvChannelModel trimVertical(int start, int end) throws IllegalArgumentException {
    int height = getHeight();
    int width = getWidth();
    if(start > width || end < 0 || end > width || start < end){
      throw new IllegalArgumentException("Invalid arguments");
    }
    double[][] trimmed = new double[height][end-start];
    for(int i=0;i<height;i++){
      for(int j=start;j<end;j++){
        trimmed[i][j] = getValue(i,j);
      }
    }
    return new AdvChannel(trimmed);
  }

  @Override
  public AdvChannelModel overlapOnBase(AdvChannelModel otherChannel, int start) throws IllegalArgumentException{
    int height = getHeight();
    int width = getWidth();
    if(start < 0 || start > width || otherChannel==null){
      throw new IllegalArgumentException("Invalid arguments");
    }
    int minWidth = Math.min(width, otherChannel.getWidth());

    double[][] overlappedChannel = getChannelValues();
    for(int i=0;i<height;i++){
      for(int j=start;j<minWidth;j++){
        overlappedChannel[i][j] = otherChannel.getValue(i,j);
      }
    }
    return new AdvChannel(overlappedChannel);
  }
}
