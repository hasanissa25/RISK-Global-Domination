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
    public Country getACountry(String country){
        Country theCountry = null;
        for(Country c: myCountries){
            if(c.getName().toLowerCase().equals(country.toLowerCase()))
                theCountry=c;
        }
        return theCountry;
    }
    public List<Country> getMyPossibleTargets(Map myMap) {
        updateMyPossibleTargets(myMap);
        return myPossibleTargets;
    }
    public List<Country> getNeighbours(Map myMap, Country country){
        return myMap.getCountryNeighbours(country);
    }
    public void updateMyPossibleTargets(Map myMap) {
        //iterate over the list of my countries, and return the neighbours of that country.
        for (Country c : myCountries) {
            for (Country cc : myMap.getCountryNeighbours(c)) {
               if(!(myCountries.contains(cc))&& !(cc.getPlayer().getPlayerNumber()==playerNumber)) myPossibleTargets.add(cc);
            }
        }
    }
}
