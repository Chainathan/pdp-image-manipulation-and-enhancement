//package sample;
//
//import Exceptions.FileFormatNotSupportedException;
//import Model.ChannelModel;
//import Model.ComponentEnum;
//import Controller.RgbImageDAO;
//import Model.RgbImeModel;
//
//import java.io.IOException;
//import java.util.List;
//
//public class RGBModelOld implements RgbImeModel {
//    private ChannelModel redChannel;
//    private ChannelModel greenChannel;
//    private ChannelModel blueChannel;
//    private int maxValue;
//
//    //private RgbImageDAO rgbImageDAO;  //static
//
//    public RGBModelOld() {
//        redChannel = new ChannelModel();
//        greenChannel = new ChannelModel();
//        blueChannel = new ChannelModel();
//        //rgbImageDAO = new RgbImageDAO();
//    }
//
//
////    private void initChannelList(int[][][] channelValues){
////        redChannel = new ChannelModel(channelValues[0]);
////        greenChannel = new ChannelModel(channelValues[1]);
////        blueChannel = new ChannelModel(channelValues[2]);
////    }
//
//
//    @Override
//    public void load(String filePath) throws IOException, FileFormatNotSupportedException {
//        RgbImageDAO.load(filePath, this);
//    }
//
//
//    @Override
//    public void save(String filePath) throws FileFormatNotSupportedException, IOException {
//
//    }
//
//    @Override
//    public RgbImeModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException {
//        RGBModelOld rgbModel = new RGBModelOld();
//        int height = this.redChannel.pixels.length;
//        int width = this.redChannel.pixels[0].length;
//        if(componentEnum.equals(ComponentEnum.RED)){
//            rgbModel.setRedChannel(this.redChannel);
//            for(int i=0;i<height;i++){
//                for(int j=0;j<width;j++){
//                    rgbModel.greenChannel.pixels[i][j]=0;
//                    rgbModel.blueChannel.pixels[i][j]=0;
//                }
//            }
//        }
//        return rgbModel;
//    }
//
//    @Override
//    public RgbImeModel horizontalFlip() {
//        return null;
//    }
//
//    @Override
//    public RgbImeModel verticalFlip() {
//        return null;
//    }
//
//    @Override
//    public RgbImeModel brighten(int increment) throws IllegalArgumentException {
//        return null;
//    }
//
//    @Override
//    public RgbImeModel darken(int decrement) throws IllegalArgumentException {
//        return null;
//    }
//
//    @Override
//    public List<RgbImeModel> rgbSplit() {
//        return null;
//    }
//
//    @Override
//    public RgbImeModel rgbCombine(List<RgbImeModel> imageModelList) throws IllegalArgumentException {
//        return null;
//    }
//
//    @Override
//    public RgbImeModel blur() {
//        return null;
//    }
//
//    @Override
//    public RgbImeModel sharpen() {
//        return null;
//    }
//
//    @Override
//    public RgbImeModel sepia() {
//        return null;
//    }
//
//    public void setRedChannel(ChannelModel redChannel) {
//        this.redChannel = redChannel;
//    }
//
//    public void setGreenChannel(ChannelModel greenChannel) {
//        this.greenChannel = greenChannel;
//    }
//
//    public void setBlueChannel(ChannelModel blueChannel) {
//        this.blueChannel = blueChannel;
//    }
//
//    public void setMaxValue(int maxValue) {
//        this.maxValue = maxValue;
//    }
//}
