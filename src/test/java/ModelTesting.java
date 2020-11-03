import Game.Command;
import Model.Game;
import View.View;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.StringTokenizer;

import static org.junit.Assert.assertEquals;

public class ModelTesting {

    static Game game;

    @BeforeAll
    static void setupAll() {
        game = new Game();
        View viewer = new View(game);
        game.setViewer(viewer);
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
    void testAttackAlgorithm(){

    }


}