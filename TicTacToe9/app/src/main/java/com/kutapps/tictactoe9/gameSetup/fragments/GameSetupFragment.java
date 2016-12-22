package com.kutapps.tictactoe9.gameSetup.fragments;

import android.databinding.Observable;
import android.os.Bundle;

import com.kutapps.tictactoe9.R;
import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.board.fragments.BoardFragment;
import com.kutapps.tictactoe9.databinding.FragmentGameSetupBinding;
import com.kutapps.tictactoe9.gameSetup.consts.GameMode;
import com.kutapps.tictactoe9.gameSetup.fragments.handlers.GameSetupHandler;
import com.kutapps.tictactoe9.gameSetup.viewmodels.GameSetupViewModel;
import com.kutapps.tictactoe9.shared.TransactionOptions;
import com.kutapps.tictactoe9.shared.commands.CommandState;
import com.kutapps.tictactoe9.shared.fragments.BaseFragment;

public class GameSetupFragment extends BaseFragment<FragmentGameSetupBinding> implements
        GameSetupHandler
{
    private GameSetupViewModel model;

    public static GameSetupFragment newInstance()
    {
        return new GameSetupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        model = new GameSetupViewModel();
        model.changeNameCommand.isExecuting.addOnPropertyChangedCallback(new Observable
                .OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable observable, int i)
            {
                CommandState state = model.changeNameCommand.isExecuting.get();
                switch (state)
                {
                    case Executing:
                        break;
                    case Succeeded:
                        binding.usernameContainer.collapse();
                        break;
                    case Error:
                        binding.usernameInput.setError(getString(R.string
                                .username_should_not_be_empty));
                        break;
                }
            }
        });
        model.changeHostedRoomNameCommand.isExecuting.addOnPropertyChangedCallback(new Observable
                .OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable observable, int i)
            {
                CommandState state = model.changeHostedRoomNameCommand.isExecuting.get();
                switch (state)
                {
                    case Executing:
                        break;
                    case Succeeded:
                        binding.hostedRoomEditContainer.collapse();
                        break;
                    case Error:
                        binding.hostedRoomInput.setError(getString(R.string
                                .username_should_not_be_empty));
                        break;
                }
            }
        });
        model.changeJoinedRoomNameCommand.isExecuting.addOnPropertyChangedCallback(new Observable
                .OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable observable, int i)
            {
                CommandState state = model.changeJoinedRoomNameCommand.isExecuting.get();
                switch (state)
                {
                    case Executing:
                        break;
                    case Succeeded:
                        binding.joinedRoomEditContainer.collapse();
                        break;
                    case Error:
                        binding.joiningRoomInput.setError(getString(R.string
                                .username_should_not_be_empty));
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_game_setup;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        binding.setModel(model);
        binding.setHandler(this);
    }

    @Override
    public void onChangeUserName()
    {
        binding.usernameInput.setError(null);
        model.username.set(model.setup.get().username.get());
        binding.etJoiningRoomName.requestFocus();
        binding.etUsername.setSelection(binding.etUsername.getText().length());
        closeAllEditContainers();
        binding.usernameContainer.toggle();
    }

    @Override
    public void onApproveUserName()
    {
        model.changeNameCommand.execute(null);
    }

    @Override
    public void onChangeMarker()
    {
        closeAllEditContainers();
        binding.markerEditContainer.toggle();
    }

    private void closeAllEditContainers()
    {
        binding.markerEditContainer.collapse();
        binding.usernameContainer.collapse();
        binding.hostedRoomEditContainer.collapse();
        binding.joinedRoomEditContainer.collapse();
    }

    @Override
    public void onApproveMarker(MarkerType type)
    {
        model.setup.get().marker.set(type);
        binding.markerEditContainer.toggle();
    }

    @Override
    public void onApproveHostRoomName()
    {
        model.changeHostedRoomNameCommand.execute(null);
    }

    @Override
    public void onChangeHostedRoomName()
    {
        binding.hostedRoomInput.setError(null);
        model.hostedRoomName.set(model.setup.get().hostedRoomName.get());
        binding.etHostedRoomName.setSelection(binding.etHostedRoomName.getText().length());
        closeAllEditContainers();
        binding.hostedRoomEditContainer.toggle();
    }

    @Override
    public void onClickHostGame()
    {
        model.setup.get().mode.set(GameMode.Host);
        callback.openFragment(BoardFragment.newInstance(model.setup.get()), TransactionOptions
                .AddToBackStack);
    }

    @Override
    public void onChangeJoiningRoomName()
    {
        binding.joiningRoomInput.setError(null);
        model.joiningRoomName.set(model.setup.get().joiningRoomName.get());
        binding.etJoiningRoomName.setSelection(binding.etJoiningRoomName.getText().length());
        closeAllEditContainers();
        binding.joinedRoomEditContainer.toggle();
    }

    @Override
    public void onApproveJoiningRoomName()
    {
        model.changeJoinedRoomNameCommand.execute(null);
    }

    @Override
    public void onClickJoinGame()
    {
        model.setup.get().mode.set(GameMode.Join);
        callback.openFragment(BoardFragment.newInstance(model.setup.get()), TransactionOptions
                .AddToBackStack);
    }
}
