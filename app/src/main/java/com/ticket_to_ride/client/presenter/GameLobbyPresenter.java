package com.ticket_to_ride.client.presenter;


import android.os.AsyncTask;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.view.GameLobbyFragment;
import com.ticket_to_ride.client.view.IGameWaiting;

import java.util.Observable;
import java.util.Observer;

import com.ticket_to_ride.client.services.ClientSetPlayerReadyService;

//Jared

public class GameLobbyPresenter implements IGameLobbyPresenter, Observer {

    private ClientM clientM;
    private GameLobbyFragment gameLobbyFrag;

    public GameLobbyPresenter(GameLobbyFragment gameLobbyFragment) {
        this.gameLobbyFrag = gameLobbyFragment;
        clientM = ClientM.get();
        clientM.addObserver(this);
        setLeaveBtnState(false);
        setReadyBtnState(true);
    }

    @Override
    public void respondReady() {
        SetPlayerStatusReadyTask setPlayerStatusReadyTask = new SetPlayerStatusReadyTask();
        //setPlayerStatusReadyTask.execute();
        //TODO: Make all async tasks do this!!!
        setPlayerStatusReadyTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void respondLeave() {
        //implement later if necessary
        makeToast_forTesting("Leaving game (not really)");
    }


    @Override
    public void setErrorMsg(String msg) {
        gameLobbyFrag.displayErrorMsg(msg);
    }

    @Override
    public void setSuccessMsg() {
        String msg = "Success";
        gameLobbyFrag.displaySuccessMsg(msg);
    }
    public void updatePlayerList() {
        gameLobbyFrag.updatePlayerListItems(clientM.getActiveGame().getPlayers());
    }
    @Override
    public void update(Observable o, Object arg) {
        gameLobbyFrag.updatePlayerListItems(clientM.getActiveGame().getPlayers());
        if (arg instanceof Boolean) {
            if ((Boolean) arg) {
                setSuccessMsg();
                setReadyBtnState(false);
                setLeaveBtnState(true);
            }
        }
        if (clientM.getActiveGame().getStatus().equals("Active"))
        {
            clientM.deleteObserver(this);
            IGameWaiting gameWaitingActivity = (IGameWaiting)gameLobbyFrag.getActivity();
            gameWaitingActivity.startGame();
        }
        if (arg instanceof String) {
            setErrorMsg(clientM.getErrorMessage());
        }
    }

    //TODO: take this function out (only for testing purposes)
    public void makeToast_forTesting(String msg) {
        gameLobbyFrag.displayErrorMsg(msg);
    }

    @Override
    public void setReadyBtnState(boolean state) {
        gameLobbyFrag.setReadyGameBtn(state);
    }

    @Override
    public void setLeaveBtnState(boolean state) {
        // implement functionality later if necessary
        gameLobbyFrag.setLeaveGameBtn(false);
    }

    public class SetPlayerStatusReadyTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... args) {
            ClientSetPlayerReadyService setPlayerReadyService =
                    new ClientSetPlayerReadyService();
            setPlayerReadyService.execute();
            return null;
        }

    }

}
