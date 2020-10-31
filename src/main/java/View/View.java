package View;

import Controller.Controller;
import Model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class View extends JFrame {
    JButton newGameButton;



    JButton attackButton;
    JButton passTurnButton;
    JButton quitButton;
    JTextArea feedbackArea;

    public View(){
        Initialize();
    }

    public void Initialize() {
        //Initialize the View
        JFrame myFrame = new JFrame("RISK");
        Container root = getContentPane();
        root.setLayout(new BorderLayout());


        //The layered pane will have multiple layers in-order for us to overlay components
        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setSize(1150, 750);
        mapPanel mapPanel = new mapPanel();
        jLayeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);
        root.add(jLayeredPane, BorderLayout.CENTER);

        //Menu Panel will have the set of commands that a user can choose from in order to play the game
        JPanel menuPanel = new JPanel();
        //Creating the buttons and adding actionlistener to them
        newGameButton = new JButton("NewGame");
        attackButton = new JButton("Attack");
        passTurnButton = new JButton("PassTurn");
        quitButton = new JButton("QuitGame");

        feedbackArea = new JTextArea("Welcome to Risk! Please press New Game in order to start!\n");
        feedbackArea.setRows(4);
        JScrollPane feedbackAreaScrollPane = new JScrollPane(feedbackArea);
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(feedbackAreaScrollPane, BorderLayout.NORTH);
        menuPanel.add(newGameButton, BorderLayout.WEST);
        menuPanel.add(attackButton, BorderLayout.CENTER);
        menuPanel.add(passTurnButton, BorderLayout.EAST);
        menuPanel.add(quitButton, BorderLayout.SOUTH);
        root.add(menuPanel, BorderLayout.SOUTH);

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(1150, 750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {

        Game gameModel = new Game();
        View gameView = new View();
        Controller gameController = new Controller(gameModel,gameView);
        gameView.initialize(gameController);


    }

    private void initialize(Controller gameController) {
        this.attackButton.addActionListener(gameController);
        this.passTurnButton.addActionListener(gameController);
        this.quitButton.addActionListener(gameController);
        this.newGameButton.addActionListener(gameController);
    }

    public void handleChangeEvent(Game game) {
        //TODO
    }

    public void setFeedbackArea(String feedbackAreaText) {
        this.feedbackArea.append(feedbackAreaText);
    }

    public JTextArea getFeedbackArea() {
        return feedbackArea;
    }
    public JButton getNewGameButton() {
        return newGameButton;
    }
    public int numberOfPlayersRequest() {
        Integer[] choices = new Integer[]{2,3,4,5,6};
        int choice = askUser(choices);
        System.out.println("The number of players selected: " + choice);
        return choice;

    }
    static int askUser(Integer[] choices){
        Integer s = (Integer) JOptionPane.showInputDialog(
                null,
                "How many players are playing today?",
                "Select number of players!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);
        return s;
    }

    /*
     * The map panel is the Image of the Risk world that we will be overlaying the components over.
     */
    class mapPanel extends JPanel {
        private Image image;

        public mapPanel() {
            this.setSize(1150, 760);
            try {
                BufferedImage loadedImage = ImageIO.read(new File(this.getClass().getResource("../riskMap.png").toURI()));
                image = loadedImage.getScaledInstance(1100, 600, Image.SCALE_DEFAULT);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(image, 0, 0, this);
        }
    }
}
