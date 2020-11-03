import Model.Country;
import Model.Game;
import Game.Command;
import Model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ModelTesting {

    Game game;

    @BeforeEach
    void init() {
        this.game = new Game();
    }

    @Test
    void testNewGameWithMaxPlayers() {
        game.startGame(6);
        assertEquals(6, game.getPlayers().size());
    }

    @Test
    void testNewGameWithMinPlayers() {
        game.startGame(2);
        assertEquals(2, game.getPlayers().size());
    }

    @Test
    void testPassTurn() {
        game.startGame(2);
        assertEquals(1,game.getCurrentPlayer().getPlayerNumber());
        game.passTurn();
        assertEquals(2,game.getCurrentPlayer().getPlayerNumber());

    }

    @Test
    void modelInitialNumberOfTroopsWith2Players() {
        game.startGame(2);
        assertEquals(50, game.getInitialNumberOfTroops());
    }

    @Test
    void modelInitialNumberOfTroopsWith6Players() {
        game.startGame(6);
        assertEquals(20, game.getInitialNumberOfTroops());
    }
    @Test
    void testNumberOfTroopsForEachPlayerUsingTheirCountries() {
        this.game.setRandomlyAllocateTroopsOnGameStart(true);
        game.initializePlayers(6);
        for (int i = 0; i < game.getPlayers().size(); i++) {
            int numberOfCountries = game.getPlayers().get(i).getMyCountries().size();
            int numberOfTroops = 0;
            for (int y = 0; y < numberOfCountries; y++) {
                numberOfTroops+=game.getPlayers().get(i).getMyCountries().get(y).getNumberOfTroops();
            }
            assertEquals(20, numberOfTroops);
        }
    }


    @RepeatedTest(10)
    void testAttackAlgorithm(){
        game.setRandomlyAllocateTroopsOnGameStart(true);
        game.initializePlayers(6);
        Player currentPlayer = game.getCurrentPlayer();
        Country c1 = currentPlayer.getMyCountries().get(0);
        Country c2 = game.getMyMap().getCountryNeighbours(c1).get(0);
        if(c1.getPlayer().getPlayerNumber() == c2.getPlayer().getPlayerNumber()) {
            System.out.println("same player");
            return;
        }
        Command c = new Command("attack", c1.getName(), c2.getName(), "1");
        int country1Troops = c1.getNumberOfTroops();
        int country2Troops = c2.getNumberOfTroops();
        game.initiateAttack(c);
        String s = "C1=" + country1Troops + ", C2=" + country2Troops + "\n" + c1 + "\n" + c2 + "\n";
        System.out.println(s);
        if(country1Troops == 1) {
            assertEquals(country1Troops, c1.getNumberOfTroops());
            assertEquals(country2Troops, c2.getNumberOfTroops());
        } else {
            assertTrue(c1.getNumberOfTroops() < country1Troops || c2.getNumberOfTroops() < country2Troops);
        }

    }
}