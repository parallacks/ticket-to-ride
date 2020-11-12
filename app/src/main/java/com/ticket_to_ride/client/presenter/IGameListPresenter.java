package com.ticket_to_ride.client.presenter;

import com.ticket_to_ride.client.model.GameInfoM;

//Jared
public interface IGameListPresenter {

    void respondLogout();
    void respondJoinGame();
    void setJoinGameBtnState(boolean state);
    void respondCreateGame();
    void setErrorMsg(String msg);
    void setSuccessMsg();
    void updateSelected(GameInfoM selectedGame);
    void showSelected();
}
