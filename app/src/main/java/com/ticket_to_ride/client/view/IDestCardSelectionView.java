package com.ticket_to_ride.client.view;

import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.HandM;

import java.util.ArrayList;
import java.util.List;

public interface IDestCardSelectionView {
    void enableSubmitBtn(boolean enable);
    void updateDestCards(List<DestinationCardM> destCards);
    void closeView();
    void displayToast(String toDisplay);
}

