package Controller;

import Exceptions.FileFormatNotSupportedException;
import Model.ImeModel;
import Model.RGBModel;

public enum FileFormatEnum {
    PNG(".png"),
    JPG(".jpg"),
    PPM(".ppm");

    private final String extension;

    FileFormatEnum(String extension) {
        this.extension = extension;
    }

    String getExtension(){
        return extension;
    }

    ImeModel getImeModel() throws FileFormatNotSupportedException{
        if (this.equals(PNG) || this.equals(JPG) || this.equals(PPM)){
            return new RGBModel();
        }
        throw new FileFormatNotSupportedException("Unsupported File format");
    }
}
