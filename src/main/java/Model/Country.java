package Model;

import java.util.Objects;


public class Country {
    private String name;
    private int numberOfTroops;
    private Player player;

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfTroops() {
        return numberOfTroops;
    }
    public void incrementNumberOfTroops(){
        numberOfTroops++;
    }
    public void addNumberOfTroops(int i){
        numberOfTroops+=i;
    }
    public void setNumberOfTroops(int numberOfTroops) {
        this.numberOfTroops = numberOfTroops;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    public boolean isEmpty(){
        return this.getNumberOfTroops()==0;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return getNumberOfTroops() == country.getNumberOfTroops() &&
                getPlayer() == country.getPlayer() &&
                getName().equals(country.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumberOfTroops(), getPlayer());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Country: ").append(name);
        sb.append(", Number Of occupying Troops= ").append(numberOfTroops);
        sb.append(", Owned by player: ").append(player.getPlayerNumber()+"\n");
        return sb.toString();
    }
}
