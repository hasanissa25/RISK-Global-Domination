package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class View extends JFrame {
    public View(){
        JFrame myFrame= new JFrame("RISK");
        JLayeredPane root= getLayeredPane();
        mapPanel mapPanel= new mapPanel();
        root.add(mapPanel, 1);
        //root.add(Ontario, 2);
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(950,750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        View view= new View();
    }
}
class mapPanel extends JPanel{
    private BufferedImage image;
    public mapPanel(){
        this.setSize(900,700);
        try{
            System.out.println("The path is: "+ this.getClass().getResource("../riskMap.png"));
            image= ImageIO.read(new File(this.getClass().getResource("../riskMap.png").toURI()));
        }catch (IOException | URISyntaxException ex){
            ex.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Image scaledImage= image.getScaledInstance(900,700,Image.SCALE_DEFAULT);
        graphics.drawImage(scaledImage,0,0,this);
    }
}
