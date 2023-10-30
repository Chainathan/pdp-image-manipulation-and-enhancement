package Controller;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import DAO.DataDAO;
import DAO.ImageData;
import Model.ImageProcessorModel;
import View.ImeTextView;

/*
 * TODO -
 * write save in dao
 * Convolution method.
 * Interface for controller.
 * access modifiers.
 * Input output stream.
 * TEST.
 * Error handling.
 * Menu and Exit options?
 *
 * VIEW.
 * Interface for view.
 * */

public class RgbController implements ImageController {
  private final ImageProcessorModel rgbImageProcessor;
  private final DataDAO imageDataDAO;
  private final ImeTextView textView;
  private final Readable in;

  public RgbController(ImageProcessorModel rgbImageProcessor, ImeTextView textView, DataDAO imageDataDAO, Readable in) {
    this.rgbImageProcessor = rgbImageProcessor;
    this.imageDataDAO = imageDataDAO;
    this.textView = textView;
    this.in = in;
  }

  public void run() throws IOException {
    Scanner scanner = new Scanner(in);

    textView.display("Image Processing Program");
    while (true) {
      try {
        textView.display("> ");
        String input = scanner.nextLine();
        String result = processOperation(input);
        if (result.equals("exit")) {
          textView.display("Program Terminated");
          return;
        } else {
          textView.display(result);
        }
      } catch (IllegalArgumentException | IOException e) {
        textView.display(e.getMessage());
      }
    }
  }

  private String processOperation(String operation) throws IOException, IllegalArgumentException {
    String[] arguments = operation.split("\\s+");
    if (arguments.length == 0) {
      return "";
    }
    String command = arguments[0];

    if (arguments.length == 3) {
      return executeThreeArgCommand(command, arguments);
    } else if (arguments.length == 4) {
      return executeFourArgCommand(command, arguments);
    } else if (arguments.length == 2) {
      return runScript(arguments[1]);
    } else if (arguments.length == 1 && arguments[0].equals("exit")) {
      return "exit";
    } else {
      return "Unknown command: " + command;
    }
  }

  private String runScript(String filePath) throws IOException, IllegalArgumentException {
    Scanner sc = new Scanner(new FileInputStream(filePath));
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        return processOperation(s);
      }
    }
    return "";
  }

  private String executeFourArgCommand(String command, String[] arguments) throws IllegalArgumentException {
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
        return "Unknown command: " + command;
    }
    return "Operation performed successfully";
  }

  private String executeThreeArgCommand(String command, String[] arguments) throws IOException, IllegalArgumentException {
    switch (command) {
      case "load":
        ImageData imageData = imageDataDAO.load(arguments[1]);
        rgbImageProcessor.addImage(arguments[2], imageData);
//        imageData.printImageData();
        break;
      case "save":
        ImageData destImageData = rgbImageProcessor.getImageData(arguments[2]);
        imageDataDAO.save(arguments[1], destImageData);
//        destImageData.printImageData();
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
        return "Unknown command: " + command;
    }
    return "Operation performed successfully";
  }
}