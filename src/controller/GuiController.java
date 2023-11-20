package controller;

import java.io.IOException;
import java.util.function.Function;

import exceptions.FileFormatNotSupportedException;
import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;
import view.GuiView;

public class GuiController implements Features, ImageController{
  private GuiView view;
  private final ImageFileIO rgbImageFileIO;
  private RgbImageModel currImage;
  private RgbImageModel actImage;
  private double splitP;
  private boolean supportSplit;
  private boolean imageSaved;

  public GuiController(FactoryRgbImageModel factory, GuiView v) {
    view = v;
    view.addFeatures(this);
    this.rgbImageFileIO = new RgbImageFileIO();
    this.currImage = factory.createImageModel();
    actImage = currImage;
    imageSaved = true;
    loadImage("res/leo.png");
  }

  @Override
  public void run() throws IOException {
    view.showWindow();
  }
//  public void setView(GuiView v) {
//    view = v;
//    //provide view with all the callbacks
//    view.addFeatures(this);
//  }

  @Override
  public void exitProgram() {
    if (!imageSaved) {
      view.showDiscardConfirmation();
    }
    System.exit(0);
  }

  @Override
  public void loadImage(String filePath) {
    try {
      ImageData imageData = rgbImageFileIO.load(filePath);
      actImage.loadImageData(imageData);
      currImage=actImage;
      refreshImage();
      imageSaved = false;
    } catch (IOException | FileFormatNotSupportedException e){
      view.displayError(e.getMessage());
    }
  }

  @Override
  public void handleLoadButton() {
    view.showLoadMenu();
  }

  @Override
  public void handleSaveButton() {
    view.showSaveMenu();
  }

  @Override
  public void saveImage(String filePath) {
    try {
      ImageData destImageData = actImage.getImageData();
      rgbImageFileIO.save(filePath, destImageData);
      imageSaved = true;
    } catch (IOException | FileFormatNotSupportedException e){
      view.displayError(e.getMessage());
    }
  }

  private void refreshImage(){
    view.displayImage(currImage.getImageData());
    RgbImageModel imageHist = actImage.createHistogram(
            new ImageGraphicsImpl(256,256,20));
    view.displayHistogram(imageHist.getImageData());
  }

  @Override
  public void blur() {
    executeOperation(RgbImageModel::blur, supportSplit);
  }

  @Override
  public void sharpen() {
    executeOperation(RgbImageModel::sharpen, supportSplit);
  }

  @Override
  public void sepia() {
    executeOperation(RgbImageModel::sepia, supportSplit);
  }

  @Override
  public void greyscale() {
    executeOperation(rgb->rgb.visualizeComponent(ComponentEnum.LUMA), supportSplit);
  }

  @Override
  public void red() {
    executeOperation(rgb->rgb.visualizeComponent(ComponentEnum.RED), false);
  }

  @Override
  public void green() {
    executeOperation(rgb->rgb.visualizeComponent(ComponentEnum.GREEN), false);
  }

  @Override
  public void blue() {
    executeOperation(rgb->rgb.visualizeComponent(ComponentEnum.BLUE), false);
  }

  @Override
  public void horizontal() {
    executeOperation(RgbImageModel::horizontalFlip, false);
  }

  @Override
  public void vertical() {
    executeOperation(RgbImageModel::verticalFlip, false);
  }

  @Override
  public void colorCorrect() {
    executeOperation(RgbImageModel::correctColor, supportSplit);
  }

  @Override
  public void handleCompress() {
    view.showCompressMenu();
  }

//  @Override
//  public void compress(double compressRatio) {
//    image = image.applyCompression(compressRatio);
//    refreshImage();
//  }
  @Override
  public void compress(String compressRatio) {
    try {
      double cr = Double.parseDouble(compressRatio);
      executeOperation(rgb->rgb.applyCompression(cr), supportSplit);
    } catch (NumberFormatException ne){
      view.displayError("Invalid input");
    }
  }
  @Override
  public void handleLevelsAdjust() {
    view.showLvlAdjMenu();
  }

//  @Override
//  public void levelsAdjust(int b, int m, int w) {
//    image = image.adjustLevels(b,m,w);
//    refreshImage();
//  }
  @Override
  public void levelsAdjust(String b, String m, String w) {
    try {
      int ib = Integer.parseInt(b);
      int im = Integer.parseInt(m);
      int iw = Integer.parseInt(w);
      executeOperation(rgb->rgb.adjustLevels(ib,im,iw), supportSplit);
    } catch (NumberFormatException ne){
      view.displayError("Invalid input");
    }
  }

  @Override
  public void handleSplitToggle(boolean supportSplit) {
    view.toggleSplit(supportSplit);
    splitP = 100;
    if (!supportSplit) {
      this.supportSplit = false;
      currImage = actImage;
      refreshImage();
    }
    String msg = "Split view: ";
    msg += (supportSplit ? String.format("Enabled %.2f", splitP) : "Disabled");
    view.setSplitLabel(msg);
    view.setSplitInput(String.format("%.2f", splitP));
  }

  @Override
  public void applySplit(String splitP) {
    try {
      this.splitP = Double.parseDouble(splitP);
      supportSplit = true;
      String msg = "Split view: ";
      msg += String.format("Enabled %.2f", this.splitP);
      view.setSplitLabel(msg);
      view.setSplitInput(String.format("%.2f", this.splitP));
    } catch (NumberFormatException ne){
      view.displayError("Invalid input");
    } catch (IllegalArgumentException e){
      view.displayError(e.getMessage());
    }
  }

  private void executeOperation(Function<RgbImageModel,RgbImageModel> fun, boolean supportSplit){
    try {
      if (supportSplit) {
        currImage = actImage;
        actImage = fun.apply(actImage);
        System.out.println(currImage==actImage);
        RgbImageModel left = actImage.cropVertical(0, splitP);
        currImage = currImage.overlapOnBase(left, 0);
      } else {
        actImage = fun.apply(actImage);
        if (this.supportSplit) {
          currImage = fun.apply(currImage);
        } else {
          currImage = actImage;
        }
      }
      refreshImage();
      imageSaved = false;
    } catch (IllegalArgumentException e){
      view.displayError(e.getMessage());
    }
  }
}
