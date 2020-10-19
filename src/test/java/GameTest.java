import Game.Command;
import Model.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.StringTokenizer;
import static org.junit.Assert.*;

public class GameTest {

    static Game game;

    @BeforeAll
    static void setupAll() {
        game = new Game();

    }

    @Test
    void testMapAllocationAlgorithmWithMaxPlayers() {
        InputStream iS = new ByteArrayInputStream("6".getBytes());
        System.setIn(iS);
        game.startGame();
        for (int i = 0; i < game.getPlayers().size(); i++){
            assertEquals(0,game.getPlayers().get(i).getUndeployedTroops());
        }
    }
    @Test
    void testMapAllocationAlgorithmWithMinPlayers() {
        InputStream iS = new ByteArrayInputStream("2".getBytes());
        System.setIn(iS);
        game.startGame();
        for (int i = 0; i < game.getPlayers().size(); i++){
            assertEquals(0,game.getPlayers().get(i).getUndeployedTroops());
        }
    }

    @Test
    void testMapAllocationAlgorithmForTwoPlayers() {
        InputStream iS = new ByteArrayInputStream("2".getBytes());
        System.setIn(iS);
        game.startGame();
        for (int i = 0; i < game.getPlayers().size(); i++){
            int numberOfCountries=game.getPlayers().get(i).getMyCountries().size();
            int numberOfTroops=0;
            for(int y=0; y<numberOfCountries;y++){
                numberOfTroops+= game.getPlayers().get(i).getMyCountries().get(y).getNumberOfTroops();
            }
            assertEquals(50,numberOfTroops);
        }
    }
    @Test
    void testMapAllocationAlgorithmForSixPlayers() {
        InputStream iS = new ByteArrayInputStream("6".getBytes());
        System.setIn(iS);
        game.startGame();
        for (int i = 0; i < game.getPlayers().size(); i++){
            int numberOfCountries=game.getPlayers().get(i).getMyCountries().size();
            int numberOfTroops=0;
            for(int y=0; y<numberOfCountries;y++){
                numberOfTroops+= game.getPlayers().get(i).getMyCountries().get(y).getNumberOfTroops();
            }
            assertEquals(20,numberOfTroops);
        }
    }
    void runCommand(String command) {
        StringTokenizer sT = new StringTokenizer(command, " ");
        String arg1 = null, arg2 = null, arg3 = null, arg4 = null;
        if (sT.hasMoreTokens()) {
            arg1 = sT.nextToken();
            if (sT.hasMoreTokens()) {
                arg2 = sT.nextToken();
                if (sT.hasMoreTokens()) {
                    arg3 = sT.nextToken();
                    if (sT.hasMoreTokens()) {
                        arg4 = sT.nextToken();
                    }
                }
            }
        }

        Command command1 = new Command(arg1, arg2, arg3, arg4);
        System.out.println("> " + command);
        game.processCommand(command1);
    }
}