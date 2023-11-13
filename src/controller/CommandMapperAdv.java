package controller;

import java.util.Map;
import java.util.function.Function;

import controller.commands.GenericCommand;
import model.FactoryRgbImageModel;
import model.RgbImageModel;

public class CommandMapperAdv extends CommandMapperBasic{
  @Override
  public Map<String, Function<String[], RgbImageCommand>> generateCommands(
          FactoryRgbImageModel factory){
    super.generateCommands(factory);
    knownCommands.put("color-correct",
            args->new GenericCommand(3, RgbImageModel::correctColor,true)
    );
    knownCommands.put("histogram",
            args->new GenericCommand(3, RgbImageModel::createHistogram,false)
    );
    knownCommands.put("compress",
            args->new GenericCommand(4,
                    rgb -> rgb.applyCompression(Double.parseDouble(args[1])),
                    false)
    );
    knownCommands.put("levels-adjust",
            args->new GenericCommand(6,
                    rgb -> rgb.adjustLevels(Integer.parseInt(args[1]),
                            Integer.parseInt(args[2]),
                            Integer.parseInt(args[3])),
                    true)
    );
    return knownCommands;
  }
}