package com.ticket_to_ride.client.view;

import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.common.data.RouteID;

import java.util.Map;

public interface IGameBoardActivity {
    void hideChat();
    void showChat();
    void hideTurn();
    void showTurn();
    void disableClaimRoute();
    void enableClaimRoute();
    void closeDestCardSelectionView();
    void openDestCardSelectionView();
    void closeGameStatusView();
    void openGameStatusView();
    void highlightRoutes(Map<RouteID, PlayerInfoM> routeIDs);
    void openGameOverView();
}
