import DAO.ImageData;
import Model.Channel;
import Model.ChannelModel;
import Model.RgbImage;
import Model.RgbImageModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class RgbImageTest {

    class MockChannel implements ChannelModel{

        private final StringBuilder log;

        public MockChannel(StringBuilder log){
            this.log = log;
        }

        @Override
        public int[][] getChannelValues() {
            return new int[0][];
        }

        @Override
        public ChannelModel getHorizontalFlipChannel() {
            return null;
        }

        @Override
        public ChannelModel getVerticalFlipChannel() {
            return null;
        }

        @Override
        public ChannelModel addBuffer(int buffer, int maxPixelValues) {
            return null;
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
            int[][] dummyChannel = {
                    {1,2,3},
                    {4,5,6},
                    {7,8,9},
                    {10,11,12}
            };
            return new Channel(dummyChannel);
        }

        @Override
        public int getHeight() {
            return 0;
        }

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getValue(int x, int y) throws IllegalArgumentException {
            return 0;
        }
    }

    @Test
    public void testSharpen(){
        //GIVEN
        StringBuilder log = new StringBuilder();
        //Made RgbImage constructor public.
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
        int[][] expectedChannel = {
                {1,2,3},
                {4,5,6},
                {7,8,9},
                {10,11,12}
        };

        //WHEN
        RgbImageModel sharpenedImage = rbgImage.sharpen();
        ImageData sharpenedData = sharpenedImage.getImageData();
        int[][][] actual = sharpenedData.getData();
        int height = actual[0].length;
        int width = actual[0][0].length;
        //THEN
        assertEquals(expectedLog.toString(),log.toString());
        assertEquals(255,sharpenedData.getMaxValue());
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
}