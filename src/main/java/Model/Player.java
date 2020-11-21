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
        for(int i=0; i<myCountries.size(); i++){
            if((myCountries.contains("Alaska")) && (myCountries.contains("northwestTerritory")) && (myCountries.contains("Greenland")) && (myCountries.contains("Alberta")) && (myCountries.contains("Quebec")) && (myCountries.contains("westernUnitedStates")) && (myCountries.contains("easternUnitedStates")) && (myCountries.contains("centralAmerica"))){
                bonus +=5;
            }
            else if((myCountries.contains("Iceland")) && (myCountries.contains("Scandinavia")) && (myCountries.contains("SouthernEurope")) && (myCountries.contains("NorthernEurope")) && (myCountries.contains("WesternEurope")) && (myCountries.contains("greatBritain")) && (myCountries.contains("Ukraine"))){
                bonus +=5;
            }
            else if((myCountries.contains("MiddleEast")) && (myCountries.contains("India")) && (myCountries.contains("Kazakhstan")) && (myCountries.contains("China")) && (myCountries.contains("Siam")) && (myCountries.contains("Irkutsk")) && (myCountries.contains("Japan")) && (myCountries.contains("Mongolia")) && (myCountries.contains("Kamchatka")) && (myCountries.contains("Siberia")) && (myCountries.contains("Yakutsk")) && (myCountries.contains("Ural"))){
                bonus +=7;
            }
            else if((myCountries.contains("Argentina")) && (myCountries.contains("Peru")) && (myCountries.contains("Brazil")) && (myCountries.contains("Venezuela"))){
                bonus +=3;
            }
            else if((myCountries.contains("EastAfrica")) && (myCountries.contains("Congo")) && (myCountries.contains("SouthAfrica")) && (myCountries.contains("Egypt")) && (myCountries.contains("Madagascar")) && (myCountries.contains("NorthAfrica"))){
                bonus +=3;
            }
            else if((myCountries.contains("EasternAustralia")) && (myCountries.contains("WesternAustralia")) && (myCountries.contains("NewGuinea")) && (myCountries.contains("Indonesia"))){
                bonus +=3;
            }
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
