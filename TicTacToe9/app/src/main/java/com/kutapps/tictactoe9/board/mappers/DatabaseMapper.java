package com.kutapps.tictactoe9.board.mappers;

import com.kutapps.tictactoe9.board.models.DatabaseGameModel;

public class DatabaseMapper
{
    public static DatabaseGameModel mapBoard(Object value)
    {
        DatabaseGameModel game = new DatabaseGameModel();
        return game;
    }
}
