package Model;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class RGBModel implements RgbImeModel{
  private BufferedImage image;

  public RGBModel(BufferedImage image) {
    this.image = image;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  @Override
  public RgbImeModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException{
    switch (componentEnum){
      case RED:
        return visualizeRedComponent();
      case GREEN:
        return visualizeGreenComponent();
      case BLUE:
        return visualizeBlueComponent();
      case VALUE:
        return visualizeValueComponent();
      case INTENSITY:
        return visualizeIntensityComponent();
      case LUMA:
        return visualizeLumaComponent();
      default:
        throw new IllegalArgumentException("Invalid Component to visualize");
    }
  }

  public RGBModel visualizeRedComponent() {
    BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        resultImage.setRGB(x, y, (red << 16));
      }
    }
    return new RGBModel(resultImage);
  }

  public RGBModel visualizeGreenComponent() {
    BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int green = (pixel >> 8) & 0xFF;
        resultImage.setRGB(x, y, (green << 8));
      }
    }
    return new RGBModel(resultImage);
  }

  public RGBModel visualizeBlueComponent() {
    BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int blue = pixel & 0xFF;
        resultImage.setRGB(x, y, blue);
      }
    }
    return new RGBModel(resultImage);
  }

  public RGBModel visualizeValueComponent() {
    int width = image.getWidth();
    int height = image.getHeight();

    BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int rgb = image.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;  // Red component
        int g = (rgb >> 8) & 0xFF;   // Green component
        int b = rgb & 0xFF;          // Blue component
        int v = (Math.max(r, Math.max(g, b)) + Math.min(r, Math.min(g, b))) / 2;  // Calculate V

        int grayscaleValue = (v << 16) | (v << 8) | v;  // Create grayscale pixel value
        grayscaleImage.setRGB(x, y, grayscaleValue);
      }
    }

    return new RGBModel(grayscaleImage);
  }

  public RGBModel visualizeLumaComponent() {
    int width = image.getWidth();
    int height = image.getHeight();

    BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int rgb = image.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;  // Red component
        int g = (rgb >> 8) & 0xFF;   // Green component
        int b = rgb & 0xFF;          // Blue component
        int yuvLuma = (int) (0.299 * r + 0.587 * g + 0.114 * b);  // Calculate YUV luma component

        int grayscaleValue = (yuvLuma << 16) | (yuvLuma << 8) | yuvLuma;  // Create grayscale pixel value
        grayscaleImage.setRGB(x, y, grayscaleValue);
      }
    }

    return new RGBModel(grayscaleImage);
  }

  public RGBModel visualizeIntensityComponent() {
    BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    ColorModel cm = resultImage.getColorModel();
    WritableRaster raster = resultImage.getRaster();

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;
        int gray = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
        raster.setSample(x, y, 0, gray);
      }
    }
    return new RGBModel(resultImage);
  }

  public RGBModel horizontalFlip() {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        resultImage.setRGB(width - 1 - x, y, pixel); // Flip horizontally
      }
    }
    return new RGBModel(resultImage);
  }

  public RGBModel verticalFlip() {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        resultImage.setRGB(x, height - 1 - y, pixel); // Flip vertically
      }
    }
    return new RGBModel(resultImage);
  }

  public RGBModel brighten(int increment) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;

        red = Math.min(255, red + increment);
        green = Math.min(255, green + increment);
        blue = Math.min(255, blue + increment);

        int newPixel = (red << 16) | (green << 8) | blue;
        resultImage.setRGB(x, y, newPixel);
      }
    }
    return new RGBModel(resultImage);
  }

  public RGBModel darken(int decrement) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;

        red = Math.min(255, red - decrement);
        green = Math.min(255, green - decrement);
        blue = Math.min(255, blue - decrement);

        int newPixel = (red << 16) | (green << 8) | blue;
        resultImage.setRGB(x, y, newPixel);
      }
    }
    return new RGBModel(resultImage);
  }

  public RGBModel blur() {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    // Gaussian Blur Kernel (3x3)
    double[][] kernel = {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
    };

    applyConvolution(width, height, resultImage, kernel);

    return new RGBModel(resultImage);
  }

  public RGBModel sharpen() {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    // Laplacian Sharpening Kernel (3x3)
    double[][] kernel = {
            {0.0, -1.0, 0.0},
            {-1.0, 5.0, -1.0},
            {0.0, -1.0, 0.0}
    };

    applyConvolution(width, height, resultImage, kernel);

    return new RGBModel(resultImage);
  }

  public RGBModel sepia() {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;

        int sepiaRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
        int sepiaGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
        int sepiaBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);

        sepiaRed = Math.min(255, sepiaRed);
        sepiaGreen = Math.min(255, sepiaGreen);
        sepiaBlue = Math.min(255, sepiaBlue);

        int newPixel = (sepiaRed << 16) | (sepiaGreen << 8) | sepiaBlue;
        resultImage.setRGB(x, y, newPixel);
      }
    }

    return new RGBModel(resultImage);
  }

  private void applyConvolution(int width, int height, BufferedImage resultImage, double[][] kernel) {
    int kernelSize = kernel.length;
    int kernelRadius = kernelSize / 2;

    for (int y = kernelRadius; y < height - kernelRadius; y++) {
      for (int x = kernelRadius; x < width - kernelRadius; x++) {
        int red = 0, green = 0, blue = 0;

        for (int ky = 0; ky < kernelSize; ky++) {
          for (int kx = 0; kx < kernelSize; kx++) {
            int pixel = image.getRGB(x + kx - kernelRadius, y + ky - kernelRadius);
            red += ((pixel >> 16) & 0xFF) * kernel[ky][kx];
            green += ((pixel >> 8) & 0xFF) * kernel[ky][kx];
            blue += (pixel & 0xFF) * kernel[ky][kx];
          }
        }

        int newPixel = ((int) red << 16) | ((int) green << 8) | (int) blue;
        resultImage.setRGB(x, y, newPixel);
      }
    }
  }
}

