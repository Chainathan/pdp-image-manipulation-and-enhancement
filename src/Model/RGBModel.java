package Model;

import Exceptions.FileFormatNotSupportedException;

import java.io.IOException;
import java.util.List;

public class RGBModel extends ImageModel{

    @Override
    public ImeModel load(String filePath) throws IOException, FileFormatNotSupportedException {
        return null;
    }

    @Override
    public void save(String filePath) throws FileFormatNotSupportedException, IOException {

    }

    @Override
    public ImeModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ImeModel horizontalFlip() {
        return null;
    }

    @Override
    public ImeModel verticalFlip() {
        return null;
    }

    @Override
    public ImeModel brighten(int increment) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ImeModel darken(int decrement) throws IllegalArgumentException {
        return null;
    }

    @Override
    public List<ImeModel> rgbSplit() {
        return null;
    }

    @Override
    public ImeModel rgbCombine(List<ImeModel> imageModelList) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ImeModel blur() {
        return null;
    }

    @Override
    public ImeModel sharpen() {
        return null;
    }

    @Override
    public ImeModel sepia() {
        return null;
    }
}
