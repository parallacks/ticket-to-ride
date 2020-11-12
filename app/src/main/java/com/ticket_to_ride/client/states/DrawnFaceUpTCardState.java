package com.ticket_to_ride.client.states;

import android.os.AsyncTask;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.presenter.IGameStatusPresenter;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.model.TrainCardColor;


/**
 * Created by jaredrp3 on 3/22/19.
 */

public class DrawnFaceUpTCardState extends GameStatusState {

    private static DrawnFaceUpTCardState instance;
    private ClientM clientM;

    public static DrawnFaceUpTCardState getInstance() {
        if (instance == null) {
            instance = new DrawnFaceUpTCardState();
        }
        return instance;
    }

    private DrawnFaceUpTCardState() {
        this.clientM = ClientM.get();
    }

    public void enter(IGameStatusPresenter presenter) {
        clientM.setCurrentGameStatusState(this);
        presenter.grayOutWildCards(true);
        System.out.println("********************** entering DRAWN FACE UP T CARD STATE");
    }

    public void exit(IGameStatusPresenter presenter) {
        presenter.grayOutWildCards(false);
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
