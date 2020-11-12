package com.ticket_to_ride.client.states;

import android.os.AsyncTask;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.presenter.IGameStatusPresenter;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.model.TrainCardColor;

/**
 * Created by jaredrp3 on 3/18/19.
 */

public class DrawnTCardFromDeckState extends GameStatusState {

    private static DrawnTCardFromDeckState instance;
    private ClientM clientM;

    public static DrawnTCardFromDeckState getInstance() {
        if (instance == null) {
            instance = new DrawnTCardFromDeckState();
        }
        return instance;
    }

    private DrawnTCardFromDeckState() {
        this.clientM = ClientM.get();
    }

    @Override
    public void enter(IGameStatusPresenter presenter) {
        clientM.setCurrentGameStatusState(this);
        System.out.println("************************* entering DRAWN T CARD FROM DECK STATE");
    }

    public void takeFaceUpTCard(TrainCardColor cardColor, int pos, IGameStatusPresenter presenter) {
        MoveID proposedMove = TurnState.getFaceUpTCardPositionMove(pos);

        if (clientM.isValidMove(proposedMove)) {
            ChooseMoveChangeStateTask chooseMoveChangeStateTask =
                    new ChooseMoveChangeStateTask(presenter, EndTurnState.getInstance());
            chooseMoveChangeStateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, proposedMove);
        }
        else {
            presenter.displayError("Invalid Move");
        }
    }

    public void takeTCardFromDeck(IGameStatusPresenter presenter) {
        if (clientM.isValidMove(MoveID.MOVE_DRAW_TRAIN_CARD)) {
            ChooseMoveChangeStateTask chooseMoveChangeStateTask =
                    new ChooseMoveChangeStateTask(presenter, EndTurnState.getInstance());
            chooseMoveChangeStateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MoveID.MOVE_DRAW_TRAIN_CARD);
        }
        else {
            presenter.displayError("Invalid Move");
        }
    }

    public void selectNewDestCards(IGameStatusPresenter presenter) {
        presenter.displayError("Invalid Move");
    }
}
