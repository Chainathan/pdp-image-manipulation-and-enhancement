package model;

public class AdvFactory implements Factory<AdvRgbImageModel>{
  @Override
  public AdvRgbImageModel factory() {
    return new AdvRgbImage();
  }
}
