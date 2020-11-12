package com.ticket_to_ride.client.states;

import android.os.AsyncTask;

import com.ticket_to_ride.client.presenter.GameStatusPresenter;
import com.ticket_to_ride.client.presenter.IGameStatusPresenter;
import com.ticket_to_ride.client.services.ClientChooseMoveService;
import com.ticket_to_ride.common.data.MoveID;

/**
 * Created by jaredrp3 on 3/25/19.
 */

public class ChooseMoveChangeStateTask extends AsyncTask<MoveID, Void, Boolean> {

        private IGameStatusPresenter presenter;
        private GameStatusState nextState;

        public ChooseMoveChangeStateTask(IGameStatusPresenter presenter, GameStatusState nextState) {
            this.presenter = presenter;
            this.nextState = nextState;
        }

        @Override
        protected Boolean doInBackground(MoveID... args) {
            ClientChooseMoveService moveService = new ClientChooseMoveService(args[0]);
            return moveService.ChooseMove();
        }

        @Override
        public void onPostExecute(Boolean resultFlag) {
            if (resultFlag) {
                presenter.setState(this.nextState);
            }
            else {
                presenter.displayError("Communication to server failed. Please try again.");
            }
        }
}
