/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabpid;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.Serializable;
import javafx.scene.image.Image;
import static javax.swing.Spring.height;
import static javax.swing.Spring.width;

/**
 *
 * @author Fernando-
 */
public class Metodos extends ImageFilter implements Serializable {

    public BufferedImage GrayScale(BufferedImage image) {

        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate average
                int avg = (r + g + b) / 3;

                //replace RGB value with avg
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                image.setRGB(x, y, p);
            }
        }
        return image;
    }

    public BufferedImage Limiar(BufferedImage image) {
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);

        int limiar = 85;
        int BLACK = Color.BLACK.getRGB();
        int WHITE = Color.WHITE.getRGB();
        //Percorre a imagem definindo na saída o pixel como branco se o valor   
        //  na entrada for menor que o threshold, ou como preto se for maior.   
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                Color pixel = new Color(image.getRGB(x, y));
                output.setRGB(x, y, pixel.getRed() < limiar ? BLACK : WHITE);
            }
        }
        return output;
    }

    public BufferedImage Dilata(BufferedImage img) {

        BufferedImage originalImage;
        BufferedImage filteredImage;

        int width;
        int height;
        
        originalImage = img;
        width = originalImage.getWidth();
        height = originalImage.getHeight();

        
        filteredImage = new BufferedImage(width, height, originalImage.getType());

        int white = 255;
        int black = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = new Color(originalImage.getRGB(i, j)).getRed();
                if (color == black) {
                    for (int x = i - 2; x <= i + 2; x++) {
                        for (int y = j - 2; y <= j + 2; y++) {
                            if (x >= 0 && y >= 0 && x < width && y < height) {
                                int preto = 0;
                                int alpha = new Color(originalImage.getRGB(x, y)).getAlpha();
                                int rgb = colorToRGB(alpha, preto, preto, preto);
                                filteredImage.setRGB(x, y, rgb);
                            }
                        }
                    }
                } else {
                    int alpha = new Color(originalImage.getRGB(i, j)).getAlpha();
                    int rgb = colorToRGB(alpha, white, white, white);
                    filteredImage.setRGB(i, j, rgb);
                }
            }
        }
        return filteredImage;

    }

    public static int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }
    
    
    

}
