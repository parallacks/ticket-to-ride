package com.ticket_to_ride.client.presenter;

//Jared
public interface IGameLobbyPresenter {

    void respondReady();
    void respondLeave();
    void setErrorMsg(String msg);
    void setSuccessMsg();
    void setReadyBtnState(boolean state);
    void setLeaveBtnState(boolean state);
}
