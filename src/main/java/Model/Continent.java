package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Objects;

@XmlRootElement
public class Continent {
    String continentName;
    int bonusForHoldingContinent;
    ArrayList<Country> countriesInTheContinent;

    public Continent() {
    }

    public Continent(String ContinentName) {
        this.continentName = ContinentName;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public int getBonusForHoldingContinent() {
        return bonusForHoldingContinent;
    }

    public void setBonusForHoldingContinent(int bonusForHoldingContinent) {
        this.bonusForHoldingContinent = bonusForHoldingContinent;
    }

    public ArrayList<Country> getCountriesInTheContinent() {
        return countriesInTheContinent;
    }

    public void setCountriesInTheContinent(ArrayList<Country> countriesInTheContinent) {
        this.countriesInTheContinent = countriesInTheContinent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Continent Name= ").append(continentName).append('\'');
        sb.append(", bonus for holding the continent: ").append(bonusForHoldingContinent);
        sb.append(", countriesInTheContinent= ").append(countriesInTheContinent);
        sb.append('\n');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Continent continent = (Continent) o;
        return getContinentName().equals(continent.getContinentName()) &&
                getCountriesInTheContinent().equals(continent.getCountriesInTheContinent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContinentName(), getCountriesInTheContinent());
    }
}
