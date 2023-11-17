package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import controller.commands.GenericCommand;
import controller.commands.RgbCombine;
import controller.commands.RgbSplit;
import model.FactoryRgbImageModel;
import model.RgbImageModel;

/**
 * The CommandMapperBasic class implements the CommandMapper interface and provides
 * a basic set of commands for RGB image processing operations.
 */
public class CommandMapperBasic implements CommandMapper {
  protected final Map<String, Function<String[], RgbImageCommand>> knownCommands;

  /**
   * Constructs a CommandMapperBasic instance and creates an empty list of knownCommands.
   */
  public CommandMapperBasic() {
    knownCommands = new HashMap<>();
  }

  /**
   * Generates a map of basic commands based on the provided factory.
   *
   * @param factory The factory responsible for creating instances of RgbImageModel.
   * @return A map of commands with their associated functions.
   */
  public Map<String, Function<String[], RgbImageCommand>> generateCommands(
          FactoryRgbImageModel factory){
    knownCommands.put("blur",
            args->new GenericCommand(3, RgbImageModel::blur, true)
    );
    knownCommands.put("sharpen",
            args->new GenericCommand(3, RgbImageModel::sharpen,true)
    );
    knownCommands.put("sepia",
            args->new GenericCommand(3, RgbImageModel::sepia,true)
    );
    knownCommands.put("vertical-flip",
            args->new GenericCommand(3, RgbImageModel::verticalFlip,false)
    );
    knownCommands.put("horizontal-flip",
            args->new GenericCommand(3, RgbImageModel::horizontalFlip,false)
    );
    for (ComponentEnum value : ComponentEnum.values()) {
      knownCommands.put(value.getComponentString(),
              args->new GenericCommand(3,
                      rgb -> rgb.visualizeComponent(value),
                      false)
      );
    }
    knownCommands.put("brighten",
            args->new GenericCommand(4,
                    rgb -> rgb.brighten(Integer.parseInt(args[1])),
                    true)
    );
    knownCommands.put("rgb-split",
            args->new RgbSplit()
    );
    knownCommands.put("rgb-combine",
            args->new RgbCombine(factory)
    );
    return knownCommands;
  }
}
