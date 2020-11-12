package com.ticket_to_ride.client.view;

import com.ticket_to_ride.client.model.DeckM;
import com.ticket_to_ride.client.model.DeckSizeM;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jared
 */

public interface IGameStatusView {
    void selectDestCardDeck();
    void selectFaceUpTCard(TrainCardM trainCard, int cardPosition);
    void selectTCardDeck();
    void updateFaceUpTCards(TrayM faceUpTCards);
    void updateOpponentStats(List<PlayerInfoM> opposingPlayers);
    void updateMyStats(PlayerInfoM me);
    void switchDestCardSelectionFragment();
    void updateDestDeckNum(int deckSize);
    void updateTCardDeckNum(int deckSizeM);
    void updateDestCardInfo(ArrayList<DestinationCardM> destCards);
    void displayToast(String toDisplay);
    void updateMyTrainCards(List<TrainCardColor> myTCards);
    void grayOutWildFaceUpTCards(boolean grayOut);
    void switchToGameOverView();
}
