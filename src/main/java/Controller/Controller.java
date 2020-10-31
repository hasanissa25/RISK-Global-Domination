package Controller;
import Model.Game;
import View.View;
import Game.Command;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    View gameView;
    Game gameModel;
    public Controller(Game gameModel,View gameView){
        this.gameModel=gameModel;
        this.gameView=gameView;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "NewGame":
                int numberOfPlayers= gameView.numberOfPlayersRequest();
                gameModel.initializePlayers(numberOfPlayers);
                gameView.setFeedbackArea("A game has been started with " + numberOfPlayers+ " players and each player has "+ gameModel.calculateTroops(numberOfPlayers)+ " starting troops!"+"\nTurn of: Player "+gameModel.printCurrentPlayer()+"\n");
               gameView.getNewGameButton().setEnabled(false);
                break;
            case "Attack":
                gameView.setFeedbackArea("Attack has been called!\n");
                break;
            case "PassTurn":
                gameView.setFeedbackArea("Pass Turn has been called\n");
                gameModel.passTurn();
                gameView.setFeedbackArea("Turn of: Player "+ (gameModel.printCurrentPlayer())+"\n");
                gameView.getFeedbackArea().getCaret().setDot( Integer.MAX_VALUE );

                break;
            case "QuitGame":
                gameView.setFeedbackArea("Quit game has been called!\n");
                gameModel.quitGame();
                break;
            default:
                System.out.println("This command was not recognized!");
        }
    }
}
