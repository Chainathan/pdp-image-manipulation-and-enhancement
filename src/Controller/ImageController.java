package Controller;

import java.io.IOException;

/**
 * The ImageController interface represents the controller component
 * for image-related operations. Classes implementing this interface
 * are responsible for running and managing image-related tasks.
 */
public interface ImageController {

    /**
     * Runs the image-related operations or tasks.
     * @throws IOException If an error occurs during the run process.
     */
    void run() throws IOException;
}
