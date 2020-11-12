package com.ticket_to_ride.client.presenter;

import com.ticket_to_ride.common.data.MoveID;

import java.util.Observer;

public interface IGameBoardPresenter extends Observer {

    void toggleObserver(boolean add);
    void claimRoute(MoveID moveID);
    void respondOpenChat();
    void respondCloseChat();
    void respondOpenDestCard();
    void respondCloseDestCard();
    void respondOpenGameStatus();
    void respondCloseGameStatus();
    void togglePlayerTurnIndicator(boolean on);
    boolean canClaimRoute(MoveID moveID);
}
