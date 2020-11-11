package Model;

import Game.Command;
import Game.Parser;
import Game.UtilArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Hasan Issa
 * <p>
 * This main class creates and initialises all the others. This class is the model of the game.
 * It contains the map of all Countries and their neighbours.
 * It has viewers who are notified and updated, when the model has made changes.
 */
public class Game {
    public String result;
    private Parser parser;
    private Player currentPlayer;
    private int currentPlayerIndex;
    private List<Player> players;
    private ModelUpdateListener viewer;
    private int numberOfPlayers;
    private int initialNumberOfTroops;
    private Map myMap = new Map();
    private Country attackingCountry;
    private int countryInitialNumberOfTroops;
    private Country defendingCountry;
    private Country moveCountry;
    private Country destinationCountry;
    private boolean randomlyAllocateTroopsOnGameStart = false;

    public Game() {
        this.myMap = new Map();
        parser = new Parser();
        this.currentPlayerIndex = 0;
    }

    //public static void main(String[] args) {
      //  Game game = new Game();
    //}


    public void setRandomlyAllocateTroopsOnGameStart(boolean randomlyAllocateTroopsOnGameStart) {
        this.randomlyAllocateTroopsOnGameStart = randomlyAllocateTroopsOnGameStart;
    }

    public Map getMyMap() {
        return myMap;
    }

    public void passTurn() {
        this.currentPlayerIndex = (this.currentPlayerIndex == (this.numberOfPlayers - 1)) ? 0 : this.currentPlayerIndex + 1;
        this.currentPlayer = this.players.get(this.currentPlayerIndex);
        update();
    }

    private void newTurn() {
        this.getCurrentPlayer().getMyPossibleTargets(myMap);
    }

    public void initializePlayers(int numberOfPlayers) {
        /**
         * @author Hasan Issa
         *
         * Ask the user for the number of players that will be playing, and then calls calculateTroops() to
         * determine how many troops each player will get.
         *
         */
        this.numberOfPlayers = numberOfPlayers;
        createPlayers(numberOfPlayers, calculateTroops(numberOfPlayers));
        this.currentPlayer = players.get(0);
        if (randomlyAllocateTroopsOnGameStart)
            randomizeMap();
        this.getCurrentPlayer().getMyPossibleTargets(myMap);
        update();
    }

    private void update() {
        if (this.viewer != null)
            this.viewer.modelUpdated();
    }

    public int calculateTroops(int numberOfPlayers) {
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

    public String getResult() {
        return result;
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
            remainingCountries = UtilArray.removeTheElement(remainingCountries, randomNumber);
            i++;
            if (i == numberOfPlayers) i = 0;
        }
        for (Player player : players) {
            for (Country country : myMap.getAllCountries()) {
                if (country.getPlayer().equals(player)) {
                    player.getMyCountries().add(country);
                }
            }
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

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getInitialNumberOfTroops() {
        return initialNumberOfTroops;
    }

    public Country getAttackingCountry() {
        return attackingCountry;
    }

    public void setAttackingCountry(Country attackingCountry) {
        this.attackingCountry = attackingCountry;
        this.countryInitialNumberOfTroops = attackingCountry.getNumberOfTroops();
    }

    public Country getDefendingCountry() {
        return defendingCountry;
    }

    public void setDefendingCountry(Country defendingCountry) {
        this.defendingCountry = defendingCountry;
    }

    public Country getMovingCountry() {
        return moveCountry;
    }

    public void setMovingCountry(Country moveCountry) {
        this.moveCountry = moveCountry;
    }

    public Country getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(Country destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    /**
     * Checks the syntax of the command passed, and makes sure it is Attack followed by AttackingCountry DefendingCountry #OfTroopsAttacking
     * Ensures that the AttackingCountry is owned by the current player, the DefendingCountry is a neighbour of his, and that he has atleast 1 troop left in the country
     *
     * @author Hasan Issa
     */
    public boolean initiateAttack(Command command) {
        if (!checkAttackCommandSyntax(command)) {
            System.out.println("Attack syntax error ");
            return false;
        }

        String attackCountryName = command.getSecondWord().toLowerCase();
        setAttackingCountry(myMap.getCountryByName(attackCountryName));


        if (!checkAttackCountry(attackingCountry)) {
            System.out.println("Check attack failed");
            return false;
        }
        System.out.println("Attacking country owned by player " + attackingCountry.getPlayer().getPlayerNumber());

        String defenceCountryName = command.getThirdWord();
        setDefendingCountry(myMap.getCountryByName(defenceCountryName));
        if (!checkDefenceCountry(attackingCountry, defendingCountry)) {
            System.out.println("Check defence failed");
            return false;
        }
        System.out.println("Defending country owned by player " + defendingCountry.getPlayer().getPlayerNumber());

        int numberOfTroopsAttacking = command.getFourthWord();
        if (checkNumberOfTroopsAttacking(attackCountryName, numberOfTroopsAttacking)) {
            attackAlgorithm(numberOfTroopsAttacking, getDefendingCountry().getNumberOfTroops());

        }
        update();
        return true;
    }

    private String attackAlgorithm(int numberOfTroopsAttacking, int numberOfTroopsDefending) {
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
        while (currentNumberOfAttackingTroops > 0 && currentNumberOfDefendingTroops > 0) {
            if (currentNumberOfAttackingTroops >= 3) {
                attackersDiceResults = rollDice(3);
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
            }
        }
        if (currentNumberOfAttackingTroops <= 0) {
            this.result = "Sadly, you have lost the attack.\nThe attacker lost " + (numberOfTroopsAttacking - currentNumberOfAttackingTroops) + " troops during the attack, and the defender lost " + (numberOfTroopsDefending - currentNumberOfDefendingTroops) + " troops.\n";
            attackingCountry.getPlayer().setTotalNumberOftroops(attackingCountry.getPlayer().getTotalNumberOftroops() - (numberOfTroopsAttacking - currentNumberOfAttackingTroops));
            defendingCountry.getPlayer().setTotalNumberOftroops(defendingCountry.getPlayer().getTotalNumberOftroops() - (numberOfTroopsDefending - currentNumberOfDefendingTroops));
            newTurn();
            if (attackingCountry.getPlayer().getTotalNumberOftroops() == 0) {
                players.remove(attackingCountry.getPlayer());
            }
            if (defendingCountry.getPlayer().getTotalNumberOftroops() == 0) {
                players.remove(defendingCountry.getPlayer());
            }
        }
        if (currentNumberOfDefendingTroops <= 0) {
            this.result = "Congratulations! You have won the attack!\nThe attacker lost " + (numberOfTroopsAttacking - currentNumberOfAttackingTroops) + " troops during the attack, and the defender lost " + (numberOfTroopsDefending - currentNumberOfDefendingTroops) + " troops.\n";
            this.attackingCountry.getPlayer().setTotalNumberOftroops((attackingCountry.getPlayer().getTotalNumberOftroops() - (numberOfTroopsAttacking - currentNumberOfAttackingTroops)));
            this.getDefendingCountry().getPlayer().setTotalNumberOftroops(defendingCountry.getPlayer().getTotalNumberOftroops() - (numberOfTroopsDefending - currentNumberOfDefendingTroops));
            this.getDefendingCountry().getPlayer().getMyCountries().remove(this.getDefendingCountry());
            this.getAttackingCountry().getPlayer().getMyCountries().add(this.getDefendingCountry());
            this.getDefendingCountry().setPlayer(currentPlayer);
            this.getDefendingCountry().setNumberOfTroops(currentNumberOfAttackingTroops);
            this.getAttackingCountry().setNumberOfTroops(countryInitialNumberOfTroops - numberOfTroopsAttacking);
            newTurn();
            if (this.getAttackingCountry().getPlayer().getTotalNumberOftroops() == 0) {
                players.remove(this.getAttackingCountry().getPlayer());
                System.out.println("Player "+this.getAttackingCountry().getPlayer().getPlayerNumber()+" has been removed" );
            }
            if (this.getDefendingCountry().getPlayer().getTotalNumberOftroops() == 0) {
                players.remove(this.getDefendingCountry().getPlayer());
                System.out.println("Player "+this.getDefendingCountry().getPlayer().getPlayerNumber()+" has been removed" );
            }
        }
        return result;
    }

    private void moveAlgorithm(int numberOfTroopsMoving,Country moveCountry, Country destinationCountry) {
        /**
         * @author John Afolayan
         * Moves troops from source country to destination country.
         *
         */
        if (moveCountry.getNumberOfTroops()>1) {
            for (int i = 0; i < numberOfTroopsMoving; i++) {
                destinationCountry.incrementNumberOfTroops();
                moveCountry.decrementNumberOfTroops();
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

        for (Integer i : attackersDiceResults) {
            if (i.intValue() >= highestAttackRoll) {
                secondHighestAttackRoll = highestAttackRoll;
                highestAttackRoll = i.intValue();
            }
            if (i.intValue() > secondHighestAttackRoll && i.intValue() != highestAttackRoll)
                secondHighestAttackRoll = i.intValue();
        }
        for (Integer i : defendersDiceResults) {
            if (i.intValue() >= highestDefenceRoll) {
                secondHighestDefenceRoll = highestDefenceRoll;
                highestDefenceRoll = i.intValue();
            }
            if (i.intValue() > secondHighestDefenceRoll && i.intValue() != highestDefenceRoll)
                secondHighestDefenceRoll = i.intValue();
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
            return false;
        }
        if (!command.hasThirdWord()) {
            return false;
        }
        if (!command.hasFourthWord()) {
            return false;
        }
        return true;
    }

    private boolean checkAttackCountry(Country attackCountry) {
        if (attackCountry == null) {
            return false;
        }
        if (this.getCurrentPlayer().getMyCountries().contains(attackCountry)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkDefenceCountry(Country attackCountry, Country defenceCountry) {
        if (defenceCountry == null) {
            return false;
        }
        if (this.getCurrentPlayer().getNeighbours(myMap, attackCountry).contains(defenceCountry)) {
            if (!attackCountry.getPlayer().equals(defenceCountry.getPlayer())) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    private boolean checkNumberOfTroopsAttacking(String attackCountryName, int numberOfTroopsAttacking) {
        if (this.getCurrentPlayer().getACountry(attackCountryName).getNumberOfTroops() <= numberOfTroopsAttacking) {
            return false;

        }
        return true;
    }

    /**
     * @author John Afolayan
     *
     * Checks the syntax of the command passed, and makes sure Move is followed by SourceCountry DestinationCountry #OfTroopsMoved
     * Ensures that the SourceCountry is owned by the current player, the DestinationCountry is also owned by the current player, and that he has atleast 1 troop left in the country
     *
     * Credit to Hasan for initiateAttack() and similar methods, the following methods use the same logic but to move troops around.
     */
    public boolean initiateMove(Command command) {
        if (!checkMoveCommandSyntax(command)) {
            System.out.println("Move syntax error ");
            return false;
        }

        String moveCountryName = command.getSecondWord().toLowerCase();
        setMovingCountry(myMap.getCountryByName(moveCountryName));


        if (!checkMoveCountry(moveCountry)) {
            System.out.println("Check move failed");
            return false;
        }

        String destinationCountryName = command.getThirdWord();
        setDestinationCountry(myMap.getCountryByName(destinationCountryName));
        if (!checkDestinationCountry(moveCountry, destinationCountry)) {
            System.out.println("Check destination failed");
            return false;
        }

        int numberOfTroopsMoving = command.getFourthWord();
        if (checkNumberOfTroopsMoving(moveCountryName, numberOfTroopsMoving)) {
            moveAlgorithm(numberOfTroopsMoving, moveCountry, destinationCountry);

        }
        update();
        return true;
    }

    private boolean checkMoveCommandSyntax(Command command) {
        if (!command.hasSecondWord()) {
            return false;
        }
        if (!command.hasThirdWord()) {
            return false;
        }
        if (!command.hasFourthWord()) {
            return false;
        }
        return true;
    }

    private boolean checkMoveCountry(Country moveCountry) {
        if (moveCountry == null) {
            return false;
        }
        if (this.getCurrentPlayer().getMyCountries().contains(moveCountry)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkDestinationCountry(Country moveCountry, Country destinationCountry) {
        if (destinationCountry == null) {
            return false;
        }
        return true;
    }

    private boolean checkNumberOfTroopsMoving(String moveCountryName, int numberOfTroopsMoving) {
        if (this.getCurrentPlayer().getACountry(moveCountryName).getNumberOfTroops() <= numberOfTroopsMoving) {
            return false;

        }
        return true;
    }

    public void setViewer(ModelUpdateListener viewer) {
        this.viewer = viewer;
    }


    public void startGame(int numberOfPlayers) {
        initializePlayers(numberOfPlayers);
        newTurn();

    }

    public void quitGame() {
        System.exit(0);
    }


}
