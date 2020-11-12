package com.ticket_to_ride.server.model.util;


import com.ticket_to_ride.common.data.RouteID;
import com.ticket_to_ride.common.model.CityM;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.MapM;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.server.model.DeckM;

import java.util.ArrayList;

public class GameFactory {

    private String[] gameNames;
    private int gameNamesIndex;

    public GameFactory() {
        //TEMP
        gameNames = new String[] {"Encouraging Seastar","Envious Squid",
                "Peaceful Oyster","Earsplitting Spider",
                "Royal Cobra","Acidic Echidna",
                "Hilarious Coyote","Expensive Llama",
                "Hot Butterfly","Wealthy Toad",
                "Deserted Cat","Magical Ram",
                "Able Pelican","Honorable Armadillo",
                "Whimsical Aardvark","Wooden Cow",
                "Materialistic Gorilla","Alluring Gerbil",
                "Fanatical Ant","Odd Chicken",
                "Nutty Horse","Demonic Hyena",
                "Wretched Pony","Soggy Duck",
                "Obedient Baboons","Tasteless Hare",
                "Sleepy Coyote","Merciful Falcon",
                "Threatening Pelican","Malicious Armadillo",
                "Abusive Guineapig"};
        gameNamesIndex = 0;
    }

    public String getNew() {
        return gameNames[gameNamesIndex++];
    }

    public void setGame(MapM map, DeckM<DestinationCardM> destinationCards, DeckM trainCards)
    {
        setCities(map);
        setRoutes(map);
        setDestinationCards(map, destinationCards);
        setTrainCards(trainCards);
    }

    private void setDestinationCards(MapM map, DeckM<DestinationCardM> destinationCards)
    {
        //Hard code all destinations
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Orgahill"), map.getCityByName("Marpha"), 15));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Verdane"), map.getCityByName("Conote"), 20));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Thracia"), map.getCityByName("Tirnanog"), 19));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Thracia"), map.getCityByName("Anphony"), 19));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Tirnanog"), map.getCityByName("Verdane"), 20));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Velthome"), map.getCityByName("Heirhein"), 11));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Melgen"), map.getCityByName("Verdane"), 10));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Throve"), map.getCityByName("Jungby"), 13));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Isaac"), map.getCityByName("Freege"), 10));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Grutia"), map.getCityByName("Melgen"), 6));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Orgahill"), map.getCityByName("Alster"), 21));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Genoa"), map.getCityByName("Madino"), 17));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Sophara"), map.getCityByName("Heirhein"), 18));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Miletos"), map.getCityByName("Throve"), 19));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Silesia"), map.getCityByName("Kapathogia"), 18));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Yied"), map.getCityByName("Throve"), 12));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Mackily"), map.getCityByName("Sailane"), 9));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Mackily"), map.getCityByName("Edda"), 4));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Rados"), map.getCityByName("Manster"), 10));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Orgahill"), map.getCityByName("Dozel"), 11));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Alster"), map.getCityByName("Rivough"), 12));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Zaxon"), map.getCityByName("Augusty"), 9));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Silesia"), map.getCityByName("Velthome"), 6));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Miletos"), map.getCityByName("Edda"), 5));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Thracia"), map.getCityByName("Melgen"), 4));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Conote"), map.getCityByName("Grutia"), 5));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Dozel"), map.getCityByName("Heirhein"), 7));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Genoa"), map.getCityByName("Evans"), 5));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Anphony"), map.getCityByName("Velthome"), 8));
        destinationCards.addToDeck(new DestinationCardM(map.getCityByName("Chronos"), map.getCityByName("Verdane"), 7));
        destinationCards.shuffleDeck();
    }

    public void setTrainCards(DeckM trainCards)
    {
        //Hard code all train cards
        for (int i = 0; i < 12; i++)
        {
            trainCards.addToDeck(new TrainCardM(TrainCardColor.RED));
            trainCards.addToDeck(new TrainCardM(TrainCardColor.ORANGE));
            trainCards.addToDeck(new TrainCardM(TrainCardColor.YELLOW));
            trainCards.addToDeck(new TrainCardM(TrainCardColor.GREEN));
            trainCards.addToDeck(new TrainCardM(TrainCardColor.BLUE));
            trainCards.addToDeck(new TrainCardM(TrainCardColor.PINK));
            trainCards.addToDeck(new TrainCardM(TrainCardColor.BLACK));
            trainCards.addToDeck(new TrainCardM(TrainCardColor.WHITE));
            trainCards.addToDeck(new TrainCardM(TrainCardColor.WILD));
        }
        trainCards.addToDeck(new TrainCardM(TrainCardColor.WILD));
        trainCards.addToDeck(new TrainCardM(TrainCardColor.WILD));
        trainCards.shuffleDeck();
    }

    public void setCities(MapM map)
    {
        //Hard code all cities
        map.addCity(new CityM("Orgahill"));
        map.addCity(new CityM("Madino"));
        map.addCity(new CityM("Anphony"));
        map.addCity(new CityM("Heirhein"));
        map.addCity(new CityM("Verdane"));
        map.addCity(new CityM("Marpha"));
        map.addCity(new CityM("Genoa"));
        map.addCity(new CityM("Evans"));
        map.addCity(new CityM("Mackily"));
        map.addCity(new CityM("Augusty"));
        map.addCity(new CityM("Sailane"));
        map.addCity(new CityM("Silesia"));
        map.addCity(new CityM("Freege"));
        map.addCity(new CityM("Dozel"));
        map.addCity(new CityM("Jungby"));
        map.addCity(new CityM("Miletos"));
        map.addCity(new CityM("Rados"));
        map.addCity(new CityM("Chronos"));
        map.addCity(new CityM("Edda"));
        map.addCity(new CityM("Velthome"));
        map.addCity(new CityM("Zaxon"));
        map.addCity(new CityM("Throve"));
        map.addCity(new CityM("Phinora"));
        map.addCity(new CityM("Yied"));
        map.addCity(new CityM("Melgen"));
        map.addCity(new CityM("Luthecia"));
        map.addCity(new CityM("Grutia"));
        map.addCity(new CityM("Kapathogia"));
        map.addCity(new CityM("Alster"));
        map.addCity(new CityM("Rivough"));
        map.addCity(new CityM("Sophara"));
        map.addCity(new CityM("Tirnanog"));
        map.addCity(new CityM("Isaac"));
        map.addCity(new CityM("Conote"));
        map.addCity(new CityM("Manster"));
        map.addCity(new CityM("Thracia"));
    }

    public void setRoutes(MapM map)
    {
        //Hard code all routeMS
        map.addRoutes(new RouteM(map.getCityByName("Orgahill"), map.getCityByName("Madino"),    TrainCardColor.WILD,    2, RouteID.ORGAHILL_TO_MADINO_1, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Orgahill"), map.getCityByName("Madino"),    TrainCardColor.WILD,    2, RouteID.ORGAHILL_TO_MADINO_2, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Anphony"),  map.getCityByName("Madino"),    TrainCardColor.RED,     3, RouteID.ANPHONY_TO_MADINO_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Anphony"),  map.getCityByName("Madino"),    TrainCardColor.YELLOW,  3, RouteID.ANPHONY_TO_MADINO_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Sailane"),  map.getCityByName("Madino"),    TrainCardColor.WILD,    2, RouteID.SAILANE_TO_MADINO_1, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Sailane"),  map.getCityByName("Madino"),    TrainCardColor.WILD,    2, RouteID.SAILANE_TO_MADINO_2, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Anphony"),  map.getCityByName("Augusty"),   TrainCardColor.BLUE,    3, RouteID.ANPHONY_TO_AUGUSTY, 4));
        map.addRoutes(new RouteM(map.getCityByName("Anphony"),  map.getCityByName("Heirhein"),  TrainCardColor.ORANGE,  3, RouteID.ANPHONY_TO_HEIRHEIN, 4));
        map.addRoutes(new RouteM(map.getCityByName("Heirhein"), map.getCityByName("Evans"),     TrainCardColor.YELLOW,  4, RouteID.HEIRHEIN_TO_EVANS, 7));
        map.addRoutes(new RouteM(map.getCityByName("Augusty"),  map.getCityByName("Mackily"),   TrainCardColor.WHITE,   1, RouteID.AUGUSTY_TO_MACKILY, 1));
        map.addRoutes(new RouteM(map.getCityByName("Augusty"),  map.getCityByName("Freege"),    TrainCardColor.GREEN,   2, RouteID.AUGUSTY_TO_FREEGE, 2));
        map.addRoutes(new RouteM(map.getCityByName("Mackily"),  map.getCityByName("Evans"),     TrainCardColor.PINK,    2, RouteID.MACKILY_TO_EVANS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Mackily"),  map.getCityByName("Edda"),      TrainCardColor.BLACK,   6, RouteID.MACKILY_TO_EDDA, 15));
        map.addRoutes(new RouteM(map.getCityByName("Evans"),    map.getCityByName("Verdane"),   TrainCardColor.BLACK,   4, RouteID.EVANS_TO_VERDANE_1, 7, true));
        map.addRoutes(new RouteM(map.getCityByName("Evans"),    map.getCityByName("Verdane"),   TrainCardColor.GREEN,   4, RouteID.EVANS_TO_VERDANE_2, 7, true));
        map.addRoutes(new RouteM(map.getCityByName("Evans"),    map.getCityByName("Jungby"),    TrainCardColor.RED,     2, RouteID.EVANS_TO_JUNGBY, 2));
        map.addRoutes(new RouteM(map.getCityByName("Verdane"),  map.getCityByName("Marpha"),    TrainCardColor.WHITE,   3, RouteID.VERDANE_TO_MARPHA_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Verdane"),  map.getCityByName("Marpha"),    TrainCardColor.BLACK,   3, RouteID.VERDANE_TO_MARPHA_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Marpha"),   map.getCityByName("Genoa"),     TrainCardColor.WILD,    3, RouteID.MARPHA_TO_GENOA_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Marpha"),   map.getCityByName("Genoa"),     TrainCardColor.WILD,    3, RouteID.MARPHA_TO_GENOA_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Genoa"),    map.getCityByName("Miletos"),   TrainCardColor.GREEN,   2, RouteID.GENOA_TO_MILETOS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Genoa"),    map.getCityByName("Rados"),     TrainCardColor.WHITE,   2, RouteID.GENOA_TO_RADOS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Rados"),    map.getCityByName("Chronos"),   TrainCardColor.BLUE,    2, RouteID.RADOS_TO_CHRONOS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Chronos"),  map.getCityByName("Miletos"),   TrainCardColor.PINK,    2, RouteID.CHRONOS_TO_MILETOS, 2));
        map.addRoutes(new RouteM(map.getCityByName("Chronos"),  map.getCityByName("Melgen"),    TrainCardColor.YELLOW,  4, RouteID.CHRONOS_TO_MELGEN_1, 7, true));
        map.addRoutes(new RouteM(map.getCityByName("Chronos"),  map.getCityByName("Melgen"),    TrainCardColor.PINK,    4, RouteID.CHRONOS_TO_MELGEN_2, 7, true));
        map.addRoutes(new RouteM(map.getCityByName("Jungby"),   map.getCityByName("Edda"),      TrainCardColor.ORANGE,  4, RouteID.JUNGBY_TO_EDDA, 7));
        map.addRoutes(new RouteM(map.getCityByName("Freege"),   map.getCityByName("Dozel"),     TrainCardColor.BLACK,   1, RouteID.FREEGE_TO_DOZEL, 1));
        map.addRoutes(new RouteM(map.getCityByName("Dozel"),    map.getCityByName("Velthome"),  TrainCardColor.YELLOW,  2, RouteID.DOZEL_TO_VELTHOME, 2));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Edda"),      TrainCardColor.WILD,    3, RouteID.VELTHOME_TO_EDDA_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Edda"),      TrainCardColor.WILD,    3, RouteID.VELTHOME_TO_EDDA_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Yied"),      TrainCardColor.WILD,    3, RouteID.VELTHOME_TO_YIED_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Yied"),      TrainCardColor.WILD,    3, RouteID.VELTHOME_TO_YIED_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Velthome"), map.getCityByName("Phinora"),   TrainCardColor.RED,     2, RouteID.VELTHOME_TO_PHINORA, 2));
        map.addRoutes(new RouteM(map.getCityByName("Yied"),     map.getCityByName("Rivough"),   TrainCardColor.WILD,    3, RouteID.YIED_TO_RIVOUGH_1, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Yied"),     map.getCityByName("Rivough"),   TrainCardColor.WILD,    3, RouteID.YIED_TO_RIVOUGH_2, 4, true));
        map.addRoutes(new RouteM(map.getCityByName("Sailane"),  map.getCityByName("Silesia"),   TrainCardColor.GREEN,   2, RouteID.SAILANE_TO_SILESIA, 2));
        map.addRoutes(new RouteM(map.getCityByName("Silesia"),  map.getCityByName("Zaxon"),     TrainCardColor.WHITE,   2, RouteID.SILESIA_TO_ZAXON, 2));
        map.addRoutes(new RouteM(map.getCityByName("Sailane"),  map.getCityByName("Throve"),    TrainCardColor.BLUE,    5, RouteID.SAILANE_TO_THROVE, 10));
        map.addRoutes(new RouteM(map.getCityByName("Throve"),   map.getCityByName("Zaxon"),     TrainCardColor.PINK,    3, RouteID.THROVE_TO_ZAXON, 4));
        map.addRoutes(new RouteM(map.getCityByName("Zaxon"),    map.getCityByName("Phinora"),   TrainCardColor.ORANGE,  2, RouteID.ZAXON_TO_PHINORA_1, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Zaxon"),    map.getCityByName("Phinora"),   TrainCardColor.GREEN,   2, RouteID.ZAXON_TO_PHINORA_2, 2, true));
        map.addRoutes(new RouteM(map.getCityByName("Phinora"),  map.getCityByName("Tirnanog"),  TrainCardColor.ORANGE,  6, RouteID.PHINORA_TO_TIRNANOG, 15));
        map.addRoutes(new RouteM(map.getCityByName("Tirnanog"), map.getCityByName("Sophara"),   TrainCardColor.BLACK,   2, RouteID.TIRNANOG_TO_SOPHARA, 2));
        map.addRoutes(new RouteM(map.getCityByName("Sophara"),  map.getCityByName("Rivough"),   TrainCardColor.RED,     1, RouteID.SOPHARA_TO_RIVOUGH, 1));
        map.addRoutes(new RouteM(map.getCityByName("Tirnanog"), map.getCityByName("Isaac"),     TrainCardColor.YELLOW,  4, RouteID.TIRNANOG_TO_ISAAC, 7));
        map.addRoutes(new RouteM(map.getCityByName("Isaac"),    map.getCityByName("Rivough"),   TrainCardColor.GREEN,   2, RouteID.ISAAC_TO_RIVOUGH, 2));
        map.addRoutes(new RouteM(map.getCityByName("Edda"),     map.getCityByName("Melgen"),    TrainCardColor.BLUE,    2, RouteID.EDDA_TO_MELGEN, 2));
        map.addRoutes(new RouteM(map.getCityByName("Luthecia"), map.getCityByName("Melgen"),    TrainCardColor.ORANGE,  3, RouteID.LUTHECIA_TO_MELGEN, 4));
        map.addRoutes(new RouteM(map.getCityByName("Luthecia"), map.getCityByName("Grutia"),    TrainCardColor.WHITE,   2, RouteID.LUTHECIA_TO_GRUTIA, 2));
        map.addRoutes(new RouteM(map.getCityByName("Grutia"),   map.getCityByName("Thracia"),   TrainCardColor.PINK,    3, RouteID.GRUTIA_TO_THRACIA, 4));
        map.addRoutes(new RouteM(map.getCityByName("Thracia"),  map.getCityByName("Kapathogia"),TrainCardColor.RED,     1, RouteID.THRACIA_TO_KAPATHOGIA, 1));
        map.addRoutes(new RouteM(map.getCityByName("Thracia"),  map.getCityByName("Manster"),   TrainCardColor.BLACK,   4, RouteID.THRACIA_TO_MANSTER, 7));
        map.addRoutes(new RouteM(map.getCityByName("Alster"),   map.getCityByName("Kapathogia"),TrainCardColor.GREEN,   3, RouteID.ALSTER_TO_KAPATHOGIA, 4));
        map.addRoutes(new RouteM(map.getCityByName("Alster"),   map.getCityByName("Melgen"),    TrainCardColor.ORANGE,  2, RouteID.ALSTER_TO_MELGEN, 2));
        map.addRoutes(new RouteM(map.getCityByName("Alster"),   map.getCityByName("Manster"),   TrainCardColor.RED,     2, RouteID.ALSTER_TO_MANSTER, 2));
        map.addRoutes(new RouteM(map.getCityByName("Manster"),  map.getCityByName("Conote"),    TrainCardColor.PINK,    1, RouteID.MANSTER_TO_CONOTE_1, 1, true));
        map.addRoutes(new RouteM(map.getCityByName("Manster"),  map.getCityByName("Conote"),    TrainCardColor.WHITE,   1, RouteID.MANSTER_TO_CONOTE_2, 1, true));
    }
}
