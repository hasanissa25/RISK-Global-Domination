package Controller;

import Game.Command;
import Model.Game;
import Model.Player;
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
    boolean requestNumberOfTroopsToMove = false;
    boolean attackCommandFlag = false;
    boolean moveInitiated = false;
    boolean bonusTroopsFlag = false;
    boolean sourceCountrySetFlag = false;
    boolean moveCommandFlag = false;
    String attackingCountry = "";
    String destinationCountry = "";
    String sourceCountry = "";
    String countryToAllocateTroopsTo ="";
    int numberOfTroops;
    int bonusTroops;
    int currentNumberOfBonusTroopsToPlace;
    int numberOfPlayers;
    int numberOfAIPlayers;
    int initialNumberOfPlayers;

    public Controller(Game gameModel, View gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        When an action is performed on the view, the controller handles it, based on the specific type of action.
        NewGame: Initialize the model with the provided number of players, and create the map, starting a new game

        Attack: Begin formulating the attack command, asking the user for the Attacking country, the target country, and the nubmer of troops attacking.
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
                initialNumberOfPlayers=gameView.numberOfPlayersRequest();
                numberOfPlayers = initialNumberOfPlayers;
                numberOfAIPlayers= gameView.numberOfAIPlayersRequest(numberOfPlayers);
                gameModel.setRandomlyAllocateTroopsOnGameStart(true);
                gameModel.initializePlayers(numberOfPlayers);
                gameView.unlockButtons();
                gameView.setFeedbackArea("A game has been started with " + numberOfPlayers + " players.\nEach player is allocated " + gameModel.calculateTroops(numberOfPlayers) + " initial troops that will be randomly assigned." + "\nCurrently turn of: Player " + gameModel.getCurrentPlayer().getPlayerNumber() + ". Your countries are shown in green!\n");
                gameView.getNewGameButton().setEnabled(false);
                initialRequestBonusTroopsAllocation();
                this.bonusTroopsFlag=true;
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
                gameView.getMoveButton().setEnabled(false);
                while(gameModel.getCurrentPlayer().getPlayerNumber()>(initialNumberOfPlayers-numberOfAIPlayers)){
                    String aiAllocateBonusTroops= gameModel.aiAllocateBonusTroops();
                    String aiMove=gameModel.aiAlgorithm();
                    gameView.setFeedbackArea("Current turn of: Player " + (gameModel.getCurrentPlayer().getPlayerNumber()) + " This player is controlled by AI!\n");
                    gameView.setFeedbackArea(aiAllocateBonusTroops);
                    gameView.setFeedbackArea(aiMove);
                    Player currentPlayer= gameModel.getCurrentPlayer();
                    gameModel.passTurn( );
                    Player afterPassPlayer= gameModel.getCurrentPlayer();
                    if(currentPlayer.getPlayerNumber()==afterPassPlayer.getPlayerNumber()){
                        JOptionPane.showMessageDialog(gameView, "Player "+afterPassPlayer.getPlayerNumber()+" has won the game!");
                        gameView.finishGame();
                        break;
                    }
                    goToTheBottomOfTextField();
                }
                    gameView.setFeedbackArea("Current turn of: Player " + (gameModel.getCurrentPlayer().getPlayerNumber()) + " Your countries are show in green!\n");
                    goToTheBottomOfTextField();
                    initialRequestBonusTroopsAllocation();
                    this.bonusTroopsFlag=true;
                break;
            case "Move":
                gameView.setFeedbackArea("Move has been called! Please click the country belonging to you (Highlighted in Green), which you would like to move troops from. \n");
                moveInitiated = true;
                goToTheBottomOfTextField();
                break;
            case "QuitGame":
                gameView.setFeedbackArea("Quit game has been called!\n");
                goToTheBottomOfTextField();
                gameModel.quitGame();
                break;
            default:
                if(bonusTroopsFlag){
                    while (this.bonusTroops>0){
                        for (Map.Entry<String, CircleButton> entry : gameView.getMapOfButtons().entrySet()) {
                            if (entry.getValue().equals(e.getSource())) {
                                if (gameModel.getCurrentPlayer().isOneOfMyCountries(entry.getKey())) {
                                    this.countryToAllocateTroopsTo = entry.getKey();
                                    gameModel.getMyMap().getCountryByName(countryToAllocateTroopsTo).addTroops(currentNumberOfBonusTroopsToPlace);
                                    gameView.setFeedbackArea("You added " +currentNumberOfBonusTroopsToPlace  + " to "+ countryToAllocateTroopsTo+ "\n");
                                    this.bonusTroops=this.bonusTroops-currentNumberOfBonusTroopsToPlace;
                                    goToTheBottomOfTextField();
                                    gameModel.update();
                                    if(this.bonusTroops==0){
                                        bonusTroopsFlag=false;
                                    }else{
                                        requestBonusTroopsAllocation();
                                    }
                                    break;
                                } else {
                                    gameView.setFeedbackArea("You may only add troops to countries that you own highlighted in green!\n");
                                    goToTheBottomOfTextField();
                                    this.countryToAllocateTroopsTo = "";
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
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
                                this.destinationCountry = entry.getKey();
                                gameView.setFeedbackArea("You are initiating an attack from " + attackingCountry + " which is targeting " + destinationCountry + " !\n");
                                goToTheBottomOfTextField();
                                attackingCountrySetFlag = false;
                                attackInitiatedFlag = false;
                                requestNumberOfTroopsFlag = true;
                                break;
                            } else {
                                gameView.setFeedbackArea("You may only attack countries that are directly neighbouring the attacking country that do not belong to you!\n");
                                this.attackingCountry = "";
                                this.destinationCountry = "";
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
                    gameView.setFeedbackArea("Attacking country: " + attackingCountry + ", Target country: " + destinationCountry + ", Number of troops: " + numberOfTroops + ".\n");
                    goToTheBottomOfTextField();
                    Command attackCommand = new Command("attack", attackingCountry, destinationCountry, Integer.toString(numberOfTroops));
                    gameModel.initiateAttack(attackCommand);
                    gameView.setFeedbackArea("Result: " + gameModel.getResult() + "\n");
                    attackCommandFlag = false;
                    gameView.getMoveButton().setEnabled(true);
                }
                if (moveInitiated) {
                    for (Map.Entry<String, CircleButton> entry : gameView.getMapOfButtons().entrySet()) {
                        if (entry.getValue().equals(e.getSource())) {
                            if (gameModel.getCurrentPlayer().isOneOfMyCountries(entry.getKey())) {
                                this.sourceCountry = entry.getKey();
                                gameView.setFeedbackArea("You are moving troops from " + sourceCountry + ". Please choose the destination country you want to move to.\n");
                                goToTheBottomOfTextField();
                                this.moveInitiated = false;
                                this.sourceCountrySetFlag = true;
                                break;
                            } else {
                                gameView.setFeedbackArea("You may only move troops from your owned countries highlighted in green!\n");
                                goToTheBottomOfTextField();
                                this.sourceCountry = "";
                                moveInitiated = false;
                            }
                            break;
                        }
                    }
                    break;
                }
                if (sourceCountrySetFlag) {
                    for (Map.Entry<String, CircleButton> entry : gameView.getMapOfButtons().entrySet()) {
                        if (entry.getValue().equals(e.getSource())) {
                            if (gameModel.getMyMap().ownedBySamePlayer(sourceCountry, entry.getKey())) {
                                this.destinationCountry = entry.getKey();
                                gameView.setFeedbackArea("You are moving troops from " + sourceCountry + " to: " + destinationCountry + " !\n");
                                goToTheBottomOfTextField();
                                sourceCountrySetFlag = false;
                                moveInitiated = false;
                                requestNumberOfTroopsToMove = true;
                                break;
                            }
                        }
                    }
                }
                if (requestNumberOfTroopsToMove) {
                    List<Integer> optionList = new ArrayList<Integer>();
                    int numberOfTroops = gameModel.getMyMap().getCountryByName(sourceCountry).getNumberOfTroops();
                    if (numberOfTroops == 1 || numberOfTroops == 0) {
                        gameView.setFeedbackArea("You do not have enough troops in this country to move around!\n");
                        gameView.getMoveButton().setEnabled(true);
                        break;
                    }
                    else if (!gameModel.getMyMap().areNeighbours(sourceCountry,destinationCountry)) {
                        gameView.setFeedbackArea("You may only move troops to neighbouring countries which you own!\n");
                        gameView.getMoveButton().setEnabled(true);
                        break;
                    }
                    for (int i = 1; i < numberOfTroops; i++) {
                        optionList.add(i);
                    }
                    Object[] options = optionList.toArray();
                    Object value = JOptionPane.showInputDialog(null,
                            "How many troops would you like to move?",
                            "Choose the number of troops to move",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    this.numberOfTroops = (Integer) value;
                    requestNumberOfTroopsToMove = false;

                    moveCommandFlag = true;
                }
                if (moveCommandFlag) {
                    gameView.setFeedbackArea("Move country: " + sourceCountry + ", Destination country: " + destinationCountry + ", Number of troops being moved: " + numberOfTroops + ".\n");
                    goToTheBottomOfTextField();
                    Command moveCommand = new Command("move", sourceCountry, destinationCountry, Integer.toString(numberOfTroops));
                    gameModel.initiateMove(moveCommand);
                    gameView.setFeedbackArea(Integer.toString(numberOfTroops) + " troop(s) moved from " + sourceCountry + " to " + destinationCountry + ".\n");
                    moveCommandFlag = false;
                }
        }
    }

    private void initialRequestBonusTroopsAllocation() {
        this.bonusTroops = gameView.bonusTroops();
        this.currentNumberOfBonusTroopsToPlace = gameView.numberOfBonusTroopsRequest();
        gameView.setFeedbackArea("Select a country to allocate the " + currentNumberOfBonusTroopsToPlace + " bonus troops to.\n");
        goToTheBottomOfTextField();
    }
    private void requestBonusTroopsAllocation() {
        this.currentNumberOfBonusTroopsToPlace = gameView.numberOfBonusTroopsRequest(this.bonusTroops);
        gameView.setFeedbackArea("Select a country to allocate the " + currentNumberOfBonusTroopsToPlace + " bonus troops to.\n");
        goToTheBottomOfTextField();
    }
    private void goToTheBottomOfTextField() {
        gameView.getFeedbackArea().getCaret().setDot(Integer.MAX_VALUE);
    }
}