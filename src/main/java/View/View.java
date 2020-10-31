package View;

import Controller.Controller;
import Model.Game;
import Game.GameEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View extends JFrame {
    Game gameModel;
    JButton newGameButton;
    JButton attackButton;
    JButton passTurnButton;
    JButton quitButton;
    JTextArea feedbackArea;
    JButton moveButton;
    JLabel mouseXYLabel;
    Map<String,CircleButton> mapOfButtons = new HashMap<>();
    CircleButton alaska;
    CircleButton alberta;
    CircleButton centralAmerica;
    CircleButton NorthwestTerritory;
    CircleButton WesternUnitedStates;
    CircleButton  Ontario;
    CircleButton Quebec;
    CircleButton greenland;
    CircleButton easternunitedstates;

    public View(Game gameModel){
        Initialize();
        this.gameModel=gameModel;
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
        mapPanel.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseXYLabel.setText("X: " + e.getX() + ", Y: " + e.getY());
            }
        });
        jLayeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

        JPanel countryPanel = new JPanel();
        countryPanel.setSize(1150,750);
        countryPanel.setOpaque(false);
        countryPanel.setLayout(null);
        countryView();
        countryPanel.add(alaska);
        countryPanel.add(alberta);
        countryPanel.add(centralAmerica);
        countryPanel.add(NorthwestTerritory);
        countryPanel.add(WesternUnitedStates);
        countryPanel.add(Ontario);
        countryPanel.add(Quebec);
        countryPanel.add(greenland);
        countryPanel.add(easternunitedstates);

        jLayeredPane.add(countryPanel,JLayeredPane.POPUP_LAYER);

        root.add(jLayeredPane, BorderLayout.CENTER);

        //Menu Panel will have the set of commands that a user can choose from in order to play the game
        JPanel menuPanel = new JPanel();
        //Creating the buttons and adding actionlistener to them
        newGameButton = new JButton("NewGame");
        attackButton = new JButton("Attack");
        passTurnButton = new JButton("PassTurn");
        quitButton = new JButton("QuitGame");
        moveButton = new JButton("Move");

        feedbackArea = new JTextArea("Welcome to Risk! Please press New Game in order to start!\n");
        feedbackArea.setRows(4);
        JScrollPane feedbackAreaScrollPane = new JScrollPane(feedbackArea);
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(feedbackAreaScrollPane, BorderLayout.NORTH);
        menuPanel.add(newGameButton, BorderLayout.WEST);

        JPanel centerPanel= new JPanel();
        centerPanel.setLayout(new BorderLayout());
        menuPanel.add(centerPanel,BorderLayout.CENTER);
        centerPanel.add(attackButton,BorderLayout.CENTER);
        centerPanel.add(moveButton,BorderLayout.EAST);
        menuPanel.add(passTurnButton, BorderLayout.EAST);
        //menuPanel.add(quitButton, BorderLayout.SOUTH);
        mouseXYLabel = new JLabel("X: , Y: ");
        menuPanel.add(mouseXYLabel, BorderLayout.SOUTH);
        root.add(menuPanel, BorderLayout.SOUTH);

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(1150, 750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void countryView() {
        alaska= new CircleButton("", 60, 80);
        alberta= new CircleButton("", 156, 131);
        centralAmerica= new CircleButton("", 180, 287);
        NorthwestTerritory= new CircleButton("", 173, 85);
        WesternUnitedStates= new CircleButton("", 160, 205);
        Ontario= new CircleButton("", 237, 148);
        Quebec= new CircleButton("", 302, 143);
        greenland= new CircleButton("", 378, 52);
        easternunitedstates= new CircleButton("", 247, 221);


        mapOfButtons.put("Alaska", alaska);
        mapOfButtons.put("Alberta", alberta);
        mapOfButtons.put("CentralAmerica",centralAmerica);
        mapOfButtons.put("NorthwestTerritory",NorthwestTerritory);
        mapOfButtons.put("WesternUnitedStates",WesternUnitedStates);
        mapOfButtons.put("Ontario",Ontario);
        mapOfButtons.put("Quebec",Quebec);
        mapOfButtons.put("Greenland",greenland);
        mapOfButtons.put("EasternUnitedStates",easternunitedstates);


    }

    public static void main(String[] args) {

        Game gameModel = new Game();
        View gameView = new View(gameModel);
        gameModel.setViewer(gameView);
        Controller gameController = new Controller(gameModel,gameView);
        gameView.initialize(gameController);


    }

    private void initialize(Controller gameController) {
        this.attackButton.addActionListener(gameController);
        this.passTurnButton.addActionListener(gameController);
        this.quitButton.addActionListener(gameController);
        this.newGameButton.addActionListener(gameController);
    }

    public void handleGameStartEvent(GameEvent game) {
        initializeCountries(game.getGameMap(),game.getNumberOfPlayers());

    }

    private void initializeCountries(Model.Map map, int numberOfTroops) {
        map.getAllCountries().forEach(country -> {
            CircleButton b=  mapOfButtons.get(country.getName());
            if(b!=null) {
                b.setText("" + country.getNumberOfTroops());
            }
        });
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


    public void assignPlayerCountries() {
        //For each player, go through my country buttons, and assign them a button based on the country name and the button name
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
