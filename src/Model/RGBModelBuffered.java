package Model;

import java.awt.image.BufferedImage;

import DAO.ImageData;

class RGBModelBuffered implements RgbImageModel {
  private BufferedImage image;
  private final int maxPixelValue;

  public RGBModelBuffered(BufferedImage image, int maxPixelValue) {
    this.image = image;
    this.maxPixelValue = maxPixelValue;
  }

  public RGBModelBuffered(BufferedImage image) {
    this.image = image;
    this.maxPixelValue = (int) Math.pow(2, image.getColorModel().getPixelSize()) - 1;
  }

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  @Override
  public RgbImageModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException {
    switch (componentEnum) {
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

  public RGBModelBuffered visualizeRedComponent() {
    BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType()); // BufferedImage.TYPE_INT_RGB
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        resultImage.setRGB(x, y, (red << 16));
      }
    }
    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered visualizeGreenComponent() {
    BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int green = (pixel >> 8) & 0xFF;
        resultImage.setRGB(x, y, (green << 8));
      }
    }
    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered visualizeBlueComponent() {
    BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int blue = pixel & 0xFF;
        resultImage.setRGB(x, y, blue);
      }
    }
    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered visualizeValueComponent() {
    int width = image.getWidth();
    int height = image.getHeight();

    BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int rgb = image.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        int v = (Math.max(r, Math.max(g, b)));

        int grayscaleValue = (v << 16) | (v << 8) | v;
        resultImage.setRGB(x, y, grayscaleValue);
      }
    }

    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered visualizeLumaComponent() {
    int width = image.getWidth();
    int height = image.getHeight();

    BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int rgb = image.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        int luma = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

        int grayscaleValue = (luma << 16) | (luma << 8) | luma;
        resultImage.setRGB(x, y, grayscaleValue);
      }
    }

    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered visualizeIntensityComponent() {
    BufferedImage resultImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;
        int intensity = (red + green + blue) / 3;
        int grayscaleValue = (intensity << 16) | (intensity << 8) | intensity;
        resultImage.setRGB(x, y, grayscaleValue);
      }
    }
    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered horizontalFlip() {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        resultImage.setRGB(width - 1 - x, y, pixel);
      }
    }
    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered verticalFlip() {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        resultImage.setRGB(x, height - 1 - y, pixel);
      }
    }
    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered brighten(int increment) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;

        red = Math.min(maxPixelValue, red + increment);
        green = Math.min(maxPixelValue, green + increment);
        blue = Math.min(maxPixelValue, blue + increment);

        int newPixel = (red << 16) | (green << 8) | blue;
        resultImage.setRGB(x, y, newPixel);
      }
    }
    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered darken(int decrement) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = image.getRGB(x, y);
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;

        red = Math.max(0, red - decrement);
        green = Math.max(0, green - decrement);
        blue = Math.max(0, blue - decrement);

        int newPixel = (red << 16) | (green << 8) | blue;
        resultImage.setRGB(x, y, newPixel);
      }
    }
    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered blur() {
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

    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered sharpen() {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage resultImage = new BufferedImage(width, height, image.getType());

    double[][] kernel = {
    };

    applyConvolution(width, height, resultImage, kernel);

    return new RGBModelBuffered(resultImage);
  }

  public RGBModelBuffered sepia() {
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

    return new RGBModelBuffered(resultImage);
  }

  @Override
  public ImageData getImageData() {
    return null;
  }

  @Override
  public void loadImageData(ImageData imageData) {

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

        int newPixel = (red << 16) | (green << 8) | blue;
        resultImage.setRGB(x, y, newPixel);
      }
    }
  }
}

