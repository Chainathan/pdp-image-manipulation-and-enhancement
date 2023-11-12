Compression -
-> Channel
Draw histogram -
-> calculate frequency in channel
-> draw the histogram using frquency in rgb model
Color correction
-> get pixel value which has max frequency() in channel
-> find avg and difference with the avg from all the maxVlues and call buffer() on the respective channels
Level Adjustment
-> level adjust in channel
Split view
-> ?
==========================================
greyscale - split???
Changes:
variable "int[][] channelValues" private to package private in Channel
variable "int[][] channelValues" to "double[][]" and all the corresponding signatures
variables in rgbImage - private to package-private.
variables in rgbImageProcessor - private to package-private.
RgbFileIO -
class - package private to public
load helper method - static to non static
extracted buffimage to imagedata method into a public static method
Channel model:
getValue() - argument name
**********************************************
Image Manipulation And Enhancement Application
**********************************************

Overview
========
This Java application is designed for image processing tasks. It allows users to load, manipulate, and visualize images with various components such as red, green, blue, value, intensity, and luma.
The application supports different file formats (e.g., ASCII PPM, JPG, PNG) and provides a text-based view for displaying the operation results. Below are the key features of the application:

Key Features
============
1. Load Images: Load an image from an ASCII PPM, JPG, or PNG file.
2. Visualize Components: Create images that visualize individual R, G, and B components of an image.
3. Visualize Properties: Create images that visualize the value, intensity, or luma of an image.
4. Image Flipping: Flip an image horizontally or vertically.
5. Brightness Control: Brighten or darken an image.
6. Image Splitting: Split a single image into three images, each representing one of the three color channels.
7. Image Combination: Combine three grayscale images into a single color image, with R, G, and B values from the three images.
8. Image Enhancement: Apply blur or sharpening effects to an image.
9. Sepia Conversion: Convert an image into sepia.
10. Save Images: Save an image to an ASCII PPM, JPG, or PNG file.
11. Text-Based Scripting: Allow users to interact with the program using text-based scripting.

Program Structure
=================

Controller
- ImageController: An interface for controlling image processing operations.
- RgbController: An implementation of ImageController for RGB image processing that parses input from command line and script files and delegates the operations to ImageProcessorModel.

ImageFileIO
- ImageFileIO: An interface defining methods for loading and saving image data.
- RgbImageFileIO: An implementation of ImageFileIO for RGB images.

Exceptions
- FileFormatNotSupportedException: A custom exception class for handling unsupported file formats.

Model
- ImageProcessorModel: An interface for image processing models - a collection of images and set of operations applicable on the images.
- RgbImageProcessor: An implementation of ImageProcessorModel for RGB image processing.
- RgbImageModel: An interface for working with RGB images and set of operations applicable on an RGB image.
- RgbImage: A class representing a single RGB image and its set of operations.
- ChannelModel: An interface defining methods for working with image channels.
- Channel: A class representing an image channel with pixel data.
- ImageData: A Data Object class representing image data and pixel information in a raw form, used as an entity for passing image data to and from the model, controller, and view components.
- TriFunction: A functional interface for a three-argument function.

View
- ImageProcessorView: An interface for different views of image processing results.
- TextView: An implementation of ImageProcessorView for displaying text-based results.

Enum
- FileFormatEnum: An enum defining supported file formats.
- ComponentEnum: An enum defining various image components.

Usage
=====
To use this application, follow these steps:
1. Initialize the necessary components, such as the model, view, and controller.
2. Run the image processing operations using the controller.

Example
=======
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

Supported Command Instructions
==============================
The program supports loading, manipulating and saving images using simple text-based commands. Here is a list of example test commands that are supported. The program supports this syntax exactly.
1. load image-path image-name: Load an image from the specified path and refer it to henceforth in the program by the given image name.
2. save image-path image-name: Save the image with the given name to the specified path which should include the name of the file.
3. red-component image-name dest-image-name: Create an image with the red-component of the image with the given name, and refer to it henceforth in the program by the given destination name. 
   Similar commands for green, blue, value, luma, intensity components are supported.
4. horizontal-flip image-name dest-image-name: Flip an image horizontally to create a new image, referred to henceforth by the given destination name.
5. vertical-flip image-name dest-image-name: Flip an image vertically to create a new image, referred to henceforth by the given destination name.
6. brighten increment image-name dest-image-name: brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. 
   The increment may be positive (brightening) or negative (darkening).
7. rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue: split the given image into three images containing its red, green and blue components respectively. 
   These would be the same images that would be individually produced with the red-component, green-component and blue-component commands. If duplicate dest-image-name are provided,
   the duplicate image name will be overridden with the last corresponding component's data.
8. rgb-combine image-name red-image green-image blue-image: Combine the three images that are individually red, green and blue into a single image that gets its red, green and 
   blue components from the three images respectively.
9. blur image-name dest-image-name: blur the given image and store the result in another image with the given name.
10. sharpen image-name dest-image-name: sharpen the given image and store the result in another image with the given name.
11. sepia image-name dest-image-name: produce a sepia-toned version of the given image and store the result in another image with the given name.
12. run script-file-path: Load and run the script commands in the specified file.
13. exit: To terminate the program.
// Advanced operations
compress percentage image-name dest-image-name
histogram image-name dest-image-name
color-correct image-name dest-image-name
levels-adjust b m w image-name dest-image-name

blur image-name dest-image split p :
The operations that must support this are
blur,
sharpen,
sepia,
greyscale,
color correction and
levels adjustment.
-file name-of-script.txt

Note
====
1. image-path, script-file-path : File path should be enclosed in double quotes if the file path contains whitespaces. This is optional if there are no whitespaces present.
   The file path should contain the relative path of the file from the context of program's root folder.
2. Supported image formats: PPM, PNG, JPG.
3. Supported script file format: TXT.
4. image-name : Image name cannot be empty, contain whitespaces, and line breaks.
5. The program supports single-line comments. Comments can be added by using '#' before starting the comment.
6. Inputs should be separated by line breaks for each operation.

Run A Script
============
The script file to execute is present under images directory.
Type this command when the program runs :
run script/leo-script.txt

leo-script.txt contains the set of image manipulation and enhancement operations that can be performed on
the given image leo.png. The results are saved in res/ directory.

- As per the current design the program accepts only .txt files for script files.

Image Attribution and Authorization
===================================
The image used in this project is an original work and is owned by [Aathira Sunil Pillai]. [Aathira Sunil Pillai] authorizes its use in this project.

Authors
=======
- Aathira Sunil Pillai
- Chainathan Santhanam Sudhakar

This README file provides an overview of the classes and their purposes in the image processing application, along with usage instructions and an example for running the program.
