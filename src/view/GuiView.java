package view;

import controller.Features;
import model.ImageData;

public interface GuiView {

  void togglePreview(boolean isEnabled);
  void toggleFeatures();
  void showWindow();
  void showDiscardConfirmation();
  void showLoadMenu();
  void showSaveMenu();
  void displayImage(ImageData imageData);
  void displayHistogram(ImageData imageData);
  void addFeatures(Features features);
  void showCompressMenu();
  void showLvlAdjMenu();
  void toggleSplit(boolean suportSplit);
  void displayError(String message);
//  void setSplitLabel(String message);
//  void setSplitInput(String message);
}
