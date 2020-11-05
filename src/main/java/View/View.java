package View;

import Controller.Controller;
import Game.GameEvent;
import Model.Country;
import Model.Game;
import Model.ModelUpdateListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    JButton quitButton;
    ArrayList<JButton> listOfCommandButtons;
    ArrayList<CircleButton> listOfCountryButtons;
    JTextArea feedbackArea;
    JButton moveButton;
    Map<String, CircleButton> mapOfButtons = new HashMap<>();
    CircleButton alaska;
    CircleButton alberta;
    CircleButton centralAmerica;
    CircleButton easternunitedstates;
    CircleButton greenland;
    CircleButton NorthwestTerritory;
    CircleButton Ontario;
    CircleButton Quebec;
    CircleButton WesternUnitedStates;
    CircleButton Argentina;
    CircleButton Brazil;
    CircleButton Peru;
    CircleButton Venezuela;
    CircleButton EasternAustralia;
    CircleButton Indonesia;
    CircleButton NewGuinea;
    CircleButton WesternAustralia;
    CircleButton Kazakhstan;
    CircleButton China;
    CircleButton India;
    CircleButton Irkutsk;
    CircleButton Japan;
    CircleButton Kamchatka;
    CircleButton MiddleEast;
    CircleButton Mongolia;
    CircleButton Siam;
    CircleButton Siberia;
    CircleButton Ural;
    CircleButton Yakutsk;
    CircleButton GreatBritain;
    CircleButton Iceland;
    CircleButton NorthernEurope;
    CircleButton Scandinavia;
    CircleButton SouthernEurope;
    CircleButton Ukraine;
    CircleButton WesternEurope;
    CircleButton Congo;
    CircleButton EastAfrica;
    CircleButton Egypt;
    CircleButton Madagascar;
    CircleButton NorthAfrica;
    CircleButton SouthAfrica;

    public View(Game gameModel) {
        Initialize();
        this.gameModel = gameModel;
    }

    public static void main(String[] args) {

        Game gameModel = new Game();
        View gameView = new View(gameModel);
        gameModel.setViewer(gameView);
        Controller gameController = new Controller(gameModel, gameView);
        gameView.initialize(gameController);
    }

    static int askUser(Integer[] choices) {
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

        JPanel countryPanel = new JPanel();
        countryPanel.setSize(1150, 750);
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
        countryPanel.add(Argentina);
        countryPanel.add(Brazil);
        countryPanel.add(Peru);
        countryPanel.add(Venezuela);
        countryPanel.add(EasternAustralia);
        countryPanel.add(Indonesia);
        countryPanel.add(NewGuinea);
        countryPanel.add(WesternAustralia);
        countryPanel.add(Kazakhstan);
        countryPanel.add(China);
        countryPanel.add(India);
        countryPanel.add(Irkutsk);
        countryPanel.add(Japan);
        countryPanel.add(Kamchatka);
        countryPanel.add(MiddleEast);
        countryPanel.add(Mongolia);
        countryPanel.add(Siam);
        countryPanel.add(Siberia);
        countryPanel.add(Ural);
        countryPanel.add(Yakutsk);
        countryPanel.add(GreatBritain);
        countryPanel.add(Iceland);
        countryPanel.add(NorthernEurope);
        countryPanel.add(Scandinavia);
        countryPanel.add(SouthernEurope);
        countryPanel.add(Ukraine);
        countryPanel.add(WesternEurope);
        countryPanel.add(Congo);
        countryPanel.add(EastAfrica);
        countryPanel.add(Egypt);
        countryPanel.add(Madagascar);
        countryPanel.add(NorthAfrica);
        countryPanel.add(SouthAfrica);
        jLayeredPane.add(countryPanel, JLayeredPane.POPUP_LAYER);
        root.add(jLayeredPane, BorderLayout.CENTER);
        listOfCountryButtons = new ArrayList<CircleButton>();
        listOfCountryButtons.add(alaska);
        listOfCountryButtons.add(alberta);
        listOfCountryButtons.add(centralAmerica);
        listOfCountryButtons.add(NorthwestTerritory);
        listOfCountryButtons.add(WesternUnitedStates);
        listOfCountryButtons.add(Ontario);
        listOfCountryButtons.add(Quebec);
        listOfCountryButtons.add(greenland);
        listOfCountryButtons.add(easternunitedstates);
        listOfCountryButtons.add(Argentina);
        listOfCountryButtons.add(Brazil);
        listOfCountryButtons.add(Peru);
        listOfCountryButtons.add(Venezuela);
        listOfCountryButtons.add(EasternAustralia);
        listOfCountryButtons.add(Indonesia);
        listOfCountryButtons.add(NewGuinea);
        listOfCountryButtons.add(WesternAustralia);
        listOfCountryButtons.add(Kazakhstan);
        listOfCountryButtons.add(China);
        listOfCountryButtons.add(India);
        listOfCountryButtons.add(Irkutsk);
        listOfCountryButtons.add(Japan);
        listOfCountryButtons.add(Kamchatka);
        listOfCountryButtons.add(MiddleEast);
        listOfCountryButtons.add(Mongolia);
        listOfCountryButtons.add(Siam);
        listOfCountryButtons.add(Siberia);
        listOfCountryButtons.add(Ural);
        listOfCountryButtons.add(Yakutsk);
        listOfCountryButtons.add(GreatBritain);
        listOfCountryButtons.add(Iceland);
        listOfCountryButtons.add(NorthernEurope);
        listOfCountryButtons.add(Scandinavia);
        listOfCountryButtons.add(SouthernEurope);
        listOfCountryButtons.add(Ukraine);
        listOfCountryButtons.add(WesternEurope);
        listOfCountryButtons.add(Congo);
        listOfCountryButtons.add(EastAfrica);
        listOfCountryButtons.add(Madagascar);
        listOfCountryButtons.add(NorthAfrica);
        listOfCountryButtons.add(SouthAfrica);
        listOfCountryButtons.add(Egypt);
        //Menu Panel will have the set of commands that a user can choose from in order to play the game
        JPanel menuPanel = new JPanel();
        //Creating the buttons and adding actionlistener to them
        newGameButton = new JButton("NewGame");
        attackButton = new JButton("Attack");
        passTurnButton = new JButton("PassTurn");
        quitButton = new JButton("QuitGame");
        moveButton = new JButton("Move");
        attackButton.setEnabled(false);
        passTurnButton.setEnabled(false);
        moveButton.setEnabled(false);
        listOfCommandButtons = new ArrayList<JButton>();
        listOfCommandButtons.add(attackButton);
        listOfCommandButtons.add(passTurnButton);
        listOfCommandButtons.add(moveButton);
        listOfCommandButtons.add(quitButton);
        listOfCommandButtons.add(newGameButton);


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

        //Initialization of the frame
        this.setVisible(true);
        this.setResizable(false);
        this.setSize(1150, 750);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void countryView() {
        alaska = new CircleButton("", 60, 80);
        alberta = new CircleButton("", 156, 131);
        centralAmerica = new CircleButton("", 180, 287);
        NorthwestTerritory = new CircleButton("", 173, 85);
        WesternUnitedStates = new CircleButton("", 160, 205);
        Ontario = new CircleButton("", 237, 148);
        Quebec = new CircleButton("", 302, 143);
        greenland = new CircleButton("", 378, 52);
        easternunitedstates = new CircleButton("", 247, 221);

        Argentina = new CircleButton("", 266, 500);
        Brazil = new CircleButton("", 335, 405);
        Peru = new CircleButton("", 230, 405);
        Venezuela = new CircleButton("", 260, 320);

        EasternAustralia = new CircleButton("", 1044, 520);
        Indonesia = new CircleButton("", 913, 439);
        NewGuinea = new CircleButton("", 1003, 415);
        WesternAustralia = new CircleButton("", 952, 520);

        Kazakhstan = new CircleButton("", 745, 222);
        China = new CircleButton("", 861, 260);
        India = new CircleButton("", 799, 310);
        Irkutsk = new CircleButton("", 882, 145);
        Japan = new CircleButton("", 1000, 210);
        Kamchatka = new CircleButton("", 969, 101);
        MiddleEast = new CircleButton("", 676, 308);
        Mongolia = new CircleButton("", 899, 195);
        Siam = new CircleButton("", 896, 344);
        Siberia = new CircleButton("", 817, 108);
        Ural = new CircleButton("", 755, 141);
        Yakutsk = new CircleButton("", 896, 73);

        GreatBritain = new CircleButton("", 451, 181);
        Iceland = new CircleButton("", 467, 119);
        NorthernEurope = new CircleButton("", 544, 194);
        Scandinavia = new CircleButton("", 556, 110);
        SouthernEurope = new CircleButton("", 557, 256);
        Ukraine = new CircleButton("", 645, 167);
        WesternEurope = new CircleButton("", 465, 264);

        Congo = new CircleButton("", 590, 443);
        EastAfrica = new CircleButton("", 650, 417);
        Egypt = new CircleButton("", 578, 339);
        Madagascar = new CircleButton("", 695, 521);
        NorthAfrica = new CircleButton("", 489, 365);
        SouthAfrica = new CircleButton("", 590, 521);

        mapOfButtons.put("Alaska", alaska);
        mapOfButtons.put("Alberta", alberta);
        mapOfButtons.put("CentralAmerica", centralAmerica);
        mapOfButtons.put("NorthwestTerritory", NorthwestTerritory);
        mapOfButtons.put("WesternUnitedStates", WesternUnitedStates);
        mapOfButtons.put("Ontario", Ontario);
        mapOfButtons.put("Quebec", Quebec);
        mapOfButtons.put("Greenland", greenland);
        mapOfButtons.put("EasternUnitedStates", easternunitedstates);
        mapOfButtons.put("Argentina", Argentina);
        mapOfButtons.put("Brazil", Brazil);
        mapOfButtons.put("Peru", Peru);
        mapOfButtons.put("Venezuela", Venezuela);

        mapOfButtons.put("EasternAustralia", EasternAustralia);
        mapOfButtons.put("Indonesia", Indonesia);
        mapOfButtons.put("NewGuinea", NewGuinea);
        mapOfButtons.put("WesternAustralia", WesternAustralia);

        mapOfButtons.put("Kazakhstan", Kazakhstan);
        mapOfButtons.put("China", China);
        mapOfButtons.put("India", India);
        mapOfButtons.put("Irkutsk", Irkutsk);
        mapOfButtons.put("Japan", Japan);
        mapOfButtons.put("Kamchatka", Kamchatka);
        mapOfButtons.put("MiddleEast", MiddleEast);
        mapOfButtons.put("Mongolia", Mongolia);
        mapOfButtons.put("Siam", Siam);
        mapOfButtons.put("Siberia", Siberia);
        mapOfButtons.put("Ural", Ural);
        mapOfButtons.put("Yakutsk", Yakutsk);

        mapOfButtons.put("GreatBritain", GreatBritain);
        mapOfButtons.put("Iceland", Iceland);
        mapOfButtons.put("NorthernEurope", NorthernEurope);
        mapOfButtons.put("Scandinavia", Scandinavia);
        mapOfButtons.put("SouthernEurope", SouthernEurope);
        mapOfButtons.put("Ukraine", Ukraine);
        mapOfButtons.put("WesternEurope", WesternEurope);

        mapOfButtons.put("Congo", Congo);
        mapOfButtons.put("EastAfrica", EastAfrica);
        mapOfButtons.put("Egypt", Egypt);
        mapOfButtons.put("Madagascar", Madagascar);
        mapOfButtons.put("NorthAfrica", NorthAfrica);
        mapOfButtons.put("SouthAfrica", SouthAfrica);


    }

    private void initialize(Controller gameController) {
        for (JButton button : listOfCommandButtons) {
            button.addActionListener(gameController);
        }
        for (CircleButton button : listOfCountryButtons) {
            button.addActionListener(gameController);
        }

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

    public int numberOfPlayersRequest() {
        Integer[] choices = new Integer[]{2, 3, 4, 5, 6};
        int choice = askUser(choices);
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

        java.util.List<Country> currentPlayerCountries = gameModel.getCurrentPlayer().getMyCountries();
        java.util.Set<Country> allCountries = gameModel.getMyMap().getAllCountries();
        java.util.List<CircleButton> whiteButtons = allCountries.stream().filter(x -> !currentPlayerCountries.contains(x)).map(x -> mapOfButtons.get(x.getName())).collect(Collectors.toList());
        java.util.List<CircleButton> greenButtons = currentPlayerCountries.stream().map(x -> mapOfButtons.get(x.getName())).collect(Collectors.toList());
        whiteButtons.forEach(b -> {
            b.setColor(Color.WHITE);
            b.setBackground(Color.WHITE);
        });

        greenButtons.forEach(b -> {
            b.setColor(Color.GREEN);
            b.setBackground(Color.GREEN);
        });
        this.repaint(0);
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
