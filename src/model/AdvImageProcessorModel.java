package model;

public interface AdvImageProcessorModel extends ImageProcessorModel{
  void compress(String imageName, String destImageName, double compressionRatio);
  void createHistogram(String imageName, String destImageName);
  void correctColor(String imageName, String destImageName);
  void adjustLevels(String imageName, String destImageName, int b, int m, int w);
  void trimVertical(String imageName, String destImageName,
             double start, double end);
  void overlapOnBase(String imageNameOriginal, String imageNameAddon,
                     String destImageName, double start);
  void removeImage(String imageName);
}
