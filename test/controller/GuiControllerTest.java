//package controller;
//
//import model.FactoryRgbImage;
//import model.FactoryRgbImageModel;
//import model.ImageData;
//import org.junit.Test;
//import view.GuiView;
//import view.JFrameView;
//
//import static org.junit.Assert.*;
//
//public class GuiControllerTest {
//
//    static class JFrameViewMock implements GuiView{
//
//        private StringBuilder log;
//
//        public JFrameViewMock(StringBuilder log) {
//            this.log = log;
//        }
//
//
//        @Override
//        public void showWindow() {
//
//        }
//
//        @Override
//        public void showDiscardConfirmation() {
//
//        }
//
//        @Override
//        public void showLoadMenu() {
//
//        }
//
//        @Override
//        public void showSaveMenu() {
//
//        }
//
//        @Override
//        public void displayImage(ImageData imageData) {
//            log.append("Max Value : ").append(imageData.getMaxValue());
//            int[][][] data = imageData.getData();
//            log.append("{");
//            for(int i=0;i< data.length;i++){
//                for(int j=0;j<data[0].length;j++){
//                    for(int k=0;k<data[0][0].length;k++){
//                        log.append(data[i][j][k]).append(" ");
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void displayHistogram(ImageData imageData) {
//
//        }
//
//        @Override
//        public void addFeatures(Features features) {
//
//        }
//
//        @Override
//        public void showCompressMenu() {
//
//        }
//
//        @Override
//        public void showLvlAdjMenu() {
//
//        }
//
//        @Override
//        public void toggleSplit(boolean suportSplit) {
//
//        }
//
//        @Override
//        public void displayError(String message) {
//
//        }
//
//        @Override
//        public void setSplitLabel(String message) {
//
//        }
//
//        @Override
//        public void setSplitInput(String message) {
//
//        }
//    }
//    @Test
//    public void testBlurWithoutSplit(){
//        //GIVEN
//        FactoryRgbImageModel factory = new FactoryRgbImage();
////        GuiView view = new JFrameView("Image Processor");
//        GuiView view = new JFrameViewMock();
//        GuiController controller = new GuiController(factory, view);
//        controller.loadImage("images/test/test.ppm");
//
//        //WHEN
//        controller.blur();
//
//        //THEN
//
//    }
//}