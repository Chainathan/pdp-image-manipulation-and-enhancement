package exceptions;

/**
 * The FileFormatNotSupportedException is a custom exception class
 * that represents an exception thrown when a file format is not supported.
 */
public class FileFormatNotSupportedException extends RuntimeException {

  /**
   * Constructs a new FileFormatNotSupportedException with the specified error message.
   *
   * @param message The error message describing the file format not being supported.
   */
  public FileFormatNotSupportedException(String message) {
    super(message);
  }
}
