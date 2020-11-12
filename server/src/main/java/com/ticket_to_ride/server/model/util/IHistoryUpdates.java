package com.ticket_to_ride.server.model.util;

import com.ticket_to_ride.common.model.RouteM;

public interface IHistoryUpdates {
    void historyDrawTrayCard(String username, int position);

    void historyDrawTrainCard(String username);

    void historyDrawDestinationCards(String username);

    void historyClaimRoute(String username, RouteM route);
}
