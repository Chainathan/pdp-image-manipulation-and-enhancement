# Image Manipulation And Enhancement Application

## Overview

This Java application is designed for image processing tasks. It allows users to load, manipulate, and visualize images with various components such as red, green, blue, value, intensity, and luma. The application supports different file formats (e.g., ASCII PPM, JPG, PNG) and provides a text-based view for displaying the operation results. Below are the key features of the application:

## Key Features

1. **Load Images**: Load an image from an ASCII PPM, JPG, or PNG file.
2. **Visualize Components**: Create images that visualize individual R, G, and B components of an image.
3. **Visualize Properties**: Create images that visualize the value, intensity, or luma of an image.
4. **Image Flipping**: Flip an image horizontally or vertically.
5. **Brightness Control**: Brighten or darken an image.
6. **Image Splitting**: Split a single image into three images, each representing one of the three color channels.
7. **Image Combination**: Combine three grayscale images into a single color image, with R, G, and B values from the three images.
8. **Image Enhancement**: Apply blur or sharpening effects to an image.
9. **Sepia Conversion**: Convert an image into sepia.
10. **Save Images**: Save an image to an ASCII PPM, JPG, or PNG file.
11. **Text-Based Scripting**: Allow users to interact with the program using text-based scripting.

## Program Structure

### controller
- **ImageController**: An interface for controlling image processing operations.
- **RgbController**: An implementation of `ImageController` for RGB image processing that parses input from command line and script files and delegates the operations to `ImageProcessorModel`.

### ImageFileIO
- **ImageFileIO**: An interface defining methods for loading and saving image data.
- **RgbImageFileIO**: An implementation of `ImageFileIO` for RGB images.

### exceptions
- **FileFormatNotSupportedException**: A custom exception class for handling unsupported file formats.

### model
- **ImageProcessorModel**: An interface for image processing models - a collection of images and set of operations applicable on the images.
- **RgbImageProcessor**: An implementation of `ImageProcessorModel` for RGB image processing.
- **RgbImageModel**: An interface for working with RGB images and set of operations applicable on an RGB image.
- **RgbImage**: A class representing a single RGB image and its set of operations.
- **ChannelModel**: An interface defining methods for working with image channels.
- **Channel**: A class representing an image channel with pixel data.
- **ImageData**: A Data Object class representing image data and pixel information in a raw form, used as an entity for passing image data to and from the model, controller, and view components.
- **TriFunction**: A functional interface for a three-argument function.

### view
- **ImageProcessorView**: An interface for different views of image processing results.
- **TextView**: An implementation of `ImageProcessorView` for displaying text-based results.

### Enum
- **FileFormatEnum**: An enum defining supported file formats.
- **ComponentEnum**: An enum defining various image components.

## Usage

To use this application, follow these steps:
1. Initialize the necessary components, such as the model, view, and controller.
2. Run the image processing operations using the controller.

## Example

```java
public class ImageProcessor {
  public static void main(String[] args) {
    // Initialize the image processing components
    ImageProcessorModel model = new RgbImageProcessor();
    ImageProcessorView textView = new TextView(System.out);
    ImageController controller = new RgbController(model, textView, new InputStreamReader(System.in);

    // Run image processing operations
    try {
      controller.run();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
```
---
## Run A Script

The script file to execute is present under images directory.
Type this command when the program runs :
```
run script/leo-script.txt
```
`leo-script.txt` contains the set of image manipulation and enhancement operations that can be performed on 
the given image leo.png. The results are saved in `res/` directory.

- As per the current design the program accepts only `.txt` files for script files.

## Image Attribution and Authorization

The image used in this project is an original work and is owned by [Aathira Sunil Pillai]. [Aathira Sunil Pillai] authorizes its use in this project.

![Image Description](res/leo.png)

## Authors

- Aathira Sunil Pillai
- Chainathan Santhanam Sudhakar

---

This README file provides an overview of the classes and their purposes in the image processing application, along with usage instructions and an example for running the program.