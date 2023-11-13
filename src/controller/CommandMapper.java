package controller;

import java.util.Map;
import java.util.function.Function;

import model.FactoryRgbImageModel;

public interface CommandMapper {
  Map<String, Function<String[], RgbImageCommand>> generateCommands(FactoryRgbImageModel factory);
}
