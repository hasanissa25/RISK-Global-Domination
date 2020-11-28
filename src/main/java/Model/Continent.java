package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Continent {
    String continentName;

    ArrayList<Country> countriesInTheContinent;

    public Continent() {
    }

    public Continent(String ContinentName) {
        this.continentName = ContinentName;
    }

    public String getContinentName() {
        return continentName;
    }

    @XmlElement
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }


    public ArrayList<Country> getCountriesInTheContinent() {
        return countriesInTheContinent;
    }

    @XmlElement
    public void setCountriesInTheContinent(ArrayList<Country> countriesInTheContinent) {
        this.countriesInTheContinent = countriesInTheContinent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Continent{");
        sb.append("continentName='").append(continentName).append('\'');
        sb.append(", countriesInTheContinent=").append(countriesInTheContinent);
        sb.append('}');
        return sb.toString();
    }
}
