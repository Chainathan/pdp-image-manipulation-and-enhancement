**********************************************
Image Manipulation And Enhancement Application
**********************************************

Overview
========
This Java application is designed for image processing tasks. It allows users to load, manipulate, and visualize images with various components such as red, green, blue, value, intensity, and luma.
The application supports different file formats (e.g., ASCII PPM, JPG, PNG) and provides a text-based view for displaying the operation results. Below are the key features of the application:

Changes:
Controller:
-RgbController class has been modified to adopt command design pattern, now taking a Factory object,
to create RgbImageModel objects, and a CommandMapper object to access and execute the respective
RgbImageModel operations, to take advantage of the command design pattern which allows us to keep
the future changes isolated and minimum as the number of commands increase in number.

-RgbImageFileIO class: extracted the logic to convert a bufferedImage to ImageData (wrapper class
for holding image data) into separate method to reuse in ImageGraphicsImpl class

-ComponentEnum: Moved from model package to controller package since we have tied the enum values
to the respective commands for components, the enum belongs more in controller than the model

-ComponentEnum: We have added a getter method for the "componentString" variable and removed the
"fromString()" method that was being used to create the component from its componentString
counterpart.
This change has been done for the following reasons: Previously ImageProcessorModel
received the "componentString" from the controller and created the corresponding ComponentEnum
to delegate it to the underlying RgbImageModels. But now since we have adopted command design
pattern and removed the ImageProcessorModel (justified the reason in the model section), the
command class will be passing the ComponentEnum directly to the RgbImageModel which removes the
need for "fromString()" method in the ComponentEnum.
But when creating the CommandMapper object and the underlying command classes, the link between
the "commands" (which is componentString in the Enum like red-component) and the Enum values
should be consistent in order to not allow bugs like allowing the controller to make the command
class call blue component when the command is actually red-component. So in order to enforce this
we have created the getter for the componentString, which is being used in the CommandMapper class.

Model:
-ChannelModel and RgbImageModel Interfaces have been modified to add in newer functionality
(new methods) since we are assuming that our current program is not being used by anyone which in
turn makes keeping the current design reduces the complexity and since we did not want to offer
the old and enhanced features separately.

-Similarly Channel and RgbImage classes that implement the above said interfaces have been modified
to implement the new methods

-ImageProcessorModel Interface and RgbImageProcessor class have been removed, since we have adopted
command design pattern, the command classes will take on the role to validate and delegate or
perform the logic that ImageProcessor previously used to do. Essentially, we have made
RgbImageModel our main model that the controller will be interacting with.

-Previously Blur and Sharpen commands were operated as follows: Imageprocessor passed the
respective kernel to the applyFilter() method which would apply the convolution on the image model.
Since the ImageProcessor has been removed, essentially the controller or rather the command class
has taken up the similar role, but it is not appropriate to have the command class pass in the
kernel to the models, we have created separate methods for blur and sharpen in the RgbImageModel
interfaces and have implemented the kernels directly in the image models, although the
applyFilter() still exists and is being made use by the blur and sharpen, we have changed the scope
to private and have removed it from the interface.

-Similarly we have modified applyTone() method and the corresponding operation "sepia".

-ImageProcessor class has been modified with the corresponding changes of removing said classes and
using the new constructor of RgbController as well the logic for supporting command line
argument of "-file" to run script files from the command line arguments.

Program Structure
=================

Controller
- ImageController: An interface for controlling image processing operations.
- RgbController: An implementation of ImageController for RGB image processing that parses input from command line and script files and delegates the operations to RgbImageCommand objects
which in turn uses RgbImageModel to perform the respective operations.
- CommandMapper: The CommandMapper interface defines methods for generating a map of commands associated with their corresponding implementations. It is used to map command strings to executable commands.
- CommandMapperBasic: The CommandMapperBasic class implements the CommandMapper interface and provides a basic set of commands for RGB image processing operations.
- CommandMapperBasic: The CommandMapperAdv class extends CommandMapperBasic and provides additional commands specific to advanced RGB image processing operations.
- RgbImageCommand: The RgbImageCommand interface defines a contract for commands that manipulate RGB images. Implementations of this interface should provide an execute method that performs the specified image
manipulation using the provided image list and command arguments.
- GenericCommand: The GenericCommand class implements the RgbImageCommand interface and represents a generic image processing command with a default argument, a function to apply on an
RGB image model, and an indication of whether the command supports splitting images.
- RgbCombine: The RgbCombine class implements the RgbImageCommand interface and represents a command to combine multiple RGB images into a single RGB image using a provided factory for
creating new image models.
- RgbSplit: The RgbSplit class implements the RgbImageCommand interface and represents a command to split an RGB image into its red, green, and blue components, creating new images
for each component in the provided image list.
- ImageGraphics: The ImageGraphics interface defines methods for drawing on an image and retrieving image data. Implementations of this interface should provide functionality for drawing lines, setting colors,
and obtaining the image data.
- ImageGraphicsImpl: The ImageGraphicsImpl class implements the ImageGraphics interface and provides a graphics plane for drawing lines, setting colors, and obtaining image data.

ImageFileIO
- ImageFileIO: An interface defining methods for loading and saving image data.
- RgbImageFileIO: An implementation of ImageFileIO for RGB images.

Exceptions
- FileFormatNotSupportedException: A custom exception class for handling unsupported file formats.

Model
- RgbImageModel: An interface for working with RGB images and set of operations applicable on an RGB image.
- RgbImage: A class representing a single RGB image and its set of operations.
- ChannelModel: An interface defining methods for working with image channels.
- Channel: A class representing an image channel with pixel data.
- ImageData: A Data Object class representing image data and pixel information in a raw form, used as an entity for passing image data to and from the model, controller, and view components.
- TriFunction: A functional interface for a three-argument function.
- FactoryRgbImage: The FactoryRgbImage class is a factory class that implements the FactoryRgbImageModel interface to provide a method for creating instances of RgbImageModel.
- FactoryRgbImageModel: The FactoryRgbImageModel interface defines a factory method for creating instances of RgbImageModel.

View
- ImageProcessorView: An interface for different views of image processing results.
- TextView: An implementation of ImageProcessorView for displaying text-based results.

Enum
- FileFormatEnum: An enum defining supported file formats.
- ComponentEnum: An enum defining various image components.
- ColorEnum: An enum defining colors corresponding to RGB.

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

Note
====
1. image-path, script-file-path : File path should be enclosed in double quotes if the file path contains whitespaces. This is optional if there are no whitespaces present.
   The file path should contain the relative path of the file from the context of program's root folder.
2. Supported image formats: PPM, PNG, JPG.
3. Supported script file format: TXT.
4. image-name : Image name cannot be empty, contain whitespaces, and line breaks.
5. The program supports single-line comments. Comments can be added by using '#' before starting the comment.
6. Inputs should be separated by line breaks for each operation.
