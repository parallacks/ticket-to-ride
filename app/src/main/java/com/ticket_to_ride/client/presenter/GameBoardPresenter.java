package com.ticket_to_ride.client.presenter;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.DestCardOptions;
import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.client.view.GameBoardActivity;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;

import java.util.Map;
import java.util.Observable;

/**
 * Created by jyancey on 3/9/19.
 */

public class GameBoardPresenter implements IGameBoardPresenter {

    private GameBoardActivity activity;

    public GameBoardPresenter(GameBoardActivity activity)
    {
        this.activity = activity;
        toggleObserver(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg.getClass() == DestCardOptions.class)
        {
            DestCardOptions options = (DestCardOptions)arg;
            if (options.getOptions().size() > 0)
            {
                toggleObserver(false);
                activity.openDestCardSelectionView();
            }
        }
        else if (arg instanceof Map) {
            Map routes = (Map) arg;
            if (routes.size() > 0) {
                Object routeID = null;
                for (Object key : routes.keySet()) {
                    routeID = key;
                    break;
                }
                if(routeID instanceof RouteID) {
                    Map<RouteID, PlayerInfoM> realRoutes = (Map<RouteID, PlayerInfoM>) routes;
                    activity.highlightRoutes(realRoutes);
                }
            }
        }
        else if (arg.getClass() == PlayerInfoM.class) {
            togglePlayerTurnIndicator(true);
            PlayerInfoM playerInfoM = (PlayerInfoM)arg;
            if(!playerInfoM.isTurn()) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.disableClaimRoute();
                    }
                });
            }

        }
        else if(arg.getClass() == Boolean.class){
            if(ClientM.get().getLastRound()){
                activity.displayToast("Last Round!!");
            }
        }

        //TODO: make sure this doesn't happen too early (the GameOver flag in model needs to be the
        //TODO:     last thing changed on each client in order for this to work each time
        if (ClientM.get().isGameOver()) {
            toggleObserver(false);
            activity.openGameOverView();
        }
    }

    @Override
    public void toggleObserver(boolean add) {
        if (add) {
            ClientM.get().addObserver(this);
        }
        else {
            ClientM.get().deleteObserver(this);
        }
    }

    @Override
    public void claimRoute(MoveID moveID) {
        new ChooseMoveClaimRouteTask(this).execute(moveID);
    }

    @Override
    public void respondCloseChat() {
        activity.hideChat();
    }

    @Override
    public void respondOpenChat() {
        activity.showChat();
    }

    @Override
    public void respondCloseDestCard() {
        activity.closeDestCardSelectionView();
    }

    @Override
    public void respondOpenDestCard() {
        activity.openDestCardSelectionView();
    }

    @Override
    public void respondCloseGameStatus() {
        activity.closeGameStatusView();
    }

    @Override
    public void respondOpenGameStatus() {
        activity.openGameStatusView();
    }

    @Override
    public void togglePlayerTurnIndicator(boolean on) {
        if(on) {
            ClientM model = ClientM.get();
            PlayerInfoM infoM = model.getMyStats();
            if (infoM.isTurn()) {
                activity.showTurn();
                return;
            }
        }
        activity.hideTurn();
    }

    @Override
    public boolean canClaimRoute(MoveID moveID) {
        ClientM model = ClientM.get();
        if(!model.getMyStats().isTurn())
            return false;
        return model.isValidMove(moveID);
    }
}
