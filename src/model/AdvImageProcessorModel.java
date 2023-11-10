package model;

public interface AdvImageProcessorModel extends ImageProcessorModel{
  void compress(String imageName, String destImageName, double compressionRatio)
          throws IllegalArgumentException;
  void createHistogram(String imageName, String destImageName)
          throws IllegalArgumentException;
  void correctColor(String imageName, String destImageName)
          throws IllegalArgumentException;
  void adjustLevels(String imageName, String destImageName, int b, int m, int w)
          throws IllegalArgumentException;
  void trimVertical(String imageName, String destImageName,
             double start, double end) throws IllegalArgumentException;
  void overlapOnBase(String imageNameOriginal, String imageNameAddon,
                     String destImageName, double start) throws IllegalArgumentException;
  void removeImage(String imageName) throws IllegalArgumentException;
}
