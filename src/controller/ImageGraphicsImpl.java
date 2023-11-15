package controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import model.ColorEnum;
import model.ImageData;

public class ImageGraphicsImpl implements ImageGraphics{
  private BufferedImage image;
  private Graphics2D g;
  private final Map<ColorEnum,Color> colorMap;
  private Color penColor;
  public ImageGraphicsImpl() {
    this.image = new BufferedImage(256,256,BufferedImage.TYPE_INT_RGB);
    g = image.createGraphics();
    penColor = Color.BLACK;
    colorMap = new HashMap<>();
    colorMap.put(ColorEnum.RED,Color.RED);
    colorMap.put(ColorEnum.GREEN,Color.GREEN);
    colorMap.put(ColorEnum.BLUE,Color.BLUE);
  }

  @Override
  public void initBlankPlane(int height, int width) throws IllegalArgumentException {
    if (height <=0 || width <=0){
      throw new IllegalArgumentException("Height and Width of the Graphics place must be positive");
    }
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    g = image.createGraphics();
    g.setBackground(Color.WHITE);
    g.clearRect(0, 0, width, height);
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
    if (!colorMap.containsKey(color)){
      throw new IllegalArgumentException("Unsupported color for the graphics plane");
    }
    penColor = colorMap.get(color);
  }

  @Override
  public void drawGrid(int gridSize) {
    g.setColor(Color.LIGHT_GRAY);
    int height = image.getHeight();
    int width = image.getWidth();

    for (int i = 1; i <= gridSize; i++) {
      int x = i*width/gridSize;
      g.drawLine(x,height,x,0);
      int y = i*height/gridSize;
      g.drawLine(0,y,width,y);
    }
    // Draw horizontal grid lines
//    for (int i = 0; i <= gridSize; i++) {
//      int yGrid = i * (height / gridSize);
//      g.drawLine(0, yGrid, width, yGrid);
//    }
//
//    // Draw vertical grid lines
//    for (int i = 0; i <= gridSize; i++) {
//      int xGrid = i * (width / gridSize);
//      g.drawLine(xGrid, 0, xGrid, height);
//    }
  }
}
