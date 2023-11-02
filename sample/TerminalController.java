//package sample;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import Model.RgbImageProcessor;
//
//public class TerminalController {
//  public static void main(String[] args) throws IOException {
//    RgbImageProcessor rgbImageProcessor = new RgbImageProcessor();
//    Scanner scanner = new Scanner(System.in);
//
//    System.out.println("Image Processing Program");
//    while (true) {
//      System.out.print("> ");
//      String input = scanner.nextLine();
//      String[] arguments = input.split("\\s+");
//
//      if (arguments.length == 0) {
//        continue;
//      }
////      String command = arguments[0].toLowerCase();
//      String command = arguments[0];
//
//      switch (command) {
//        case "load":
//          if (arguments.length == 3) {
//            rgbImageProcessor.addImage(arguments[2], arguments[1]);
//          } else {
//            System.out.println("Invalid 'load' command syntax.");
//          }
//          break;
//        case "save":
//          if (arguments.length == 3) {
//            rgbImageProcessor.getImageData(arguments[2], arguments[1]);
//          } else {
//            System.out.println("Invalid 'save' command syntax.");
//          }
//          break;
//        case "red-component":
//        case "green-component":
//        case "blue-component":
//        case "value-component":
//        case "luma-component":
//        case "intensity-component":
//          if (arguments.length == 3) {
//            rgbImageProcessor.visualizeComponent(arguments[1],arguments[2],arguments[0]);
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "horizontal-flip":
//          if (arguments.length == 3) {
//            rgbImageProcessor.horizontalFlip(arguments[1],arguments[2]);
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "vertical-flip":
//          if (arguments.length == 3) {
//            rgbImageProcessor.verticalFlip(arguments[1],arguments[2]);
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "brighten":
//          if (arguments.length == 4) {
//            rgbImageProcessor.brighten(arguments[1],arguments[2],Integer.parseInt(arguments[0]));
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "darken":
//          if (arguments.length == 4) {
//            rgbImageProcessor.darken(arguments[2],arguments[3],Integer.parseInt(arguments[1]));
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "rgb-split":
//          if (arguments.length == 5) {
//            List<String> componentList = new ArrayList<>();
//            for (int i=2;i<5;i++){
//              componentList.add(arguments[i]);
//            }
//            rgbImageProcessor.splitComponents(arguments[1],componentList);
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "rgb-combine":
//          if (arguments.length == 5) {
//            List<String> componentList = new ArrayList<>();
//            for (int i=2;i<5;i++){
//              componentList.add(arguments[i]);
//            }
//            rgbImageProcessor.combineComponents(arguments[1],componentList);
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "blur":
//          if (arguments.length == 3) {
//            rgbImageProcessor.blur(arguments[1],arguments[2]);
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "sharpen":
//          if (arguments.length == 3) {
//            rgbImageProcessor.sharpen(arguments[1],arguments[2]);
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "sepia":
//          if (arguments.length == 3) {
//            rgbImageProcessor.sepia(arguments[1],arguments[2]);
//          } else {
//            System.out.println("Invalid '" + command + "' command syntax.");
//          }
//          break;
//        case "run":
//          if (arguments.length == 2) {
//            runScript(arguments[1]);
//          } else {
//            System.out.println("Invalid 'run' command syntax.");
//          }
//          break;
//        case "menu":
//          System.out.println("List of available commands:");
//          System.out.println("1. menu - Display menu");
//          System.out.println("2. exit - Exit the program");
//          break;
//        case "exit":
//          System.out.println("Exiting the program.");
//          System.exit(0);
//          break;
//        default:
//          System.out.println("Unknown command: " + command);
//      }
//    }
//  }
//  static void runScript(String scriptPath){
//
//  }
//}
