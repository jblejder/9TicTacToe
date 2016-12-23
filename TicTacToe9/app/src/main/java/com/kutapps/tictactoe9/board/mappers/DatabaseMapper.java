package com.kutapps.tictactoe9.board.mappers;

import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.board.models.database.DatabaseGameModel;
import com.kutapps.tictactoe9.board.models.database.DatabaseSmallBoardModel;
import com.kutapps.tictactoe9.board.viewmodels.BoardViewModel;
import com.kutapps.tictactoe9.board.viewmodels.FieldViewModel;
import com.kutapps.tictactoe9.board.viewmodels.SingleBoardViewModel;

public class DatabaseMapper
{
    public static DatabaseGameModel mapBoardToDb(BoardViewModel model)
    {
        DatabaseGameModel game = new DatabaseGameModel();
        switch (model.gameSetup.mode.get())
        {
            case Host:
                game.hostId = model.currentUser.get().getUid();
                game.userId = model.secondUserId;
                break;
            case Join:
                game.hostId = model.secondUserId;
                game.userId = model.currentUser.get().getUid();
                break;
        }
        for (SingleBoardViewModel smallBoard : model.boards)
        {
            game.smallBoards.add(mapSmallBoard(smallBoard));
        }

        return game;
    }

    private static DatabaseSmallBoardModel mapSmallBoard(SingleBoardViewModel model)
    {
        DatabaseSmallBoardModel smallBoard = new DatabaseSmallBoardModel();
        for (FieldViewModel field : model.fields)
        {
            smallBoard.fields.add(mapField(field));
        }

        return smallBoard;
    }

    private static MarkerType mapField(FieldViewModel field)
    {
        return field.marker.get();
    }
}
