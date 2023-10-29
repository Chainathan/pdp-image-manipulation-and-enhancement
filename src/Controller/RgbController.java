package Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import DAO.ImageData;
import DAO.ImageDataDAO;
import Model.ImageProcessorModel;

/*
* TODO -
* write save in dao
* Convolution method.
* Interface for controller & view.
* access modifiers.
* Input output stream.
* TEST.
* Error handling.
* Menu and Exit options?
*
* VIEW.
* */

public class RgbController {
  private final ImageProcessorModel rgbImageProcessor;
  private final ImageDataDAO imageDataDAO;

  public RgbController(ImageProcessorModel rgbImageProcessor) {
    this.rgbImageProcessor = rgbImageProcessor;
    this.imageDataDAO = new ImageDataDAO();
  }

  public void run() throws IOException {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Image Processing Program");
    while (true) {
      System.out.print("> ");
      String input = scanner.nextLine();
      processOperation(input);
    }
  }

  private void processOperation(String operation) throws IOException {
    String[] arguments = operation.split("\\s+");
    if (arguments.length == 0) {
      return;
    }
    String command = arguments[0].toLowerCase();

    if (arguments.length == 3) {
      executeThreeArgCommand(command,arguments);
    } else if (arguments.length == 4) {
      executeFourArgCommand(command,arguments);
    } else if (arguments.length == 2) {
      runScript(arguments[1]);
    } else {
      System.out.println("Unknown command: " + command);
    }
  }

  private void runScript(String filePath) throws IOException {
    Scanner sc = new Scanner(new FileInputStream(filePath));
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        processOperation(s);
      }
    }
  }

  private void executeFourArgCommand(String command, String[] arguments) {
    switch (command) {
      case "red-component":
      case "green-component":
      case "blue-component":
      case "value-component":
      case "luma-component":
      case "intensity-component":
        rgbImageProcessor.visualizeComponent(arguments[1], arguments[2], arguments[0]);
        break;
      case "brighten":
        int increment = Integer.parseInt(arguments[1]);
        rgbImageProcessor.brighten(arguments[2], arguments[3], increment);
        break;
      case "darken":
        int decrement = Integer.parseInt(arguments[1]);
        rgbImageProcessor.darken(arguments[2], arguments[3], decrement);
        break;
      case "rgb-split":
        List<String> destComponentList = new ArrayList<>();
        destComponentList.addAll(Arrays.asList(arguments).subList(2, arguments.length));
        rgbImageProcessor.splitComponents(arguments[1], destComponentList);
        break;
      case "rgb-combine":
        List<String> componentList = new ArrayList<>();
        componentList.addAll(Arrays.asList(arguments).subList(2, arguments.length));
        rgbImageProcessor.combineComponents(arguments[1], componentList);
        break;
      default:
        System.out.println("Unknown command: " + command);
    }
  }

  private void executeThreeArgCommand(String command, String[] arguments) throws IOException {
    switch (command) {
      case "load":
        ImageData imageData = imageDataDAO.load(arguments[1]);
        rgbImageProcessor.addImage(arguments[2],imageData);
        break;
      case "save":
        ImageData destImageData = rgbImageProcessor.getImageData(arguments[1]);
        imageDataDAO.save(arguments[2],destImageData);
        break;
      case "horizontal-flip":
        rgbImageProcessor.horizontalFlip(arguments[1], arguments[2]);
        break;
      case "vertical-flip":
        rgbImageProcessor.verticalFlip(arguments[1], arguments[2]);
        break;
      case "blur":
        rgbImageProcessor.blur(arguments[1], arguments[2]);
        break;
      case "sharpen":
        rgbImageProcessor.sharpen(arguments[1], arguments[2]);
        break;
      case "sepia":
        rgbImageProcessor.sepia(arguments[1], arguments[2]);
        break;
      default:
        System.out.println("Unknown command: " + command);
    }
  }
}