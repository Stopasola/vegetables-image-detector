/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabpid;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Fernando-
 */
public class Descritor {

    public void knn() throws IOException {
        File Test_Path = new File("C:\\Users\\Fernando-\\Dropbox\\Trabalhos\\PID\\OutputSegmentacao");
        File[] Testarq;
        Testarq = Test_Path.listFiles();
        File Train_Path = new File("C:\\Users\\Fernando-\\Dropbox\\Trabalhos\\PID\\Train");
        File[] Trainarq;
        Trainarq = Train_Path.listFiles();
        ArrayList<Integer> VetorTeste = new ArrayList<Integer>();
        ArrayList<Integer> VetorTrain = new ArrayList<Integer>();
        ArrayList<No> VetorKNN = new ArrayList<No>();
        ArrayList<No> VetorKNNTreino = new ArrayList<No>();
        int cen = 0, ban = 0, ceb = 0, maca = 0, bat = 0, tom = 0, count = 0, maior = 0;
        int Contcen = 0, Contban = 0, Contceb = 0, Contmaca = 0, Contbat = 0, Conttom = 0;
        
        String label;
        No objetoTeste = new No();

        for (int i = 0; i < Testarq.length; i++) {
            BufferedImage ImagemTeste = ImageIO.read(new File(Testarq[i].toString()));
                VetorTeste = Histograma(ImagemTeste);
                System.out.println(Testarq[i]);

                for (int j = 0; j < Trainarq.length; j++) {
                    BufferedImage ImagemTrain = ImageIO.read(new File(Trainarq[j].toString()));
                    
                    VetorTrain = Histograma(ImagemTrain);

                    double coincidencia = Cor(ImagemTeste,ImagemTrain);
                     
                    No objetoTreino = new No();
                    objetoTreino.Correlacao = coincidencia/*correlacao*/;
                    objetoTreino.Classes = Trainarq[j].getAbsolutePath().substring(Trainarq[j].getAbsolutePath().lastIndexOf("\\") + 1);
                    objetoTreino.Classes = removeLastsChar(objetoTreino.Classes);
                    
                    VetorKNN.add(objetoTreino);
                }
                
                
                
                Collections.sort(VetorKNN, new Comparator<No>() {
                    @Override
                    public int compare(No a, No b) {
                        //return Double.valueOf(b.getCorrelacao()).compareTo(Double.valueOf(a.getCorrelacao()));
                        return Double.compare(a.getCorrelacao(), b.getCorrelacao());
                    }
                });
                
                //Comeco Classificacao
                for (No instKnn : VetorKNN) {
                    switch (instKnn.Classes) {
                        case "banana":
                            ban++;
                            break;
                        case "batata":
                            bat++;
                            break;
                        case "tomate":
                            tom++;
                            break;
                        case "tomate1":
                            tom++;
                            break;
                        case "cebola":
                            ceb++;
                            break;
                        case "cenoura":
                            cen++;
                            break;
                        case "maca":
                            maca++;
                            break;
                        case "maca1":
                            maca++;
                            break;
                    }
                    count++;
                    if (count == 5) {
                        break;
                    }
                }
                count = 0;
                maior = 0;
                maior = ban;
                label = "banana";
                if (maior < bat) {
                    label = "batata";
                    maior = bat;
                }
                if (maior < tom) {
                    label = "tomate";
                    maior = tom;
                }
                if (maior < ceb) {
                    label = "cebola";
                    maior = ceb;
                }
                if (maior < cen) {
                    label = "cenoura";
                    maior = cen;
                }
                if (maior < maca) {
                    label = "maca";
                    maior = maca;
                }

                ban = 0; maca = 0; cen = 0; ceb = 0; tom = 0; bat = 0;
                if(label == "maca" || label == "tomate" ||  label == "maca1" || label == "tomate1"){
                    label = VetorKNN.get(0).Classes;
                }
                VetorKNN.clear();
                
                
                System.out.println("label: " + label + "\n-------------");
                PidMain Pm = new PidMain();
                Pm.SavaArq(ImagemTeste, label , i, "SaidaDescritor");
                //Armazenando a label predita no vetor
                switch (label) {
                    case "banana":
                        Contban++;
                        break;
                    case "batata":
                        Contbat++;
                        break;
                    case "tomate":
                        Conttom++;
                        break;
                    case "cebola":
                        Contceb++;
                        break;
                    case "cenoura":
                        Contcen++;
                        break;
                    case "maca":
                        Contmaca++;
                        break;
                }
        }
        SalvaInformacoes(Contban,Contbat,Conttom,Contceb,Contcen,Contmaca);
        Contcen = 0; Contban = 0; Contceb = 0; Contmaca = 0; Contbat = 0; Conttom = 0;
    }
    
    public void Redondez(BufferedImage ImagemTeste) {
        int width = ImagemTeste.getWidth();
        int height = ImagemTeste.getHeight();
        int ImgParametroMaiorX = -1, ImgParametroMaiorY = -1;
        int ImgParametroMenorX = 5000, ImgParametroMenorY = 5000;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int rgbB = ImagemTeste.getRGB(x, y);
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) & 0xff;
                if (redB != 255 && greenB != 255 && blueB != 255) {
                    if (ImgParametroMaiorX < x) {
                        ImgParametroMaiorX = x;
                    }
                    if (ImgParametroMaiorY < y) {
                        ImgParametroMaiorY = y;
                    }
                    if (ImgParametroMenorX > x) {
                        ImgParametroMenorX = x;
                    }
                    if (ImgParametroMenorY > y) {
                        ImgParametroMenorY = y;
                    }
                }        
                
            }
        }
        /*System.out.println("ImgParametroMaiorX:" + ImgParametroMaiorX);
        System.out.println("ImgParametroMaiorY:" + ImgParametroMaiorY);
        System.out.println("ImgParametroMenorX:" + ImgParametroMenorX);
        System.out.println("ImgParametroMenorY:" + ImgParametroMenorY);*/
    }
    
    public double Cor(BufferedImage ImagemParametro, BufferedImage ImagemTeste) throws IOException {

        
        ArrayList<Integer> Imagem1 = new ArrayList<Integer>();
        ArrayList<Integer> Imagem2 = new ArrayList<Integer>();
        
        long difference = 0;
        int width1, height1;
        int tamanhoImg1 = 0, tamanhoImg2 = 0;

        if(ImagemTeste.getWidth() > ImagemParametro.getWidth()){
             width1 = ImagemParametro.getWidth();
        }else{
             width1 = ImagemTeste.getWidth();
        }
        
        if(ImagemTeste.getHeight() > ImagemParametro.getHeight()){
            height1 = ImagemParametro.getHeight();
        }else{
             height1 = ImagemTeste.getHeight();
        }
            
            
            
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                
                int rgbA = ImagemTeste.getRGB(x, y);
                int rgbB = ImagemParametro.getRGB(x, y);
                int redA = (rgbA >> 16) & 0xff;
                int greenA = (rgbA >> 8) & 0xff;
                int blueA = (rgbA) & 0xff;
                int redB = (rgbB >> 16) & 0xff;
                int greenB = (rgbB >> 8) & 0xff;
                int blueB = (rgbB) & 0xff;
                if (redA != 255 && greenA != 255 && blueA != 255) {
                    Imagem1.add(redA);
                    Imagem1.add(greenA);
                    Imagem1.add(blueA);
                    tamanhoImg1++;
                }
                if (redB != 255 && greenB != 255 && blueB != 255) {
                    Imagem2.add(redB);
                    Imagem2.add(greenB);
                    Imagem2.add(blueB);
                    tamanhoImg2++;
             
                    
                }
            }
        }
       
        int inicio = 0, fim = 0;
        if (tamanhoImg1 >= tamanhoImg2) {
            fim = tamanhoImg1;
            inicio = tamanhoImg2;
        } else {
            fim = tamanhoImg2;
            inicio = tamanhoImg1;
        }

        for (int i = inicio; i < fim; i++) {
            if (tamanhoImg1 >= tamanhoImg2) {
                Imagem2.add(-1);
                Imagem2.add(-1);
                Imagem2.add(-1);
                
            } else {
                Imagem1.add(-1);
                Imagem1.add(-1);
                Imagem1.add(-1);
                
            }
        }

        for(int i = 0; i < fim - 2; i++){
            difference += Math.abs(Imagem1.get(i) - Imagem2.get(i));
            difference += Math.abs(Imagem1.get(i + 1) - Imagem2.get(i + 1));
            difference += Math.abs(Imagem1.get(i + 2) - Imagem2.get(i + 2));
        }
        long DiferencaTamanho = (Math.abs(tamanhoImg1 - tamanhoImg2)) * 255;
        difference += DiferencaTamanho;
        //System.out.println("difference: " + difference);
        double total_pixels = fim * 3;
        double avg_different_pixels = difference / total_pixels;
        //System.out.println("avg_different_pixels-->" + avg_different_pixels);

        
        Imagem1.clear();
        Imagem2.clear();
        //double percentage=1;
        return avg_different_pixels;
    }
    
    
    public ArrayList<No> Descendente(ArrayList<No> VetorKNN){
        Collections.sort(VetorKNN, new Comparator<No>() {
            @Override
            public int compare(No a, No b) {
                return Double.valueOf(b.getCorrelacao()).compareTo(Double.valueOf(a.getCorrelacao()));
            }
        });
        return VetorKNN;
    }

    private static String removeLastsChar(String str) {
        return str.substring(0, str.length() - 5);
    }

    public ArrayList<Integer> Histograma(BufferedImage Objeto) throws IOException {

        ArrayList<Integer> Vetor = new ArrayList<Integer>();
        int PixelsTotais = 0, TotalRed = 0, TotalGreen = 0, TotalBlue = 0;
        int[][][] ch = new int[4][4][4];
        double covariancia;
        double correlacao;

        for (int x = 0; x < Objeto.getWidth(); x++) {
            for (int y = 0; y < Objeto.getHeight(); y++) {
                int color = Objeto.getRGB(x, y);
                int alpha = (color & 0xff000000) >> 24;
                int red = (color & 0x00ff0000) >> 16;
                int green = (color & 0x0000ff00) >> 8;
                int blue = color & 0x000000ff;
                if (red != 255 && green != 255 && blue != 255) {
                    ch[red / 64][green / 64][blue / 64]++;
                    PixelsTotais++;
                    TotalRed += red;
                    TotalGreen += green;
                    TotalBlue += blue;
                }
            }
        }
        int i, j, p;
        for (i = 0; i < ch.length; i++) {
            for (j = 0; j < ch[i].length; j++) {
                for (p = 0; p < ch[i][j].length; p++) {
                    if (ch[i][j][p] > 0) {
                        //System.out.println("t[" + i + "][" + j + "][" + p + "] = " + ch[i][j][p]);
                        Vetor.add(ch[i][j][p]);
                    }else{
                        Vetor.add(0);
                    }
                }
            }
        }
        

        return Vetor;
    }

    public double Covariancia(ArrayList<Integer> Vetor1, ArrayList<Integer> Vetor2) {
        double sum = 0, covariaca;
        
        for (int i = 0; i < Vetor1.size(); i++) {
            sum = sum + (Vetor1.get(i) - mean(Vetor1, Vetor1.size())) * (Vetor2.get(i) - mean(Vetor2, Vetor1.size()));
        }
        covariaca = (sum / (Vetor1.size() - 1));
        return Correlacao(covariaca, Vetor1, Vetor2);
    }

    static float mean(ArrayList<Integer> arr, int n) {

        float sum = 0;
        for (int i = 0; i < n; i++) {
            sum = sum + arr.get(i);
        }

        return sum / n;
    }

    public double Correlacao(double covariancia, ArrayList<Integer> Vetor1, ArrayList<Integer> Vetor2) {

        double variancia1 = 0, variancia2 = 0;
        for (int i = 0; i < Vetor1.size(); i++) {
            variancia1 = variancia1 + Math.pow((Vetor1.get(i) - mean(Vetor1, Vetor1.size())), 2);
            variancia2 = variancia2 + Math.pow((Vetor2.get(i) - mean(Vetor2, Vetor2.size())), 2);
        }
        variancia1 = (variancia1 / (Vetor1.size() - 1));
        variancia2 = (variancia2 / (Vetor2.size() - 1));
        return ((covariancia / (Math.sqrt(variancia1 * variancia2))));
    }

    private void SalvaInformacoes(int Contban, int Contbat, int Conttom, int Contceb, int Contcen, int Contmaca) throws IOException {
        FileWriter arq = new FileWriter("C:\\Users\\Fernando-\\Dropbox\\Trabalhos\\PID\\Dados.txt", true);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf("Quantidade de Bananas: %d \n", Contban);
        gravarArq.printf("Quantidade de Batatas: %d \n", Contbat);
        gravarArq.printf("Quantidade de Tomates: %d \n", Conttom);
        gravarArq.printf("Quantidade de Cebolas: %d \n", Contceb);
        gravarArq.printf("Quantidade de Cenouras: %d \n", Contcen);
        gravarArq.printf("Quantidade de Macas: %d \n-----------\n", Contmaca);
        
        arq.close();
    }
}
