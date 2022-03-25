/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabpid;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;



import java.io.IOException;
import static javafx.scene.input.KeyCode.R;
import javax.imageio.ImageIO;

/**
 *
 * @author Fernando-
 */
public class PreProcessamento {
   /*
    BufferedImage RetiraFundo(BufferedImage fechamento) throws IOException{
        
        int width = fechamento.getWidth();
        int height = fechamento.getHeight();
        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) {
                Color cor = new Color(fechamento.getRGB(x, y));
                int r = cor.getRed(); int g = cor.getGreen(); int b = cor.getBlue();
                if (r == 255 && g == 255 && b == 255) {
                    Color NovaCor = new Color(0, 0, 0);
                    fechamento.setRGB(x, y, NovaCor.getRGB());
                }else{
                    Color NovaCor = new Color(255, 255, 255);
                    fechamento.setRGB(x, y, NovaCor.getRGB());
                }
            }
        }
        
        
        //Reading the image
        Mat mat = new Mat(fechamento.getHeight(), fechamento.getWidth(), CvType.CV_8UC3);
       
        byte[] pixels = ((DataBufferByte) fechamento.getRaster().getDataBuffer()).getData();
        mat.put(0,0, pixels);
        
        // Creating an empty matrix to store the result
        Mat dst = new Mat();
 
        // Creating kernel matrix
        Mat kernel = Mat.ones(5,5, CvType.CV_32F);
 
        // Applying Blur effect on the Image
        Imgproc.morphologyEx(mat, dst, Imgproc.MORPH_BLACKHAT, kernel);
 
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, mob);
        byte ba[] = mob.toArray();

        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
       
        PidMain Pm = new PidMain();
        //Pm.SavaArq(bi, "ImgResultante", 0); 
        
        return fechamento;
    }

    */
}
