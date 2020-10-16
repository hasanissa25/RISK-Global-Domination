import org.jgrapht.Graph;

public class MapUtil {
    
    public static void createCountries(Graph map) {

        Country Alaska = new Country("Alaska");
        map.addVertex(Alaska);
        Country Alberta = new Country("Alberta");
        map.addVertex(Alberta);
        Country centralAmerica = new Country("Central America");
        map.addVertex(centralAmerica);
        Country easternUnitedStates = new Country("Eastern United States");
        map.addVertex(easternUnitedStates);
        Country Greenland = new Country("Greenland");
        map.addVertex(Greenland);
        Country northwestTerritory = new Country("Northwest Territory");
        map.addVertex(northwestTerritory);
        Country Ontario = new Country("Ontario");
        map.addVertex(Ontario);
        Country Quebec = new Country("Quebec");
        map.addVertex(Quebec);
        Country westernUnitedStates = new Country("Western United States");
        map.addVertex(westernUnitedStates);
        Country Argentina = new Country("Argentina");
        map.addVertex(Argentina);
        Country Brazil = new Country("Brazil");
        map.addVertex(Brazil);
        Country Peru = new Country("Peru");
        map.addVertex(Peru);
        Country Venezuela = new Country("Venezuela");
        map.addVertex(Venezuela);
        Country greatBritain = new Country("Great Britain");
        map.addVertex(greatBritain);
        Country Iceland = new Country("Iceland");
        map.addVertex(Iceland);
        Country NorthernEurope = new Country("Northern Europe");
        map.addVertex(NorthernEurope);
        Country Scandinavia = new Country("Scandinavia");
        map.addVertex(Scandinavia);
        Country SouthernEurope = new Country("Southern Europe");
        map.addVertex(SouthernEurope);
        Country Ukraine = new Country("Ukraine");
        map.addVertex(Ukraine);
        Country WesternEurope = new Country("Western Europe");
        map.addVertex(WesternEurope);
        Country Congo = new Country("Congo");
        map.addVertex(Congo);
        Country EastAfrica = new Country("East Africa");
        map.addVertex(EastAfrica);
        Country Egypt = new Country("Egypt");
        map.addVertex(Egypt);
        Country Madagascar = new Country("Madagascar");
        map.addVertex(Madagascar);
        Country NorthAfrica = new Country("North Africa");
        map.addVertex(NorthAfrica);
        Country SouthAfrica = new Country("South Africa");
        map.addVertex(SouthAfrica);
        Country Afghanistan = new Country("Afghanistan");
        map.addVertex(Afghanistan);
        Country China = new Country("China");
        map.addVertex(China);
        Country India = new Country("India");
        map.addVertex(India);
        Country Irkutsk = new Country("Irkutsk");
        map.addVertex(Irkutsk);
        Country Japan = new Country("Japan");
        map.addVertex(Japan);
        Country Kamchatka = new Country("Kamchatka");
        map.addVertex(Kamchatka);
        Country MiddleEast = new Country("Middle East");
        map.addVertex(MiddleEast);
        Country Mongolia = new Country("Mongolia");
        map.addVertex(Mongolia);
        Country Siam = new Country("Siam");
        map.addVertex(Siam);
        Country Siberia = new Country("Siberia");
        map.addVertex(Siberia);
        Country Ural = new Country("Ural");
        map.addVertex(Ural);
        Country Yakutsk = new Country("Yakutsk");
        map.addVertex(Yakutsk);
        Country EasternAustralia = new Country("Eastern Australia");
        map.addVertex(EasternAustralia);
        Country Indonesia = new Country("Indonesia");
        map.addVertex(Indonesia);
        Country NewGuinea = new Country("New Guinea");
        map.addVertex(NewGuinea);
        Country WesternAustralia = new Country("Western Australia");
        map.addVertex(WesternAustralia);
        //North AMerica
        map.addEdge(Alaska, Alberta);
        map.addEdge(Alaska, northwestTerritory);
        map.addEdge(Alberta, northwestTerritory);
        map.addEdge(Alberta, Ontario);
        map.addEdge(Alberta, westernUnitedStates);
        map.addEdge(centralAmerica, westernUnitedStates);
        map.addEdge(centralAmerica, easternUnitedStates);
        map.addEdge(centralAmerica, Venezuela);
        map.addEdge(easternUnitedStates, Ontario);
        map.addEdge(easternUnitedStates, Quebec);
        map.addEdge(easternUnitedStates, westernUnitedStates);
        map.addEdge(Greenland, northwestTerritory);
        map.addEdge(Greenland, Ontario);
        map.addEdge(Greenland, Quebec);
        map.addEdge(Greenland, Iceland);
        map.addEdge(northwestTerritory, Ontario);
        map.addEdge(Ontario, Quebec);
        map.addEdge(Ontario, westernUnitedStates);
        //South America
        map.addEdge(Venezuela, Brazil);
        map.addEdge(Venezuela, Peru);
        map.addEdge(Peru,Brazil);
        map.addEdge(Peru, Argentina);
        map.addEdge(Brazil, Argentina);
        map.addEdge(Brazil, NorthAfrica);
        //Africa
        map.addEdge(Congo, EastAfrica);
        map.addEdge(Congo, NorthAfrica);
        map.addEdge(Congo, SouthAfrica);
        map.addEdge(EastAfrica, Egypt);
        map.addEdge(EastAfrica, Madagascar);
        map.addEdge(EastAfrica, NorthAfrica);
        map.addEdge(EastAfrica, SouthAfrica);
        map.addEdge(EastAfrica, MiddleEast);

        map.addEdge(Egypt, NorthAfrica);
        map.addEdge(Egypt, MiddleEast);
        map.addEdge(Egypt, SouthernEurope);

        map.addEdge(Madagascar, SouthAfrica);

        map.addEdge(NorthAfrica, WesternEurope);
        map.addEdge(NorthAfrica, SouthernEurope);


        //Australia
        map.addEdge(EasternAustralia, NewGuinea);
        map.addEdge(EasternAustralia, WesternAustralia);
        map.addEdge(EasternAustralia, NewGuinea);
        map.addEdge(Indonesia, NewGuinea);
        map.addEdge(Indonesia, WesternAustralia);
        map.addEdge(Indonesia, Siam);

        //FINISH ASIA AND EURUOPE
    }

}
