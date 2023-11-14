package controller;

import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.ImageProcessorView;
import view.TextView;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import static org.junit.Assert.*;

public class AdvRgbControllerTest {

    String originalImage;
    String destImage;
    String expectedImage;
    Appendable appendable;
    ImageProcessorView textView;
    ImageData imageData;
    FactoryRgbImageModel factory;
    CommandMapper commandMapper;
    @Before
    public void setup() {
        appendable = new StringBuilder();
        textView = new TextView(appendable);
        factory = new FactoryRgbImage();
        commandMapper = new CommandMapperAdv();
        originalImage = "test";
        destImage = "result";
    }

    //test grayscale split.
    //test histogram
    //test compress without any percentage.
    @Test
    public void testCompress(){
        double[][][] exp ={
                {{154.0, 154.0, 154.0, 154.0, 38.0},
                {154.0, 154.0, 154.0, 154.0, 38.0},
                {154.0, 154.0, 154.0, 154.0, 38.0},
                {154.0, 154.0, 154.0, 154.0, 38.0}},
                {{91.0, 91.0, 91.0, 91.0, 22.0},
                        {91.0, 91.0, 91.0, 91.0, 22.0},
                        {91.0, 91.0, 91.0, 91.0, 22.0},
                        {91.0, 91.0, 91.0, 91.0, 22.0}},
                {{63.0, 63.0, 63.0, 63.0, 15.0},
                        {63.0, 63.0, 63.0, 63.0, 15.0},
                        {63.0, 63.0, 63.0, 63.0, 15.0},
                        {63.0, 63.0, 63.0, 63.0, 15.0}}
        };
        imageData = new ImageData(exp, 255);
        assertTrue(testHelper("compress 90 %s %s",imageData));
    }

    @Test
    public void testColorCorrect(){
        double[][][] exp ={{{101.0, 103.0, 110.0, 104.0, 104.0},
                {104.0, 108.0, 100.0, 103.0, 105.0},
                {101.0, 103.0, 106.0, 103.0, 105.0},
                {103.0, 105.0, 111.0, 110.0, 104.0}},
                {{102.0, 104.0, 107.0, 103.0, 103.0},
                        {105.0, 106.0, 98.0, 102.0, 103.0},
                        {101.0, 103.0, 104.0, 98.0, 103.0},
                        {103.0, 103.0, 109.0, 108.0, 105.0}},
                {{96.0, 100.0, 104.0, 97.0, 97.0},
                        {101.0, 103.0, 95.0, 94.0, 102.0},
                        {101.0, 103.0, 104.0, 104.0, 102.0},
                        {103.0, 102.0, 108.0, 107.0, 103.0}}
        };
        imageData = new ImageData(exp, 255);
        assertTrue(testHelper("color-correct %s %s",imageData));
    }

    @Test
    public void testLevelAdjust(){
        double[][][] exp ={{{187.0, 189.0, 196.0, 190.0, 190.0},
                {190.0, 194.0, 186.0, 189.0, 191.0},
                {187.0, 189.0, 192.0, 189.0, 191.0},
                {189.0, 191.0, 197.0, 196.0, 190.0}},
                {{114.0, 117.0, 121.0, 115.0, 115.0},
                        {118.0, 119.0, 108.0, 114.0, 115.0},
                        {112.0, 115.0, 117.0, 108.0, 115.0},
                        {115.0, 115.0, 123.0, 122.0, 118.0}},
                {{66.0, 72.0, 78.0, 67.0, 67.0},
                        {74.0, 77.0, 64.0, 62.0, 75.0},
                        {74.0, 77.0, 78.0, 78.0, 75.0},
                        {77.0, 75.0, 84.0, 83.0, 77.0}}
        };
        imageData = new ImageData(exp, 255);
        assertTrue(testHelper("levels-adjust 20 100 255 %s %s",imageData));
    }

    @Test
    public void testBlurSplit(){
        double[][][] exp ={{{57.0, 153.0, 160.0, 154.0, 154.0},
                {77.0, 158.0, 150.0, 153.0, 155.0},
                {76.0, 153.0, 156.0, 153.0, 155.0},
                {57.0, 155.0, 161.0, 160.0, 154.0}},
                {{35.0, 92.0, 95.0, 91.0, 91.0},
                        {45.0, 94.0, 86.0, 90.0, 91.0},
                        {45.0, 91.0, 92.0, 86.0, 91.0},
                        {34.0, 91.0, 97.0, 96.0, 93.0}},
                {{23.0, 62.0, 66.0, 59.0, 59.0},
                        {31.0, 65.0, 57.0, 56.0, 64.0},
                        {32.0, 65.0, 66.0, 66.0, 64.0},
                        {24.0, 64.0, 70.0, 69.0, 65.0}}
        };
        imageData = new ImageData(exp, 255);
        assertTrue(testHelper("blur %s %s split 20",imageData));
    }

    @Test
    public void testSepiaSplit(){
        double[][][] exp ={{{140.0, 153.0, 160.0, 154.0, 154.0},
                {144.0, 158.0, 150.0, 153.0, 155.0},
                {140.0, 153.0, 156.0, 153.0, 155.0},
                {142.0, 155.0, 161.0, 160.0, 154.0}},
                {{124.0, 92.0, 95.0, 91.0, 91.0},
                        {128.0, 94.0, 86.0, 90.0, 91.0},
                        {124.0, 91.0, 92.0, 86.0, 91.0},
                        {127.0, 91.0, 97.0, 96.0, 93.0}},
                {{97.0, 62.0, 66.0, 59.0, 59.0},
                        {100.0, 65.0, 57.0, 56.0, 64.0},
                        {97.0, 65.0, 66.0, 66.0, 64.0},
                        {99.0, 64.0, 70.0, 69.0, 65.0}}
        };
        imageData = new ImageData(exp, 255);
        assertTrue(testHelper("sepia %s %s split 20",imageData));
    }

    @Test
    public void testSharpenSplit(){
        double[][][] exp ={{{171.0, 153.0, 160.0, 154.0, 154.0},
                {211.0, 158.0, 150.0, 153.0, 155.0},
                {209.0, 153.0, 156.0, 153.0, 155.0},
                {172.0, 155.0, 161.0, 160.0, 154.0}},
                {{102.0, 92.0, 95.0, 91.0, 91.0},
                        {127.0, 94.0, 86.0, 90.0, 91.0},
                        {124.0, 91.0, 92.0, 86.0, 91.0},
                        {101.0, 91.0, 97.0, 96.0, 93.0}},
                {{66.0, 62.0, 66.0, 59.0, 59.0},
                        {86.0, 65.0, 57.0, 56.0, 64.0},
                        {88.0, 65.0, 66.0, 66.0, 64.0},
                        {73.0, 64.0, 70.0, 69.0, 65.0}}
        };
        imageData = new ImageData(exp, 255);
        assertTrue(testHelper("sharpen %s %s split 20",imageData));
    }

    @Test
    public void testColorCorrectSplit(){
        double[][][] exp ={{{101.0, 153.0, 160.0, 154.0, 154.0},
                {104.0, 158.0, 150.0, 153.0, 155.0},
                {101.0, 153.0, 156.0, 153.0, 155.0},
                {103.0, 155.0, 161.0, 160.0, 154.0}},
                {{102.0, 92.0, 95.0, 91.0, 91.0},
                        {105.0, 94.0, 86.0, 90.0, 91.0},
                        {101.0, 91.0, 92.0, 86.0, 91.0},
                        {103.0, 91.0, 97.0, 96.0, 93.0}},
                {{96.0, 62.0, 66.0, 59.0, 59.0},
                        {101.0, 65.0, 57.0, 56.0, 64.0},
                        {101.0, 65.0, 66.0, 66.0, 64.0},
                        {103.0, 64.0, 70.0, 69.0, 65.0}}
        };
        imageData = new ImageData(exp, 255);
        assertTrue(testHelper("color-correct %s %s split 20",imageData));
    }

    @Test
    public void testLevelAdjustSplit(){
        double[][][] exp ={{{187.0, 189.0, 196.0, 190.0, 190.0},
                {190.0, 194.0, 186.0, 189.0, 191.0},
                {187.0, 189.0, 192.0, 189.0, 191.0},
                {189.0, 191.0, 197.0, 196.0, 190.0}},
                {{114.0, 117.0, 121.0, 115.0, 115.0},
                        {118.0, 119.0, 108.0, 114.0, 115.0},
                        {112.0, 115.0, 117.0, 108.0, 115.0},
                        {115.0, 115.0, 123.0, 122.0, 118.0}},
                {{66.0, 72.0, 78.0, 67.0, 67.0},
                        {74.0, 77.0, 64.0, 62.0, 75.0},
                        {74.0, 77.0, 78.0, 78.0, 75.0},
                        {77.0, 75.0, 84.0, 83.0, 77.0}}
        };
        imageData = new ImageData(exp, 255);
        assertTrue(testHelper("levels-adjust 20 100 255 %s %s",imageData));
    }

    private void runCommand(String command) {
        Readable reader = new StringReader(command);
        ImageController controller = new AdvRgbController(factory, textView, reader, commandMapper);
        try {
            controller.run();
        } catch (IOException e) {
            fail("Should not have thrown error");
        }
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
    private boolean testHelper(String command, ImageData exp) {
//        ImageProcessorModel model = new RgbImageProcessor();
//        model.addImage(originalImage, imageData);
        String load = "load images/test/"+originalImage+".png "+originalImage;
        String save = "save images/temp/"+destImage +".png "+destImage;
        command = load + "\n" + String.format(command, originalImage, destImage) + "\n" + save;
        runCommand(command);

        String res = "images/temp/";
        ImageFileIO fileIO = new RgbImageFileIO();
        ImageData act;
        try {
            act = fileIO.load(res + destImage+".png");
            printData(act);
            return exp.equals(act);
        } catch (Exception e) {
            System.out.println("Issue while loading the files "+e.getMessage());
            return false;
        }
    }

    private void deleteFiles(String folderPath) {
        try {
            Path directory = Paths.get(folderPath);
            if (Files.exists(directory) && Files.isDirectory(directory)) {
                Files.walkFileTree(directory, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
                        new SimpleFileVisitor<Path>() {
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                                    throws IOException {
                                Files.delete(file);
                                return FileVisitResult.CONTINUE;
                            }
                        });
            }
        } catch (IOException e) {
            // delete fail but test pass
        }
    }

//    @After
//    public void tearDown() {
//        // Cleanup code to delete all test generated files.
//        deleteFiles("images/temp/");
//    }
}