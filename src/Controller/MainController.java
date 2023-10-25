package Controller;


import java.io.IOException;
import java.util.Scanner;

import Model.RgbImageProcessor;

public class MainController {

  public static void main(String[] args) throws IOException {
    RgbImageProcessor controller = new RgbImageProcessor();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Image Processing Program");
    while (true) {
      System.out.print("> ");
      String input = scanner.nextLine();
      String[] arguments = input.split("\\s+");

      if (arguments.length == 0) {
        continue;
      }

      String command = arguments[0].toLowerCase();

      switch (command) {
        case "load":
        case "save":
        case "horizontal-flip":
        case "vertical-flip":
          executeBasicCommand(controller, command, arguments);
          break;
        case "red-component":
        case "green-component":
        case "blue-component":
        case "value-component":
        case "luma-component":
        case "intensity-component":
        case "brighten":
        case "darken":
        case "rgb-split":
        case "rgb-combine":
        case "blur":
        case "sharpen":
        case "sepia":
          executeAdvancedCommand(controller, command, arguments);
          break;
        case "run":
          if (arguments.length == 2) {
            runScript(arguments[1]);
          } else {
            System.out.println("Invalid 'run' command syntax.");
          }
          break;
        default:
          System.out.println("Unknown command: " + command);
      }
    }
  }

  private static void runScript(String argument) {
  }

  // Method to execute basic commands
  private static void executeBasicCommand(RgbImageProcessor controller, String command, String[] arguments) throws IOException {
    if (arguments.length == 3) {
      if ("load".equals(command) || "save".equals(command)) {
        controller.load(arguments[1], arguments[2]);
      } else if ("horizontal-flip".equals(command)) {
        controller.horizontalFlip(arguments[1], arguments[2]);
      } else if ("vertical-flip".equals(command)) {
        controller.verticalFlip(arguments[1], arguments[2]);
      }
    } else {
      System.out.println("Invalid '" + command + "' command syntax.");
    }
  }

  // Method to execute advanced commands
  private static void executeAdvancedCommand(RgbImageProcessor controller, String command, String[] arguments) {
    if (arguments.length == 4) {
      if ("red-component".equals(command) || "green-component".equals(command) || "blue-component".equals(command)
              || "value-component".equals(command) || "luma-component".equals(command)
              || "intensity-component".equals(command)) {
        controller.visualizeComponent(arguments[1], arguments[2], arguments[0]);
      } else if ("brighten".equals(command)) {
        int increment = Integer.parseInt(arguments[1]);
        controller.brighten(arguments[2], arguments[3], increment);
      }
      else if ("darken".equals(command)) {
        int increment = Integer.parseInt(arguments[1]);
        controller.darken(arguments[2], arguments[3], increment);
      }
    } else {
      System.out.println("Invalid '" + command + "' command syntax.");
    }
  }

}
