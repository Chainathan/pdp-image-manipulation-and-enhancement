package controller;

import java.io.IOException;

import exceptions.FileFormatNotSupportedException;
import model.ImageData;

/**
 * The DataDAO interface defines methods for loading and saving image data.
 */
interface ImageFileIO {

  /**
   * Load image data from a file.
   *
   * @param filePath The path to the file from which to load the image data.
   * @return An ImageData object containing the loaded image data.
   * @throws IOException                     If an error occurs during file reading or loading.
   * @throws FileFormatNotSupportedException If the file format is not supported
   *                                         (other than .ppm, .png. .jpg).
   */
  ImageData load(String filePath) throws IOException, FileFormatNotSupportedException;

  /**
   * Save image data to a file.
   *
   * @param filePath   The path to the file where the image data will be saved.
   * @param imageModel The ImageData object containing the data to be saved.
   * @throws IOException                     If an error occurs during file writing or saving.
   * @throws FileFormatNotSupportedException If the file format is not supported
   *                                         (other than .ppm, .png. .jpg).
   */
  void save(String filePath, ImageData imageModel)
          throws IOException, FileFormatNotSupportedException;
}
