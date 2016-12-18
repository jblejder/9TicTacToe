package com.kutapps.tictactoe9.board.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Pair;

import com.google.gson.Gson;
import com.kutapps.tictactoe9.R;
import com.kutapps.tictactoe9.board.fragments.handlers.BoardHandler;
import com.kutapps.tictactoe9.board.viewmodels.BoardViewModel;
import com.kutapps.tictactoe9.databinding.FragmentBoardBinding;
import com.kutapps.tictactoe9.gameSetup.models.GameSetupModel;
import com.kutapps.tictactoe9.shared.fragments.BaseFragment;
import com.kutapps.tictactoe9.shared.interfaces.OnBackPressedListener;

import org.joda.time.DateTime;

public class BoardFragment extends BaseFragment<FragmentBoardBinding> implements BoardHandler,
        OnBackPressedListener
{
    private static final String DATA_KEY             = "board.setup";
    private static final int    BACK_CONFIRM_SECONDS = 2;
    private BoardViewModel model;
    private DateTime       warningTimestamp;

    //region newInstance
    public static BoardFragment newInstance(GameSetupModel setup)
    {
        BoardFragment fragment = new BoardFragment();
        fragment.setSetup(setup);

        return fragment;
    }

    private void setSetup(GameSetupModel setup)
    {
        Bundle args = getArguments();
        if (args == null)
        {
            args = new Bundle();
        }
        args.putString(DATA_KEY, new Gson().toJson(setup));
        setArguments(args);
    }
    //endregion

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_board;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        if (getArguments() != null && getArguments().containsKey(DATA_KEY))
        {
            String dataRaw = getArguments().getString(DATA_KEY);
            GameSetupModel setup = new Gson().fromJson(dataRaw, GameSetupModel.class);
            model = new BoardViewModel(setup);
        }
        warningTimestamp = DateTime.now().minusSeconds(BACK_CONFIRM_SECONDS);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState)
    {
        binding.setModel(model);
        binding.setHandler(this);
    }

    @Override
    public void onClickField(int board, int field)
    {
        model.moveCommand.execute(new Pair<>(board, field));
    }

    @Override
    public void onClickClear()
    {
        model.clearCommand.execute(null);
    }

    @Override
    public void onClickGenerateRandom()
    {
        model.randomizeCommand.execute(null);
    }

    @Override
    public boolean onBackPressed()
    {
        if (warningTimestamp.plusSeconds(BACK_CONFIRM_SECONDS).isAfterNow())
        {
            return false;
        }
        else
        {
            showWarning();
            return true;
        }
    }

    private void showWarning()
    {
        Snackbar.make(binding.getRoot(), R.string.press_again_to_confirm, Snackbar.LENGTH_SHORT)
                .show();
        warningTimestamp = DateTime.now();
    }
}
