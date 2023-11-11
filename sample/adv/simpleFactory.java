package model;

public class simpleFactory implements Factory<RgbImageModel>{
  @Override
  public RgbImageModel factory() {
    return new RgbImage();
  }
}
