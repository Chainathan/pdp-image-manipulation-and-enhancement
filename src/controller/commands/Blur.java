package controller.commands;

import controller.RgbImageCommand;
import model.RgbImageModel;

public class Blur implements RgbImageCommand {
  @Override
  public void execute(RgbImageModel image, String input) throws IllegalArgumentException {
    String[] arguments = input.split("\\s+");
    if (arguments.length != 3 && arguments.length != 5){
      throw new IllegalArgumentException("Invalid arguments for Blur");
    }

  }
}
