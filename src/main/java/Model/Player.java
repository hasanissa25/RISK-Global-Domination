package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private int bonusTroops;

    private int totalNumberOftroops;

    public Player(int playerID, int startingNumberOfTroops,Map map) {
        this.playerNumber = playerID;
        this.undeployedTroops = startingNumberOfTroops;
        this.myCountries = new ArrayList<>();
        this.myPossibleTargets = new ArrayList<>();
        this.totalNumberOftroops = startingNumberOfTroops;
        this.theMap=map;
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
    public boolean isOneOfMyCountries(String countryName){
        for (Country country: myCountries) {
            if (country.getName().equals(countryName)) return true;
        }
        return false;
    }

    public Country getACountry(String country) {
        Country theCountry = null;
        for (Country c : myCountries) {
            if (c.getName().toLowerCase().equals(country.toLowerCase()))
                theCountry = c;
        }
        return theCountry;
    }

    public List<Country> getMyPossibleTargets(Map myMap) {
        updateMyPossibleTargets(myMap);
        return myPossibleTargets;
    }

    public List<Country> getMyPossibleTargets2() {
        return this.myPossibleTargets;
    }

    public List<Country> getNeighbours(Map myMap, Country country) {
        return myMap.getCountryNeighbours(country);
    }

    public int bonusTerritoryTroops(){
        int bonus=0;
        if(myCountries.size()<=11){
            bonus=3;
        }else{
            bonus= myCountries.size() / 3;
        }
        return bonus;
    }

    public int bonusContinentTroops(){
        int bonus = 0;
        List<Continent> listOfContinents= theMap.getListOfContinents();
        for (int i = 0; i < listOfContinents.size() ; i++) {
            boolean containsCountries=false;
            List<Country> countriesInContinent= listOfContinents.get(i).getCountriesInTheContinent();
            for (Country country : countriesInContinent) {
                if(isOneOfMyCountries(country.getName())){
                    containsCountries=true;
                }else{
                    containsCountries=false;
                    break;
                }
            }
            if(containsCountries) bonus=bonus+listOfContinents.get(i).getBonusForHoldingContinent();
        }
        return bonus;
    }

    public int totalBonusTroops(){
        return bonusTerritoryTroops() + bonusContinentTroops();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Player{");
        sb.append("playerNumber=").append(playerNumber);
        sb.append(", totalNumberOftroops=").append(totalNumberOftroops);
        sb.append('}');
        return sb.toString();
    }

    public void updateMyPossibleTargets(Map myMap) {
        //iterate over the list of my countries, and return the neighbours of that country.
        for (Country c : myCountries) {
            for (Country cc : myMap.getCountryNeighbours(c)) {
                if (!(myCountries.contains(cc)) && !(cc.getPlayer().getPlayerNumber() == playerNumber)) {
                    myPossibleTargets.add(cc);
                }
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getPlayerNumber() == player.getPlayerNumber() &&
                getUndeployedTroops() == player.getUndeployedTroops() &&
                getTotalNumberOftroops() == player.getTotalNumberOftroops() &&
                Objects.equals(getMyCountries(), player.getMyCountries()) &&
                Objects.equals(myPossibleTargets, player.myPossibleTargets) &&
                Objects.equals(theMap, player.theMap);
    }


}
