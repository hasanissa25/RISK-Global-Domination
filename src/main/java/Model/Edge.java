package Model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement
public class Edge {
    Country firstCountry;
    Country secondCountry;

    public Edge() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return getFirstCountry().equals(edge.getFirstCountry()) &&
                getSecondCountry().equals(edge.getSecondCountry());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Edge{");
        sb.append("firstCountry=").append(firstCountry);
        sb.append(", secondCountry=").append(secondCountry);
        sb.append('}');
        return sb.toString();
    }

    public Edge(Country firstCountry, Country secondCountry) {
        this.firstCountry = firstCountry;
        this.secondCountry = secondCountry;
    }

    public Country getFirstCountry() {
        return firstCountry;
    }

    public void setFirstCountry(Country firstCountry) {
        this.firstCountry = firstCountry;
    }

    public Country getSecondCountry() {
        return secondCountry;
    }

    public void setSecondCountry(Country secondCountry) {
        this.secondCountry = secondCountry;
    }
}
