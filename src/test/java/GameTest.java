import Model.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.StringTokenizer;

import Game.Command;

public class GameTest {

    static Game game;

    @BeforeAll
    static void setupAll() {
        game = new Game();

    }

    @Test
    void test() {
        InputStream iS = new ByteArrayInputStream("2".getBytes());
        System.setIn(iS);
        game.startGame();
        runCommand("map");
        runCommand("attack Alaska Egypt 5");
    }

    void runCommand(String command) {
        StringTokenizer sT = new StringTokenizer(command, " ");
        String arg1 = null, arg2 = null, arg3 = null, arg4 = null;
        if(sT.hasMoreTokens()) {
            arg1 = sT.nextToken();
            if(sT.hasMoreTokens()) {
                arg2 = sT.nextToken();
                if(sT.hasMoreTokens()) {
                    arg3 = sT.nextToken();
                    if(sT.hasMoreTokens()) {
                        arg4 = sT.nextToken();
                    }
                }
            }
        }

        Command command1= new Command(arg1, arg2, arg3, arg4);
        System.out.println("> " + command);
        game.processCommand(command1);
    }
}