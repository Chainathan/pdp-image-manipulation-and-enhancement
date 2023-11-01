package Model;

import Model.ImageData;
import Model.Channel;
import Model.ChannelModel;
import Model.RgbImage;
import Model.RgbImageModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class RgbImageTest {

    class MockChannel implements ChannelModel{

        private final StringBuilder log;
        private final int[][] dummyChannel= {
                {1,2,3},
                {4,5,6},
                {7,8,9},
                {10,11,12}
        };
        public MockChannel(StringBuilder log){
            this.log = log;
        }

        @Override
        public int[][] getChannelValues() {
            int[][] channel = {
                    {1,2,3},
                    {4,5,6},
                    {7,8,9},
                    {10,11,12}
            };
            return channel;
        }

        @Override
        public ChannelModel getHorizontalFlipChannel() {
            log.append("HorizontalFlip is called");
            int[][] flippedChannel = {
                    {11,12,13},
                    {14,15,16},
                    {17,18,19},
                    {20,21,22}
            };
            return new Channel(flippedChannel);
        }

        @Override
        public ChannelModel getVerticalFlipChannel() {
            log.append("VerticalFlip is called");
            int[][] flippedChannel = {
                    {11,12,13},
                    {14,15,16},
                    {17,18,19},
                    {20,21,22}
            };
            return new Channel(flippedChannel);
        }

        @Override
        public ChannelModel addBuffer(int buffer, int maxPixelValues) {
            log.append("Buffer : ")
                            .append(buffer)
                    .append(" Max pixel value : ")
                    .append(maxPixelValues);
            int[][] bufferChannel = {
                    {11,12,13},
                    {14,15,16},
                    {17,18,19},
                    {20,21,22}
            };
            return new Channel(bufferChannel);
        }

        @Override
        public ChannelModel applyConvolution(double[][] kernel, int maxPixelValues) {
            int height = kernel.length;
            int width = kernel[0].length;
            log.append("Kernel : { ");
            for (int i = 0; i < height; i++) {
                log.append("{ ");
                for (int j = 0; j < width; j++) {
                    log.append(kernel[i][j] + " ");
                }
                log.append("} ");
            }
            log.append("} \nMax Pixel Size : "+maxPixelValues);
            int[][] convChannel = {
                    {11,12,13},
                    {14,15,16},
                    {17,18,19},
                    {20,21,22}
            };
            return new Channel(convChannel);
        }

        @Override
        public int getHeight() {
            return dummyChannel.length;
        }

        @Override
        public int getWidth() {
            return dummyChannel[0].length;
        }

        @Override
        public int getValue(int x, int y) throws IllegalArgumentException {
            return dummyChannel[x][y];
        }
    }

    private void assertUtility(RgbImageModel actualImage){
        int[][] expectedChannel = {
                {11,12,13},
                {14,15,16},
                {17,18,19},
                {20,21,22}
        };
        int[][][] actual =  actualImage.getImageData().getData();
        int height = actual[0].length;
        int width = actual[0][0].length;
        //THEN
        assertEquals(255,actualImage.getImageData().getMaxValue());
        assertEquals(expectedChannel.length, height);
        assertEquals(expectedChannel[0].length, width);
        for(int i=0;i< height;i++){
            for(int j=0;j<width;j++){
                assertEquals(expectedChannel[i][j],actual[0][i][j]);
                assertEquals(expectedChannel[i][j],actual[1][i][j]);
                assertEquals(expectedChannel[i][j],actual[2][i][j]);
            }
        }
    }

    @Test
    public void testBrightenWithPositiveIncrement(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );

        //WHEN
        RgbImageModel actualImageModel = rgbImage.brighten(50);
        //THEN
        assertEquals("Buffer : 50 Max pixel value : 255",log.toString());
        assertUtility(actualImageModel);
    }

    @Test
    public void testBrightenWithNegativeIncrement(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );

        //WHEN
        RgbImageModel actualImageModel = rgbImage.brighten(50);
        //THEN
        assertEquals("Buffer : -50 Max pixel value : 255",log.toString());
        assertUtility(actualImageModel);
    }

    @Test
    public void testHorizontalFlip(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );

        //WHEN
        RgbImageModel actualImageModel = rgbImage.horizontalFlip();
        //THEN
        assertEquals("HorizontalFlip is called",log.toString());
        assertUtility(actualImageModel);
    }

    @Test
    public void testVerticalFlip(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );

        //WHEN
        RgbImageModel actualImageModel = rgbImage.verticalFlip();
        //THEN
        assertEquals("VerticalFlip is called",log.toString());
        assertUtility(actualImageModel);
    }

    @Test
    public void testApplyFilter(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rbgImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        StringBuilder expectedLog = new StringBuilder();
        double[][] kernel = {
                {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
                {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
                {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
                {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
                {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
        };
        expectedLog.append("Kernel : { ");
        for (double[] doubles : kernel) {
            expectedLog.append("{ ");
            for (int j = 0; j < kernel[0].length; j++) {
                expectedLog.append(doubles[j]).append(" ");
            }
            expectedLog.append("} ");
        }
        expectedLog.append("} \nMax Pixel Size : 255");

        //WHEN
        RgbImageModel sharpenedImage = rbgImage.applyFilter(kernel);

        //THEN
        assertEquals(expectedLog.toString(),log.toString());
        assertUtility(sharpenedImage);
    }

    @Test
    public void testApplyTone(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rbgImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        double[][] buffer = {
                {1,0,2},
                {0,1,2},
                {2,0,1}
        };

        int[][][] expectedImage = {
                {
                        {3,6,9},
                        {12,15,18},
                        {21,24,27},
                        {30,33,36}
                },
                {
                        {3,6,9},
                        {12,15,18},
                        {21,24,27},
                        {30,33,36}
                },
                {
                        {3,6,9},
                        {12,15,18},
                        {21,24,27},
                        {30,33,36}
                }
        };
        //WHEN
        RgbImageModel tonedImage = rbgImage.applyTone(buffer);
        assertImages(expectedImage, tonedImage);
    }

    private void assertImages(int[][][] expectedImage, RgbImageModel rgbImageModel){
        int[][][] actual = rgbImageModel.getImageData().getData();
        int height = actual[0].length;
        int width = actual[0][0].length;
        //THEN
        assertEquals(255, rgbImageModel.getImageData().getMaxValue());
        for(int i=0;i< height;i++){
            for(int j=0;j<width;j++){
                assertEquals(expectedImage[0][i][j],actual[0][i][j]);
                assertEquals(expectedImage[1][i][j],actual[1][i][j]);
                assertEquals(expectedImage[2][i][j],actual[2][i][j]);
            }
        }
    }

    @Test
    public void visualizeComponentForRed(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        int[][][] expectedImage = {
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                },
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                }
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.RED);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForGreen(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        int[][][] expectedImage = {
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                }
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.GREEN);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForBlue(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        int[][][] expectedImage = {
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                },
                {
                        {0,0,0},
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                }
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.BLUE);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForLUMA(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        int[][][] expectedImage = {
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                }
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.LUMA);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForIntensity(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        int[][][] expectedImage = {
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                }
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.INTENSITY);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void visualizeComponentForValue(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        int[][][] expectedImage = {
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                }
        };
        //WHEN
        RgbImageModel visualizedImage = rgbImage.visualizeComponent(ComponentEnum.VALUE);

        //THEN
        assertImages(expectedImage, visualizedImage);
    }

    @Test
    public void testRgbImageForInvalidChannelSize(){
        try{
            RgbImageModel rgbImageModel = new RgbImage(
                    new Channel(),
                    new Channel(new int[3][4]),
                    new Channel(),
                    255
            );
            fail("Above line should throw an exception.");
        }
        catch (IllegalArgumentException e){
            //Exception catched.
        }
    }

    @Test
    public void testRgbImageForInvalidMaxPixelSize(){
        try{
            RgbImageModel rgbImageModel = new RgbImage(
                    new Channel(),
                    new Channel(),
                    new Channel(),
                    -2
            );
            fail("Above line should throw an exception.");
        }
        catch (IllegalArgumentException e){
            //Exception catched.
        }
    }

    @Test
    public void testRgbImageForValidArguments(){
        try{
            RgbImageModel rgbImageModel = new RgbImage(
                    new Channel(),
                    new Channel(),
                    new Channel(),
                    255
            );
        }
        catch (IllegalArgumentException e){
            fail("Above line should not throw an exception.");
        }
    }

    @Test
    public void testGetImageData(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        RgbImageModel rgbImage = new RgbImage(
                new MockChannel(log),
                new MockChannel(new StringBuilder()),
                new MockChannel(new StringBuilder()),
                255
        );
        int[][][] expectedImage = {
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                },
                {
                        {1,2,3},
                        {4,5,6},
                        {7,8,9},
                        {10,11,12}
                }
        };
        //THEN
        assertImages(expectedImage, rgbImage);

    }

    @Test
    public void testLoadImageData(){
        try{
            RgbImageModel rgbImageModel = new RgbImage();
            int[][][] values = {
                    {
                            {1,2,3},
                            {4,5,6},
                            {7,8,9},
                            {10,11,12}
                    },
                    {
                            {1,2,3},
                            {4,5,6},
                            {7,8,9},
                            {10,11,12}
                    },
                    {
                            {1,2,3},
                            {4,5,6},
                            {7,8,9},
                            {10,11}
                    }
            };
            ImageData imageData = new ImageData(values,255);
            rgbImageModel.loadImageData(imageData);
            fail("Above line should throw an exception.");
        }
        catch (IllegalArgumentException e){
            //Exception catched.
        }
    }
}