package com.ticket_to_ride.client.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ticket_to_ride.client.model.PlayerInfoM;
import com.ticket_to_ride.client.presenter.GameLobbyPresenter;

import java.util.List;


//Jared
public class GameLobbyFragment extends Fragment {
    private IGameWaiting gameWaitingActivity;
    private Button leaveGameBtn;
    private Button readyBtn;
    private RecyclerView recyclerView;
    private Adapter adapter;

    private GameLobbyPresenter gameLobbyPresenter;
    // Other variables for recycler view for g
    // ame list

    public GameLobbyFragment() {
        // Required empty public constructor
    }

    public static GameLobbyFragment newInstance() {
        GameLobbyFragment fragment = new GameLobbyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_game_lobby, container, false);

        readyBtn = view.findViewById(R.id.readyBtn);
        leaveGameBtn = view.findViewById(R.id.leaveGameBtn);

        gameLobbyPresenter = new GameLobbyPresenter(this);

        recyclerView = view.findViewById(R.id.game_lobby_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setButtonListeners();
        gameLobbyPresenter.updatePlayerList();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        gameWaitingActivity = (IGameWaiting) context;

    }

    private void setButtonListeners() {
        readyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyBtnPressed();
            }
        });

        leaveGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveGameBtnPressed();
            }
        });
    }

    // Respond to logout button press by alerting presenter
    public void leaveGameBtnPressed() {
        gameLobbyPresenter.respondLeave();
    }

    public void readyBtnPressed() {
        gameLobbyPresenter.respondReady();
    }

    public void toGameBoard() {
        Intent intent = new Intent(getActivity(), GameBoardActivity.class);
        startActivity(intent);
    }

    public void setLeaveGameBtn(boolean state) {
        leaveGameBtn.setEnabled(state);
    }

    public void setReadyGameBtn(boolean state) {
        final boolean stateFinal = state;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                readyBtn.setEnabled(stateFinal);
            }
        });
    }


    public void displayErrorMsg(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    public void displaySuccessMsg(String msg) {
        final String message = msg;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void switchFragments() {
        gameWaitingActivity.switchToGameList();
    }

    public void updatePlayerListItems(List<PlayerInfoM> items) {

        adapter = new Adapter(getActivity(), items);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(adapter);
            }
        });
    }

    // Adaptor for the recycler view
    class Adapter extends RecyclerView.Adapter<Holder> {

        private List<PlayerInfoM> items;
        private LayoutInflater inflater;

        public Adapter(Context context, List<PlayerInfoM> items) {
            this.items = items;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.game_lobby_player_list_item, parent,
                    false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            PlayerInfoM item = items.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    }

    // Holds the information for each game list item
    class Holder extends RecyclerView.ViewHolder {

        // Display text and description for each setting
        private TextView playerName;
        private PlayerInfoM item;

        public Holder(View view) {
            super(view);
            playerName = view.findViewById(R.id.playerListName);
        }

        void bind(PlayerInfoM item) {
            this.item = item;
            playerName.setText(this.item.getUserM().getUsername());
        }
    }

}
