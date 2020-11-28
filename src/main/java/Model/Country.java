package Model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

/**
 * @author Hasan Issa
 * <p>
 * This is the Country object which knows the occupying player, and the number of currently occupying troops.
 */

@XmlRootElement
public class Country {

    public Country() {
    }

    private String name;

    private int numberOfTroops;

    private Player player;

    public void setName(String name) {
        this.name = name;
    }

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

    public void decrementNumberOfTroops() {
        numberOfTroops--;
    }

    @XmlTransient
    public void setNumberOfTroops(int numberOfTroops) {
        this.numberOfTroops = numberOfTroops;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addTroops(int numberOfTroops) {
        this.numberOfTroops += numberOfTroops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return getName().equals(country.getName()
        // ) && getPlayer().equals(country.getPlayer()
                );
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("\nCountry: ").append(name);
        sb.append(", Number of Troops= ").append(numberOfTroops);
        sb.append(", Owned by player= ").append(player == null ? "" : player);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
