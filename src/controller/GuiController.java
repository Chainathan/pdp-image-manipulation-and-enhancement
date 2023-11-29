package controller;

import java.io.IOException;
import java.util.function.Function;

import exceptions.FileFormatNotSupportedException;
import model.FactoryRgbImageModel;
import model.ImageData;
import model.RgbImageModel;
import view.GuiView;

/**
 * GuiControllerSplit is a class that implements both the Features and ImageController interfaces
 * to provide functionality for handling image operations and user interactions in a graphical user
 * interface. This class manages the GUI components and serves as a controller for image processing
 * operations.
 */
public class GuiController implements Features, ImageController {

  private GuiView view;
  private final ImageFileIO rgbImageFileIO;
  private RgbImageModel currImage;
  private RgbImageModel actImage;
  private RgbImageModel tempImage;
  private boolean imageSaved;

  private Function<RgbImageModel, RgbImageModel> currentOperation;

  /**
   * This constructor initializes the GuiControllerSplit with the provided FactoryRgbImageModel
   * and GuiView.
   * It sets up the necessary components for the GUI, including the view and its action listener.
   * The constructor also initializes the RgbImageFileIO, the current image model (currImage)
   * created by the factory, and sets the actImage to be the same as currImage initially.
   * The imageSaved flag is set to true, indicating that there are no unsaved changes to
   * the image upon initialization.
   *
   * @param factory The FactoryRgbImageModel used to create the initial RgbImageModel
   *                for the controller.
   * @param v       The GuiView associated with this controller, providing the user
   *                interface components.
   */
  public GuiController(FactoryRgbImageModel factory, GuiView v) {
    view = v;
    view.addFeatures(this);
    this.rgbImageFileIO = new RgbImageFileIO();
    this.currImage = factory.createImageModel();
    actImage = currImage;
    imageSaved = true;
  }

  @Override
  public void run() throws IOException {
    view.showWindow();
  }

  @Override
  public void apply() {
    if (currentOperation != null) {
      try {
        if (tempImage != null) {
          actImage = tempImage;
        } else {
          actImage = currentOperation.apply(actImage);
        }
        currImage = actImage;
        refreshImage();
        view.resetInputSliders();
        imageSaved = false;
      } catch (IllegalArgumentException e) {
        view.displayError(e.getMessage());
      }
    } else {
      view.displayError("Invalid Operation");
    }
  }

  @Override
  public void cancel() {
    currImage = actImage;
    refreshImage();
    view.resetPreviewSlider();
  }

  @Override
  public void exitProgram() {
    if (!imageSaved) {
      view.showDiscardConfirmation();
    }
    System.exit(0);  // check
  }

  @Override
  public void loadImage(String filePath) {
    try {
      ImageData imageData = rgbImageFileIO.load(filePath);
      actImage.loadImageData(imageData);
      currImage = actImage;
      refreshImage();
      view.resetDropdown();
      imageSaved = true;
    } catch (IOException | FileFormatNotSupportedException e) {
      view.displayError(e.getMessage());
    }
  }

  @Override
  public void handleLoadButton() {
    if (isImagePresent() && !imageSaved) {
      view.showDiscardConfirmation();
    }
    view.showLoadMenu();
  }

  @Override
  public void handleSaveButton() {
    if (isImagePresent()) {
      view.showSaveMenu();
    } else {
      view.displayError("No Image to Save");
    }
  }

  @Override
  public void saveImage(String filePath) {
    try {
      ImageData destImageData = actImage.getImageData();
      rgbImageFileIO.save(filePath, destImageData);
      imageSaved = true;
    } catch (IOException | FileFormatNotSupportedException e) {
      view.displayError(e.getMessage());
    }
  }

  private boolean isImagePresent() {
    return actImage.getImageData().getData()[0].length != 0;
  }

  private void refreshImage() {
    view.displayImage(currImage.getImageData());
    if (isImagePresent()) {
      RgbImageModel imageHist = actImage.createHistogram(
              new ImageGraphicsImpl(256, 256, 20));
      view.displayHistogram(imageHist.getImageData());
    }
  }

  private void setUpForOperation(Function<RgbImageModel, RgbImageModel> operation,
                                 boolean isPreviewEnabled) {
    if (isImagePresent()) {
      currentOperation = operation;
      if (currImage != actImage) {
        currImage = actImage;
        refreshImage();
      }
      tempImage = null;
      view.togglePreview(isPreviewEnabled);
    } else {
      view.displayError("No Image Present");
      view.resetDropdown();
    }
  }

  @Override
  public void noOperation() {
    currentOperation = null;
    tempImage = null;
    view.togglePreview(false);
  }

  @Override
  public void blur() {
    setUpForOperation(RgbImageModel::blur, true);
  }

  @Override
  public void sharpen() {
    setUpForOperation(RgbImageModel::sharpen, true);
  }

  @Override
  public void sepia() {
    setUpForOperation(RgbImageModel::sepia, true);
  }

  @Override
  public void greyscale() {
    setUpForOperation(rgb -> rgb.visualizeComponent(ComponentEnum.LUMA), true);
  }

  @Override
  public void red() {
    setUpForOperation(rgb -> rgb.visualizeComponent(ComponentEnum.RED), false);
  }

  @Override
  public void green() {
    setUpForOperation(rgb -> rgb.visualizeComponent(ComponentEnum.GREEN), false);
  }

  @Override
  public void blue() {
    setUpForOperation(rgb -> rgb.visualizeComponent(ComponentEnum.BLUE), false);
  }

  @Override
  public void horizontal() {
    setUpForOperation(RgbImageModel::horizontalFlip, false);
  }

  @Override
  public void vertical() {
    setUpForOperation(RgbImageModel::verticalFlip, false);
  }

  @Override
  public void colorCorrect() {
    setUpForOperation(RgbImageModel::correctColor, true);
  }

  @Override
  public void handleCompress() {
    if (isImagePresent()) {
      view.showCompressMenu();
    } else {
      view.displayError("No Image Present");
      view.resetDropdown();
    }
  }

  @Override
  public void compress(double compressRatio) {
    setUpForOperation(rgb -> rgb.applyCompression(compressRatio), false);
  }

  @Override
  public void handleLevelsAdjust() {
    if (isImagePresent()) {
      view.showLvlAdjMenu();
    } else {
      view.displayError("No Image Present");
      view.resetDropdown();
    }
  }

  @Override
  public void levelsAdjust(int b, int m, int w) {
    setUpForOperation(rgb -> rgb.adjustLevels(b, m, w), true);
  }

  @Override
  public void preview(int splitP) {
    try {
      tempImage = currentOperation.apply(actImage);
      RgbImageModel left = tempImage.cropVertical(0, splitP);
      currImage = actImage.overlapOnBase(left, 0);
      refreshImage();
    } catch (IllegalArgumentException e) {
      view.displayError(e.getMessage());
    }
  }
}
