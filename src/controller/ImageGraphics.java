package controller;

import model.ColorEnum;
import model.ImageData;

// for working with 2d place graphics operations in a single channel
public interface ImageGraphics {
  void drawLine(int x1, int y1, int x2, int y2) throws IllegalArgumentException;
  ImageData getImageData();
  void setColor(ColorEnum color);
}
