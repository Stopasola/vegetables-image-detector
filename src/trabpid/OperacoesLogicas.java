/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabpid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Fernando-
 */
public class OperacoesLogicas {
    
    public BufferedImage OperacaoAND(BufferedImage imgoriginal, BufferedImage imgfinal){
        PidMain Pm = new PidMain();
        //Pm.SavaArq(imgoriginal, "imgoriginal" , 0);
        //Pm.SavaArq(imgfinal, "imgfinal", 0 );
        
	BufferedImage imgresul = new BufferedImage(imgoriginal.getWidth(), imgoriginal.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D  graphics =   imgresul.createGraphics();
        graphics.setPaint(new Color (255, 255, 255));
        graphics.fillRect(0, 0, imgresul.getWidth(), imgresul.getHeight() );
        
        
        int width = imgoriginal.getWidth();
        int height = imgoriginal.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color cor = new Color(imgfinal.getRGB(x, y));
                int red = cor.getRed(); int green = cor.getGreen(); int blue = cor.getBlue();
                if(red == 0 && green == 0 && blue == 0){
                    imgresul.setRGB(x, y, imgoriginal.getRGB(x, y));
                }
            }
        }
        
        return imgresul;
    }
}
