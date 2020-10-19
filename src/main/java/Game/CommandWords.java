package Game;

import Model.Game;

import java.util.Arrays;


public class CommandWords
{

    private static final String[] validCommands = {
            "map","attack", "pass","quit"
    };
    public void printCommands(){
        System.out.println(Arrays.deepToString(validCommands));
    }

    public CommandWords()
    {
    }

    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString))
                return true;
        }
        return false;
    }
}
