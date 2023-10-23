package Controller;

import java.util.Map;

import Model.ImeModel;

public class MainController {

  public static void main (String[] args){
    if ("script".equals(args[0])){
      ScriptController.run(args[1]);
    }
    else {

    }
  }
  
  public static void runScript(String commands){

  }
}
