package Model;

import Exceptions.FileFormatNotSupportedException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RgbImageProcessor implements ImageProcessorModel {
    Map<String, RgbImeModel> imageList;

    @Override
    public void load(String destImageName, String filePath) throws IllegalArgumentException,
            IOException, FileFormatNotSupportedException {
        checkValidImageName(destImageName);

        RgbImeModel imageModel = RgbImageDAO.load(filePath);
        imageList.put(destImageName,imageModel);
    }

    @Override
    public void save(String imageName, String filePath) throws IllegalArgumentException,
            IOException, FileFormatNotSupportedException {
        checkImageNameExists(imageName);
        RgbImeModel imageModel = imageList.get(imageName);
        RgbImageDAO.save(filePath, imageModel);
    }

    @Override
    public void visualizeComponent(String imageName, String destImageName, String component)
            throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        ComponentEnum componentEnum = ComponentEnum.valueOf(component);

        RgbImeModel destImage = imageList.get(imageName).visualizeComponent(componentEnum);
        imageList.put(destImageName, destImage);
    }

    @Override
    public void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        RgbImeModel destImage = imageList.get(imageName).horizontalFlip();
        imageList.put(destImageName, destImage);
    }

    @Override
    public void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        RgbImeModel destImage = imageList.get(imageName).verticalFlip();
        imageList.put(destImageName, destImage);
    }

    @Override
    public void brighten(String imageName, String destImageName, int increment)
            throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        RgbImeModel destImage = imageList.get(imageName).brighten(increment);
        imageList.put(destImageName, destImage);
    }

    @Override
    public void darken(String imageName, String destImageName, int decrement)
            throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        RgbImeModel destImage = imageList.get(imageName).darken(decrement);
        imageList.put(destImageName, destImage);
    }

    @Override
    public void splitComponents(String imageName, List<String> destComponentImageList)
            throws IllegalArgumentException{
        destComponentImageList.forEach(this::checkImageNameExists);
        checkImageNameExists(imageName);

//        List<RgbImeModel> destImageList = imageList.get(imageName).rgbSplit();
//        imageList.put(destImageNameRed, destImageList.get(0));
//        imageList.put(destImageNameGreen, destImageList.get(1));
//        imageList.put(destImageNameBlue, destImageList.get(2));
    }

    @Override
    public void combineComponents(String destImageName, List<String> componentImageList)
            throws IllegalArgumentException{
        componentImageList.forEach(this::checkImageNameExists);
        checkValidImageName(destImageName);

//        RgbImeModel red = imageList.get(redImage);
//        RgbImeModel green = imageList.get(greenImage);
//        RgbImeModel blue = imageList.get(blueImage);
//        List<RgbImeModel> rgbList = new ArrayList<>();
//        rgbList.add(red);
//        rgbList.add(green);
//        rgbList.add(blue);
//
//        RgbImeModel destImage = red.rgbCombine(rgbList);
//        imageList.put(destImageName, destImage);
    }

    @Override
    public void blur(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        RgbImeModel destImage = imageList.get(imageName).blur();
        imageList.put(destImageName, destImage);
    }

    @Override
    public void sharpen(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        RgbImeModel destImage = imageList.get(imageName).sharpen();
        imageList.put(destImageName, destImage);
    }

    @Override
    public void sepia(String imageName, String destImageName) throws IllegalArgumentException{
        checkValidImageName(destImageName);
        checkImageNameExists(imageName);
        RgbImeModel destImage = imageList.get(imageName).sepia();
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
