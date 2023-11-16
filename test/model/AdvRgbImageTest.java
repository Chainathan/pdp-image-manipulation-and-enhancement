package model;

import org.junit.Test;

import controller.ImageGraphicsImpl;

import static org.junit.Assert.*;

/**
 * Test class for RGB Image class.
 */
public class AdvRgbImageTest {
    //
////    class MockChannel implements ChannelModel {
////
////        private final StringBuilder log;
////        private final double[][] dummyChannel = {
////                {1, 2, 3},
////                {4, 5, 6},
////                {7, 8, 9},
////                {10, 11, 12}
////        };
////
////        public MockChannel(StringBuilder log) {
////            this.log = log;
////        }
////
////
////        @Override
////        public ChannelModel createInstance(double[][] channelValues) {
////            return new MockChannel(log);
////        }
////
////        @Override
////        public double[][] getChannelValues() {
////            double[][] channel = new double[dummyChannel.length][dummyChannel[0].length];
////            for(int i=0;i< channel.length;i++){
////                for(int j=0;j<channel[0].length;j++){
////                    channel[i][j] = dummyChannel[i][j];
////                }
////            }
////            return channel;
////        }
////
////        @Override
////        public ChannelModel getHorizontalFlipChannel() {
////            log.append("HorizontalFlip is called");
////            double[][] flippedChannel = {
////                    {11, 12, 13},
////                    {14, 15, 16},
////                    {17, 18, 19},
////                    {20, 21, 22}
////            };
////            return new Channel(flippedChannel);
////        }
////
////        @Override
////        public ChannelModel getVerticalFlipChannel() {
////            log.append("VerticalFlip is called");
////            double[][] flippedChannel = {
////                    {11, 12, 13},
////                    {14, 15, 16},
////                    {17, 18, 19},
////                    {20, 21, 22}
////            };
////            return new Channel(flippedChannel);
////        }
////
////        @Override
////        public ChannelModel addBuffer(int buffer, int maxPixelValues) {
////            log.append("Buffer : ")
////                    .append(buffer)
////                    .append(" Max pixel value : ")
////                    .append(maxPixelValues);
////            double[][] bufferChannel = {
////                    {11, 12, 13},
////                    {14, 15, 16},
////                    {17, 18, 19},
////                    {20, 21, 22}
////            };
////            return new Channel(bufferChannel);
////        }
////
////        @Override
////        public ChannelModel applyConvolution(double[][] kernel, int maxPixelValues) {
////            int height = kernel.length;
////            int width = kernel[0].length;
////            log.append("Kernel : { ");
////            for (int i = 0; i < height; i++) {
////                log.append("{ ");
////                for (int j = 0; j < width; j++) {
////                    log.append(kernel[i][j] + " ");
////                }
////                log.append("} ");
////            }
////            log.append("} \nMax Pixel Size : " + maxPixelValues);
////            double[][] convChannel = {
////                    {11, 12, 13},
////                    {14, 15, 16},
////                    {17, 18, 19},
////                    {20, 21, 22}
////            };
////            return new Channel(convChannel);
////        }
////
////        @Override
////        public int getHeight() {
////            return dummyChannel.length;
////        }
////
////        @Override
////        public int getWidth() {
////            return dummyChannel[0].length;
////        }
////
////        @Override
////        public double getValue(int x, int y) throws IllegalArgumentException {
////            return dummyChannel[x][y];
////        }
////
////        @Override
////        public ChannelModel applyHaarTransform() {
////            return null;
////        }
////
////        @Override
////        public ChannelModel applyHaarInverse() {
////            return null;
////        }
////
////        @Override
////        public ChannelModel applyThreshold(double threshold) {
////            return null;
////        }
////
////        @Override
////        public ChannelModel applyPadding() {
////            return null;
////        }
////
////        @Override
////        public ChannelModel applyUnpad(int originalHeight, int originalWidth) throws IllegalArgumentException {
////            return null;
////        }
////
////        @Override
////        public int[] getFrequencyValues() {
////            return new int[0];
////        }
////
////        @Override
////        public int getMaxFreqPixel() {
////            return 0;
////        }
////
////        @Override
////        public ChannelModel adjustLevels(int b, int m, int w) throws IllegalArgumentException {
////            return null;
////        }
////
////        @Override
////        public ChannelModel cropVertical(int start, int end) throws IllegalArgumentException {
////            return null;
////        }
////
////        @Override
////        public ChannelModel overlapOnBase(ChannelModel otherChannel, int start) throws IllegalArgumentException {
////            log.append("Start value : ").append(start).append("\n");
////            log.append("Other channel : { ");
////            double[][] values = otherChannel.getChannelValues();
////            int height = otherChannel.getHeight();
////            int width = otherChannel.getWidth();
////            for (int i = 0; i < height; i++) {
////                log.append("{ ");
////                for (int j = 0; j < width; j++) {
////                    log.append(values[i][j] + " ");
////                }
////                log.append("} ");
////            }
////            log.append("}");
////            return createInstance(otherChannel.getChannelValues());
////        }
////
////
////    }
//

    @Test
    public void testCreateHistogram(){
      //GIVEN
      int[][] red = {{255, 228, 46},
              {255, 255, 255},
              {198, 255, 255},
              {255, 255, 255}};
      int[][] green = {{255, 228, 46},
                      {255, 255, 255},
                      {198, 255, 255},
                      {255, 255, 255}};
      int[][] blue = {{0, 0, 0},
              {0, 0, 0},
              {0, 0, 0},
              {0, 0, 0}};

      ChannelModel channel1 = new Channel(red);
      ChannelModel channel2 = new Channel(green);
      ChannelModel channel3 = new Channel(blue);
      RgbImageModel rgbImage = new RgbImage(
              channel1, channel2, channel3,
              255
      );
      int[][] expectedChannel = {
              {2},
              {5},
              {8},
              {11}
      };
      int[][][] expectedImage = {
              expectedChannel,
              expectedChannel,
              expectedChannel
      };
      //WHEN
      RgbImageModel croppedImage = rgbImage.createHistogram(new ImageGraphicsImpl(5,5, 0));
      printData(croppedImage.getImageData());
      //THEN
//      assertTrue(assertImages(expectedImage, croppedImage));
    }
    @Test
    public void testOverlapOnBase() {
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        RgbImageModel otherImage = new RgbImage(
                channel, channel, channel,
                255
        );
        int[][] expectedChannel = {
                {1, 2, 1},
                {4, 5, 4},
                {7, 8, 7},
                {10, 11, 10}
        };
        int[][][] expectedImage = {
                expectedChannel,
                expectedChannel,
                expectedChannel
        };
        //WHEN
        RgbImageModel overlappedImage = rgbImage.overlapOnBase(otherImage, 50);

        //THEN
        assertTrue(assertImages(expectedImage, overlappedImage));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOverlapOnBaseForNegativeStart() {
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        RgbImageModel otherImage = new RgbImage(
                channel, channel, channel,
                255
        );

        //WHEN
        rgbImage.overlapOnBase(otherImage, -50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOverlapOnBaseForStartGreaterThan100() {
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        RgbImageModel otherImage = new RgbImage(
                channel, channel, channel,
                255
        );

        //WHEN
        rgbImage.overlapOnBase(otherImage, 150);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOverlapOnBaseForNullImage() {
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );

        //WHEN
        rgbImage.overlapOnBase(null, 50);
    }

    @Test
    public void testCropVertical(){
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        int[][] expectedChannel = {
                {2},
                {5},
                {8},
                {11}
        };
        int[][][] expectedImage = {
                expectedChannel,
                expectedChannel,
                expectedChannel
        };
        //WHEN
        RgbImageModel croppedImage = rgbImage.cropVertical(20, 50);

        //THEN
        assertTrue(assertImages(expectedImage, croppedImage));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCropVerticalForNegativeStart(){
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        //WHEN
        rgbImage.cropVertical(-20, 50);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCropVerticalForStartGreaterThan100(){
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        //WHEN
        rgbImage.cropVertical(120, 50);


    }

    @Test(expected = IllegalArgumentException.class)
    public void testCropVerticalNegativeEnd(){
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        //WHEN
        rgbImage.cropVertical(20, -50);


    }

    @Test(expected = IllegalArgumentException.class)
    public void testCropVerticalEndGreaterThan100(){
        //GIVEN
        int[][] value = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
                {10, 11, 12}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        //WHEN
        rgbImage.cropVertical(20, 150);
    }

    @Test
    public void testAdjustLevels(){
        //GIVEN
        int[][] value = {
                {241, 80, 30},
                {220, 158, 106},
                {70, 118, 90},
                {100, 110, 120}
        };
        ChannelModel channel = new Channel(value);
        RgbImageModel rgbImage = new RgbImage(
                channel, channel, channel,
                255
        );
        int[][][] expectedImage ={{{255, 228, 46},
                {255, 255, 255},
                {198, 255, 255},
                {255, 255, 255}},
                {{255, 228, 46},
                        {255, 255, 255},
                        {198, 255, 255},
                        {255, 255, 255}},
                {{255, 228, 46},
                        {255, 255, 255},
                        {198, 255, 255},
                        {255, 255, 255}}
        };
        //WHEN
        RgbImageModel adjustedImage = rgbImage.adjustLevels(20, 50,255);
        //THEN
        assertTrue(assertImages(expectedImage, adjustedImage));
    }
    @Test(expected = IllegalArgumentException.class)
    public void adjustLevelsForNegativeBlack(){
          int[][] value = {
                  {241, 80, 30},
                  {220, 158, 106},
                  {70, 118, 90},
                  {100, 110, 120}
          };
          ChannelModel channel = new Channel(value);
          RgbImageModel rgbImage = new RgbImage(
                  channel, channel, channel,
                  255
          );
          rgbImage.adjustLevels(-20, 50,255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForNegativeMid(){
      int[][] value = {
              {241, 80, 30},
              {220, 158, 106},
              {70, 118, 90},
              {100, 110, 120}
      };
      ChannelModel channel = new Channel(value);
      RgbImageModel rgbImage = new RgbImage(
              channel, channel, channel,
              255
      );
    rgbImage.adjustLevels(0, -20,255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForNegativeWhite(){
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(0, 20,-255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForBlackGreatherThanMid(){
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(30, 20,255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForMidGreatherThanWhite(){
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(30, 255,200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void adjustLevelsForWhiteGreatherThan255(){
    int[][] value = {
            {241, 80, 30},
            {220, 158, 106},
            {70, 118, 90},
            {100, 110, 120}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    rgbImage.adjustLevels(0, 20,260);
  }


  @Test
  public void testCorrectColor(){
    //GIVEN
    int[][] red = {
            {241, 80, 30},
            {220, 158, 158},
            {70, 100, 100},
            {100, 110, 120}
    };
    int[][] green = {
            {158, 80, 30},
            {220, 158, 158},
            {70, 100, 100},
            {158, 110, 120}
    };
    int[][] blue = {
            {241, 80, 30},
            {220, 158, 158},
            {70, 100, 100},
            {100, 110, 120}
    };
    ChannelModel channel1 = new Channel(red);
    ChannelModel channel2 = new Channel(green);
    ChannelModel channel3 = new Channel(blue);
    RgbImageModel rgbImage = new RgbImage(
            channel1, channel2, channel3,
            255
    );
    int[][][] expectedImage ={
            {{255, 99, 49},
            {239, 177, 177},
            {89, 119, 119},
            {119, 129, 139}},
            {{119, 41, 0},
                    {181, 119, 119},
                    {31, 61, 61},
                    {119, 71, 81}},
            {{255, 99, 49},
                    {239, 177, 177},
                    {89, 119, 119},
                    {119, 129, 139}}
    };
    //WHEN
    RgbImageModel colorCorrected = rgbImage.correctColor();
    //THEN
    assertTrue(assertImages(expectedImage, colorCorrected));
  }


  @Test
  public void testApplyCompression(){
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    int[][][] expectedImage = {
            {{3, 3, 4},
                    {3, 3, 4},
                    {9, 9, 10},
                    {9, 9, 10}},
                    {{3, 3, 4},
                            {3, 3, 4},
                            {9, 9, 10},
                            {9, 9, 10}},
                    {{3, 3, 4},
                            {3, 3, 4},
                            {9, 9, 10},
                            {9, 9, 10}}
            };
    //WHEN
    RgbImageModel compressedImage = rgbImage.applyCompression(50);

    //THEN
    assertTrue(assertImages(expectedImage, compressedImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyCompressionForNegativeCompression(){
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    //WHEN
    rgbImage.applyCompression(-50);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testApplyCompressionForCompressionGreaterThan100(){
    int[][] value = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
            {10, 11, 12}
    };
    ChannelModel channel = new Channel(value);
    RgbImageModel rgbImage = new RgbImage(
            channel, channel, channel,
            255
    );
    //WHEN
    rgbImage.applyCompression(150);

  }



    private void printData(ImageData data){
        System.out.print("{");
        for(int k=0;k<3;k++){
            System.out.print("{");
            for(int i=0;i<data.getData()[0].length;i++){
                System.out.print("{");
                for(int j=0;j<data.getData()[0][0].length;j++){
                    if(j+1==data.getData()[0][0].length){
                        System.out.print(data.getData()[k][i][j]);
                    }
                    else{
                        System.out.print(data.getData()[k][i][j] + ", ");
                    }
                }
                if(i+1==data.getData()[0].length){
                    System.out.print("}");
                }
                else{
                    System.out.println("},");
                }
            }
            if(k+1==3){
                System.out.println("}");
            }
            else{
                System.out.println("},");
            }
        }
        System.out.print("}");
    }

    private boolean assertImages(int[][][] expectedImage, RgbImageModel rgbImageModel) {
        int[][][] actual = rgbImageModel.getImageData().getData();
        int height = actual[0].length;
        int width = actual[0][0].length;
        //THEN
        if(rgbImageModel.getImageData().getMaxValue()!=255){
          return false;
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (expectedImage[0][i][j] != actual[0][i][j]
                        || expectedImage[1][i][j] != actual[1][i][j]
                        || expectedImage[2][i][j] != actual[2][i][j])
                    return false;
            }
        }
        return true;
    }
//
//    private void assertUtility(RgbImageModel actualImage) {
//        int[][][] expectedImage = {
//                {{11, 12, 13},
//                        {14, 15, 16},
//                        {17, 18, 19},
//                        {20, 21, 22}},
//                {{11, 12, 13},
//                        {14, 15, 16},
//                        {17, 18, 19},
//                        {20, 21, 22}},
//                {{11, 12, 13},
//                        {14, 15, 16},
//                        {17, 18, 19},
//                        {20, 21, 22}}
//        };
//        assertImages(expectedImage, actualImage);
//    }
//
//    @Test
//    public void testBrightenWithPositiveIncrement() {
//        //GIVEN
//        StringBuilder log = new StringBuilder();
//        RgbImageModel rgbImage = new RgbImage(
//                new MockChannel(log),
//                new MockChannel(new StringBuilder()),
//                new MockChannel(new StringBuilder()),
//                255
//        );
//
//        //WHEN
//        RgbImageModel actualImageModel = rgbImage.brighten(50);
//        //THEN
//        assertEquals("Buffer : 50 Max pixel value : 255", log.toString());
//        assertUtility(actualImageModel);
//    }
//
//    @Test
//    public void testBrightenWithNegativeIncrement() {
//        //GIVEN
//        StringBuilder log = new StringBuilder();
//        RgbImageModel rgbImage = new RgbImage(
//                new MockChannel(log),
//                new MockChannel(new StringBuilder()),
//                new MockChannel(new StringBuilder()),
//                255
//        );
//
//        //WHEN
//        RgbImageModel actualImageModel = rgbImage.brighten(-50);
//        //THEN
//        assertEquals("Buffer : -50 Max pixel value : 255", log.toString());
//        assertUtility(actualImageModel);
//    }
//
//    @Test
//    public void testHorizontalFlip() {
//        //GIVEN
//        StringBuilder log = new StringBuilder();
//        RgbImageModel rgbImage = new RgbImage(
//                new MockChannel(log),
//                new MockChannel(new StringBuilder()),
//                new MockChannel(new StringBuilder()),
//                255
//        );
//
//        //WHEN
//        RgbImageModel actualImageModel = rgbImage.horizontalFlip();
//        //THEN
//        assertEquals("HorizontalFlip is called", log.toString());
//        assertUtility(actualImageModel);
//    }
//
//    @Test
//    public void testVerticalFlip() {
//        //GIVEN
//        StringBuilder log = new StringBuilder();
//        RgbImageModel rgbImage = new RgbImage(
//                new MockChannel(log),
//                new MockChannel(new StringBuilder()),
//                new MockChannel(new StringBuilder()),
//                255
//        );
//
//        //WHEN
//        RgbImageModel actualImageModel = rgbImage.verticalFlip();
//        //THEN
//        assertEquals("VerticalFlip is called", log.toString());
//        assertUtility(actualImageModel);
//    }
//
////    @Test
////    public void testApplyFilter() {
////        //GIVEN
////        StringBuilder log = new StringBuilder();
////        RgbImageModel rbgImage = new RgbImage(
////                new MockChannel(log),
////                new MockChannel(new StringBuilder()),
////                new MockChannel(new StringBuilder()),
////                255
////        );
////        StringBuilder expectedLog = new StringBuilder();
////        double[][] kernel = {
////                {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
////                {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
////                {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
////                {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
////                {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
////        };
////        expectedLog.append("Kernel : { ");
////        for (double[] doubles : kernel) {
////            expectedLog.append("{ ");
////            for (int j = 0; j < kernel[0].length; j++) {
////                expectedLog.append(doubles[j]).append(" ");
////            }
////            expectedLog.append("} ");
////        }
////        expectedLog.append("} \nMax Pixel Size : 255");
////
////        //WHEN
////        RgbImageModel filteredImage = rbgImage.applyFilter(kernel);
////
////        //THEN
////        assertEquals(expectedLog.toString(), log.toString());
////        assertUtility(filteredImage);
////    }
//
////    @Test
////    public void testApplyTone() {
////        //GIVEN
////        StringBuilder log = new StringBuilder();
////        RgbImageModel rbgImage = new RgbImage(
////                new MockChannel(log),
////                new MockChannel(new StringBuilder()),
////                new MockChannel(new StringBuilder()),
////                255
////        );
////        double[][] buffer = {
////                {1, 0, 2},
////                {0, 1, 2},
////                {2, 0, 1}
////        };
////
////        int[][][] expectedImage = {
////                {{3, 6, 9}, {12, 15, 18}, {21, 24, 27}, {30, 33, 36}},
////                {{3, 6, 9}, {12, 15, 18}, {21, 24, 27}, {30, 33, 36}},
////                {{3, 6, 9}, {12, 15, 18}, {21, 24, 27}, {30, 33, 36}}
////        };
////        //WHEN
////        RgbImageModel tonedImage = rbgImage.applyTone(buffer);
////        assertImages(expectedImage, tonedImage);
////    }
//
////    @Test(expected = IllegalArgumentException.class)
////    public void testApplyToneForBufferHeightNotThree() {
////        //GIVEN
////        StringBuilder log = new StringBuilder();
////        RgbImageModel rbgImage = new RgbImage(
////                new MockChannel(log),
////                new MockChannel(new StringBuilder()),
////                new MockChannel(new StringBuilder()),
////                255
////        );
////        double[][] buffer = {
////                {1, 0, 2},
////                {0, 1, 2}
////        };
////
////        //WHEN
////        rbgImage.applyTone(buffer);
////    }
//
////    @Test(expected = IllegalArgumentException.class)
////    public void testApplyToneForBufferWidthtNotThree() {
////        //GIVEN
////        StringBuilder log = new StringBuilder();
////        RgbImageModel rbgImage = new RgbImage(
////                new MockChannel(log),
////                new MockChannel(new StringBuilder()),
////                new MockChannel(new StringBuilder()),
////                255
////        );
////        double[][] buffer = {
////                {1, 0, 2},
////                {0, 1, 2},
////                {1, 1}
////        };
////
////        //WHEN
////        rbgImage.applyTone(buffer);
////    }
//
////    private void assertImages(int[][][] expectedImage, RgbImageModel rgbImageModel) {
////        double[][][] actual = rgbImageModel.getImageData().getData();
////        int height = actual[0].length;
////        int width = actual[0][0].length;
////        //THEN
////        assertEquals(255, rgbImageModel.getImageData().getMaxValue());
////        for (int i = 0; i < height; i++) {
////            for (int j = 0; j < width; j++) {
////                assertEquals(expectedImage[0][i][j], actual[0][i][j]);
////                assertEquals(expectedImage[1][i][j], actual[1][i][j]);
////                assertEquals(expectedImage[2][i][j], actual[2][i][j]);
////            }
////        }
////    }
//
    @Test
    public void visualizeComponentForRed() {
        //GIVEN
      int[][][] imageValues = new int[][][]{
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
              {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
              {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
      };
      ChannelModel channel1 = new Channel(imageValues[0]);
      ChannelModel channel2 = new Channel(imageValues[1]);
      ChannelModel channel3 = new Channel(imageValues[2]);
      RgbImageModel rgbImage = new RgbImage(channel1,channel2,channel3,255);
        int[][][] expectedImage = {
                            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.RED);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForGreen() {
        //GIVEN
      int[][][] imageValues = new int[][][]{
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
              {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
              {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
      };
      ChannelModel channel1 = new Channel(imageValues[0]);
      ChannelModel channel2 = new Channel(imageValues[1]);
      ChannelModel channel3 = new Channel(imageValues[2]);
      RgbImageModel rgbImage = new RgbImage(channel1,channel2,channel3,255);
        int[][][] expectedImage = {
                            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{10, 11, 12}, {13, 14, 15}, {16, 17, 18}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.GREEN);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForBlue() {
        //GIVEN
      int[][][] imageValues = new int[][][]{
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
              {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
              {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
      };
      ChannelModel channel1 = new Channel(imageValues[0]);
      ChannelModel channel2 = new Channel(imageValues[1]);
      ChannelModel channel3 = new Channel(imageValues[2]);
      RgbImageModel rgbImage = new RgbImage(channel1,channel2,channel3,255);
        int[][][] expectedImage = {
                            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{19, 20, 21}, {22, 23, 24}, {25, 26, 27}}
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.BLUE);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForLUMA() {
        //GIVEN
      int[][][] imageValues = new int[][][]{
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
              {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
              {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
      };
      ChannelModel channel1 = new Channel(imageValues[0]);
      ChannelModel channel2 = new Channel(imageValues[1]);
      ChannelModel channel3 = new Channel(imageValues[2]);
      RgbImageModel rgbImage = new RgbImage(channel1,channel2,channel3,255);
        int[][][] expectedImage = {
                            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}},
            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}},
            {{3, 4, 5}, {2, 3, 4}, {7, 8, 9}}
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.LUMA);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForIntensity() {
        //GIVEN
      int[][][] imageValues = new int[][][]{
              {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
              {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
              {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
      };
      ChannelModel channel1 = new Channel(imageValues[0]);
      ChannelModel channel2 = new Channel(imageValues[1]);
      ChannelModel channel3 = new Channel(imageValues[2]);
      RgbImageModel rgbImage = new RgbImage(channel1,channel2,channel3,255);
        int[][][] expectedImage = {
                            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}},
            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}},
            {{2, 3, 4}, {4, 5, 6}, {6, 7, 8}}
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.INTENSITY);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForValue() {
        //GIVEN
          int[][][] imageValues = new int[][][]{
            {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}},
            {{4, 5, 6}, {1, 2, 3}, {7, 8, 9}},
            {{1, 2, 3}, {7, 8, 9}, {4, 5, 6}}
    };
      ChannelModel channel1 = new Channel(imageValues[0]);
      ChannelModel channel2 = new Channel(imageValues[1]);
      ChannelModel channel3 = new Channel(imageValues[2]);
      RgbImageModel rgbImage = new RgbImage(channel1,channel2,channel3,255);
        int[][][] expectedImage = {
                {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}},
            {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}},
            {{4, 5, 6}, {7, 8, 9}, {7, 8, 9}}
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.VALUE);
        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void testRgbImageForInvalidChannelSize() {
        try {
            RgbImageModel rgbImageModel = new RgbImage(
                    new Channel(),
                    new Channel(new int[3][4]),
                    new Channel(),
                    255
            );
            fail("Above line should throw an exception.");
        } catch (IllegalArgumentException e) {
            //Exception catched.
        }
    }

    @Test
    public void testRgbImageForInvalidMaxPixelSize() {
        try {
            RgbImageModel rgbImageModel = new RgbImage(
                    new Channel(),
                    new Channel(),
                    new Channel(),
                    -2
            );
            fail("Above line should throw an exception.");
        } catch (IllegalArgumentException e) {
            //Exception catched.
        }
    }

    @Test
    public void testRgbImageForValidArguments() {
        try {
            RgbImageModel rgbImageModel = new RgbImage(
                    new Channel(),
                    new Channel(),
                    new Channel(),
                    255
            );
        } catch (IllegalArgumentException e) {
            fail("Above line should not throw an exception.");
        }
    }

    @Test
    public void testGetImageData() {
        //GIVEN
        int[][] values = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        ChannelModel channel = new Channel(values);
        RgbImageModel rgbImage = new RgbImage(channel,channel,channel,255);
        int[][][] expectedImage = {
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
        };
        //THEN
        assertImages(expectedImage, rgbImage);

    }

    @Test
    public void testLoadImageDataForInvalidRgb() {
        try {
            RgbImageModel rgbImageModel = new RgbImage();
            int[][][] values = {
                    {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
                    {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
            };
            ImageData imageData = new ImageData(values, 255);
            rgbImageModel.loadImageData(imageData);
            fail("Above line should throw an exception.");
        } catch (IllegalArgumentException e) {
            //Exception catched.
        }
    }

    @Test
    public void testLoadImageDataForValidRgb() {
        try {
            RgbImageModel rgbImageModel = new RgbImage();
            int[][][] values = {
                    {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
                    {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
                    {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
            };
            ImageData imageData = new ImageData(values, 255);
            rgbImageModel.loadImageData(imageData);
        } catch (IllegalArgumentException e) {
            fail("Above line should not throw an exception.");
        }
    }

    @Test
    public void testCheckValidRgbImageData(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImage rgbImage = new RgbImage();
        int[][][] image = {
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
        };
        ImageData imageData = new ImageData(image, 255);

        //WHEN
        try{
            rgbImage.checkValidRgbImageData(imageData);
            //Above line won't throw an exception.
        }
        catch (IllegalArgumentException e){
            fail("Above line should not throw an exception");
        }
    }

    @Test
    public void testCheckInValidRgbImageData(){
        //GIVEN
        RgbImage rgbImage = new RgbImage();
        int[][][] image = {
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}},
                {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}}
        };
        ImageData imageData = new ImageData(image, 255);

        //WHEN
        try{
            rgbImage.checkValidRgbImageData(imageData);
            fail("Above line should throw an exception");
        }
        catch (IllegalArgumentException e){
            //exception catched.
        }
    }
}