package controller;

import model.RgbImageModel;

import java.util.Map;

/**
 * The RgbImageCommand interface defines a contract for commands that manipulate RGB images.
 * Implementations of this interface should provide an execute method that performs the
 * specified image manipulation using the provided image list and command arguments.
 */
public interface RgbImageCommand {

  /**
   * Executes the RGB image manipulation command.
   *
   * @param imageList A map containing named RGB images to be manipulated.
   * @param arguments An array of command-specific arguments required for the execution.
   * @throws IllegalArgumentException If the command execution encounters invalid arguments.
   */
  void execute(Map<String, RgbImageModel> imageList, String[] arguments)
          throws IllegalArgumentException;
}
