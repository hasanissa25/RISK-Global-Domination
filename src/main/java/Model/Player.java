package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hasan Issa
 * <p>
 * This is the player object denoted by a playerNumber. The player knows the countries they own, and the possible targets they can choose to attack.
 * They know how many troops they have to deploy for later on milestones when players start receiving troops throughout the game.
 */
public class Player {
    private int playerNumber;
    private int undeployedTroops;
    private List<Country> myCountries;
    private List<Country> myPossibleTargets;
    private Map theMap;

    private int totalNumberOftroops;

    public Player(int playerID, int startingNumberOfTroops) {
        this.playerNumber = playerID;
        this.undeployedTroops = startingNumberOfTroops;
        this.myCountries = new ArrayList<>();
        this.myPossibleTargets = new ArrayList<>();
        this.totalNumberOftroops = startingNumberOfTroops;
    }

    public int getTotalNumberOftroops() {
        return totalNumberOftroops;
    }

    public void setTotalNumberOftroops(int totalNumberOftroops) {
        this.totalNumberOftroops = totalNumberOftroops;
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

    public Country getACountry(String country) {
        Country theCountry = null;
        for (Country c : myCountries) {
            if (c.getName().toLowerCase().equals(country.toLowerCase()))
                theCountry = c;
        }
        return theCountry;
    }

    public void getMyPossibleTargets(Map myMap) {
        updateMyPossibleTargets(myMap);
    }

    public List<Country> getNeighbours(Map myMap, Country country) {
        return myMap.getCountryNeighbours(country);
    }

    public void updateMyPossibleTargets(Map myMap) {
        //iterate over the list of my countries, and return the neighbours of that country.
        for (Country c : myCountries) {
            System.out.println("\nFrom " + c.getName() + " you can choose to attack one of these countries:\n");
            for (Country cc : myMap.getCountryNeighbours(c)) {
                if (!(myCountries.contains(cc)) && !(cc.getPlayer().getPlayerNumber() == playerNumber)) {
                    myPossibleTargets.add(cc);
                    System.out.println(cc.toString2());
                }
            }
        }

    }
}
