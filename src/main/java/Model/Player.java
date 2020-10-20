package Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int playerNumber;
    private int undeployedTroops;
    private List<Country> myCountries;
    private List<Country> myPossibleTargets;
    private Map theMap;

    public Player(int playerID, int startingNumberOfTroops) {
        this.playerNumber = playerID;
        this.undeployedTroops = startingNumberOfTroops;
        this.myCountries = new ArrayList<>();
        this.myPossibleTargets = new ArrayList<>();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void decrementUndeployedNumberOfTroops() {
        undeployedTroops--;
    }

    public int getUndeployedTroops() {
        return undeployedTroops;
    }

    public List<Country> getMyCountries() {
        return myCountries;
    }

    public List<Country> getMyPossibleTargets(Map myMap) {
        updateMyPossibleTargets(myMap);
        return myPossibleTargets;
    }

    public void updateMyPossibleTargets(Map myMap) {
        //iterate over the list of my countries, and return the neighbours of that country.
        for (Country c : myCountries) {
            for (Country cc : myMap.getNeighbours(c)) {
               if(!(myCountries.contains(cc))&& !(cc.getPlayer().getPlayerNumber()==playerNumber)) myPossibleTargets.add(cc);
            }
        }
    }
}
