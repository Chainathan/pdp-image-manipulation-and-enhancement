package Controller;

import Exceptions.FileFormatNotSupportedException;
import Model.ComponentEnum;
import Model.ImeModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IMEController {
    Map<String,ImeModel> imageList;

    void load(String destImageName, String filePath) throws IllegalArgumentException,
            IOException, FileFormatNotSupportedException {
        checkValidImageName(destImageName);

        String extension = filePath.substring(filePath.lastIndexOf('.'));
        ImeModel imageModel = FileFormatEnum.valueOf(extension).getImeModel();
        imageModel.load(filePath);

        imageList.put(destImageName,imageModel);
    }

    void save(String imageName, String filePath) throws IllegalArgumentException,
            IOException, FileFormatNotSupportedException {
        checkImageNameExists(imageName);
        imageList.get(imageName).save(filePath);
    }

    void visualizeComponent(String imageName, String destImageName, String component)
            throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ComponentEnum componentEnum = ComponentEnum.valueOf(component);

        ImeModel destImage = imageList.get(imageName).visualizeComponent(componentEnum);
        imageList.put(destImageName, destImage);
    }

    void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ImeModel destImage = imageList.get(imageName).horizontalFlip();
        imageList.put(destImageName, destImage);
    }

    void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ImeModel destImage = imageList.get(imageName).verticalFlip();
        imageList.put(destImageName, destImage);
    }

    void brighten(String imageName, String destImageName, int increment)
            throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ImeModel destImage = imageList.get(imageName).brighten(increment);
        imageList.put(destImageName, destImage);
    }

    void darken(String imageName, String destImageName, int decrement)
            throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ImeModel destImage = imageList.get(imageName).darken(decrement);
        imageList.put(destImageName, destImage);
    }

    void rgbSplit(String imageName,
                  String destImageNameRed, String destImageNameGreen, String destImageNameBlue)
            throws IllegalArgumentException{
        checkValidImageName(destImageNameRed);
        checkValidImageName(destImageNameGreen);
        checkValidImageName(destImageNameBlue);
        checkImageNameExists(imageName);

        List<ImeModel> destImageList = imageList.get(imageName).rgbSplit();
        imageList.put(destImageNameRed, destImageList.get(0));
        imageList.put(destImageNameGreen, destImageList.get(1));
        imageList.put(destImageNameBlue, destImageList.get(2));
    }

    void rgbCombine(String destImageName, String redImage, String greenImage, String blueImage)
            throws IllegalArgumentException{
        checkImageNameExists(redImage);
        checkImageNameExists(greenImage);
        checkImageNameExists(blueImage);
        checkValidImageName(destImageName);

        ImeModel red = imageList.get(redImage);
        ImeModel green = imageList.get(greenImage);
        ImeModel blue = imageList.get(blueImage);
        List<ImeModel> rgbList = new ArrayList<>();
        rgbList.add(red);
        rgbList.add(green);
        rgbList.add(blue);

        ImeModel destImage = red.rgbCombine(rgbList);
        imageList.put(destImageName, destImage);
    }

    void blur(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ImeModel destImage = imageList.get(imageName).blur();
        imageList.put(destImageName, destImage);
    }

    void sharpen(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ImeModel destImage = imageList.get(imageName).sharpen();
        imageList.put(destImageName, destImage);
    }

    void sepia(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ImeModel destImage = imageList.get(imageName).sepia();
        imageList.put(destImageName, destImage);
    }

    private static void checkValidImageName(String imageName) throws IllegalArgumentException{
        if (imageName.trim().isEmpty()){
            throw new IllegalArgumentException("Invalid image name");
        }
    }
    private void checkImageNameExists(String imageName) throws IllegalArgumentException{
        if (!imageList.containsKey(imageName)){
            throw new IllegalArgumentException("Image does not exist");
        }
    }
}
