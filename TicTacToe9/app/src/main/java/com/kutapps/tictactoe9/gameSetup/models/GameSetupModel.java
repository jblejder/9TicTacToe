package com.kutapps.tictactoe9.gameSetup.models;

import android.databinding.ObservableField;

import com.kutapps.tictactoe9.board.consts.MarkerType;

public class GameSetupModel
{
    public final ObservableField<String>     username;
    public final ObservableField<String>     roomName;
    public final ObservableField<MarkerType> marker;

    {
        username = new ObservableField<>();
        roomName = new ObservableField<>();
        marker = new ObservableField<>();
    }

    public GameSetupModel()
    {
        username.set("Janusz");
        roomName.set("Elo");
        marker.set(MarkerType.Nought);
    }
}
