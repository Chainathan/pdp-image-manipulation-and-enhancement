package controller;

import org.junit.Test;

import model.ColorEnum;
import model.ImageData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

/**
 * Test class for ImageGraphicsImpl class.
 */
public class ImageGraphicsImplTest {
  private ImageGraphics g;

  @Test
  public void testConstructor() {
    try {
      g = new ImageGraphicsImpl(5, 5, 0);
      int[][][] data = new int[3][5][5];
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 5; j++) {
          for (int k = 0; k < 5; k++) {
            data[i][j][k] = 255;
          }
        }
      }
      ImageData exp = new ImageData(data, 255);
      ImageData res = g.getImageData();
      assertEquals(exp, res);
    } catch (Exception e) {
      fail("Should not have thrown error");
    }
  }

  @Test
  public void initInvalidHeight() {
    assertThrows(IllegalArgumentException.class, () -> new ImageGraphicsImpl(-50, 55, 0));
  }

  @Test
  public void initInvalidWidth() {
    assertThrows(IllegalArgumentException.class, () -> new ImageGraphicsImpl(50, -55, 0));
  }

  @Test
  public void initInvalidHeightZero() {
    assertThrows(IllegalArgumentException.class, () -> new ImageGraphicsImpl(0, 55, 0));
  }

  @Test
  public void initInvalidWidthZero() {
    assertThrows(IllegalArgumentException.class, () -> new ImageGraphicsImpl(50, 0, 0));
  }

  @Test
  public void initInvalidGrid() {
    assertThrows(IllegalArgumentException.class, () -> new ImageGraphicsImpl(50, 50, -6));
  }

  @Test
  public void drawLine() {
    g = new ImageGraphicsImpl(4, 4, 0);
    g.drawLine(1, 2, 3, 3);
    int[][][] expData = new int[][][]{
            {{255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 0, 255, 255},
                    {255, 255, 0, 0}},
            {{255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 0, 255, 255},
                    {255, 255, 0, 0}},
            {{255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 0, 255, 255},
                    {255, 255, 0, 0}}
    };
    ImageData exp = new ImageData(expData, 255);
    assertEquals(exp, g.getImageData());
  }

  @Test
  public void drawLineRed() {
    g = new ImageGraphicsImpl(4, 4, 0);
    g.setColor(ColorEnum.RED);
    g.drawLine(2, 0, 1, 3);
    int[][][] expData = {
            {{255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 255, 255, 255}},
            {{255, 255, 0, 255},
                    {255, 255, 0, 255},
                    {255, 0, 255, 255},
                    {255, 0, 255, 255}},
            {{255, 255, 0, 255},
                    {255, 255, 0, 255},
                    {255, 0, 255, 255},
                    {255, 0, 255, 255}}
    };
    ImageData exp = new ImageData(expData, 255);
    assertEquals(exp, g.getImageData());
  }

  @Test
  public void drawLineBlue() {
    g = new ImageGraphicsImpl(4, 4, 0);
    g.setColor(ColorEnum.BLUE);
    g.drawLine(1, 0, 2, 3);
    int[][][] expData = {
            {{255, 0, 255, 255},
                    {255, 0, 255, 255},
                    {255, 255, 0, 255},
                    {255, 255, 0, 255}},
            {{255, 0, 255, 255},
                    {255, 0, 255, 255},
                    {255, 255, 0, 255},
                    {255, 255, 0, 255}},
            {{255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 255, 255, 255}}
    };
    ImageData exp = new ImageData(expData, 255);
    assertEquals(exp, g.getImageData());
  }

  @Test
  public void drawLineGreen() {
    g = new ImageGraphicsImpl(4, 4, 0);
    g.setColor(ColorEnum.GREEN);
    g.drawLine(3, 0, 3, 4);
    int[][][] expData = {
            {{255, 255, 255, 0},
                    {255, 255, 255, 0},
                    {255, 255, 255, 0},
                    {255, 255, 255, 0}},
            {{255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 255, 255, 255},
                    {255, 255, 255, 255}},
            {{255, 255, 255, 0},
                    {255, 255, 255, 0},
                    {255, 255, 255, 0},
                    {255, 255, 255, 0}}
    };
    ImageData exp = new ImageData(expData, 255);
    assertEquals(exp, g.getImageData());
  }

  @Test
  public void testGrid() {
    g = new ImageGraphicsImpl(4, 4, 2);
    int[][][] expData = {
            {{255, 255, 192, 255},
                    {255, 255, 192, 255},
                    {192, 192, 192, 192},
                    {255, 255, 192, 255}},
            {{255, 255, 192, 255},
                    {255, 255, 192, 255},
                    {192, 192, 192, 192},
                    {255, 255, 192, 255}},
            {{255, 255, 192, 255},
                    {255, 255, 192, 255},
                    {192, 192, 192, 192},
                    {255, 255, 192, 255}}
    };
    ImageData exp = new ImageData(expData, 255);
    assertEquals(exp, g.getImageData());
  }
}