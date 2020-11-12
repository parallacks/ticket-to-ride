package com.ticket_to_ride.client.presenter;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.GameInfoM;
import com.ticket_to_ride.client.view.GameListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import android.os.AsyncTask;

import com.ticket_to_ride.client.services.ClientCreateGameService;
import com.ticket_to_ride.client.services.ClientJoinGameService;

//Jared

public class GameListPresenter implements IGameListPresenter, Observer {

    private ClientM clientM;
    private GameListFragment gameListFrag;

    public GameListPresenter(GameListFragment gameListFrag) {
        this.gameListFrag = gameListFrag;
        clientM = ClientM.get();
        clientM.addObserver(this);
        setJoinGameBtnState(false);
        gameListFrag.updateGameListItems(updateGameList());
    }

    @Override
    public void respondLogout() {
        //add functionality later
        makeToast_forTesting("RESPONDING TO LOGOUT");
    }

    @Override
    public void respondJoinGame() {
        JoinGameTask joinGametask = new JoinGameTask();
        //joinGametask.execute(clientM.getSelectedGame().getGameName());
        joinGametask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, clientM.getSelectedGame().getGameName());
    }

    @Override
    public void respondCreateGame() {
        CreateGameTask createGameTask = new CreateGameTask();
        createGameTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void setErrorMsg(String msg) {
        gameListFrag.displayErrorMsg(msg);
    }

    @Override
    public void setSuccessMsg() {
        String msg = "Success";
        gameListFrag.displaySuccessMsg(msg);
    }

    //TODO: take this function out (only for testing purposes)
    public void makeToast_forTesting(String msg) {
        gameListFrag.displayErrorMsg(msg);
    }


    @Override
    public void update(Observable o, Object arg) {
        gameListFrag.updateGameListItems(updateGameList());
        if (clientM.getActiveGame() != null) {
            setSuccessMsg();
            clientM.deleteObserver(this);
            gameListFrag.switchFragments();
        }
        if (arg instanceof String) {
            setErrorMsg(clientM.getErrorMessage());
        }
    }

    private List<GameInfoM> updateGameList() {
        ArrayList<GameInfoM> games = new ArrayList<>();
        for (GameInfoM game : clientM.getLobbyGames()) {
            if(!game.getStatus().equals("Active"))
                games.add(game);
        }
        return games;
    }


    @Override
    public void updateSelected(GameInfoM selectedGame) {
        clientM.setSelectedGame(selectedGame);
        setJoinGameBtnState(true);
    }


    @Override
    public void setJoinGameBtnState(boolean state) {
        gameListFrag.setJoinGameBtn(state);
    }

    public class JoinGameTask extends AsyncTask<String, Void, Void> {


        public JoinGameTask() {
        }

        @Override
        protected Void doInBackground(String... args) {
            ClientJoinGameService joinGameService = new ClientJoinGameService(args[0]);
            joinGameService.JoinGame();
            return null;
        }

    }

    public class CreateGameTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            ClientCreateGameService createGameService = new ClientCreateGameService();
            createGameService.CreateGame();
            return null;
        }
    }

    @Override
    public void showSelected() {
        if (clientM.getSelectedGame() != null) {
            gameListFrag.highlightText(clientM.getSelectedGame().getGameName());
        }
    }

}
