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
    public int getNumberOfTroops() {
        return numberOfTroops;
    }
    public void incrementNumberOfTroops() {
        numberOfTroops++;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
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

//    @Override
//    public int hashCode() {
//        return Objects.hash(getName());
//    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nname='").append(name);
        sb.append(", numberOfTroops=").append(numberOfTroops);
        sb.append(", player=").append(player == null ? "" : player.getPlayerNumber());
        return sb.toString();
    }
}
