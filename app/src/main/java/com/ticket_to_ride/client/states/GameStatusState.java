package com.ticket_to_ride.client.states;

import com.ticket_to_ride.client.presenter.IGameStatusPresenter;
import com.ticket_to_ride.common.model.TrainCardColor;

/**
 * Created by jaredrp3 on 3/18/19.
 */

public abstract class GameStatusState {
    public void enter(IGameStatusPresenter presenter) { }
    public void exit(IGameStatusPresenter presenter) { }
    public void takeFaceUpTCard(TrainCardColor cardColor, int pos,
                                         IGameStatusPresenter presenter) { }
    public void takeTCardFromDeck(IGameStatusPresenter presenter) { }
    public void selectNewDestCards(IGameStatusPresenter presenter) { }
}
