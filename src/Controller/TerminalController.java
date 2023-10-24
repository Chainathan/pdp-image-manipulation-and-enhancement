package Controller;

import java.io.IOException;
import java.util.Scanner;

public class TerminalController {
  public static void main(String[] args) throws IOException {
    IMEController controller = new IMEController();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Image Processing Program");
    while (true) {
      System.out.print("> ");
      String input = scanner.nextLine();
      String[] arguments = input.split("\\s+");

      if (arguments.length == 0) {
        continue;
      }
//      String command = arguments[0].toLowerCase();
      String command = arguments[0];

      switch (command) {
        case "load":
          if (arguments.length == 3) {
            controller.load(arguments[2], arguments[1]);
          } else {
            System.out.println("Invalid 'load' command syntax.");
          }
          break;
        case "save":
          if (arguments.length == 3) {
            controller.save(arguments[2], arguments[1]);
          } else {
            System.out.println("Invalid 'save' command syntax.");
          }
          break;
        case "red-component":
        case "green-component":
        case "blue-component":
        case "value-component":
        case "luma-component":
        case "intensity-component":
          if (arguments.length == 3) {
            controller.visualizeComponent(arguments[1],arguments[2],arguments[0]);
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "horizontal-flip":
          if (arguments.length == 3) {
            controller.horizontalFlip(arguments[1],arguments[2]);
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "vertical-flip":
          if (arguments.length == 3) {
            controller.verticalFlip(arguments[1],arguments[2]);
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "brighten":
          if (arguments.length == 4) {
            controller.brighten(arguments[1],arguments[2],Integer.parseInt(arguments[0]));
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "darken":
          if (arguments.length == 4) {
            controller.darken(arguments[2],arguments[3],Integer.parseInt(arguments[1]));
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "rgb-split":
          if (arguments.length == 5) {
            controller.rgbSplit(arguments[1],arguments[2],arguments[3],arguments[4]);
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "rgb-combine":
          if (arguments.length == 5) {
            controller.rgbCombine(arguments[1],arguments[2],arguments[3],arguments[4]);
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "blur":
          if (arguments.length == 3) {
            controller.blur(arguments[1],arguments[2]);
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "sharpen":
          if (arguments.length == 3) {
            controller.sharpen(arguments[1],arguments[2]);
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "sepia":
          if (arguments.length == 3) {
            controller.sepia(arguments[1],arguments[2]);
          } else {
            System.out.println("Invalid '" + command + "' command syntax.");
          }
          break;
        case "run":
          if (arguments.length == 2) {
            runScript(arguments[1]);
          } else {
            System.out.println("Invalid 'run' command syntax.");
          }
          break;
        case "menu":
          System.out.println("List of available commands:");
          System.out.println("1. menu - Display menu");
          System.out.println("2. exit - Exit the program");
          break;
        case "exit":
          System.out.println("Exiting the program.");
          System.exit(0);
          break;
        default:
          System.out.println("Unknown command: " + command);
      }
    }
  }
  static void runScript(String scriptPath){

  }
}
