import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Map;
import java.util.Scanner;

/**
 * This main class creates and initialises all the others: it create the map of all
 * Countries and their connections, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 */

public class Game {
    private Parser parser;
    private Graph<Country, DefaultEdge> map = new SimpleGraph<>(DefaultEdge.class);
    private int currentPlayer, numberOfPlayers;
    private MapUtil myMap= new MapUtil();

    public Game() {
        myMap.createTheInitialMap(map);
        parser = new Parser();
    }


    public void play() {
        boolean startedGame = false;
        do {
            try {
                startGame();
                startedGame = true;
            } catch (Exception exception) {
                System.err.println("You have encountered an error. Please report it to the administrator");
            }
        }
        while (!startedGame);
        boolean finished = false;
        while (!finished) {
            try {
                Command command = parser.getCommand();
                finished = processCommand(command);
            } catch (Exception exception) {
                System.err.println("You have encountered an error. Please report it to the administrator");
            }

        }
        System.out.println("Thank you for playing!");
    }

    private void printCurrentPlayer() {
        System.out.println("The current player is player-" + currentPlayer);
    }


    private boolean processCommand(Command command) {
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
        map.vertexSet().forEach(x -> System.out.println(x));
    }

    private void passTurn() {
        //pass the turn to the next player
        this.currentPlayer = (this.currentPlayer == this.numberOfPlayers) ? 1 : this.currentPlayer + 1;
        printCurrentPlayer();

    }

    private void startGame() {
        System.out.println("Welcome to RISK!");
        System.out.println("How many players will be playing today?");
        Scanner sc = new Scanner(System.in);
        this.numberOfPlayers = sc.nextInt();
        this.currentPlayer = 1;
        System.out.println("There will be " + numberOfPlayers + " players this game!");
        System.out.println("Your command words are:");
        parser.showCommands();
        printCurrentPlayer();

        // TODO implement the automatic allocation
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
        Country attackCountry = getCountry(attackCountryName);
        Country defenceCountry = getCountry(defenceCountryName);


        if (attackCountry.getPlayer() == this.currentPlayer) {
            // TODO implement the attack
        }
    }

    private Country getCountry(String CountryName) {
        for (Country c : map.vertexSet()) {
            c.getName().equals(CountryName);
            return c;
        }
        return null;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
