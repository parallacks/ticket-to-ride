package com.ticket_to_ride.client.presenter;

import android.os.AsyncTask;

import com.ticket_to_ride.client.services.ClientChooseMoveService;
import com.ticket_to_ride.common.data.MoveID;

/**
 * Created by jaredrp3 on 3/27/19.
 */

public class ChooseMoveClaimRouteTask extends AsyncTask<MoveID, Void, Boolean> {

    private IGameBoardPresenter presenter;

    public ChooseMoveClaimRouteTask(IGameBoardPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected Boolean doInBackground(MoveID... args) {
        ClientChooseMoveService moveService = new ClientChooseMoveService(args[0]);
        return moveService.ChooseMove();
    }

    @Override
    public void onPostExecute(Boolean resultFlag) {

    }
}
