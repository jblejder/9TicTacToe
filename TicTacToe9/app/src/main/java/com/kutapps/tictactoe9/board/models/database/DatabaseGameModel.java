package com.kutapps.tictactoe9.board.models.database;

import android.support.annotation.Nullable;

import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.board.models.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseGameModel
{
    public PlayerModel                   host;
    @Nullable
    public PlayerModel                   joiner;
    public List<DatabaseSmallBoardModel> smallBoards;
    public Integer                       nextBoardNumber;
    public MarkerType                    nextMarker;

    {
        smallBoards = new ArrayList<>(9);
        nextBoardNumber = Integer.MIN_VALUE;
    }
}
