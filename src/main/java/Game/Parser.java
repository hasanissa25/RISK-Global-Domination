package Game;

import java.util.Scanner;
/**
 * @author      Hasan Issa
 *
 * This class handles the gathering of user input, and turns it into a Command.
 *
 */
public class Parser
{
    private CommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    public Parser()    {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }
    public Command getCommand(int currentPlayer)    {
        /**
         * @author      Hasan Issa
         *
         * This method handles the user input.
         *
         */
        String inputLine;
        String word1 = null;
        String word2 = null;
        String word3 = null;
        String word4 = null;


        System.out.print("Player-"+ (currentPlayer+1)+ "> ");

        inputLine = reader.nextLine().toLowerCase();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
                if(tokenizer.hasNext()) {
                    word3 = tokenizer.next();
                }
                if(tokenizer.hasNext()) {
                    word4 = tokenizer.next();
                }
            }
        }

        // Now check whether this word is known. If so, create a command
        // with it. If not, create a "null" command (for unknown command).
        if(commands.isCommand(word1)) {
            return new Command(word1, word2, word3,word4);
        }
        else {
            return new Command(null, null, null,null);
        }
    }
    public void showCommands(){
        System.out.println("\nYour available commands are:");
        commands.printCommands();
    }
}
