package model;

public interface Factory<T extends RgbImageModel> {
  T factory();
}
