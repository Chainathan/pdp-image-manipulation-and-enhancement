package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.Blur;
import controller.commands.Brighten;
import exceptions.FileFormatNotSupportedException;
import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;
import view.ImageProcessorView;

public class AdvRgbController implements ImageController{
  private final FactoryRgbImageModel factory;
  private final ImageFileIO rgbImageFileIO;
  private final ImageProcessorView textView;
  private final Readable in;
  private final Map<String, RgbImageModel> imageModelMap;
  private final Map<String, Function<Scanner, RgbImageCommand>> knownCommands;

  public AdvRgbController(FactoryRgbImageModel factory,
                          ImageProcessorView textView, Readable in) {
    this.factory = factory;
    this.rgbImageFileIO = new RgbImageFileIO();
    this.textView = textView;
    this.in = in;
    imageModelMap = new HashMap<>();
    knownCommands = new HashMap<>();
    initKnownCommands();
  }
  private void initKnownCommands(){
    knownCommands.put("blur",s->new Blur());
    knownCommands.put("brighten",s->new Brighten());
  }
  @Override
  public void run() throws IOException{
    textView.display("Image Processing program started");
    Scanner scanner = new Scanner(in);
    processScanner(scanner);
    scanner.close();
  }

  private void processScanner(Scanner sc)
          throws IOException, IllegalArgumentException, FileFormatNotSupportedException {
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      try {
        String result = processOperation(s);
        if (result.equals("exit")) {
          textView.display("Program Terminated");
          break;
        } else if (!result.isBlank()) {
          textView.display(result);
        }
      } catch (Exception e) {
        textView.display(e.getMessage());
      }
    }
  }

  private String processOperation(String operation)
          throws IOException, IllegalArgumentException, FileFormatNotSupportedException {
    if (operation.contains("#")) {
      operation = operation.substring(0, operation.indexOf("#"));
    }
    if (operation.isBlank()) {
      return "";
    }
    operation = operation.trim();
    String[] arguments = operation.split("\\s+");
    String command = arguments[0];
    String result;

    switch (command) {
      case "exit":
        System.exit(0);
      case "load":
      case "save":
      case "run":
        result = executeIOOperation(command, arguments);
        break;
      default:
        result = executeFunction(command, arguments);
    }
    return result;
  }
  private String executeFunction(String command, String[] arguments)
          throws IllegalArgumentException{
    Function<Scanner, RgbImageCommand> cmd = knownCommands.get(command);
    if (cmd == null) {
      throw new IllegalArgumentException("Invalid Command");
    }
    RgbImageCommand commandObject = cmd.apply(new Scanner(in));
    commandObject.execute(imageModelMap, arguments);
    return command + " Operation performed successfully";
  }
  private String executeIOOperation(String command, String[] arguments)
          throws IOException, FileFormatNotSupportedException {
    String filePath = arguments[1];
    int filePathEndIndex = 1;
    if (arguments[1].startsWith("\"")) {
      StringBuilder sb = new StringBuilder();
      for (int i = 1; i < arguments.length; i++) {
        sb.append(arguments[i]).append(" ");
        filePathEndIndex = i;
        if (arguments[i].endsWith("\"")) {
          break;
        }
      }
      filePath = sb.toString().trim();
      if (!filePath.endsWith("\"")) {
        return "Invalid Command";
      }
      filePath = filePath.replaceAll("\"", "");
    }
    switch (command) {
      case "load":
        if (filePathEndIndex != arguments.length - 2) {
          return "Invalid Command";
        }
        String imageName = arguments[arguments.length - 1];
        ImageData imageData = rgbImageFileIO.load(filePath);
        RgbImageModel image = factory.createImageModel();
        image.loadImageData(imageData);
        imageModelMap.put(imageName,image);
        break;
      case "save":
        if (filePathEndIndex != arguments.length - 2) {
          return "Invalid Command";
        }
        String destImageName = arguments[arguments.length - 1];
        checkImageExists(imageModelMap,destImageName);
        ImageData destImageData = imageModelMap.get(destImageName).getImageData();
        rgbImageFileIO.save(filePath, destImageData);
        break;
      case "run":
        if (filePathEndIndex != arguments.length - 1) {
          return "Invalid Command";
        }
        return runScript(filePath);
      default:
        return "Invalid Command";
    }
    return command + " Operation performed successfully";
  }

  private String runScript(String filePath)
          throws IOException, IllegalArgumentException, FileFormatNotSupportedException {
    int startingIndex = filePath.lastIndexOf(".");
    String extension = filePath.substring(startingIndex == -1 ? 0 : startingIndex).replace(".", "");
    if (extension.equals(".txt")) {
      throw new FileFormatNotSupportedException("Unsupported File format");
    }
    Scanner sc = new Scanner(new FileInputStream(filePath));
    processScanner(sc);
    sc.close();
    return "Run Script Operation successful";
  }

  public static void checkImageExists(Map<String, RgbImageModel> map, String imageName)
          throws IllegalArgumentException {
    if (!map.containsKey(imageName)){
      throw new IllegalArgumentException("Image does not exist");
    }
  }
}
