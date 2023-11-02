package model;

import java.util.HashMap;
import java.util.Map;

/**
 * The ComponentEnum represents various image components,
 * each associated with a descriptive string.
 */
enum ComponentEnum {
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
   * Retrieves the ComponentEnum value associated with the provided descriptive string.
   *
   * @param componentString The descriptive string to map to a ComponentEnum value.
   * @return The ComponentEnum value corresponding to the descriptive string,
   *        or null if not found.
   */
  static ComponentEnum fromString(String componentString) {
    return stringToEnum.get(componentString);
  }
}
