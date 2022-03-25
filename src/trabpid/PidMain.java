/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabpid;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import static javax.swing.Spring.height;
import static javax.swing.Spring.width;

/**
 *
 * @author Fernando-
 */
public class PidMain implements Initializable {
    
    
    @FXML
    private Button btnAbrir;
    @FXML
    private Button btnExecutar;
    @FXML
    private ImageView ImageAction;
    @FXML
    private ImageView Exibe_Inicial;
    private Object deck;
    
    String enderecoPasta;
    String enderecoCompleto;
    
    @FXML
    private void abrir(ActionEvent event) throws IOException {
        
        
        String Dir = new String(enderecoCompleto);
        File file = new File(Dir);
        Image image = new Image(file.toURI().toString());
        
        BufferedImage RGBImagem = ImageIO.read(new File(Dir));
        BufferedImage limiar = new BufferedImage(RGBImagem.getWidth(),RGBImagem.getHeight(), BufferedImage.TYPE_BYTE_GRAY);   
        BufferedImage fechamento = new BufferedImage(RGBImagem.getWidth(),RGBImagem.getHeight(), BufferedImage.TYPE_BYTE_GRAY); 
        BufferedImage ResulPre = new BufferedImage(RGBImagem.getWidth(),RGBImagem.getHeight(), BufferedImage.TYPE_BYTE_GRAY); 
        BufferedImage ImgOriginal = new BufferedImage(RGBImagem.getWidth(),RGBImagem.getHeight(), BufferedImage.TYPE_INT_ARGB); ;          
             
        
        ImgOriginal = drawImage(RGBImagem, ImgOriginal);
        
        Sobel sobel = new Sobel();
        BufferedImage SaidaSobel = sobel.Borda(RGBImagem);
       
        
        Metodos Met = new Metodos();
        limiar = Met.Limiar(SaidaSobel);
        
        
        Segmentacao seg = new Segmentacao();
        ArrayList<BufferedImage> SaidaSegmentacao = seg.Segmenta(limiar, ImgOriginal);
        
        
        Descritor desc = new Descritor();
        desc.knn();
        
        System.out.println("Fim de Execução");
        
    }
    
    @FXML
    private void executar(ActionEvent event) {
        FileChooser open = new FileChooser();
        open.setTitle("Selecione o Arquivo");
        FileChooser.ExtensionFilter extensoes = new FileChooser.ExtensionFilter("Fotos Pid", "*.bmp");
        open.getExtensionFilters().add(extensoes);
        File end = open.showOpenDialog(null);
        
        
        String endereco = end.getPath();
        
        String nome = end.getName();
        int tamanho = endereco.length() - nome.length();
        String enderecoP = endereco.substring(0, tamanho);
        
        
        enderecoCompleto = endereco;
        enderecoPasta = enderecoP;
    }
    
    public void SavaArq(BufferedImage imagem, String nameImg, int ContornoNum, String nomepasta){
        try {
            //System.out.println("Entoru p salvar" + nameImg);
            File outputfile = new File("C:\\Users\\Fernando-\\Dropbox\\Trabalhos\\PID\\" + nomepasta + "\\" + nameImg + ContornoNum + ".png");
            ImageIO.write(imagem, "png", outputfile);
        
        } catch (IOException e) {
            System.out.println("Problema ao salvar");
        }
    }
    
    public BufferedImage drawImage(BufferedImage RGBImagem, BufferedImage ImgOriginal) {
        int width = RGBImagem.getWidth();
        int height = RGBImagem.getHeight();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                Color cor = new Color(RGBImagem.getRGB(x, y));
                ImgOriginal.setRGB(x, y, cor.getRGB());
            }
        }
        return ImgOriginal;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

