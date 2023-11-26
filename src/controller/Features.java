package controller;

public interface Features {
  //Add brighten
  void apply();
  void cancel();
  void noOperation();
  void preview(int splitP);
  void exitProgram();
  void loadImage(String filePath);
  void handleLoadButton();
  void saveImage(String filePath);
  void handleSaveButton();
  void blur();
  void sharpen();
  void sepia();
  void greyscale();
  void red();
  void green();
  void blue();
  void horizontal();
  void vertical();
  void handleCompress();
  void compress(double compressRatio);  //double or int?
//  void compress(double compressRatio);
  void handleLevelsAdjust();
  void levelsAdjust(int b, int m, int w);
//  void levelsAdjust(int b, int m, int w);
  void colorCorrect();
//  void handleSplitToggle(boolean supportSplit);
//  void applySplit(String splitP);
//  void applySplit(double splitP);
}
