package view;

public enum FeatureEnum {
  BLUR("blur"),
  SHARPEN("sharpen"),
  SEPIA("sepia"),
  GREYSCALE("grayscale"),
  COLOR_CORRECT("color-correct"),
  LEVELS_ADJUST("levels-adjust"),
  FLIP_H("horizontal-flip"),
  FLIP_V("vertical-flip"),
  COMPRESS("compress"),
  RED("red-component"),
  GREEN("green-component"),
  BLUE("blue-component");

  private final String operation;
  FeatureEnum(String operation) {
    this.operation = operation;
  }
  public String getOperation(){
    return operation;
  }
}
