/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabpid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;


/**
 *
 * @author Fernando-
 */
public class Segmentacao {

    private ArrayList<Integer> CoordenadasBordaX = new ArrayList<Integer>();
    private ArrayList<Integer> CoordenadasBordaY = new ArrayList<Integer>();
    private ArrayList<Integer> InsidePoligonoX = new ArrayList<Integer>();
    private ArrayList<Integer> InsidePoligonoY = new ArrayList<Integer>();
    private ArrayList<BufferedImage> ListaObjetos = new ArrayList<BufferedImage>();
    
    private int[] dx = { -1, 0, 1, 0 };
    private int[] dy = { 0, 1, 0, -1 };
    
    public ArrayList<BufferedImage> Segmenta(BufferedImage SaidaSobel, BufferedImage ImagemOriginal ) {
       
        int width = SaidaSobel.getWidth();
        int height = SaidaSobel.getHeight();
        int ContornoNum = 0;
        for (int y = 1; y < height-1; y++) {
            for (int x = 1; x < width-1; x++) {
                Color cor = new Color(SaidaSobel.getRGB(x, y));
                int r = cor.getRed(); int g = cor.getGreen(); int b = cor.getBlue();
                if (r == 255 && g == 255 && b == 255) { // Achou um contorno pixel central
                    
                    
                    floodIterativo(SaidaSobel, SaidaSobel.getWidth(), SaidaSobel.getHeight(), x, y, cor.WHITE, cor.BLACK, 0); // Acha as coordenadas da borda e edita a img original
                    
                    
                    
                    if(CoordenadasBordaX.size() < 4000){ // Valor para desconsiderar ruidos
                        CoordenadasBordaX.clear();
                        CoordenadasBordaY.clear();
                    }else{
                        ContornoNum++;
                        int teste[] = new int[2];
                        teste = CentroObj(CoordenadasBordaX, CoordenadasBordaY);
                        
                             
                        
                        CriaImagem(ImagemOriginal, teste[0] , teste[1], ContornoNum); // Esse metodo pega o poligono achado faz um mask com a imagem original e salva em uma pasta
                        
                        CoordenadasBordaX.clear();
                        CoordenadasBordaY.clear();
                        InsidePoligonoX.clear();
                        InsidePoligonoY.clear();
                    
                    }
                }

            }
        }
        return ListaObjetos;
    }
    
     public boolean contains(ArrayList<Integer> CoordenadasX, ArrayList<Integer> CoordenadasY, int[] teste) {
        int i;
        int j;
        
        boolean result = false;
        for (i = 0, j = CoordenadasX.size() - 2; i < CoordenadasX.size()-1; j = i++) { // Considerei Q COOREDENADAS X e Y sao ==
            
            if ((CoordenadasY.get(i) > teste[1]) != (CoordenadasY.get(j) > teste[1])
                    && (teste[0] < (CoordenadasX.get(j) - CoordenadasX.get(i)) * (teste[1] - CoordenadasY.get(i)) / (CoordenadasY.get(j) - CoordenadasY.get(i)) + CoordenadasX.get(i))) {
                result = !result;
            }
        }
        return result;
        
    }

    public int[] CentroObj(ArrayList<Integer> BordaX, ArrayList<Integer> BordaY) {
        double centroidX = 0, centroidY = 0;

        for(int i=0; i<BordaX.size();i++){
            
            centroidX += BordaX.get(i);
            centroidY += BordaY.get(i);
        }
        int PontoX = (int)(centroidX / BordaX.size());
        int PontoY = (int)(centroidY / BordaY.size());

        return new int[]{PontoX, PontoY};
    }
    
    
    private void CriaImagem(BufferedImage ImagemOriginal, int x, int y, int ContornoNum) {
        
        //System.out.println("Cria Img --- Valor encontrado X: " + x + "Valor encontrado Y: " + y);
        BufferedImage ImagemObjeto = new BufferedImage(ImagemOriginal.getWidth(), ImagemOriginal.getHeight(), BufferedImage.TYPE_INT_ARGB);
        BufferedImage ImgResultante = new BufferedImage(ImagemOriginal.getWidth(), ImagemOriginal.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D  graphics =   ImagemObjeto.createGraphics();
        graphics.setPaint(new Color (255, 255, 255));
        graphics.fillRect(0, 0, ImagemObjeto.getWidth(), ImagemObjeto.getHeight() );
        
        
        PidMain Pm = new PidMain();
        //System.out.println("CoordenadasBordaX.size(): " + CoordenadasBordaX.size());
        
        for(int k=0; k<CoordenadasBordaX.size();k++){
             Color NovaCor = new Color(0, 0, 0);
             ImagemObjeto.setRGB(CoordenadasBordaX.get(k), CoordenadasBordaY.get(k), NovaCor.getRGB());      
        }
        
        Pm.SavaArq(ImagemObjeto, "ImgBORDA", ContornoNum, "OutputBorda");
        
        Color cor = new Color(ImagemObjeto.getRGB(x, y));
        floodIterativo(ImagemObjeto, ImagemObjeto.getWidth(), ImagemObjeto.getHeight(), x, y, cor.WHITE, cor.BLACK, 1);
        
        //System.out.println("InsidePoligonoX.size(): " + InsidePoligonoX.size());
        int teste[] = new int[2];
        boolean resultado;
        if(InsidePoligonoX.size() > 1000000){ //Verifica se não pintou a imagem toda
            InsidePoligonoX.clear();
            InsidePoligonoY.clear();
            
        }else{
        
            for(int k=0; k<InsidePoligonoX.size();k++){
                 Color NovaCor = new Color(0, 0, 0);
                 ImagemObjeto.setRGB(InsidePoligonoX.get(k), InsidePoligonoY.get(k), NovaCor.getRGB());      
                }
                OperacoesLogicas opl = new OperacoesLogicas();
                ImgResultante = opl.OperacaoAND(ImagemOriginal, ImagemObjeto);
                Pm.SavaArq(ImgResultante, "ImgResultante", ContornoNum, "OutputSegmentacao");
                ListaObjetos.add(ImgResultante);
        }
    }
    
    
    public void floodIterativo(BufferedImage imageBuffer, int largura, int altura, int x, int y, Color target_color, Color replacement_color, int arg){
        
 	Stack <Integer[]> stack = new Stack<Integer[]>();
        
	if (imageBuffer.getRGB(x, y) == replacement_color.getRGB()){
            return;
        }
		
        
	stack.push(new Integer[] { x, y });
	while (!stack.isEmpty()) {
		Integer[] aux = stack.peek();
		imageBuffer.setRGB(aux[0], aux[1], replacement_color.getRGB()); // pinta de preto para sumir contorno
                if(arg == 0){
                    CoordenadasBordaX.add(aux[0]);
                    CoordenadasBordaY.add(aux[1]);
                }
                if(arg == 1){
                    InsidePoligonoX.add(aux[0]);
                    InsidePoligonoY.add(aux[1]);
                }
                stack.pop();
		for(int i = 0; i<4;i++){
                    if((aux[0] + dx[i])< 0 || (aux[1] + dy[i])<0 || (aux[0] + dx[i]) >= largura || (aux[1] + dy[i]) >= altura){
			break;
                        
                    }else if(imageBuffer.getRGB(aux[0] + dx[i], aux[1] + dy[i]) == target_color.getRGB()){
                        stack.push(new Integer[] { aux[0] + dx[i], aux[1] + dy[i] });
                    }    
               }

	}
    }
    

}
