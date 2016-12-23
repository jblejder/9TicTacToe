package com.kutapps.tictactoe9.board.models.database;

import android.support.annotation.Nullable;

import com.kutapps.tictactoe9.board.consts.MarkerType;

import java.util.ArrayList;
import java.util.List;

public class DatabaseGameModel
{
    public String                        hostId;
    @Nullable
    public String                        userId;
    public List<DatabaseSmallBoardModel> smallBoards;

    {
        smallBoards = new ArrayList<>(9);
    }
}
