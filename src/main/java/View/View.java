package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class View extends JFrame {
    public View(){
        //Initialize the View
        JFrame myFrame= new JFrame("RISK");
        Container root= getContentPane();
        root.setLayout(new BorderLayout());


        //The layered pane will have multiple layers in-order for us to overlay components
        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setSize(1150,750);
        mapPanel mapPanel= new mapPanel();
        jLayeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);
        root.add(jLayeredPane, BorderLayout.CENTER);

        //Menu Panel will have the set of commands that a user can choose from in order to play the game
        JPanel menuPanel= new JPanel();
        JButton newGameButton= new JButton("New Game");
        JButton attackButton= new JButton("Attack!");
        JButton passTurnButton= new JButton("Pass Turn");
        JButton quitButton= new JButton("Quit Game");
        JTextArea feedbackArea= new JTextArea("Welcome to Risk! Please press New Game in order to start!");
        feedbackArea.setRows(4);
        JScrollPane feedbackAreaScrollPane= new JScrollPane(feedbackArea);

        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(feedbackAreaScrollPane,BorderLayout.NORTH);
        menuPanel.add(newGameButton, BorderLayout.WEST);
        menuPanel.add(attackButton,BorderLayout.CENTER);
        menuPanel.add(passTurnButton,BorderLayout.EAST);
        menuPanel.add(quitButton,BorderLayout.SOUTH);
        root.add(menuPanel,BorderLayout.SOUTH);

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(1150,750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        View view= new View();

    }
}
/*
* The map panel is the Image of the Risk world that we will be overlaying the components over.
*/
class mapPanel extends JPanel{
    private BufferedImage image;
    public mapPanel(){
        this.setSize(1150,760);
        try{
            image= ImageIO.read(new File(this.getClass().getResource("../riskMap.png").toURI()));
        }catch (IOException | URISyntaxException ex){
            ex.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Image scaledImage= image.getScaledInstance(1100,600,Image.SCALE_DEFAULT);
        graphics.drawImage(scaledImage,0,0,this);
    }
}
