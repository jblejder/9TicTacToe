package com.kutapps.tictactoe9.gameSetup.viewmodels;

import android.databinding.ObservableField;

import com.kutapps.tictactoe9.gameSetup.models.GameSetupModel;
import com.kutapps.tictactoe9.shared.commands.Command;

public class GameSetupViewModel
{
    public final ObservableField<GameSetupModel> setup;
    public final ObservableField<String>         hostedRoomName;
    public final ObservableField<String>         joiningRoomName;
    public final ObservableField<String>         username;
    public       Command<Void, Void>             changeNameCommand;
    public       Command<Void, Void>             changeHostedRoomNameCommand;
    public       Command<Void, Void>             changeJoinedRoomNameCommand;

    {
        hostedRoomName = new ObservableField<>();
        joiningRoomName = new ObservableField<>();
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
        changeHostedRoomNameCommand = new Command<>(this::changeHostedRoom);
        changeJoinedRoomNameCommand = new Command<>(this::changeJoiningRoom);
    }

    private Void changeJoiningRoom(Void o)
    {
        String newName = joiningRoomName.get().trim();
        if (newName.isEmpty())
        {
            throw new IllegalStateException("Username can't be null");
        }
        setup.get().joiningRoomName.set(newName);

        return null;
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

    private Void changeHostedRoom(Void aVoid)
    {
        String newName = hostedRoomName.get().trim();
        if (newName.isEmpty())
        {
            throw new IllegalStateException("Username can't be null");
        }
        setup.get().hostedRoomName.set(newName);

        return null;
    }
}
