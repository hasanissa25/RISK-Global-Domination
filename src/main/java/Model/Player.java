package Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerNumber;
    private int deployedTroops;
    private int undeployedTroops;
    private List<Country> myCountries;
    private List<Country> myPossibleTargets;

    public Player(int playerID, int startingNumberOfTroops) {
        this.playerNumber = playerID;
        this.undeployedTroops = startingNumberOfTroops;
        this.myCountries= new ArrayList<>();
        this.myPossibleTargets=new ArrayList<>();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getDeployedTroops() {
        return deployedTroops;
    }
    public void decrementUndeployedNumberOfTroops(){
        undeployedTroops--;
    }
    public void setDeployedTroops(int deployedTroops) {
        this.deployedTroops = deployedTroops;
    }

    public int getUndeployedTroops() {
        return undeployedTroops;
    }

    public void setUndeployedTroops(int undeployedTroops) {
        this.undeployedTroops = undeployedTroops;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("playerNumber=").append(playerNumber);
        sb.append(", deployedTroops=").append(deployedTroops);
        sb.append(", undeployedTroops=").append(undeployedTroops);
        sb.append(", myCountries=").append(myCountries);
        sb.append(", myPossibleTargets=").append(myPossibleTargets);
        sb.append('}');
        return sb.toString();
    }
}
