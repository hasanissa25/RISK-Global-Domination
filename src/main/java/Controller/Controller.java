package Controller;

import Model.Game;
import View.CircleButton;
import View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Controller implements ActionListener {
    View gameView;
    Game gameModel;
    boolean attackInitiatedFlag = false;
    boolean attackingCountrySetFlag = false;
    String attackingCountry = "";
    String targetedCountry = "";

    public Controller(Game gameModel, View gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "NewGame":
                int numberOfPlayers = gameView.numberOfPlayersRequest();
                gameModel.initializePlayers(numberOfPlayers);
                gameView.setFeedbackArea("A game has been started with " + numberOfPlayers + " players.\nEach player is allocated " + gameModel.calculateTroops(numberOfPlayers) + " initial troops that will be randomly assigned." + "\nCurrently turn of: Player " + gameModel.printCurrentPlayer() + ". Your countries are shown in green!\n");
                gameView.getNewGameButton().setEnabled(false);
                gameView.assignPlayerCountries();
                break;
            case "Attack":
                if (attackInitiatedFlag) {
                    gameView.setFeedbackArea("Please click a country that belongs to you (Highlighted in Green), to initiate an attack from it. \n");
                    goToTheBottomOfTextField();

                } else
                    gameView.setFeedbackArea("Attack has been called! Please Select one of your countries (Highlighted in Green), that you would like to initiate an attack from.\n");
                goToTheBottomOfTextField();
                attackInitiatedFlag = true;
                break;
            case "PassTurn":
                gameView.setFeedbackArea("Pass Turn has been called\n");
                gameModel.passTurn();
                gameView.setFeedbackArea("Current turn of: Player " + (gameModel.printCurrentPlayer()) + " Your countries are show in green!\n");
                goToTheBottomOfTextField();
                gameView.assignPlayerCountries();
                break;
            case "Move":
                gameView.setFeedbackArea("Move has been called! This feature is not implemented yet!\n");
                goToTheBottomOfTextField();
                break;
            case "QuitGame":
                gameView.setFeedbackArea("Quit game has been called!\n");
                goToTheBottomOfTextField();
                gameModel.quitGame();
                break;
            default:
                if (attackInitiatedFlag) {
                    for (Map.Entry<String, CircleButton> entry : gameView.getMapOfButtons().entrySet()) {
                        if (entry.getValue().equals(e.getSource())) {
                            if (gameModel.getPlayers().get(gameModel.getCurrentPlayer()).isOneOfMyCountries(entry.getKey())) {
                                this.attackingCountry = entry.getKey();
                                gameView.setFeedbackArea("You are initiating an attack from " + attackingCountry + "! Please choose the neighbouring country you want to target.\n");
                                goToTheBottomOfTextField();
                                this.attackInitiatedFlag = false;
                                this.attackingCountrySetFlag = true;
                                break;
                            } else {
                                gameView.setFeedbackArea("You may only initiate attacks from your owned countries highlighted in green!\n");
                                goToTheBottomOfTextField();
                                this.attackingCountry="";
                                attackInitiatedFlag=false;
                                break;
                            }
                        }
                    }
                    break;

                    //Command attackCommand = new Command("attack",secondWord,thirdWord,fourthWord);
                    //gameModel.initiateAttack(attackCommand);
                }

                if (attackingCountrySetFlag) {
                    for (Map.Entry<String, CircleButton> entry : gameView.getMapOfButtons().entrySet()) {
                        if (entry.getValue().equals(e.getSource())) {
                            System.out.println("Attacking country is "+attackingCountry+ "The country you want to target is : "+entry.getKey());
                            if (gameModel.getMyMap().areNeighbours(attackingCountry,entry.getKey()) & !(gameModel.getMyMap().ownedBySamePlayer(attackingCountry,entry.getKey()))) {
                                this.targetedCountry = entry.getKey();
                                gameView.setFeedbackArea("You are initiating an attack from " + attackingCountry + " which is targeting " + targetedCountry + " !\n");
                                goToTheBottomOfTextField();
                                attackingCountrySetFlag=false;
                                attackInitiatedFlag=false;
                                break;
                            } else{
                                gameView.setFeedbackArea("You may only attack countries that are directly neighbouring the attacking country that do not belong to you!\n");
                                this.attackingCountry="";
                                this.targetedCountry="";
                                attackingCountrySetFlag=false;
                                attackInitiatedFlag=false;
                                goToTheBottomOfTextField();
                                break;
                            }
                        }
                    }
                    break;
                }
                if (!gameView.getAttackButton().isEnabled()) {
                    gameView.setFeedbackArea("Please press New Game first!\n");
                    goToTheBottomOfTextField();
                } else {
                    gameView.setFeedbackArea("Please press the Attack button, then click on one of your countries (Highlighted in Green), then click on the country you want to attack.\n");
                    goToTheBottomOfTextField();
                }
        }
    }

    private void goToTheBottomOfTextField() {
        gameView.getFeedbackArea().getCaret().setDot(Integer.MAX_VALUE);
    }
}

