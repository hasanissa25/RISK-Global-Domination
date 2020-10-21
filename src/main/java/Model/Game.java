package Model;

import Game.Command;
import Game.Parser;
import Game.UtilArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This main class creates and initialises all the others: it create the map of all
 * Countries and their connections, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 */

public class Game {
    private Parser parser;
    private int currentPlayer = 0;

    private List<Player> players;

    private int numberOfPlayers;
    private int initialNumberOfTroops;
    private Map myMap = new Map();
    private InputStream inputStream;
    private Country attackingCountry;

    public Game() {
        this.myMap = new Map();
        parser = new Parser();
    }


    private void printCurrentPlayer() {
        System.out.println("\n!*-----------------------------------------------NEW TURN!-------------------------------------------------------*!");
        System.out.println("The current player is player-" + (currentPlayer + 1)+"\n");
    }

    public boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("Unknown command");
            return false;
        }

        String commandWord = command.getCommandWord();
        switch (commandWord) {
            case "attack":
                initiateAttack(command);
                break;
            case "pass":
                passTurn();
                break;
            case "map":
                printMap();
                break;
            case "quit":
                wantToQuit = true;
                break;
        }

        return wantToQuit;
    }

    private void printMap() {
        //print a representation of the map
        System.out.println(this.myMap);
    }

    private void passTurn() {
        //pass the turn to the next player
        this.currentPlayer = (this.currentPlayer == this.numberOfPlayers) ? 0 : this.currentPlayer + 1;
        newTurn();
    }

    private void newTurn() {
        printCurrentPlayer();
        printListOfCurrentPlayerCountries();
        printListOfCurrentPlayerPossibleCountriesToAttack();
        parser.showCommands();
    }

    private void initializePlayers() {
        Scanner sc = new Scanner(System.in);
        this.numberOfPlayers = sc.nextInt();
        boolean correctNumberOfPlayers;
        do {
            if (numberOfPlayers < 7 && numberOfPlayers > 1) {
                correctNumberOfPlayers = true;

            } else {
                correctNumberOfPlayers = false;
                System.out.println("The number of players allowed is 2,3,4,5 or 6 players. Please try again.");
                numberOfPlayers = sc.nextInt();

            }
        } while (!correctNumberOfPlayers);
        createPlayers(numberOfPlayers, calculateTroops(numberOfPlayers));
    }

    private int calculateTroops(int numberOfPlayers) {
        initialNumberOfTroops = 0;
        switch (numberOfPlayers) {
            case 2:
                initialNumberOfTroops = 50;
                break;
            case 3:
                initialNumberOfTroops = 35;
                break;
            case 4:
                initialNumberOfTroops = 30;
                break;
            case 5:
                initialNumberOfTroops = 25;
                break;
            case 6:
                initialNumberOfTroops = 20;
                break;
        }
        return initialNumberOfTroops;
    }

    private void createPlayers(int numberOfPlayers, int initialNumberOfTroops) {
        players = new ArrayList<Player>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            players.add(new Player(i, initialNumberOfTroops));
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    private void randomizeMap() {
        //im going to iterate over the players and assign troops until the troops left is zero
        //im going to assign one troop from a player to a country if its empty.
        //Once all countries have 1 troop, I will randomize the allocation of the leftover troops to the countries that are owned by the player
        firstPhaseOfDeployment();
        secondPhaseOfDeployment();

    }

    private void firstPhaseOfDeployment() {
        Country[] remainingCountries = myMap.getAllCountries().toArray(new Country[0]);
        Random randomize = new Random();
        int i = 0;
        while (remainingCountries.length != 0) {
            int randomNumber = randomize.nextInt(remainingCountries.length);
            remainingCountries[randomNumber].setPlayer(players.get(i));
            players.get(i).decrementUndeployedNumberOfTroops();
            remainingCountries[randomNumber].incrementNumberOfTroops();
            players.get(i).getMyCountries().add(remainingCountries[randomNumber]);
            remainingCountries = UtilArray.removeTheElement(remainingCountries, randomNumber);
            i++;
            if (i == numberOfPlayers) i = 0;
        }
    }

    private void secondPhaseOfDeployment() {
        //For each Player, iterate over all the countries that he owns, and randomly increment the number of troops in his countries until he has no more troops left to allocate
        Random randomize = new Random();
        for (int i = 0; i < players.size(); i++) {
            while (players.get(i).getUndeployedTroops() > 0) {
                int randomNumber = randomize.nextInt(players.get(i).getMyCountries().size());
                players.get(i).getMyCountries().get(randomNumber).incrementNumberOfTroops();
                players.get(i).decrementUndeployedNumberOfTroops();
            }
        }
    }

    private void printListOfCurrentPlayerCountries() {
        System.out.println("Player " + (currentPlayer + 1) + " currently occupies the following countries: ");
        System.out.println(players.get(currentPlayer).getMyCountries().toString());
    }

    private void printListOfCurrentPlayerPossibleCountriesToAttack() {
        players.get(currentPlayer).getMyPossibleTargets(myMap);
    }

    public Country getAttackingCountry() {
        return attackingCountry;
    }

    public void setAttackingCountry(Country attackingCountry) {
        this.attackingCountry = attackingCountry;
    }

    private Country defendingCountry;


    public Country getDefendingCountry() {
        return defendingCountry;
    }

    public void setDefendingCountry(Country defendingCountry) {
        this.defendingCountry = defendingCountry;
    }

    private void initiateAttack(Command command) {
        if (!checkAttackCommandSyntax(command)) return;

        String attackCountryName = command.getSecondWord().toLowerCase();
        setAttackingCountry(myMap.getCountryByName(attackCountryName)); ;
        if (!checkAttackCountry(attackingCountry)) {
            return;
        }

        String defenceCountryName = command.getThirdWord();
        setDefendingCountry(myMap.getCountryByName(defenceCountryName));
        if (!checkDefenceCountry(attackingCountry, defendingCountry)) {
            return;
        }

        int numberOfTroopsAttacking = command.getFourthWord();
        if (checkNumberOfTroopsAttacking(attackCountryName, numberOfTroopsAttacking)) {
            System.out.println("You have initiated an attack from " + attackCountryName + " using " + numberOfTroopsAttacking + " troops against " + defenceCountryName);
        }

        //getNumberOfDefendingDice();

        //getAttackResult(){}


    }
    private boolean checkAttackCommandSyntax(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Which country are you attacking from? Please enter the attack command as such: attack MyCountry TargetCountry #ofTroopsAttacking");
            return false;
        }
        if (!command.hasThirdWord()) {
            System.out.println("What country are you attacking? Please enter the attack command as such: attack MyCountry TargetCountry #ofTroopsAttacking");
            return false;
        }
        if (!command.hasFourthWord()) {
            System.out.println("How many troops are you using to attack? Please enter the attack command as such: attack MyCountry TargetCountry #ofTroopsAttacking");
            return false;
        }
        return true;
    }

    private boolean checkAttackCountry(Country attackCountry) {
        if (attackCountry == null) {
            System.out.println("The attacking country name is invalid. Please try again using a valid country name. Refer to the map command to get a list of countries");
            return false;
        }
        if (players.get(currentPlayer).getMyCountries().contains(attackCountry)) {
            return true;
        } else {
            System.out.println("You do not own that country, so you can not attack with it. Please select one of your countries.");
            return false;
        }
    }

    private boolean checkDefenceCountry(Country attackCountry, Country defenceCountry) {
        if (defenceCountry == null) {
            System.out.println("The country you are trying to attack is invalid. Please try again using a valid country name. Refer to the map command to get a list of countries");
            return false;
        }
        if (players.get(currentPlayer).getNeighbours(myMap, attackCountry).contains(defenceCountry)) {
            if (!attackCountry.getPlayer().equals(defenceCountry.getPlayer())) {
                return true;
            } else {
                System.out.println("You can not attack your owned countries!");
                return false;
            }

        }


        System.out.println(attackCountry + " is not directly neighbouring " + defenceCountry + ". Please make sure the two countries share a border.");
        return false;
    }

    private boolean checkNumberOfTroopsAttacking(String attackCountryName, int numberOfTroopsAttacking) {
        if (players.get(currentPlayer).getACountry(attackCountryName).getNumberOfTroops() <= numberOfTroopsAttacking) {
            System.out.println("Please adjust the number of attacking troops. You must have at-least 1 troop left to defend your country. You can refer to the list of your countries to see how many troops you have in that country.");
            return false;

        }
        return true;
    }

    public void play() {
        boolean startedGame = false;
        System.out.println("Welcome to Risk! How many players will be playing today? This version of risk can hold up to and including 6 players.");
        do {
            try {
                startGame();
                startedGame = true;
            } catch (Exception exception) {
                System.err.println("Please enter a valid integer for the number of players. Your options are 2,3,4,5,6. ");
            }
        }
        while (!startedGame);
        boolean finished = false;
        while (!finished) {
            try {
                Command command = parser.getCommand(this.currentPlayer);
                finished = processCommand(command);
            } catch (Exception exception) {
                System.err.println("You have encountered an error. Please report it to the administrator");
                exception.printStackTrace();
            }

        }
        System.out.println("Thank you for playing!");
    }

    public void startGame() {
        initializePlayers();
        System.out.println("There will be " + numberOfPlayers + " players this game! The current version of the game uses an automatic allocation of troops and countries to the players.");
        randomizeMap();
        System.out.println("\nYour available commands are:");
        parser.showCommands();
        printCurrentPlayer();
        printListOfCurrentPlayerCountries();


    }

    private void printListOfCurrentPlayerCountries() {
        System.out.println(players.get(currentPlayer - 1).getMyCountries().toString());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
