package com.kutapps.tictactoe9.board.fragments;

import android.os.Bundle;
import android.util.Pair;

import com.kutapps.tictactoe9.R;
import com.kutapps.tictactoe9.board.fragments.handlers.BoardHandler;
import com.kutapps.tictactoe9.board.viewmodels.BoardViewModel;
import com.kutapps.tictactoe9.databinding.FragmentBoardBinding;
import com.kutapps.tictactoe9.shared.fragments.BaseFragment;

public class BoardFragment extends BaseFragment<FragmentBoardBinding> implements BoardHandler
{
    private BoardViewModel model;

    public static BoardFragment newInstance()
    {
        return new BoardFragment();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_board;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        model = new BoardViewModel();
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
}
