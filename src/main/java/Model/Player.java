package Model;

import java.util.List;

public class Player {
    int playerNumber;
    int numberOfTroops;
    List<Country> myCountries;
    List<Country> myPossibleTargets;

    public Player(int playerID, int numberOfTroops) {
        this.playerNumber=playerID;
        this.numberOfTroops=numberOfTroops;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getNumberOfTroops() {
        return numberOfTroops;
    }

    public void setNumberOfTroops(int numberOfTroops) {
        this.numberOfTroops = numberOfTroops;
    }

    public List<Country> getMyCountries() {
        return myCountries;
    }

    public void setMyCountries(List<Country> myCountries) {
        this.myCountries = myCountries;
    }

    public List<Country> getMyPossibleTargets() {
        return myPossibleTargets;
    }

    public void setMyPossibleTargets(List<Country> myPossibleTargets) {
        this.myPossibleTargets = myPossibleTargets;
    }
}
