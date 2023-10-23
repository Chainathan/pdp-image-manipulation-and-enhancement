package Exceptions;

public class FileFormatNotSupportedException extends RuntimeException{
  public FileFormatNotSupportedException(String message) {
    super(message);
  }
}
