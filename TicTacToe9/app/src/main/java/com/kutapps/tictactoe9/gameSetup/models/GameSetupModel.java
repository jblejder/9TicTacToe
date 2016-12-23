package com.kutapps.tictactoe9.gameSetup.models;

import android.databinding.ObservableField;

import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.gameSetup.consts.GameMode;

public class GameSetupModel
{
    public final ObservableField<String>     username;
    public final ObservableField<String>     hostedRoomName;
    public final ObservableField<String>     joiningRoomName;
    public final ObservableField<MarkerType> marker;
    public final ObservableField<GameMode>   mode;

    {
        username = new ObservableField<>();
        hostedRoomName = new ObservableField<>();
        marker = new ObservableField<>();
        joiningRoomName = new ObservableField<>();
        mode = new ObservableField<>();
    }

    public GameSetupModel()
    {
        username.set("Janusz");
        hostedRoomName.set("Elo");
        joiningRoomName.set("RandomElo");
        marker.set(MarkerType.Nought);
    }

    public String getRoomId()
    {
        switch (mode.get())
        {
            case Host:
                return hostedRoomName.get();
            case Join:
            default:
                return joiningRoomName.get();
        }
    }
}
