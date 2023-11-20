package controller;

public enum CommandEnum {
  BLUR("blur"),
  SHARPEN("sharpen"),
  SEPIA("sepia"),
  GREYSCALE("luma-component"),
  COLOR_CORRECT("color-correct"),
  LEVELS_ADJUST("levels-adjust"),
  FLIP_H("horizontal-flip"),
  FLIP_V("vertical-flip"),
  COMPRESS("compress"),
  RED("red-component"),
  GREEN("green-component"),
  BLUE("blue-component");

  private final String command;
  CommandEnum(String command) {
    this.command = command;
  }
  public String getCommand(){
    return command;
  }
}
