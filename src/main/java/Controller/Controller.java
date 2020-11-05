package Controller;

import Game.Command;
import Model.Game;
import View.CircleButton;
import View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author      Hasan Issa
 *
 * This is the Controller which takes user inputs, and transforms it into commands onto the Model
 */
public class Controller implements ActionListener {
    View gameView;
    Game gameModel;
    boolean attackInitiatedFlag = false;
    boolean attackingCountrySetFlag = false;
    boolean requestNumberOfTroopsFlag = false;
    boolean attackCommandFlag = false;
    String attackingCountry = "";
    String targetedCountry = "";
    int numberOfTroops;

    public Controller(Game gameModel, View gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        When an action is performed on the view, the controller handles it, based on the specific type of action.
        NewGame: Initialize the model with the provided number of players, and create the map, starting a new game

        Attack: Begin formulating the attack command, asking the user for the Attacking country, the target country, and the numer of troops attacking.
        Attack is done using state based algorithm, where the current state is determined by the level of input provided by the user. If the user has selected the attack button,
        they are in the attack initiated state, and the game is waiting for the attacking country to be selected, in order to enter the attacking country state.
        When the user selects the attacking country, the game is now in the wait for target country to be selected state. Then its the choose number of troops state.
        The controller then passes the model an attack command.

        PassTurn: Pass the turn from current player, to the next player in line
        Move: To be implemented next milestone
        QuitGame:Exit out the game
         */
        switch (e.getActionCommand()) {
            case "NewGame":
                int numberOfPlayers = gameView.numberOfPlayersRequest();
                gameModel.setRandomlyAllocateTroopsOnGameStart(true);
                gameModel.initializePlayers(numberOfPlayers);
                gameView.unlockButtons();
                gameView.setFeedbackArea("A game has been started with " + numberOfPlayers + " players.\nEach player is allocated " + gameModel.calculateTroops(numberOfPlayers) + " initial troops that will be randomly assigned." + "\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ". Your countries are shown in green!\n");
                gameView.getNewGameButton().setEnabled(false);
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
                gameView.setFeedbackArea("Current turn of: Player " + (gameModel.getCurrentPlayer().getPlayerNumber()) + " Your countries are show in green!\n");
                goToTheBottomOfTextField();
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
                            if (gameModel.getCurrentPlayer().isOneOfMyCountries(entry.getKey())) {
                                this.attackingCountry = entry.getKey();
                                gameView.setFeedbackArea("You are initiating an attack from " + attackingCountry + "! Please choose the neighbouring country you want to target.\n");
                                goToTheBottomOfTextField();
                                this.attackInitiatedFlag = false;
                                this.attackingCountrySetFlag = true;
                                break;
                            } else {
                                gameView.setFeedbackArea("You may only initiate attacks from your owned countries highlighted in green!\n");
                                goToTheBottomOfTextField();
                                this.attackingCountry = "";
                                attackInitiatedFlag = false;
                                break;
                            }
                        }
                    }
                    break;
                }

                if (attackingCountrySetFlag) {
                    for (Map.Entry<String, CircleButton> entry : gameView.getMapOfButtons().entrySet()) {
                        if (entry.getValue().equals(e.getSource())) {
                            if (gameModel.getMyMap().areNeighbours(attackingCountry, entry.getKey()) & !(gameModel.getMyMap().ownedBySamePlayer(attackingCountry, entry.getKey()))) {
                                this.targetedCountry = entry.getKey();
                                gameView.setFeedbackArea("You are initiating an attack from " + attackingCountry + " which is targeting " + targetedCountry + " !\n");
                                goToTheBottomOfTextField();
                                attackingCountrySetFlag = false;
                                attackInitiatedFlag = false;
                                requestNumberOfTroopsFlag = true;
                                break;
                            } else {
                                gameView.setFeedbackArea("You may only attack countries that are directly neighbouring the attacking country that do not belong to you!\n");
                                this.attackingCountry = "";
                                this.targetedCountry = "";
                                attackingCountrySetFlag = false;
                                attackInitiatedFlag = false;
                                goToTheBottomOfTextField();
                                break;
                            }
                        }
                    }
                }
                if (requestNumberOfTroopsFlag) {
                    List<Integer> optionList = new ArrayList<Integer>();
                    int numberOfTroops = gameModel.getMyMap().getCountryByName(attackingCountry).getNumberOfTroops();
                    if (numberOfTroops == 1 || numberOfTroops == 0) {
                        gameView.setFeedbackArea("You do not have enough troops in this country to initiate an attack!\n");
                        break;
                    }
                    for (int i = 1; i < numberOfTroops; i++) {
                        optionList.add(i);
                    }
                    Object[] options = optionList.toArray();
                    Object value = JOptionPane.showInputDialog(null,
                            "How many troops would you like to attack with?",
                            "Choose a number of troops to attack with",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    this.numberOfTroops = (Integer) value;
                    requestNumberOfTroopsFlag = false;
                    attackCommandFlag = true;
                }
                if (attackCommandFlag) {
                    gameView.setFeedbackArea("Attacking country: " + attackingCountry + ", Target country: " + targetedCountry + ", Number of troops: " + numberOfTroops + ".\n");
                    goToTheBottomOfTextField();
                    Command attackCommand = new Command("attack", attackingCountry, targetedCountry, Integer.toString(numberOfTroops));
                    gameModel.initiateAttack(attackCommand);
                    gameView.setFeedbackArea("Result: " + gameModel.getResult() + "\n");
                    attackCommandFlag = false;
                }
        }
    }

    private void goToTheBottomOfTextField() {
        gameView.getFeedbackArea().getCaret().setDot(Integer.MAX_VALUE);
    }
}

