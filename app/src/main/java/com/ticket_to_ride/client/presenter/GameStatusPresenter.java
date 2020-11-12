package com.ticket_to_ride.client.presenter;

import android.os.AsyncTask;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.model.DeckSizeM;
import com.ticket_to_ride.client.model.DestCardOptions;
import com.ticket_to_ride.client.model.PlayerInfoM;
//import com.ticket_to_ride.client.services.MockServiceCaller;
import com.ticket_to_ride.client.states.DrawnFaceUpTCardState;
import com.ticket_to_ride.client.states.DrawnTCardFromDeckState;
import com.ticket_to_ride.client.states.EndTurnState;
import com.ticket_to_ride.client.states.GameStatusState;
import com.ticket_to_ride.client.states.TurnState;
import com.ticket_to_ride.client.view.GameStatusFragment;
import com.ticket_to_ride.client.view.IGameStatusView;
import com.ticket_to_ride.common.model.DestinationCardM;
import com.ticket_to_ride.common.model.HandM;
import com.ticket_to_ride.common.model.TrainCardColor;
import com.ticket_to_ride.common.model.TrainCardM;
import com.ticket_to_ride.common.model.TrayM;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jaredrp3 on 3/4/19.
 */

public class GameStatusPresenter implements Observer, IGameStatusPresenter {

    private GameStatusFragment gameStatusFragment;
    private ClientM clientM;
    //private MockServiceCaller mockServiceCaller;
    private GameStatusState currentState;


    public GameStatusPresenter(IGameStatusView gameStatusView) {
        System.out.println("Made it to Game status presenter constructor");
        this.gameStatusFragment = (GameStatusFragment) gameStatusView;
        clientM = ClientM.get();
        setObserver(true);
        initScreen();
        setInitialState();
    }

    private void setInitialState() {
        if (clientM.getMyStats().isTurn()) {
            if (clientM.getCurrentGameStatusState() instanceof DrawnTCardFromDeckState) {
                setState(DrawnTCardFromDeckState.getInstance());
            }
            else if (clientM.getCurrentGameStatusState() instanceof DrawnFaceUpTCardState) {
                setState(DrawnFaceUpTCardState.getInstance());
            }
            // Could be TurnFlag or EndTurnFlag; in either case, it's the user's turn, so go to
            //      TurnState
            else {
                setState(TurnState.getInstance());
            }
        }
        else {
            setState(EndTurnState.getInstance());
        }
    }

//    public void testRun() {
//        try {
//            mockServiceCaller = new MockServiceCaller();
//            mockServiceCaller.runStatusDisplay();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    private void updateMyTrainCards() {
        //System.out.println("Should be updating train cards");
        List<TrainCardColor> trainCards = new ArrayList<>(clientM.getNumEachTrainCard().keySet());
        gameStatusFragment.updateMyTrainCards(trainCards);
    }

    @Override
    public void initScreen() {
        gameStatusFragment.updateFaceUpTCards(clientM.getFaceUpTCards());
        gameStatusFragment.updateDestDeckNum(clientM.getNumDestCards());
        gameStatusFragment.updateTCardDeckNum(clientM.getNumTrainCards());
        gameStatusFragment.updateMyStats(clientM.getMyStats());
        gameStatusFragment.updateOpponentStats(clientM.getOpponentStats());
        gameStatusFragment.updateDestCardInfo(clientM.getDestinationCards().getHand());
        updateMyTrainCards();
    }

    @Override
    public void setObserver(boolean add) {
        if (add)
            clientM.addObserver(this);
        else
            clientM.deleteObserver(this);
    }


    @Override
    public int getCardNum(TrainCardColor tCardColor) {
        return clientM.getNumTCard(tCardColor);
    }

    @Override
    public void displayError(String errorMsg) {
        gameStatusFragment.displayToast(errorMsg);
    }

    @Override
    public void takeFaceUpTCard(TrainCardM trainCard, int pos) {
        this.currentState.takeFaceUpTCard(trainCard.getColor(), pos, this);
    }

    @Override
    public void takeTCardFromDeck() {
        this.currentState.takeTCardFromDeck(this);
    }

    @Override
    public void selectNewDestCards() {
        this.currentState.selectNewDestCards(this);
    }

    @Override
    public void setState(GameStatusState newState) {
        if (currentState != null) {
            currentState.exit(this);
        }

        currentState = newState;

        if (currentState != null) {
            currentState.enter(this);
        }
    }

    @Override
    public void grayOutWildCards(boolean grayOut) {
        gameStatusFragment.grayOutWildFaceUpTCards(grayOut);
        gameStatusFragment.updateFaceUpTCards(clientM.getFaceUpTCards());
    }

    private boolean checkForDestCardSelection(Object arg) {
        if (arg.getClass() == DestCardOptions.class) {
            //TODO: make sure this doesn't break things
            clientM.deleteObserver(this);
            gameStatusFragment.switchDestCardSelectionFragment();
            return true;
        }
        return false;
    }


    private boolean checkForTCardUpdate(Object arg) {
        System.out.println("checking for T Card update");
        if (arg.getClass() == TrayM.class || arg.getClass() == HandM.class) {
            gameStatusFragment.updateFaceUpTCards(clientM.getFaceUpTCards());
            updateMyTrainCards();
            gameStatusFragment.updateMyStats(clientM.getMyStats());
            return true;
        }
        return false;
    }

    private boolean checkForNumInDecksUpdate(Object arg) {
        System.out.println("checking for num decks update");
        if (arg.getClass() == DeckSizeM.class) {
            DeckSizeM deckSize = (DeckSizeM) arg;
            if (deckSize.getType().equals("destinationCards")) {
                gameStatusFragment.updateDestDeckNum(deckSize.getSize());
                return true;
            }
            else {
                gameStatusFragment.updateTCardDeckNum(deckSize.getSize());
                updateMyTrainCards();
                return true;
            }
        }
        return false;
    }

    private boolean checkForOpponentStatsUpdate(Object arg) {
        if (arg.getClass() == ArrayList.class) {
            ArrayList<Object> oppStats = (ArrayList<Object>) arg;
            if (oppStats.size() > 0 && oppStats.get(0).getClass() == PlayerInfoM.class) {
                gameStatusFragment.updateOpponentStats(clientM.getOpponentStats());
                gameStatusFragment.updateMyStats(clientM.getMyStats());
                return true;
            }
        }
        return false;
    }

    private boolean checkForMyStatsUpdate(Object arg) {
        if (arg.getClass() == PlayerInfoM.class) {
            gameStatusFragment.updateMyStats(clientM.getMyStats());
            gameStatusFragment.updateOpponentStats(clientM.getOpponentStats());
            setInitialState();
            return true;
        }
        return false;
    }

    private boolean checkForDestCardInfoUpdate(Object arg) {
        if (arg.getClass() == ArrayList.class) {
            ArrayList<Object> list = (ArrayList<Object>) arg;
            if (list.size() > 0 && list.get(0) instanceof DestinationCardM) {
                gameStatusFragment.updateDestCardInfo(clientM.getDestinationCards().getHand());
                return true;
            }
        }
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {
        //gameStatusFragment.updateMyStats(clientM.getMyStats());
        //gameStatusFragment.updateOpponentStats(clientM.getOpponentStats());
        if (arg != null) {
            checkForDestCardSelection(arg);
            checkForTCardUpdate(arg);
            checkForNumInDecksUpdate(arg);
            checkForOpponentStatsUpdate(arg);
            checkForMyStatsUpdate(arg);
            checkForDestCardInfoUpdate(arg);
        }
        //TODO: make sure this doesn't happen too early (the GameOver flag in model needs to be the
        //TODO:     last thing changed on each client in order for this to work each time
        if (ClientM.get().isGameOver()) {
            clientM.deleteObserver(this);
            gameStatusFragment.switchToGameOverView();
        }
    }
}
