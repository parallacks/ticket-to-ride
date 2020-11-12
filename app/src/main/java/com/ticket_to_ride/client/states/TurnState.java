package com.ticket_to_ride.client.states;

import android.os.AsyncTask;

import com.ticket_to_ride.client.model.ClientM;
import com.ticket_to_ride.client.presenter.IGameStatusPresenter;
import com.ticket_to_ride.common.data.MoveID;
import com.ticket_to_ride.common.model.TrainCardColor;


/**
 * Created by jaredrp3 on 3/18/19.
 */

public class TurnState extends GameStatusState {

    private static TurnState instance;
    private ClientM clientM;

    public static TurnState getInstance() {
        if (instance == null) {
            instance = new TurnState();
        }
        return instance;
    }

    private TurnState() {
        this.clientM = ClientM.get();
    }

    @Override
    public void enter(IGameStatusPresenter presenter) {
        System.out.println("****************** entering TURN STATE");
    }

    public void takeFaceUpTCard(TrainCardColor cardColor, int pos, IGameStatusPresenter presenter) {
        MoveID proposedMove = getFaceUpTCardPositionMove(pos);

        if (clientM.isValidMove(proposedMove)) {
            ChooseMoveChangeStateTask chooseMoveChangeStateTask;
            if (cardColor == TrainCardColor.WILD) {
                chooseMoveChangeStateTask = new ChooseMoveChangeStateTask(presenter,
                        EndTurnState.getInstance());
            }
            else {
                chooseMoveChangeStateTask = new ChooseMoveChangeStateTask(presenter,
                        DrawnFaceUpTCardState.getInstance());
            }
            chooseMoveChangeStateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, proposedMove);

        }
        else {
            presenter.displayError("Invalid Move");
        }
    }

    public void takeTCardFromDeck(IGameStatusPresenter presenter) {
        if (clientM.isValidMove(MoveID.MOVE_DRAW_TRAIN_CARD)) {
            ChooseMoveChangeStateTask chooseMoveChangeStateTask =
                    new ChooseMoveChangeStateTask(presenter, DrawnFaceUpTCardState.getInstance());
            //chooseMoveChangeStateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MoveID.MOVE_DRAW_TRAIN_CARD);
            chooseMoveChangeStateTask.execute(MoveID.MOVE_DRAW_TRAIN_CARD);
        }
        else {
            presenter.displayError("Invalid Move");
        }
    }

    public void selectNewDestCards(IGameStatusPresenter presenter) {
        if (clientM.isValidMove(MoveID.MOVE_DRAW_DESTINATION_CARDS)) {
            ChooseMoveChangeStateTask chooseMoveChangeStateTask =
                    new ChooseMoveChangeStateTask(presenter, EndTurnState.getInstance());
            chooseMoveChangeStateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, MoveID.MOVE_DRAW_DESTINATION_CARDS);
        }
        else {
            presenter.displayError("Invalid Move");
        }
    }

    public static MoveID getFaceUpTCardPositionMove(int position) {
        switch(position) {
            case 0:
                return MoveID.MOVE_TAKE_SHOP_CARD_1;
            case 1:
                return MoveID.MOVE_TAKE_SHOP_CARD_2;
            case 2:
                return MoveID.MOVE_TAKE_SHOP_CARD_3;
            case 3:
                return MoveID.MOVE_TAKE_SHOP_CARD_4;
            case 4:
                return MoveID.MOVE_TAKE_SHOP_CARD_5;
            default:
                return null;
        }
    }

}
