package controller;

import java.util.Map;
import java.util.function.Function;

import model.FactoryRgbImageModel;

/**
 * The CommandMapper interface defines methods for generating a map of commands associated
 * with their corresponding implementations. It is used to map command strings to executable commands.
 */
public interface CommandMapper {

  /**
   * Generates a map of commands associated with their corresponding implementations.
   *
   * @param factory The FactoryRgbImageModel used to create instances of RgbImageModel.
   * @return A map where command strings are mapped to functions that produce RgbImageCommand instances.
   */
  Map<String, Function<String[], RgbImageCommand>> generateCommands(FactoryRgbImageModel factory);
}
