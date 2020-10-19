package Model;

import Game.Command;
import Game.Parser;

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
    private int currentPlayer = 1;
    private List<Player> players;
    private int numberOfPlayers;
    private int initialNumberOfTroops;
    private Map myMap = new Map();
    private InputStream inputStream;

    public Game() {
        this.myMap = new Map();
        parser = new Parser();
    }


    private void printCurrentPlayer() {
        System.out.println("The current player is player-" + currentPlayer);
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
                attack(command);
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
        this.currentPlayer = (this.currentPlayer == this.numberOfPlayers) ? 1 : this.currentPlayer + 1;
        printCurrentPlayer();

    }

    private void initializePlayers() {
        System.out.println("How many players will be playing today?");
        Scanner sc = new Scanner(System.in);
        this.numberOfPlayers = sc.nextInt();
        boolean correctNumberOfPlayers;
        do {
            if (numberOfPlayers < 7 && numberOfPlayers > 1) {
                correctNumberOfPlayers = true;

            } else {
                correctNumberOfPlayers = false;
                System.out.println("The number of players allowed is 2,3,4,5 or 6 players");
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


    private void attack(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Which country are you attacking from?");
            return;
        }
        if (!command.hasThirdWord()) {
            System.out.println("What country are you attacking?");
            return;
        }
        if (!command.hasFourthWord()) {
            System.out.println("How many troops are you using to attack?");
            return;
        }
        String attackCountryName = command.getSecondWord();
        String defenceCountryName = command.getThirdWord();
        int numberOfTroopsAttacking = command.getFourthWord();
        Country attackCountry = myMap.getCountryByName(attackCountryName);
        if (attackCountry == null) {
            System.out.println("The attacking country name is invalid. Please try again");
            return;
        }
        Country defenceCountry = myMap.getCountryByName(defenceCountryName);
        if (defenceCountry == null) {
            System.out.println("The country you are trying to attack is invalid. Please try again");
            return;
        }

    }

    public void play() {
        boolean startedGame = false;
        do {
            try {
                startGame();
                startedGame = true;
            } catch (Exception exception) {
                System.err.println("You have encountered an error starting the game. Please report it to the administrator");
                exception.printStackTrace();
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
        System.out.println("Welcome to RISK!");
        initializePlayers();
        System.out.println("There will be " + numberOfPlayers + " players this game!");
        randomizeMap();
        System.out.println("Your command words are:");
        parser.showCommands();
        printCurrentPlayer();

        // TODO implement the automatic allocation
    }

    private void randomizeMap() {
        //im going to iterate over the players and assign troops until the troops left is zero
        //im going to assign one troop from a player to a country if its empty.
        //Once all countries have 1 troop, I will randomize the allocation of the leftover troops to the countries that are owned by the player
        firstPhaseOfDeployment();

    }

    private int getUndeployedTroopsLeft() {
        return players.stream().map(x -> x.getUndeployedTroops()).reduce(0, Integer::sum);
    }

    private void firstPhaseOfDeployment() {
        List<Country> remainingCountries = new ArrayList<>(myMap.getAllCountries());
        Random randomize = new Random();
        int i = 0;
        while (!remainingCountries.isEmpty()) {
            int randomNumber = randomize.nextInt(remainingCountries.size());
            remainingCountries.get(randomNumber).setPlayer(players.get(i));
            players.get(i).decrementUndeployedNumberOfTroops();
            remainingCountries.get(randomNumber).incrementNumberOfTroops();
            players.get(i).getMyCountries().add(remainingCountries.get(randomNumber));
            remainingCountries.remove(randomNumber);
            i++;
            if (i == numberOfPlayers) i = 0;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
