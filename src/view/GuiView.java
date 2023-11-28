package view;

import controller.Features;
import model.ImageData;

/**
 * The GuiView interface represents the graphical user interface (GUI) for
 * image processing applications.
 * Implementing classes are expected to provide methods for interacting with
 * and displaying the GUI components.
 */
public interface GuiView {
  /**
   * Toggles the preview mode in the GUI.
   *
   * @param isEnabled A boolean indicating whether preview mode should be enabled or disabled.
   */
  void togglePreview(boolean isEnabled);

  /**
   * Resets the dropdowns in the GUI to their default state.
   */
  void resetDropdown();

  /**
   * Resets the split percentage slider of the preview.
   */
  void resetPreviewSlider();

  /**
   * Resets all the input sliders.
   */
  void resetInputSliders();

  /**
   * Displays the main window of the GUI.
   */
  void showWindow();

  /**
   * Displays a confirmation dialog for discarding unsaved changes in the GUI.
   */
  void showDiscardConfirmation();

  /**
   * Displays the load menu in the GUI.
   */
  void showLoadMenu();

  /**
   * Displays the save menu in the GUI.
   */
  void showSaveMenu();

  /**
   * Displays the image represented by the provided ImageData in the GUI.
   *
   * @param imageData The ImageData object representing the image to be displayed.
   */
  void displayImage(ImageData imageData);

  /**
   * Displays the histogram represented by the provided ImageData in the GUI.
   *
   * @param imageData The ImageData object representing the histogram to be displayed.
   */
  void displayHistogram(ImageData imageData);

  /**
   * Adds the specified Features object to the GUI, enabling interaction with
   * image processing features and allowing view to use the functions provided
   * by the Features as callback functions in its action listeners.
   *
   * @param features The Features object representing image processing features
   *                 to be added to the GUI.
   */
  void addFeatures(Features features);

  /**
   * Displays the compress input menu in the GUI.
   */
  void showCompressMenu();

  /**
   * Displays the levels adjustment input menu in the GUI.
   */
  void showLvlAdjMenu();

  /**
   * Displays an error message in the GUI.
   *
   * @param message The error message to be displayed.
   */
  void displayError(String message);
}

