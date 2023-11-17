package controller;

import java.util.HashMap;
import java.util.Map;

/**
 * The ComponentEnum represents various image components,
 * each associated with a descriptive string.
 */
public enum ComponentEnum {
  /**
   * The red component of an image.
   */
  RED("red-component"),

  /**
   * The green component of an image.
   */
  GREEN("green-component"),

  /**
   * The blue component of an image.
   */
  BLUE("blue-component"),

  /**
   * The value component of an image.
   */
  VALUE("value-component"),

  /**
   * The intensity component of an image.
   */
  INTENSITY("intensity-component"),

  /**
   * The luma component of an image.
   */
  LUMA("luma-component");

  private final String componentString;
  private static final Map<String, ComponentEnum> stringToEnum = new HashMap<>();

  static {
    for (ComponentEnum enumValue : ComponentEnum.values()) {
      stringToEnum.put(enumValue.componentString, enumValue);
    }
  }

  /**
   * Constructs a ComponentEnum with the specified descriptive string.
   *
   * @param componentString The descriptive string associated with the image component.
   */
  ComponentEnum(String componentString) {
    this.componentString = componentString;
  }

  /**
   * Retrieves the descriptive string associated with the ComponentEnum value.
   *
   * @return The descriptive string corresponding to the ComponentEnum value.
   *
   */
  public String getComponentString(){
    return componentString;
  }
}
