package com.ticket_to_ride.client.model;

import java.util.ArrayList;
import com.ticket_to_ride.common.model.*;

/**
 * Created by jyancey on 3/9/19.
 */

public class DestCardOptions {
    private ArrayList<DestinationCardM> options;

    public DestCardOptions()
    {
        this.options = new ArrayList<>();
    }

    public ArrayList<DestinationCardM> getOptions()
    {
        return options;
    }

    public void setOptions(ArrayList<DestinationCardM> options)
    {
        this.options = options;
    }
}
