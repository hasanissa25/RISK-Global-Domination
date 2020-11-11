package Game;

import Model.Game;

import java.util.Arrays;

/**
 * @author      Hasan Issa
 *
 * This class holds the commands that could be passed to the game.
 *
 *
 */
public class CommandWords
{

    private static final String[] validCommands = {
            "map","attack", "move", "pass","quit"
    };
    public void printCommands(){
    }

    public CommandWords()
    {
    }

    public boolean isCommand(String aString)
    {
        /**
         * @author      Hasan Issa
         *
         * This method checks a string passed from the parser, to see if it is a valid command.
         *
         */
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        return false;
    }
}
