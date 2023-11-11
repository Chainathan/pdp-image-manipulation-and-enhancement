package controller;

import model.RgbImageModel;
import java.util.Map;

public interface RgbImageCommand {
  void execute(Map<String, RgbImageModel> imageList, String input) throws IllegalArgumentException;
}
