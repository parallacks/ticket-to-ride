package com.ticket_to_ride.client.states;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.presenter.IGameStatusPresenter;
import com.ticket_to_ride.common.model.TrainCardColor;

/**
 * Created by jaredrp3 on 3/18/19.
 */

public class EndTurnState extends GameStatusState {

    private static EndTurnState instance;
    ClientM clientM;

    public static EndTurnState getInstance() {
        if (instance == null) {
            instance = new EndTurnState();
        }
        return instance;
    }

    private EndTurnState() {
        clientM = ClientM.get();
    }

    public void enter(IGameStatusPresenter presenter) {
        clientM.setCurrentGameStatusState(this);
        System.out.println("************************* entering END TURN STATE");
        //clientM.getMyStats().setTurn(false);
    }

    public void takeFaceUpTCard(TrainCardColor cardColor, int pos, IGameStatusPresenter presenter) {
        presenter.displayError("Not your turn buddy");
    }

    public void takeTCardFromDeck(IGameStatusPresenter presenter) {
        presenter.displayError("Not your turn buddy");
    }

    public void selectNewDestCards(IGameStatusPresenter presenter) {
        presenter.displayError("Not your turn buddy");
    }

}
