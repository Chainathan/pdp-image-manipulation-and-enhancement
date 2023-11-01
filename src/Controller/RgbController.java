package Controller;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Model.ImageData;
import Model.ImageProcessorModel;
import View.ImeTextView;

/*
 * TODO -
 * Error handling.
 *
 * TEST.
 *
 * VIEW.
 * Interface for view.
 * Input output stream.
 * */

public class RgbController implements ImageController {
  private final ImageProcessorModel rgbImageProcessor;
  private final ImageFileIO imageImageFileIO;
  private final ImeTextView textView;
  private final Readable in;

  public RgbController(ImageProcessorModel rgbImageProcessor, ImeTextView textView, Readable in) {
    this.rgbImageProcessor = rgbImageProcessor;
    this.imageImageFileIO = new RgbImageFileIO();
    this.textView = textView;
    this.in = in;
  }

  public void run() throws IOException {
    textView.display("Image Processing program started");
    Scanner scanner = new Scanner(in);
    function(scanner);
  }
  private void function(Scanner sc) throws IOException, IllegalArgumentException {
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      try {
        String result = processOperation(s);
        if (result.equals("exit")) {
          textView.display("Program Terminated");
          System.exit(0);
        } else if (!result.isBlank()) {
          textView.display(result);
        }
      }
      catch (Exception e){
        textView.display(e.getMessage());
      }
    }
  }
  private String runScript(String filePath) throws IOException, IllegalArgumentException {
    Scanner sc = new Scanner(new FileInputStream(filePath));
    function(sc);
    return "Run Script Operation successful";
  }

  private String processOperation(String operation) throws IOException, IllegalArgumentException {
    if (operation.contains("#")) {
      operation = operation.substring(0, operation.indexOf("#"));
    }
    if (operation.isBlank()){
      return "";
    }

    String[] arguments = operation.trim().split("\\s+");
    System.out.println("arg length "+arguments.length);
    String command = arguments[0];
    String result;

    if (operation.startsWith("load ")
            || operation.startsWith("save ")
            || operation.startsWith("run ")){
      result =  executeIOOperation(command, arguments);
    } else if (arguments.length == 3) {
      result =  executeThreeArgCommand(command, arguments);
    } else if (arguments.length == 4) {
      result =  executeFourArgCommand(command, arguments);
    } else if (arguments.length == 5) {
      result =  executeFiveArgCommand(command, arguments);
    } else if (arguments.length == 1 && arguments[0].equals("exit")) {
      result =  "exit";
    } else {
      result =  "Unknown Operation: " + operation;
    }
    return result;
  }

  private String executeFiveArgCommand(String command, String[] arguments) throws IllegalArgumentException {
    switch (command) {
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
    return command+" Operation performed successfully";
  }

  private String executeFourArgCommand(String command, String[] arguments) throws IllegalArgumentException {
    switch (command) {
      case "brighten":
        int increment = Integer.parseInt(arguments[1]);
        rgbImageProcessor.brighten(arguments[2], arguments[3], increment);
        break;
      default:
        return "Unknown command: " + command;
    }
    return command+" Operation performed successfully";
  }

  private String executeIOOperation(String command, String[] arguments) throws IOException {
    String filePath = arguments[1];
    int filePathEndIndex = 1;
    if (arguments[1].startsWith("\"")){
      StringBuilder sb = new StringBuilder();
      for (int i = 1; i < arguments.length; i++) {
        sb.append(arguments[i]).append(" ");
        filePathEndIndex = i;
        if (arguments[i].endsWith("\"")){
          break;
        }
      }
      filePath = sb.toString().trim();
      if (!filePath.endsWith("\"")){
        return "Invalid Command";
      }
      filePath = filePath.replaceAll("\"","");
    }
    switch (command) {
      case "load":
        if (filePathEndIndex!=arguments.length-2){
          return "Invalid Command";
        }
        String imageName = arguments[arguments.length-1];
        ImageData imageData = imageImageFileIO.load(filePath);
        rgbImageProcessor.addImage(imageName, imageData);
        break;
      case "save":
        if (filePathEndIndex!=arguments.length-2){
          return "Invalid Command";
        }
        String destImageName = arguments[arguments.length-1];
        ImageData destImageData = rgbImageProcessor.getImageData(destImageName);
        imageImageFileIO.save(filePath, destImageData);
        break;
      case "run":
        if (filePathEndIndex!=arguments.length-1){
          return "Invalid Command";
        }
        return runScript(filePath);
    }
    return command+" Operation performed successfully";
  }
  private String executeThreeArgCommand(String command, String[] arguments)
          throws IllegalArgumentException {
    switch (command) {
      case "horizontal-flip":
        rgbImageProcessor.horizontalFlip(arguments[1], arguments[2]);
        break;
      case "vertical-flip":
        rgbImageProcessor.verticalFlip(arguments[1], arguments[2]);
        break;
      case "red-component":
      case "green-component":
      case "blue-component":
      case "value-component":
      case "luma-component":
      case "intensity-component":
        rgbImageProcessor.visualizeComponent(arguments[1], arguments[2], arguments[0]);
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
    return command+" Operation performed successfully";
  }
}