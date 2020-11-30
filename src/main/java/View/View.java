package View;

import Controller.Controller;
import Game.GameEvent;
import Model.Country;
import Model.Game;
import Model.ModelUpdateListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author      Hasan Issa
 *
 * This is view of the game, where all the user input is provided to be sent to the controller.
 * The view is composed of multiple layers. The Bottom layer, (Default layer) is a picture of a risk board, which then has another layer on top of it, of the countries.
 * The countries are represented as buttons, which have a controller to handle on click events.
 * Each button has the number of troops represented on them.
 * The current players countries, are highlighted in green, during their turn.
 *
 */
public class View extends JFrame implements ModelUpdateListener {
    Game gameModel;
    JButton newGameButton;
    JButton attackButton;
    JButton passTurnButton;
    JButton saveButton;
    JButton continueButton;
    JButton quitButton;
    JButton moveButton;
    ArrayList<JButton> listOfCommandButtons;
    JTextArea feedbackArea;
    Map<String, CircleButton> mapOfButtons = new HashMap<>();
    JPanel countryPanel;
    mapPanel mapPanel;
    private Controller gameController;

    public View(Game gameModel) {
        this.gameModel = gameModel;
        Initialize();

    }

    public static void main(String[] args) {

        Game gameModel = new Game();
        View gameView = new View(gameModel);
        gameModel.setViewer(gameView);
        Controller gameController = new Controller(gameModel, gameView);
        gameView.initialize(gameController);
    }
    static String askUserChoiceOfMap(String[] choices) {
        String s = (String) JOptionPane.showInputDialog(
                null,
                "Which Map would you like to play on?",
                "Select the map!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);
        return s;
    }
    static int askUserNumberOfPlayers(Integer[] choices) {
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
    static int askUserAboutAI(Integer[] choices) {
        Integer s = (Integer) JOptionPane.showInputDialog(
                null,
                "How many AI controlled players would you like to set?",
                "Select the number of AI players!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);
        return s;
    }

        int askUserAboutBonusTroops(Integer[] choices) {
        Integer s = (Integer) JOptionPane.showInputDialog(
                null,
                "You have " + gameModel.getCurrentPlayer().totalBonusTroops() + " bonus troops. How many bonus troops would you like to currently allocate?",
                "Select the number of bonus troops!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);
        return s;
    }
    int askUserAboutBonusTroops(Integer[] choices, int maxNumberOfBonuses) {
        Integer s = (Integer) JOptionPane.showInputDialog(
                null,
                "You have " + maxNumberOfBonuses + " bonus troops. How many bonus troops would you like to allocate?",
                "Select the number of bonus troops!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                choices,
                choices[0]);
        return s;
    }

    public void Initialize() {
        //Initialize the View
        JFrame myFrame = new JFrame("RISK");


        Container root = getContentPane();
        root.setLayout(new BorderLayout());

        //The layered pane will have multiple layers in-order for us to overlay components
        JLayeredPane jLayeredPane = new JLayeredPane();
        jLayeredPane.setSize(1150, 750);
        mapPanel = new mapPanel();
        jLayeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);
/*        mapPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("x:"+ e.getX()+" y: "+e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });*/
        countryPanel = new JPanel();
        countryPanel.setSize(1150, 750);
        countryPanel.setOpaque(false);
        countryPanel.setLayout(null);
        jLayeredPane.add(countryPanel, JLayeredPane.POPUP_LAYER);
        root.add(jLayeredPane, BorderLayout.CENTER);

        //Menu Panel will have the set of commands that a user can choose from in order to play the game
        JPanel menuPanel = new JPanel();
        //Creating the buttons and adding actionlistener to them
        newGameButton = new JButton("NewGame");
        attackButton = new JButton("Attack");
        passTurnButton = new JButton("PassTurn");
        saveButton = new JButton("SaveGame");
        continueButton = new JButton("ContinueGame");
        quitButton = new JButton("QuitGame");
        moveButton = new JButton("Move");
        attackButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        moveButton.setEnabled(false);
        listOfCommandButtons = new ArrayList<JButton>();
        listOfCommandButtons.add(attackButton);
        listOfCommandButtons.add(passTurnButton);
        //listOfCommandButtons.add(moveButton);
        listOfCommandButtons.add(quitButton);
        listOfCommandButtons.add(newGameButton);
        listOfCommandButtons.add(saveButton);
        listOfCommandButtons.add(continueButton);


        feedbackArea = new JTextArea("Welcome to Risk! Please press New Game in order to start!\n");
        feedbackArea.setRows(4);
        JScrollPane feedbackAreaScrollPane = new JScrollPane(feedbackArea);
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(feedbackAreaScrollPane, BorderLayout.NORTH);
        menuPanel.add(newGameButton, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        menuPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(attackButton, BorderLayout.CENTER);
        centerPanel.add(moveButton, BorderLayout.EAST);
        menuPanel.add(passTurnButton, BorderLayout.EAST);
        menuPanel.add(quitButton, BorderLayout.SOUTH);
        root.add(menuPanel, BorderLayout.SOUTH);

        JPanel southPanel = new JPanel();
        southPanel.setLayout((new BorderLayout()));
        menuPanel.add(southPanel, BorderLayout.AFTER_LAST_LINE);
        southPanel.add(saveButton, BorderLayout.CENTER);
        southPanel.add(continueButton, BorderLayout.EAST);
        root.add(menuPanel, BorderLayout.SOUTH);

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(1150, 750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void generateMapButtons() {
        this.mapPanel.setMapPicture(this.gameModel.getMyMap().getMapBackgroundFileName());
        this.gameModel.getMyMap().getAllCountries().forEach(country -> {
            CircleButton countryCircleButton = new CircleButton("", country.getCoordinate().getX(), country.getCoordinate().getY());
            this.countryPanel.add(countryCircleButton);
            mapOfButtons.put(country.getName(), countryCircleButton);
        });
        mapOfButtons.values().forEach(circleButton -> circleButton.addActionListener(gameController));

    }

    private void initialize(Controller gameController) {
        listOfCommandButtons.forEach(commandButton -> commandButton.addActionListener(gameController));
        mapOfButtons.values().forEach(circleButton -> circleButton.addActionListener(gameController));
        moveButton.addActionListener(gameController);
        this.gameController=gameController;
    }

    @Deprecated
    public void handleGameStartEvent(GameEvent game) {
        updateCountriesTroops(game.getGameMap());
        unlockButtons();

    }

    public void unlockButtons() {
        for (JButton button : listOfCommandButtons) {
            button.setEnabled(true);
        }
    }
    public void lockButtons() {
        for (JButton button : listOfCommandButtons) {
            button.setEnabled(false);
        }
    }
    @Deprecated
    private void updateCountriesTroops(Model.Map map) {
        map.getAllCountries().forEach(country -> {
            CircleButton b = mapOfButtons.get(country.getName());
            if (b != null) {
                b.setText("" + country.getNumberOfTroops());
            }
        });
    }

    public JTextArea getFeedbackArea() {
        return feedbackArea;
    }

    public void setFeedbackArea(String feedbackAreaText) {
        this.feedbackArea.append(feedbackAreaText);
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getMoveButton() {
        return moveButton;
    }

    public int numberOfPlayersRequest() {
        Integer[] choices = new Integer[]{2, 3, 4, 5, 6};
        int choice = askUserNumberOfPlayers(choices);
        return choice;

    }
    public int numberOfAIPlayersRequest(int numberOfPlayers) {
        Integer[] choices = new Integer[numberOfPlayers-1];
        for (int i = 0; i < (numberOfPlayers-1); i++) {
            choices[i]=i+1;
        }
        int choice = askUserAboutAI(choices);
        return choice;

    }

    public int bonusTroops() {
        return gameModel.getCurrentPlayer().totalBonusTroops();
    }

    public int numberOfBonusTroopsRequest() {
        Integer[] choices = new Integer[gameModel.getCurrentPlayer().totalBonusTroops()];
        for (int i = 0; i < (gameModel.getCurrentPlayer().totalBonusTroops()); i++) {
            choices[i]=i+1;
        }
        int choice = askUserAboutBonusTroops(choices);
        return choice;
    }
    public int numberOfBonusTroopsRequest(int numberOfMaxTroops) {
        Integer[] choices = new Integer[numberOfMaxTroops];
        for (int i = 0; i < numberOfMaxTroops; i++) {
            choices[i]=i+1;
        }
        int choice = askUserAboutBonusTroops(choices,numberOfMaxTroops);
        return choice;
    }
    public JButton getAttackButton() {
        return attackButton;
    }

    public Map<String, CircleButton> getMapOfButtons() {
        return mapOfButtons;
    }

    @Deprecated
    public void updateNumberOfTroopsOnCountryButtons() {
        //get the current player countries/buttons, turn them green
        //get the list of my countries
        ArrayList<Country> currentPlayersCountries = new ArrayList<Country>(gameModel.getCurrentPlayer().getMyCountries());
        //compare the list of my countries, with the list of buttons, and find out which buttons are mine
        mapOfButtons.forEach((key, value) -> value.setColor(Color.WHITE));
        mapOfButtons.forEach((key, value) -> value.setBackground(Color.WHITE));
        currentPlayersCountries.forEach(country -> {
            mapOfButtons.get(country.getName()).setColor(Color.GREEN);
            mapOfButtons.get(country.getName()).setBackground(Color.GREEN);
        });

    }

    @Override
    public void modelUpdated() {
        // set number of troops and color them as white
        gameModel.getMyMap().getAllCountries().forEach(country -> {
            CircleButton b = mapOfButtons.get(country.getName());
            if (b != null) {
                b.setText("" + country.getNumberOfTroops());
            }
        });

        gameModel.getPlayers().forEach(player -> {
            player.getMyCountries().stream().map(country -> mapOfButtons.get(country.getName())).forEach(circleButton -> {
                circleButton.setColor(player.getColour());
                circleButton.setBackground(player.getColour());
            });
        });

        gameModel.getCurrentPlayer().getMyCountries().stream().map(country -> mapOfButtons.get(country.getName())).forEach(circleButton -> {
            circleButton.setColor(Color.WHITE);
            circleButton.setBackground(Color.WHITE);
        });

        this.repaint(0);
    }

    @Override
    public void gameOver() {
        lockButtons();
        newGameButton.setEnabled(true);
        JOptionPane.showMessageDialog(this, "You have been eliminated! Starting a new game!");
    }

    @Override
    public void announceElimination(int playerNumber) {
        JOptionPane.showMessageDialog(this, "Player "+playerNumber+" has been eliminated!");
    }
    public void finishGame(){
        JOptionPane.showMessageDialog(this, "Thank you for playing, the game will now exit!");
        System.exit(0);
    }

    public String customMapRequest() {
            String[] choices = new String[]{"Default-Map.xml", "Custom-Map.xml"};
            String choice = askUserChoiceOfMap(choices);
            return choice;

        }

    /*
     * The map panel is the Image of the Risk world that we will be overlaying the components over.
     */
    class mapPanel extends JPanel {
        private Image image;

        public mapPanel() {

        }

        public void setMapPicture(String mapBackgroundFileName) {
            this.setSize(1150, 760);
            try {
                InputStream iS = this.getClass().getClassLoader().getResourceAsStream(mapBackgroundFileName);
                BufferedImage loadedImage = ImageIO.read(iS);
                image = loadedImage.getScaledInstance(1100, 600, Image.SCALE_DEFAULT);
            } catch (IOException ex) {
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