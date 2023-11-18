# USEME File for Java Image Manipulation Program

## Supported Commands:

### 1. Load Image
**Command:**
```
load image-path image-name
```
**Example:**
```
load "images/sample.jpg" myImage
```
**Conditions:**
- The image-path should be enclosed in double quotes if it contains whitespaces.
- Supported image formats: PPM, PNG, JPG.
- Image name cannot be empty, contain whitespaces, and line breaks.

### 2. Save Image
**Command:**
```
save image-path image-name
```
**Example:**
```
save "output/modified.jpg" myModifiedImage
```
**Conditions:**
- The image-path should include the name of the file and be enclosed in double quotes if it contains whitespaces.

### 3. Component Extraction
- **Red Component:**
```
red-component image-name dest-image-name
```
- **Green, Blue, Value, Luma, Intensity Components:**
```
green-component image-name dest-image-name
blue-component image-name dest-image-name
value-component image-name dest-image-name
luma-component image-name dest-image-name
intensity-component image-name dest-image-name
```
**Example:**
```
red-component myImage redImage
```

### 4. Image Flipping
- **Horizontal Flip:**
```
horizontal-flip image-name dest-image-name
```
- **Vertical Flip:**
```
vertical-flip image-name dest-image-name
```
**Example:**
```
horizontal-flip myImage flippedImage
```

### 5. Brighten/Darken Image
**Command:**
```
brighten increment image-name dest-image-name
```
**Example:**
```
brighten 30 myImage brightenedImage
```
**Conditions:**
- The increment may be positive (brightening) or negative (darkening).

### 6. RGB Splitting
**Command:**
```
rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue
```
**Example:**
```
rgb-split myImage redImage greenImage blueImage
```

### 7. RGB Combining
**Command:**
```
rgb-combine image-name red-image green-image blue-image
```
**Example:**
```
rgb-combine myImage redImage greenImage blueImage
```

### 8. Image Blurring
**Command:**
```
blur image-name dest-image-name
```
**Example:**
```
blur myImage blurredImage
```

### 9. Image Sharpening
**Command:**
```
sharpen image-name dest-image-name
```
**Example:**
```
sharpen myImage sharpenedImage
```

### 10. Sepia Effect
**Command:**
```
sepia image-name dest-image-name
```
**Example:**
```
sepia myImage sepiaImage
```

### 11. Run Script from File
**Command:**
```
run script-file-path
```
**Example:**
```
run "scripts/myscript.txt"
```
**Conditions:**
- The script-file-path should contain the relative path from the program's root folder.
- Supported script file format: TXT.

### 12. Exit
**Command:**
```
exit
```
**Conditions:**
- If used inside a script file, the program will end the script.
- If used in a command-line script, the program will terminate.

## Advanced Operations:

### 13. Image Compression
**Command:**
```
compress percentage image-name dest-image-name
```
**Example:**
```
compress 50 myImage compressedImage
```
**Conditions:**
- Percentage should be between 0 and 100.

### 14. Histogram Generation
**Command:**
```
histogram image-name dest-image-name
```
**Example:**
```
histogram myImage histogramImage
```

### 15. Color Correction
**Command:**
```
color-correct image-name dest-image-name
```
**Example:**
```
color-correct myImage correctedImage
```

### 16. Levels Adjustment
**Command:**
```
levels-adjust b m w image-name dest-image-name
```
**Example:**
```
levels-adjust 10 50 200 myImage adjustedImage
```
**Conditions:**
- Values of b, m, and w should be within 0 and 255 and in ascending order.

### 17. Image Splitting
**Command:**
```
supported-command-operation-with-arguments split p
```
**Example:**
```
blur myImage splitImage split 30
```
**Conditions:**
- Supported for operations: blur, sharpen, sepia, greyscale, color correction, luma-component and levels adjustment.
- 'p' is a percentage of the width (e.g., 50 means placing the line halfway through the width of the image).

### 18. Command-Line Script
**Command:**
```
-file name-of-script.txt
```
**Example:**
```
-file "scripts/mycommandscript.txt"
```
**Conditions:**
- Supports passing a script file to run via the command-line argument.
- Supports .txt files only.

### General Instructions
- If any directory contains whitespaces, whole directory must be wrapped in double quotes.
- If the targeted `image-name` in any of the operations does not exist or was not created/loaded into the instance of the program, the program will fail since the image does not exist.