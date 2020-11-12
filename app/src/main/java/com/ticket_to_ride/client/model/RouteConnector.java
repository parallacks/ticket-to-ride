//Created by Lance
package com.ticket_to_ride.client.model;

import android.graphics.Color;
import android.widget.ImageView;


import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.model.RouteM;
import com.ticket_to_ride.common.util.RouteMoveMatcher;


public class RouteConnector {
    private ImageView displayRoute;
    private RouteM serverRoute;
    private MoveID moveID;
    private boolean claimed = false;

    public RouteConnector (ImageView i, RouteM r) {
        displayRoute = i;
        serverRoute = r;
        RouteMoveMatcher match = new RouteMoveMatcher();
        moveID = match.matchMoveToRoute(serverRoute.getId());
    }

    public ImageView getDisplayRoute() {
        return displayRoute;
    }

    public void setDisplayRoute(ImageView displayRoute) {
        this.displayRoute = displayRoute;
    }

    public RouteM getServerRoute() {
        return serverRoute;
    }

    public void setServerRoute(RouteM serverRoute) {
        this.serverRoute = serverRoute;
    }

    public int getRouteColor() {

        switch (serverRoute.getRouteColor()) {
            case RED: return Color.RED;
            case WHITE: return Color.WHITE;
            case ORANGE: return Color.rgb(255, 152, 0);
            case BLACK: return Color.BLACK;
            case BLUE: return Color.BLUE;
            case YELLOW: return Color.YELLOW;
            case GREEN: return Color.GREEN;
            case PINK: return Color.rgb(199, 47, 179);
            default: return Color.rgb(128, 128, 128);
        }
    }
    public MoveID getMoveID () {
        return moveID;
    }

    public boolean isClaimed () {
        return claimed;
    }

    public void setClaimed (boolean isClaimed) {
        claimed = isClaimed;
    }
}
