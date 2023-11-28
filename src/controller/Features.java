package controller;

/**
 * The Features interface represents a set of callback functions for image processing and user interaction features
 * that can be implemented by classes providing image processing functionality in a graphical user interface.
 * Classes implementing this interface are expected to handle various image operations and user interactions.
 * The operations provided by this interface can be majorly used as callback functions for the action listeners.
 */
public interface Features {
  /**
   * Applies the current image processing operation.
   */
  void apply();

  /**
   * Cancels the current image processing operation and reverts to the previous state.
   */
  void cancel();

  /**
   * Clears the current image processing operation, returning to the default state.
   */
  void noOperation();

  /**
   * Displays a preview of the image with a specified split percentage.
   *
   * @param splitP The percentage at which to split and preview the image.
   */
  void preview(int splitP);

  /**
   * Exits the program, prompting the user to save unsaved changes if necessary.
   */
  void exitProgram();

  /**
   * Loads an image from the specified file path.
   *
   * @param filePath The file path of the image to be loaded.
   */
  void loadImage(String filePath);

  /**
   * Handles the action associated with the load button in the user interface.
   */
  void handleLoadButton();

  /**
   * Saves the current image to the specified file path.
   *
   * @param filePath The file path where the image will be saved.
   */
  void saveImage(String filePath);

  /**
   * Handles the action associated with the save button in the user interface.
   */
  void handleSaveButton();

  /**
   * Applies a blur filter to the image.
   */
  void blur();

  /**
   * Applies a sharpening filter to the image.
   */
  void sharpen();

  /**
   * Applies a sepia tone effect to the image.
   */
  void sepia();

  /**
   * Converts the image to greyscale.
   */
  void greyscale();

  /**
   * Visualizes the red component of the image.
   */
  void red();

  /**
   * Visualizes the green component of the image.
   */
  void green();

  /**
   * Visualizes the blue component of the image.
   */
  void blue();

  /**
   * Flips the image horizontally.
   */
  void horizontal();

  /**
   * Flips the image vertically.
   */
  void vertical();

  /**
   * Handles the action associated with compressing the image.
   */
  void handleCompress();

  /**
   * Set the input arguments for the compress operation with the specified compression ratio.
   *
   * @param compressRatio The compression ratio to be applied to the image.
   */
  void compress(double compressRatio);

  /**
   * Handles the action associated with adjusting image levels.
   */
  void handleLevelsAdjust();

  /**
   * Set the input arguments for the levels adjust operation using the provided parameters.
   *
   * @param b The black point for level adjustment.
   * @param m The mid-point for level adjustment.
   * @param w The white point for level adjustment.
   */
  void levelsAdjust(int b, int m, int w);

  /**
   * Applies color correction to the image.
   */
  void colorCorrect();
}
