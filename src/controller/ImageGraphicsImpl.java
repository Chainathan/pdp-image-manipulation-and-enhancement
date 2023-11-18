package controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import model.ColorEnum;
import model.ImageData;

import java.awt.Color;
import java.util.Map;

/**
 * The ImageGraphicsImpl class implements the ImageGraphics interface and provides
 * a graphics plane for drawing lines, setting colors, and obtaining image data.
 */
public class ImageGraphicsImpl implements ImageGraphics {
  private BufferedImage image;
  private Graphics2D g;
  private final Map<ColorEnum, Color> colorMap;
  private Color penColor;

  /**
   * Constructs an ImageGraphicsImpl instance with the specified height, width, and grid size.
   *
   * @param height   The height of the graphics plane.
   * @param width    The width of the graphics plane.
   * @param gridSize The size of the grid to be drawn on the graphics plane.
   * @throws IllegalArgumentException If the height/width is non-positive or gridSize is negative.
   */
  public ImageGraphicsImpl(int height, int width, int gridSize) throws IllegalArgumentException {
    if (height <= 0 || width <= 0 || gridSize < 0) {
      throw new IllegalArgumentException("Invalid arguments for the Image Graphics");
    }
    this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    g = image.createGraphics();
    g.setBackground(Color.WHITE);
    g.clearRect(0, 0, width, height);

    drawGrid(gridSize);

    penColor = Color.BLACK;

    colorMap = new HashMap<>();
    colorMap.put(ColorEnum.RED, Color.RED);
    colorMap.put(ColorEnum.GREEN, Color.GREEN);
    colorMap.put(ColorEnum.BLUE, Color.BLUE);
  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
    g.setColor(penColor);
    g.drawLine(x1, y1, x2, y2);
  }

  @Override
  public ImageData getImageData() {
    return RgbImageFileIO.convertBuffImgToImgData(image);
  }

  @Override
  public void setColor(ColorEnum color) throws IllegalArgumentException {
    if (!colorMap.containsKey(color)) {
      throw new IllegalArgumentException("Unsupported color for the graphics plane");
    }
    penColor = colorMap.get(color);
  }

  private void drawGrid(int gridSize) {

    g.setColor(Color.LIGHT_GRAY);
    int height = image.getHeight();
    int width = image.getWidth();

    for (int i = 1; i <= gridSize; i++) {
      int x = i * width / gridSize;
      g.drawLine(x, height, x, 0);
      int y = i * height / gridSize;
      g.drawLine(0, y, width, y);
    }
  }
}
