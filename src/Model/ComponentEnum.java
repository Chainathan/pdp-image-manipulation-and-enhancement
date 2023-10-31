package Model;

import java.util.HashMap;
import java.util.Map;

public enum ComponentEnum {
  RED("red-component"),
  GREEN("green-component"),
  BLUE("blue-component"),
  VALUE("value-component"),
  INTENSITY("intensity-component"),
  LUMA("luma-component");

  final String componentString;
  private static final Map<String, ComponentEnum> stringToEnum = new HashMap<>();

  static {
    for (ComponentEnum enumValue : ComponentEnum.values()) {
      stringToEnum.put(enumValue.componentString, enumValue);
    }
  }

  ComponentEnum(String componentString) {
    this.componentString = componentString;
  }

  public static ComponentEnum fromString(String componentString) {
    return stringToEnum.get(componentString);
  }
}
