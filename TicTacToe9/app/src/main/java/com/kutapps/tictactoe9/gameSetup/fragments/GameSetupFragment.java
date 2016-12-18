package com.kutapps.tictactoe9.gameSetup.fragments;

import android.databinding.Observable;
import android.os.Bundle;

import com.kutapps.tictactoe9.R;
import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.board.fragments.BoardFragment;
import com.kutapps.tictactoe9.databinding.FragmentGameSetupBinding;
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
        model.changeRoomNameCommand.isExecuting.addOnPropertyChangedCallback(new Observable
                .OnPropertyChangedCallback()
        {
            @Override
            public void onPropertyChanged(Observable observable, int i)
            {
                CommandState state = model.changeRoomNameCommand.isExecuting.get();
                switch (state)
                {
                    case Executing:
                        break;
                    case Succeeded:
                        binding.roomEditContainer.collapse();
                        break;
                    case Error:
                        binding.roomInput.setError(getString(R.string
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
    public void onChangeRoomName()
    {
        binding.roomInput.setError(null);
        model.roomName.set(model.setup.get().roomName.get());
        binding.etRoomName.setSelection(binding.etRoomName.getText().length());
        closeAllEditContainers();
        binding.roomEditContainer.toggle();
    }

    @Override
    public void onApproveRoomName()
    {
        model.changeRoomNameCommand.execute(null);
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
        binding.roomEditContainer.collapse();
    }

    @Override
    public void onApproveMarker(MarkerType type)
    {
        model.setup.get().marker.set(type);
        binding.markerEditContainer.toggle();
    }

    @Override
    public void onClickStart()
    {
        callback.openFragment(BoardFragment.newInstance(model.setup.get()), TransactionOptions
                .AddToBackStack);
    }
}
