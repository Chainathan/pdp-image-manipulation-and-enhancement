package Model;

import Model.ImageData;
import Model.*;
import org.junit.Test;

public class RgbImageProcessorTest {


//    class MockRgbModel implements RgbImageModel{
//
//        private final StringBuilder log;
//        private final int[][] dummyChannel;
//
//        public MockRgbModel(int[][] dummyChannel,StringBuilder log)
//        {
//            this.log = log;
//            this.dummyChannel = dummyChannel;
//        }
//        @Override
//        public RgbImageModel visualizeComponent(ComponentEnum componentEnum) throws IllegalArgumentException {
//            log.append("Component : ").append(componentEnum);
//            ChannelModel channel = new Channel(dummyChannel);
//            return new RgbImage(channel,channel,channel,255);
//        }
//
//        @Override
//        public RgbImageModel horizontalFlip() {
//            return null;
//        }
//
//        @Override
//        public RgbImageModel verticalFlip() {
//            return null;
//        }
//
//        @Override
//        public RgbImageModel brighten(int increment) throws IllegalArgumentException {
//            return null;
//        }
//
//        @Override
//        public RgbImageModel darken(int decrement) throws IllegalArgumentException {
//            return null;
//        }
//
//        @Override
//        public RgbImageModel blur() throws IllegalArgumentException {
//            return null;
//        }
//
//        @Override
//        public RgbImageModel sharpen() throws IllegalArgumentException {
//            return null;
//        }
//
//        @Override
//        public RgbImageModel sepia() throws IllegalArgumentException {
//            return null;
//        }
//
//        @Override
//        public ImageData getImageData() {
//            return null;
//        }
//
//        @Override
//        public void loadImageData(ImageData imageData) {
//
//        }
//    }
//    @Test
//    public void testVisualizeComponent(){
//        //GIVEN
//        RgbImageProcessor rgbImageProcessor = new RgbImageProcessor();
//        int[][] dummyChannel = {
//                {1,2,3},
//                {4,5,6},
//                {7,8,9},
//                {10,11,12}
//        };
//        int[][][] image1 = {
//                {{1,2,3}, {4,5,6}, {7,8,9}},
//                {{4,5,6}, {1,2,3}, {7,8,9}},
//                {{1,2,3}, {7,8,9}, {4,5,6}}
//        };
//        ImageData imageData = new ImageData(image1,255);
//        rgbImageProcessor.addImage("image1",imageData);
//        //WHEN
//        rgbImageProcessor.visualizeComponent("image1","visualizedImage","red-component");
//
//        //THEN
//
//    }

}