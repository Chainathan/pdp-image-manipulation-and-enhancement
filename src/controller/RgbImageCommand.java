package controller;

import model.ImageProcessorModel;
import model.RgbImageModel;

public interface RgbImageCommand {
  void execute(RgbImageModel image, String input) throws IllegalArgumentException;
}
