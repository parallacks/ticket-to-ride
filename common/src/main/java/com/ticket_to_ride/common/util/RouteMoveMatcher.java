//Created by Lance
package com.ticket_to_ride.common.util;

import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.data.RouteID;

public class RouteMoveMatcher {
    public MoveID matchMoveToRoute(RouteID routeID) {
        MoveID selectedMove=null;
        MoveID[] moveEnum = MoveID.values();
        for (MoveID move : moveEnum) {
            if (move.toString().contains(routeID.toString())){
                selectedMove = move;
            }
        }
        return selectedMove;
    }

    public RouteID matchRouteToMove(MoveID moveID)
    {
        RouteID selectedRoute = null;
        RouteID[] routeEnum = RouteID.values();
        for (RouteID route : routeEnum)
        {
            if (moveID.toString().contains(route.toString())) {
                selectedRoute = route;
                break;
            }
        }
        return selectedRoute;
    }
}
