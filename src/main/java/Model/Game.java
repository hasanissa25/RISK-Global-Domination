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
 * @author Hasan Issa
 * <p>
 * This main class creates and initialises all the others. This class acts the Controller.
 * It create the map of all Countries and their neighbours.
 * It creates the parser and starts the game.
 * It also evaluates and executes the commands that the parser returns.
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
        System.out.println("The current player is player-" + (currentPlayer + 1) + "\n");
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
        /**
         * @author Hasan Issa
         *
         * Print a representation of the map
         *
         */
        System.out.println(this.myMap);
    }

    private void passTurn() {
        /**
         * @author Hasan Issa
         *
         * Pass the turn to the next player
         *
         */
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
        /**
         * @author Hasan Issa
         *
         * Ask the user for the number of players that will be playing, and then calls calculateTroops() to
         * determine how many troops each player will get.
         *
         */
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
        /**
         * @author Hasan Issa
         *
         * Iterate over each Country and assign them 1 troop from a random player. Iterate until each country has 1 troop.
         * Once all countries have 1 troop, randomize the allocation of the leftover troops. Only 1 player is allowed in a country.
         * Once all players have all their troops allocated, finish.
         *
         */
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
        //For each Player, iterate over all the countries that they own, and randomly increment the number of troops in their countries until they have no more troops left to allocate
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
        /**
         * @author Hasan Issa
         *
         * Checks the syntax of the command passed, and makes sure it is Attack followed by AttackingCountry DefendingCountry #OfTroopsAttacking
         * Ensures that the AttackingCountry is owned by the current player, the DefendingCountry is a neighbour of his, and that he has atleast 1 troop left in the country
         *
         */
        if (!checkAttackCommandSyntax(command)) return;

        String attackCountryName = command.getSecondWord().toLowerCase();
        setAttackingCountry(myMap.getCountryByName(attackCountryName));
        ;
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
            System.out.println("\n********************************Deploying Troops!********************************\n You have initiated an attack from " + attackCountryName + " using " + numberOfTroopsAttacking + " troop(s) against " + defenceCountryName + " who has " + getDefendingCountry().getNumberOfTroops() + " troop(s)!");
            attackAlgorithm(numberOfTroopsAttacking, getDefendingCountry().getNumberOfTroops());

        }
    }

    private void attackAlgorithm(int numberOfTroopsAttacking, int numberOfTroopsDefending) {
        /**
         * @author Hasan Issa
         *
         * Rolls dice based on the number of troops attacking and defending
         * Checks the highest attack dice with the highest defence dice, the higher number wins. A tie is a Defender win.
         * Checks the second highest attack dice with the second highest defence dice, the higher number wins. A tie is a Defender win.
         * Decrements the number of troops for the loser side.
         * If the attacker has no troops left, he loses the attack.
         * If the defender has no troops left, the attacker wins the attack.
         *
         */
        List<Integer> attackersDiceResults = new ArrayList<>();
        List<Integer> defendersDiceResults = new ArrayList<>();
        List<Integer> troopsLost = new ArrayList<>();
        int currentNumberOfAttackingTroops = numberOfTroopsAttacking;
        int currentNumberOfDefendingTroops = numberOfTroopsDefending;
        System.out.println("\n*******************************In attack algorithm!*******************************");
        System.out.println("The number of troops attacking is = " + currentNumberOfAttackingTroops + "\nThe number of troops defending is = " + currentNumberOfDefendingTroops);
        while (currentNumberOfAttackingTroops > 0 && currentNumberOfDefendingTroops > 0) {
            if (currentNumberOfAttackingTroops >= 3) {
                System.out.println("The current number of attacking troops is >= 3, therefore they get to use 3 dice!");
                attackersDiceResults = rollDice(3);
                if (currentNumberOfDefendingTroops == 1) {
                    System.out.println("The current number of defending troops is 1, so they get to use 1 dice.");
                    defendersDiceResults = rollDice(1);
                } else {
                    System.out.println("The current number of defending troops = 2, so they get to use 2 dice.");
                    defendersDiceResults = rollDice(2);
                }
                troopsLost = checkOutcomeOfBattle(attackersDiceResults, defendersDiceResults);
                currentNumberOfAttackingTroops = currentNumberOfAttackingTroops - troopsLost.get(0);
                attackingCountry.setNumberOfTroops(attackingCountry.getNumberOfTroops() - troopsLost.get(0));
                currentNumberOfDefendingTroops = currentNumberOfDefendingTroops - troopsLost.get(1);
                defendingCountry.setNumberOfTroops(defendingCountry.getNumberOfTroops() - troopsLost.get(1));

                System.out.println("Attacker lost " + troopsLost.get(0) + " troops and the Defender lost " + troopsLost.get(1));
                System.out.println("The Attackers current number of attacking troops left is " + currentNumberOfAttackingTroops);
                System.out.println("The Defenders current number of defending troops left is " + currentNumberOfDefendingTroops);
            }
            if (currentNumberOfAttackingTroops > 0 && currentNumberOfAttackingTroops < 3) {
                attackersDiceResults = rollDice(currentNumberOfAttackingTroops);
                if (currentNumberOfDefendingTroops == 1) {
                    defendersDiceResults = rollDice(1);
                } else {
                    defendersDiceResults = rollDice(2);
                }
                troopsLost = checkOutcomeOfBattle(attackersDiceResults, defendersDiceResults);
                currentNumberOfAttackingTroops = currentNumberOfAttackingTroops - troopsLost.get(0);
                attackingCountry.setNumberOfTroops(attackingCountry.getNumberOfTroops() - troopsLost.get(0));
                currentNumberOfDefendingTroops = currentNumberOfDefendingTroops - troopsLost.get(1);
                defendingCountry.setNumberOfTroops(defendingCountry.getNumberOfTroops() - troopsLost.get(1));

                System.out.println("Attacker lost " + troopsLost.get(0) + " troops and the Defender lost " + troopsLost.get(1));
                System.out.println("The Attackers current number of attacking troops left is " + currentNumberOfAttackingTroops);
                System.out.println("The Defenders current number of defending troops left is " + currentNumberOfDefendingTroops);
            }
        }
        if (currentNumberOfAttackingTroops <= 0) {
            System.out.println("\nSadly, you have lost the attack :(");
            System.out.println(attackingCountry.getName() + " now has " + attackingCountry.getNumberOfTroops() + " troop(s) left in the country");
            System.out.println(defendingCountry.getName() + " now has " + defendingCountry.getNumberOfTroops() + " troop(s) left in the country");
            attackingCountry.getPlayer().setTotalNumberOftroops(attackingCountry.getPlayer().getTotalNumberOftroops()- (numberOfTroopsAttacking-currentNumberOfAttackingTroops));
            System.out.println("Player "+attackingCountry.getPlayer().getPlayerNumber()+" has " + attackingCountry.getPlayer().getTotalNumberOftroops() + " total troops remaining");
            defendingCountry.getPlayer().setTotalNumberOftroops(defendingCountry.getPlayer().getTotalNumberOftroops()- (numberOfTroopsDefending-currentNumberOfDefendingTroops));
            System.out.println("Player "+defendingCountry.getPlayer().getPlayerNumber()+" has " + defendingCountry.getPlayer().getTotalNumberOftroops() + " total troops remaining");
            newTurn();
            if(attackingCountry.getPlayer().getTotalNumberOftroops()==0){
                players.remove(attackingCountry.getPlayer());
                System.out.println("Player "+attackingCountry.getPlayer().getPlayerNumber()+" has been removed from the game.");
            }
            if(defendingCountry.getPlayer().getTotalNumberOftroops()==0){
                players.remove(defendingCountry.getPlayer());
                System.out.println("Player "+defendingCountry.getPlayer().getPlayerNumber()+" has been removed from the game.");
            }
        }
        if (currentNumberOfDefendingTroops <= 0) {
            System.out.println("\nCongratulations! You have won the attack!");
            System.out.println(attackingCountry.getName() + " now has " + attackingCountry.getNumberOfTroops() + " troop(s) left in the country");
            System.out.println(defendingCountry.getName() + " now has " + defendingCountry.getNumberOfTroops() + " troop(s) left in the country");
            attackingCountry.getPlayer().setTotalNumberOftroops(attackingCountry.getPlayer().getTotalNumberOftroops()- (numberOfTroopsAttacking-currentNumberOfAttackingTroops));
            System.out.println("Player "+attackingCountry.getPlayer().getPlayerNumber()+" has " + attackingCountry.getPlayer().getTotalNumberOftroops() + " total troops remaining");
            defendingCountry.getPlayer().setTotalNumberOftroops(defendingCountry.getPlayer().getTotalNumberOftroops()- (numberOfTroopsDefending-currentNumberOfDefendingTroops));
            System.out.println("Player "+defendingCountry.getPlayer().getPlayerNumber()+" has " + defendingCountry.getPlayer().getTotalNumberOftroops() + " total troops remaining");
            newTurn();
            if(attackingCountry.getPlayer().getTotalNumberOftroops()==0){
                players.remove(attackingCountry.getPlayer());
                System.out.println("Player "+attackingCountry.getPlayer().getPlayerNumber()+" has been removed from the game.");
            }
            if(defendingCountry.getPlayer().getTotalNumberOftroops()==0){
                players.remove(defendingCountry.getPlayer());
                System.out.println("Player "+defendingCountry.getPlayer().getPlayerNumber()+" has been removed from the game.");
            }
        }

    }

    private List<Integer> checkOutcomeOfBattle(List<Integer> attackersDiceResults, List<Integer> defendersDiceResults) {
        List<Integer> troopsLost = new ArrayList<>();
        int troopsLostFromAttacker = 0;
        int troopsLostFromDefender = 0;
        int highestAttackRoll = 0;
        int secondHighestAttackRoll = 0;
        int highestDefenceRoll = 0;
        int secondHighestDefenceRoll = 0;
        System.out.println("\n******************************In Battle!******************************");
        System.out.println("The attackers dice roll :" + attackersDiceResults);
        System.out.println("The defenders dice roll :" + defendersDiceResults);

        for (Integer i : attackersDiceResults) {
            if (i.intValue() >= highestAttackRoll) {
                secondHighestAttackRoll = highestAttackRoll;
                highestAttackRoll = i.intValue();
            }
            if (i.intValue() > secondHighestAttackRoll && i.intValue() != highestAttackRoll)
                secondHighestAttackRoll = i.intValue();
            System.out.println("Checking dice; Highest attack roll is = " + highestAttackRoll + " Second highest attack roll is = " + secondHighestAttackRoll);
        }
        for (Integer i : defendersDiceResults) {
            if (i.intValue() >= highestDefenceRoll) {
                secondHighestDefenceRoll = highestDefenceRoll;
                highestDefenceRoll = i.intValue();
            }
            if (i.intValue() > secondHighestDefenceRoll && i.intValue() != highestDefenceRoll)
                secondHighestDefenceRoll = i.intValue();
            System.out.println("Checking dice; Highest defence roll is = " + highestDefenceRoll + " Second highest defence roll is = " + secondHighestDefenceRoll);
        }

        if (highestAttackRoll <= highestDefenceRoll) {
            troopsLostFromAttacker++;
        } else {
            troopsLostFromDefender++;
        }

        if (secondHighestAttackRoll <= secondHighestDefenceRoll && secondHighestAttackRoll != 0 && secondHighestDefenceRoll != 0) {
            troopsLostFromAttacker++;
        }
        if (secondHighestAttackRoll > secondHighestDefenceRoll && secondHighestAttackRoll != 0 && secondHighestDefenceRoll != 0) {
            troopsLostFromDefender++;
        }

        troopsLost.add(troopsLostFromAttacker);
        troopsLost.add(troopsLostFromDefender);
        return troopsLost;
    }

    private List<Integer> rollDice(Integer numberOfDiceToRoll) {
        List<Integer> diceRolls = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numberOfDiceToRoll; i++) {
            int diceRoll = random.nextInt(6) + 1;
            diceRolls.add(diceRoll);
        }
        return diceRolls;
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
        /**
         * @author Hasan Issa
         *
         * The main loop of the game. Takes user input until the user inputs Quit.
         *
         */
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
