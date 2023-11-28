# USEME File for Java Image Manipulation GUI Program

## Supported Operations and Usages:

### 1. Load Image

- Click the `load image` button.
- Select the desired file to be loaded.
- Click `load`.

**Conditions:**
- Supported image formats: PPM, PNG, JPG.

### 2. Save Image

- Click the `save image` button.
- Select the file directory to save the image.
- Provide a filename including the file extension.
- Click `save`.

**Conditions:**
- The file name should include a valid file extension: `.ppm`, `.png`, `.jpg`
- An image should be loaded before trying to save the image.

### 3. General Operations

#### Following are set of general operations:

- Horizontal flip
- Vertical flip
- Red component
- Green component
- Blue component
- Blur
- Sepia
- Sharpen
- Greyscale
- Color Correct

**Usage:**

- Select the corresponding option from the features dropdown menu.
- click `Apply` button to apply the operation on the image.

### 4. Compress

- Select the `Compress` option from the features dropdown menu.
- A slider for compression ratio will be made visible, set the appropriate compression ratio to be applied.
- click `Apply` button to apply the compression on the image.

### 5. Levels Adjust

- Select the `Levels adjust` option from the features dropdown menu.
- A slider for black(b), mid(m), white(w) will be made visible, set the appropriate values to be applied.
- click `Apply` button to apply the levels adjust on the image.

### 6. Preview

#### Following are set of operations that support preview:

- Blur
- Sepia
- Sharpen
- Greyscale
- Color Correct
- Levels Adjust

**Usage:**

- Select the corresponding option from the features dropdown menu.
- Preview slider will be enabled if a supported operation is selected.
- Changing the slider's value will show the "split" preview of the selected operation in real time.
- Click `Cancel` button or `Apply` button to toggle out of the split preview or to apply the operation on the image respectively.

### General Instructions
- An image should be loaded before trying any operations.