package unittests;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;
class ImageWriterTest {

    @Test
    void writeToImage() {
        int nX=800;
        int nY=500;
        ImageWriter imageWriter=new ImageWriter("Big Green", nX, nY);
        for(int i=0; i<nY; i++)
        {
            for (int j = 0; j < nX; j++) {
                int gapX = 800 / 16;
                int gapY = 500 / 10;
                if (i % gapY == 0 || j % gapX == 0) {
                    imageWriter.writePixel(j, i, Color.BLACK);
                } else {//writing blue pixel
                    imageWriter.writePixel(j, i, new Color(0, 1000, 0));
                }
            }
        }
        imageWriter.writeToImage();
    }

}