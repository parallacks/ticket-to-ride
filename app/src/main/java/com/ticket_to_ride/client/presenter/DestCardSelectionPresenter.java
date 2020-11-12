package com.ticket_to_ride.client.presenter;

import android.os.AsyncTask;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.DestCardOptions;
import com.ticket_to_ride.client.services.ClientChooseCardService;
import com.ticket_to_ride.client.services.ClientChooseMoveService;
import com.ticket_to_ride.client.view.DestCardSelectionFragment;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.model.DestinationCardM;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static com.ticket_to_ride.common.data.MoveID.*;

/**
 * Created by jared on 3/1/19.
 */

public class DestCardSelectionPresenter implements Observer {

    private DestCardSelectionFragment destCardSelectionFragment;
    private ClientM clientM;
    private boolean firstTimePickingDestCards;

    public DestCardSelectionPresenter(DestCardSelectionFragment destCardSelectionFragment) {
        this.destCardSelectionFragment = destCardSelectionFragment;
        clientM = ClientM.get();
        clientM.addObserver(this);

        // Add destination card options to fragment initially
        // Notes: might not need if fragment gets updated with initial DestCardOptions
        if (clientM.getDestCardOptions() != null) {
            destCardSelectionFragment.updateDestCards(clientM.getDestCardOptions().getOptions());
        }
    }

    public void setFirstTimePickingDestCards(boolean firstTimePickingDestCards) {
        this.firstTimePickingDestCards = firstTimePickingDestCards;
    }


    private MoveID getDestSelectionMove(ArrayList<Integer> destCardsPositions) {
        switch(destCardsPositions.size()) {
            case 3:
                return MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_KEEP;
            case 2:
                if (destCardsPositions.contains(0) && destCardsPositions.contains(1)) {
                    return MOVE_CONFIRM_DESTINATION_CARDS_KEEP_KEEP_RTRN;
                }
                else if (destCardsPositions.contains(0) && destCardsPositions.contains(2)) {
                    return MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_KEEP;
                }
                else {
                    return MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_KEEP;
                }
            default:
                if (destCardsPositions.contains(0)) {
                    return MOVE_CONFIRM_DESTINATION_CARDS_KEEP_RTRN_RTRN;
                }
                else if (destCardsPositions.contains(1)) {
                    return MOVE_CONFIRM_DESTINATION_CARDS_RTRN_KEEP_RTRN;
                }
                else {
                    return MOVE_CONFIRM_DESTINATION_CARDS_RTRN_RTRN_KEEP;
                }
        }
    }

    public void respondSubmitBtnPress(ArrayList<DestinationCardM> selectedDestCards,
                                      ArrayList<Integer> selectedDestCardPositions,
                                      ArrayList<DestinationCardM> toDiscard) {
        if (firstTimePickingDestCards) {
            FirstTimeChoosingDestCardsTask firstTimeChoosingDestCardsTask = new
                    FirstTimeChoosingDestCardsTask(this, selectedDestCards);
            firstTimeChoosingDestCardsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, toDiscard);
        }
        else {
            MoveID proposedMove = getDestSelectionMove(selectedDestCardPositions);
            SendSelectedDestCardsTask sendDestCardsTask = new SendSelectedDestCardsTask(this,
                    selectedDestCards);
            sendDestCardsTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, proposedMove);
        }
    }


    public void displayError(String error) {
        destCardSelectionFragment.displayToast(error);
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println("In update function in DestCardSelectionPresenter");
        if (arg.getClass() == DestCardOptions.class) {
            clientM.deleteObserver(this);
            destCardSelectionFragment.closeView();
        }
    }

    public class SendSelectedDestCardsTask extends AsyncTask<MoveID, Void, Boolean> {

        DestCardSelectionPresenter presenter;
        ArrayList<DestinationCardM> selectedDestCards;

        public SendSelectedDestCardsTask(DestCardSelectionPresenter presenter,
                                         ArrayList<DestinationCardM> selectedDestCards) {
            this.presenter = presenter;
            this.selectedDestCards = selectedDestCards;
        }

        @Override
        protected Boolean doInBackground(MoveID... args) {
            ClientChooseMoveService chooseMoveService = new ClientChooseMoveService(args[0]);
            return chooseMoveService.ChooseMove();
        }

        @Override
        protected void onPostExecute(Boolean successFlag) {
            if (successFlag) {
                ClientM.get().addDestinationCards(selectedDestCards);
            }
            else {
                presenter.displayError("Communication to server failed. Please try again.");
            }
        }
    }

    public class FirstTimeChoosingDestCardsTask extends AsyncTask<ArrayList<DestinationCardM>, Void, Boolean> {

        private DestCardSelectionPresenter presenter;
        private ArrayList<DestinationCardM> toKeep;

        public FirstTimeChoosingDestCardsTask(DestCardSelectionPresenter presenter,
                                              ArrayList<DestinationCardM> toKeep) {
            this.presenter = presenter;
            this.toKeep = toKeep;
        }

        @Override
        protected Boolean doInBackground(ArrayList<DestinationCardM>... args) {
            ClientChooseCardService chooseCardService = new ClientChooseCardService(args[0]);
            return chooseCardService.ChooseCards();
        }

        @Override
        protected void onPostExecute(Boolean successFlag) {
            if (successFlag) {
                ClientM.get().addDestinationCards(toKeep);
            }
            else {
                presenter.displayError("Communication to server failed. Please try again.");
            }
        }
    }
}
