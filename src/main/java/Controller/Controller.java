package Controller;
import Model.Game;
import View.View;

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
                gameView.setFeedbackArea("New Game has been called!\n");
                break;
            case "Attack":
                gameView.setFeedbackArea("Attack has been called!\n");
                break;
            case "PassTurn":
                gameView.setFeedbackArea("Pass Turn has been called\n");
                break;
            case "QuitGame":
                gameView.setFeedbackArea("Quit game has been called!\n");
                break;
            default:
                System.out.println("This command was not recognized!");
        }
    }
}
