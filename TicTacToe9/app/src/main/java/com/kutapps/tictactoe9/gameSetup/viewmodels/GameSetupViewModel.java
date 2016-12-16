package com.kutapps.tictactoe9.gameSetup.viewmodels;

import android.databinding.ObservableField;

import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.gameSetup.models.GameSetupModel;
import com.kutapps.tictactoe9.shared.commands.Command;

public class GameSetupViewModel
{
    public final ObservableField<GameSetupModel> setup;
    public final ObservableField<String>         roomName;
    public final ObservableField<String>         username;
    public       Command<Void, Void>             changeNameCommand;
    public       Command<Void, Void>             changeRoomNameCommand;

    {
        roomName = new ObservableField<>();
        username = new ObservableField<>();
        setup = new ObservableField<>();
    }

    public GameSetupViewModel()
    {
        setup.set(new GameSetupModel());

        initCommands();
    }

    private void initCommands()
    {
        changeNameCommand = new Command<>(this::changeName);
        changeRoomNameCommand = new Command<>(this::changeRoom);
    }

    private Void changeName(Void aVoid)
    {
        String newName = username.get().trim();
        if (newName.isEmpty())
        {
            throw new IllegalStateException("Username can't be null");
        }
        setup.get().username.set(newName);

        return null;
    }

    private Void changeRoom(Void aVoid)
    {
        String newName = roomName.get().trim();
        if (newName.isEmpty())
        {
            throw new IllegalStateException("Username can't be null");
        }
        setup.get().roomName.set(newName);

        return null;
    }
}
