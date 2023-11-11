package model;

//interface AdvRgbImageModel extends RgbImageModel<AdvRgbImageModel>{
interface AdvRgbImageModel extends RgbImageModel{
  AdvRgbImageModel applyCompression(double compressionRatio) throws IllegalArgumentException;
  AdvRgbImageModel createHistogram();
  AdvRgbImageModel correctColor();
  AdvRgbImageModel adjustLevels(int b, int m, int w) throws IllegalArgumentException;
  AdvRgbImageModel trimVertical(double start, double end) throws IllegalArgumentException;
  AdvRgbImageModel overlapOnBase(AdvRgbImageModel otherImage, double start)
          throws IllegalArgumentException;
}
