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
        System.out.println("The current bonus out of number of territories is :"+bonus);
        return bonus;
    }

    public int bonusContinentTroops(){
        int bonus = 0;
            if((isOneOfMyCountries("Alaska")) && (isOneOfMyCountries("northwestTerritory")) && (isOneOfMyCountries("Greenland")) && (isOneOfMyCountries("Alberta")) && (isOneOfMyCountries("Quebec")) && (isOneOfMyCountries("westernUnitedStates")) && (isOneOfMyCountries("easternUnitedStates")) && (isOneOfMyCountries("centralAmerica"))){
                bonus +=5;
            }
            if((isOneOfMyCountries("Iceland")) && (isOneOfMyCountries("Scandinavia")) && (isOneOfMyCountries("SouthernEurope")) && (isOneOfMyCountries("NorthernEurope")) && (isOneOfMyCountries("WesternEurope")) && (isOneOfMyCountries("greatBritain")) && (isOneOfMyCountries("Ukraine"))){
                bonus +=5;
            }
            if((isOneOfMyCountries("MiddleEast")) && (isOneOfMyCountries("India")) && (isOneOfMyCountries("Kazakhstan")) && (isOneOfMyCountries("China")) && (isOneOfMyCountries("Siam")) && (isOneOfMyCountries("Irkutsk")) && (isOneOfMyCountries("Japan")) && (isOneOfMyCountries("Mongolia")) && (isOneOfMyCountries("Kamchatka")) && (isOneOfMyCountries("Siberia")) && (isOneOfMyCountries("Yakutsk")) && (isOneOfMyCountries("Ural"))){
                bonus +=7;
            }
            if((isOneOfMyCountries("Argentina")) && (isOneOfMyCountries("Peru")) && (isOneOfMyCountries("Brazil")) && (isOneOfMyCountries("Venezuela"))){
                bonus +=2;
            }
            if((isOneOfMyCountries("EastAfrica")) && (isOneOfMyCountries("Congo")) && (isOneOfMyCountries("SouthAfrica")) && (isOneOfMyCountries("Egypt")) && (isOneOfMyCountries("Madagascar")) && (isOneOfMyCountries("NorthAfrica"))){
                bonus +=3;
            }
            if((isOneOfMyCountries("EasternAustralia")) && (isOneOfMyCountries("WesternAustralia")) && (isOneOfMyCountries("NewGuinea")) && (isOneOfMyCountries("Indonesia"))){
                bonus +=2;
            }
        System.out.println("The bonus out of continents held is :"+bonus);
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
