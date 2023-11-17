package controller;

import model.ColorEnum;
import model.ImageData;

// for working with 2d place graphics operations in a single channel
/**
 * The ImageGraphics interface defines methods for drawing on an image and retrieving image data.
 * Implementations of this interface should provide functionality for drawing lines, setting colors,
 * and obtaining the image data.
 */
public interface ImageGraphics {

  /**
   * Draws a line on the image between the specified points.
   *
   * @param x1 The x-coordinate of the starting point.
   * @param y1 The y-coordinate of the starting point.
   * @param x2 The x-coordinate of the ending point.
   * @param y2 The y-coordinate of the ending point.
   * @throws IllegalArgumentException If the provided coordinates are invalid or out of bounds.
   */
  void drawLine(int x1, int y1, int x2, int y2);

  /**
   * Gets the image data representing the current state of the drawn image.
   *
   * @return An ImageData object containing the raw data of the drawn image.
   */
  ImageData getImageData();

  /**
   * Sets the drawing color to the specified color.
   *
   * @param color The ColorEnum representing the color to be used for drawing.
   * @throws IllegalArgumentException If the color is not supported by the image graphics plane.
   */
  void setColor(ColorEnum color) throws IllegalArgumentException;
}
