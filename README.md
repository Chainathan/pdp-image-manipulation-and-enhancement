# Image Manipulation And Enhancement Application

## Overview

This Java application is designed for image processing tasks. It allows users to load, manipulate, and visualize images with various components such as red, green, blue, value, intensity, and luma. The application supports different file formats (e.g., ASCII PPM, JPG, PNG) and provides a text-based view for displaying the operation results. Below are the key features of the application:

# Changes

## Controller

### RgbController

The `RgbController` class has been modified to adopt the command design pattern. It now takes a `Factory` object to create `RgbImageModel` objects and a `CommandMapper` object to access and execute the respective `RgbImageModel` operations. This change allows for better isolation of future changes as the number of commands increases.

### RgbImageFileIO

In the `RgbImageFileIO` class, the logic to convert a `BufferedImage` to `ImageData` (a wrapper class for holding image data) has been extracted into a separate method. This method is now reused in the `ImageGraphicsImpl` class.

### ComponentEnum

The `ComponentEnum` has been moved from the `model` package to the `controller` package. This move reflects the tie between the enum values and the respective commands for components, making the enum more fitting in the controller.

### ComponentEnum

In the `ComponentEnum`, a getter method for the "componentString" variable has been added, and the "fromString()" method has been removed. This change is made to align with the adoption of the command design pattern. Now, the command class passes the `ComponentEnum` directly to the `RgbImageModel`, eliminating the need for the "fromString()" method. To ensure consistency in the link between the "commands" and the `Enum` values, a getter for the `componentString` has been added, used in the `CommandMapper` class.

## Model

### ChannelModel and RgbImageModel Interfaces

The `ChannelModel` and `RgbImageModel` interfaces have been modified to add new functionality (new methods). This change assumes that the current program is not being used by anyone, reducing complexity, and providing enhanced features without offering the old and new features separately.

### Channel and RgbImage Classes

The `Channel` and `RgbImage` classes, which implement the above interfaces, have been modified to implement the new methods.

### ImageProcessorModel Interface and RgbImageProcessor Class

The `ImageProcessorModel` interface and `RgbImageProcessor` class have been removed. With the adoption of the command design pattern, the command classes now validate, delegate, or perform logic previously handled by `ImageProcessorModel`. `RgbImageModel` becomes the main model for interaction with the controller.

### Blur and Sharpen Commands

Previously, the `Blur` and `Sharpen` commands were operated by passing the respective kernel to the `applyFilter()` method in `RgbImageModel`. With the removal of `ImageProcessorModel`, the controller or command class takes on a similar role. However, it is inappropriate for the command class to pass the kernel to the models. Separate methods for `blur` and `sharpen` have been created in the `RgbImageModel` interfaces, implementing the kernels directly in the image models. The `applyFilter()` method, though still exists, is now private and removed from the interface.

### ApplyTone Method and "Sepia" Operation

Similar to `Blur` and `Sharpen` the `applyTone()` method and the corresponding `sepia` operation have been modified in the `RgbImageModel` interfaces.

### ImageProcessor Class

The `ImageProcessor` class has been modified to accommodate the changes mentioned above. Corresponding changes include removing certain classes, using the new constructor of `RgbController`, and adding logic to support the command line argument "-file" for running script files.

## Program Structure

### Controller

- **ImageController:** An interface for controlling image processing operations.
- **RgbController:** An implementation of `ImageController` for RGB image processing. It parses input from the command line and script files, delegates operations to `RgbImageCommand` objects, which, in turn, use `RgbImageModel` to perform the respective operations.
- **CommandMapper:** The `CommandMapper` interface defines methods for generating a map of commands associated with their corresponding implementations.
- **CommandMapperBasic:** The `CommandMapperBasic` class implements the `CommandMapper` interface, providing a basic set of commands for RGB image processing operations.
- **CommandMapperAdv:** The `CommandMapperAdv` class extends `CommandMapperBasic` and provides additional commands specific to advanced RGB image processing operations.
- **RgbImageCommand:** The `RgbImageCommand` interface defines a contract for commands that manipulate RGB images.
- **GenericCommand:** The `GenericCommand` class implements the `RgbImageCommand` interface, representing a generic image processing command with a default argument.
- **RgbCombine:** The `RgbCombine` class implements the `RgbImageCommand` interface, representing a command to combine multiple RGB images into a single RGB image.
- **RgbSplit:** The `RgbSplit` class implements the `RgbImageCommand` interface, representing a command to split an RGB image into its red, green, and blue components.
- **ImageGraphics:** The `ImageGraphics` interface defines methods for drawing on an image and retrieving image data.
- **ImageGraphicsImpl:** The `ImageGraphicsImpl` class implements the `ImageGraphics` interface, providing a graphics plane for drawing lines, setting colors, and obtaining image data.

### ImageFileIO

- **ImageFileIO:** An interface defining methods for loading and saving image data.
- **RgbImageFileIO:** An implementation of `ImageFileIO` for RGB images.

### Exceptions

- **FileFormatNotSupportedException:** A custom exception class for handling unsupported file formats.

### Model

- **RgbImageModel:** An interface for working with RGB images and set of operations applicable on an RGB image.
- **RgbImage:** A class representing a single RGB image and its set of operations.
- **ChannelModel:** An interface defining methods for working with image channels.
- **Channel:** A class representing an image channel with pixel data.
- **ImageData:** A Data Object class representing image data and pixel information in a raw form, used as an entity for passing image data to and from the model, controller, and view components.
- **TriFunction:** A functional interface for a three-argument function.
- **FactoryRgbImage:** The `FactoryRgbImage` class is a factory class that implements the `FactoryRgbImageModel` interface to provide a method for creating instances of `RgbImageModel`.
- **FactoryRgbImageModel:** The `FactoryRgbImageModel` interface defines a factory method for creating instances of `RgbImageModel`.

### View

- **ImageProcessorView:** An interface for different views of image processing results.
- **TextView:** An implementation of `ImageProcessorView` for displaying text-based results.

### Enum

- **FileFormatEnum:** An enum defining supported file formats.
- **ComponentEnum:** An enum defining various image components.
- **ColorEnum:** An enum defining colors corresponding to RGB.

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