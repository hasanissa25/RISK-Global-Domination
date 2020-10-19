package Model;

import java.util.Objects;


public class Country {
    private String name;
    private int numberOfTroops;
    private int player;

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

    public void setNumberOfTroops(int numberOfTroops) {
        this.numberOfTroops = numberOfTroops;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
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

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNumberOfTroops(), getPlayer());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Country{");
        sb.append("name='").append(name).append('\'');
        sb.append(", numberOfTroops=").append(numberOfTroops);
        sb.append(", player=").append(player);
        sb.append('}');
        return sb.toString();
    }
}
