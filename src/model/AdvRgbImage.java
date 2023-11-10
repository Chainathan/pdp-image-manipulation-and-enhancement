package model;

class AdvRgbImage extends RgbImage implements AdvRgbImageModel{
  AdvChannelModel red;
  AdvChannelModel green;
  AdvChannelModel blue;
  AdvRgbImage(){
    red = new AdvChannel();
    green = new AdvChannel();
    blue = new AdvChannel();
    maxPixelValue = 255;
  }
  AdvRgbImage(AdvChannelModel red, AdvChannelModel green, AdvChannelModel blue, int maxPixelValue)
          throws IllegalArgumentException {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.maxPixelValue = maxPixelValue;
  }

  @Override
  public AdvRgbImageModel createInstance(ChannelModel red,
                                         ChannelModel green,
                                         ChannelModel blue,
                                      int maxPixelValue){

    return new AdvRgbImage(
            new AdvChannel(red.getChannelValues()),
            new AdvChannel(green.getChannelValues()),
            new AdvChannel(blue.getChannelValues()),
            maxPixelValue);
  }

  @Override
  public void loadImageData(ImageData imageData) throws IllegalArgumentException {
    checkValidRgbImageData(imageData);
    red = new AdvChannel(imageData.getData()[0]);
    green = new AdvChannel(imageData.getData()[1]);
    blue = new AdvChannel(imageData.getData()[2]);
    this.maxPixelValue = imageData.getMaxValue();
  }

  @Override
  public AdvRgbImageModel applyCompression(double compressionRatio) throws IllegalArgumentException{
    if (compressionRatio < 0 || compressionRatio >100){
      throw new IllegalArgumentException("Invalid Compression Ratio");
    }
//    return new AdvRgbImage(red.applyCompression(2),
//            red.applyCompression(2),
//            red.applyCompression(2),
//            255);
    return this.createInstance(
            red.applyCompression(compressionRatio),
            red.applyCompression(compressionRatio),
            red.applyCompression(compressionRatio),
            255);
  }

  @Override
  public AdvRgbImageModel createHistogram() {
    return null;
  }

  @Override
  public AdvRgbImageModel correctColor() {
    return null;
  }

  @Override
  public AdvRgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException{
    return null;
  }

  @Override
  public AdvRgbImageModel trimVertical(double start, double end) throws IllegalArgumentException{
    return null;
  }

  @Override
  public AdvRgbImageModel overlapOnBase(AdvRgbImageModel otherImage, double start) throws IllegalArgumentException{
    return null;
  }
}
