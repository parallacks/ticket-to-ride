package com.ticket_to_ride.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


//Jared
public class GameWaitingActivity extends AppCompatActivity implements IGameWaiting {

    private GameListFragment gameListFrag;
    private GameLobbyFragment gameLobbyFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_waiting);

        // Switch to Game List fragment by default
        FragmentManager fm = getSupportFragmentManager();
        if (gameListFrag == null) {
            gameListFrag = GameListFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container, gameListFrag).commit();
        }
    }

    // Switch from Game List to Game Lobby
    @Override
    public void switchToGameLobby() {
        FragmentManager fm = getSupportFragmentManager();
        if (gameLobbyFrag == null) {
            gameLobbyFrag = GameLobbyFragment.newInstance();
        }
        fm.beginTransaction().replace(R.id.fragment_container, gameLobbyFrag).commit();
    }

    // Switch from Game Lobby to Game List
    @Override
    public void switchToGameList() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, gameListFrag).commit();
    }

    @Override
    public void startGame() {
        Intent intent = new Intent(this, GameBoardActivity.class);
        startActivity(intent);
    }
}
