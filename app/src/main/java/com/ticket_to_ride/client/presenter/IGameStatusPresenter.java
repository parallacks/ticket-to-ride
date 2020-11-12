package com.ticket_to_ride.client.presenter;

import com.ticket_to_ride.client.states.GameStatusState;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;

/**
 * Created by jaredrp3 on 3/18/19.
 */

public interface IGameStatusPresenter {
    void initScreen();
    void setObserver(boolean add);
    int getCardNum(TrainCardColor color);
    void takeFaceUpTCard(TrainCardM trainCard, int pos);
    void takeTCardFromDeck();
    void selectNewDestCards();
    void setState(GameStatusState newState);
    void displayError(String errorMsg);
    void grayOutWildCards(boolean grayOut);
}
