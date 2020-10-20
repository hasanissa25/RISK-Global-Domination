package Model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Map {
    private Graph<Country, DefaultEdge> mapGraph;
    private Set<Country> listOfCountries;
    public Set<Country> getAllCountries(){
        return listOfCountries;
    }
    public Country getCountryByName(String CountryName) {
        for (Country c : listOfCountries) {
            if(c.getName().equals(CountryName)) {
                return c;
            }
        }
        return null;
    }

    public List<Country> getNeighbours(Country country){
        List<Country> neighbours = new ArrayList<Country>();
        for(DefaultEdge e: mapGraph.edgesOf(country)){
            neighbours.add(mapGraph.getEdgeSource(e));
            neighbours.add(mapGraph.getEdgeTarget(e));
        }
        return neighbours;
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        listOfCountries.forEach(x-> sb.append(x+"\n"));
        return sb.toString();
    }

    public Map() {
        this.mapGraph= new SimpleGraph<>(DefaultEdge.class);
        Country Alaska = new Country("Alaska");
        mapGraph.addVertex(Alaska);
        Country Alberta = new Country("Alberta");
        mapGraph.addVertex(Alberta);
        Country centralAmerica = new Country("Central America");
        mapGraph.addVertex(centralAmerica);
        Country easternUnitedStates = new Country("Eastern United States");
        mapGraph.addVertex(easternUnitedStates);
        Country Greenland = new Country("Greenland");
        mapGraph.addVertex(Greenland);
        Country northwestTerritory = new Country("Northwest Territory");
        mapGraph.addVertex(northwestTerritory);
        Country Ontario = new Country("Ontario");
        mapGraph.addVertex(Ontario);
        Country Quebec = new Country("Quebec");
        mapGraph.addVertex(Quebec);
        Country westernUnitedStates = new Country("Western United States");
        mapGraph.addVertex(westernUnitedStates);
        Country Argentina = new Country("Argentina");
        mapGraph.addVertex(Argentina);
        Country Brazil = new Country("Brazil");
        mapGraph.addVertex(Brazil);
        Country Peru = new Country("Peru");
        mapGraph.addVertex(Peru);
        Country Venezuela = new Country("Venezuela");
        mapGraph.addVertex(Venezuela);
        Country greatBritain = new Country("Great Britain");
        mapGraph.addVertex(greatBritain);
        Country Iceland = new Country("Iceland");
        mapGraph.addVertex(Iceland);
        Country NorthernEurope = new Country("Northern Europe");
        mapGraph.addVertex(NorthernEurope);
        Country Scandinavia = new Country("Scandinavia");
        mapGraph.addVertex(Scandinavia);
        Country SouthernEurope = new Country("Southern Europe");
        mapGraph.addVertex(SouthernEurope);
        Country Ukraine = new Country("Ukraine");
        mapGraph.addVertex(Ukraine);
        Country WesternEurope = new Country("Western Europe");
        mapGraph.addVertex(WesternEurope);
        Country Congo = new Country("Congo");
        mapGraph.addVertex(Congo);
        Country EastAfrica = new Country("East Africa");
        mapGraph.addVertex(EastAfrica);
        Country Egypt = new Country("Egypt");
        mapGraph.addVertex(Egypt);
        Country Madagascar = new Country("Madagascar");
        mapGraph.addVertex(Madagascar);
        Country NorthAfrica = new Country("North Africa");
        mapGraph.addVertex(NorthAfrica);
        Country SouthAfrica = new Country("South Africa");
        mapGraph.addVertex(SouthAfrica);
        Country Kazakhstan = new Country("Kazakhstan");
        mapGraph.addVertex(Kazakhstan);
        Country China = new Country("China");
        mapGraph.addVertex(China);
        Country India = new Country("India");
        mapGraph.addVertex(India);
        Country Irkutsk = new Country("Irkutsk");
        mapGraph.addVertex(Irkutsk);
        Country Japan = new Country("Japan");
        mapGraph.addVertex(Japan);
        Country Kamchatka = new Country("Kamchatka");
        mapGraph.addVertex(Kamchatka);
        Country MiddleEast = new Country("Middle East");
        mapGraph.addVertex(MiddleEast);
        Country Mongolia = new Country("Mongolia");
        mapGraph.addVertex(Mongolia);
        Country Siam = new Country("Siam");
        mapGraph.addVertex(Siam);
        Country Siberia = new Country("Siberia");
        mapGraph.addVertex(Siberia);
        Country Ural = new Country("Ural");
        mapGraph.addVertex(Ural);
        Country Yakutsk = new Country("Yakutsk");
        mapGraph.addVertex(Yakutsk);
        Country EasternAustralia = new Country("Eastern Australia");
        mapGraph.addVertex(EasternAustralia);
        Country Indonesia = new Country("Indonesia");
        mapGraph.addVertex(Indonesia);
        Country NewGuinea = new Country("New Guinea");
        mapGraph.addVertex(NewGuinea);
        Country WesternAustralia = new Country("Western Australia");
        mapGraph.addVertex(WesternAustralia);
        //North AMerica
        mapGraph.addEdge(Alaska, Alberta);
        mapGraph.addEdge(Alaska, northwestTerritory);
        mapGraph.addEdge(Alaska, Kamchatka);
        mapGraph.addEdge(Alberta, northwestTerritory);
        mapGraph.addEdge(Alberta, Ontario);
        mapGraph.addEdge(Alberta, westernUnitedStates);
        mapGraph.addEdge(centralAmerica, westernUnitedStates);
        mapGraph.addEdge(centralAmerica, easternUnitedStates);
        mapGraph.addEdge(centralAmerica, Venezuela);
        mapGraph.addEdge(easternUnitedStates, Ontario);
        mapGraph.addEdge(easternUnitedStates, Quebec);
        mapGraph.addEdge(easternUnitedStates, westernUnitedStates);
        mapGraph.addEdge(Greenland, northwestTerritory);
        mapGraph.addEdge(Greenland, Ontario);
        mapGraph.addEdge(Greenland, Quebec);
        mapGraph.addEdge(Greenland, Iceland);
        mapGraph.addEdge(northwestTerritory, Ontario);
        mapGraph.addEdge(Ontario, Quebec);
        mapGraph.addEdge(Ontario, westernUnitedStates);
        //South America
        mapGraph.addEdge(Venezuela, Brazil);
        mapGraph.addEdge(Venezuela, Peru);
        mapGraph.addEdge(Peru, Brazil);
        mapGraph.addEdge(Peru, Argentina);
        mapGraph.addEdge(Brazil, Argentina);
        mapGraph.addEdge(Brazil, NorthAfrica);
        //Africa
        mapGraph.addEdge(Congo, EastAfrica);
        mapGraph.addEdge(Congo, NorthAfrica);
        mapGraph.addEdge(Congo, SouthAfrica);
        mapGraph.addEdge(EastAfrica, Egypt);
        mapGraph.addEdge(EastAfrica, Madagascar);
        mapGraph.addEdge(EastAfrica, NorthAfrica);
        mapGraph.addEdge(EastAfrica, SouthAfrica);
        mapGraph.addEdge(EastAfrica, MiddleEast);
        mapGraph.addEdge(Egypt, NorthAfrica);
        mapGraph.addEdge(Egypt, MiddleEast);
        mapGraph.addEdge(Egypt, SouthernEurope);
        mapGraph.addEdge(Madagascar, SouthAfrica);
        mapGraph.addEdge(NorthAfrica, WesternEurope);
        mapGraph.addEdge(NorthAfrica, SouthernEurope);
        //Australia
        mapGraph.addEdge(EasternAustralia, NewGuinea);
        mapGraph.addEdge(EasternAustralia, WesternAustralia);
        mapGraph.addEdge(WesternAustralia, NewGuinea);
        mapGraph.addEdge(Indonesia, NewGuinea);
        mapGraph.addEdge(Indonesia, WesternAustralia);
        mapGraph.addEdge(Indonesia, Siam);
        //ASIA
        mapGraph.addEdge(Kazakhstan, China);
        mapGraph.addEdge(Kazakhstan, India);
        mapGraph.addEdge(Kazakhstan, Ukraine);
        mapGraph.addEdge(Kazakhstan, MiddleEast);
        mapGraph.addEdge(Kazakhstan, Ural);
        mapGraph.addEdge(China, India);
        mapGraph.addEdge(China, Mongolia);
        mapGraph.addEdge(China, Siam);
        mapGraph.addEdge(China, Ural);
        mapGraph.addEdge(China, Siberia);
        mapGraph.addEdge(China, Mongolia);
        mapGraph.addEdge(India, MiddleEast);
        mapGraph.addEdge(India, Siam);
        mapGraph.addEdge(Irkutsk, Mongolia);
        mapGraph.addEdge(Irkutsk, Siberia);
        mapGraph.addEdge(Irkutsk, Kamchatka);
        mapGraph.addEdge(Japan, Kamchatka);
        mapGraph.addEdge(Japan, Mongolia);
        mapGraph.addEdge(Kamchatka, Mongolia);
        mapGraph.addEdge(Kamchatka, Yakutsk);
        mapGraph.addEdge(MiddleEast, Ukraine);
        mapGraph.addEdge(MiddleEast, SouthernEurope);
        mapGraph.addEdge(Mongolia, Siberia);
        mapGraph.addEdge(Siberia, Ural);
        mapGraph.addEdge(Siberia, Yakutsk);
        mapGraph.addEdge(Ural, Ukraine);
        //Europe
        mapGraph.addEdge(greatBritain, Iceland);
        mapGraph.addEdge(greatBritain, NorthernEurope);
        mapGraph.addEdge(greatBritain, Scandinavia);
        mapGraph.addEdge(greatBritain, WesternEurope);
        mapGraph.addEdge(Iceland, Scandinavia);
        mapGraph.addEdge(NorthernEurope, Scandinavia);
        mapGraph.addEdge(NorthernEurope, SouthernEurope);
        mapGraph.addEdge(NorthernEurope, Ukraine);
        mapGraph.addEdge(NorthernEurope, WesternEurope);
        mapGraph.addEdge(Scandinavia, Ukraine);
        mapGraph.addEdge(SouthernEurope, Ukraine);
        listOfCountries=mapGraph.vertexSet();

    }
}
