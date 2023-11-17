package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for FactoryRgbImage.
 */
public class FactoryRgbImageTest {

    @Test
    public void testCreateImageModel(){
        //GIVEN
        FactoryRgbImageModel factory = new FactoryRgbImage();

        //WHEN
        RgbImageModel rgbImage = factory.createImageModel();

        assertTrue(rgbImage instanceof RgbImage);
    }

}