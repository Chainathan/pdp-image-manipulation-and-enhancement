package Model;

public enum FileFormatEnum {
  png,
  jpg,
  ppm;

    /*private final String extension;

    FileFormatEnum(String extension) {
        this.extension = extension;
    }

    String getExtension(){
        return extension;
    }*/

    /*RgbImeModel getImeModel() throws FileFormatNotSupportedException{
        if (this.equals(PNG) || this.equals(JPG) || this.equals(PPM)){
            return new RGBModelOld();
        }
        throw new FileFormatNotSupportedException("Unsupported File format");
    }*/
}
