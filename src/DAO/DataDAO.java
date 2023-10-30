package DAO;

import java.io.IOException;

/**
 * The DataDAO interface defines methods for loading and saving image data.
 */
public interface DataDAO {

  /**
   * Load image data from a file.
   *
   * @param filePath The path to the file from which to load the image data.
   * @return An ImageData object containing the loaded image data.
   * @throws IOException If an error occurs during file reading or loading.
   */
  public ImageData load(String filePath) throws IOException;

  /**
   * Save image data to a file.
   *
   * @param filePath   The path to the file where the image data will be saved.
   * @param imageModel The ImageData object containing the data to be saved.
   * @throws IOException If an error occurs during file writing or saving.
   */
  public void save(String filePath, ImageData imageModel) throws IOException;
}