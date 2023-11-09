package model;

interface AdvRgbImageModel extends RgbImageModel{
  AdvRgbImageModel applyCompression(double compressionRatio);
  AdvRgbImageModel createHistogram();
  AdvRgbImageModel correctColor();
  AdvRgbImageModel adjustLevels(int b, int m, int w);
  AdvRgbImageModel trimVertical(double start, double end);
  AdvRgbImageModel overlapOnBase(AdvRgbImageModel otherImage, double start);
}
