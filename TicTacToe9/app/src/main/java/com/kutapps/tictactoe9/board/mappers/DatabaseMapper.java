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
        switch (model.gameMode)
        {
            case Host:
                game.host = model.currentUser.get();
                game.joiner = model.secondUser;
                break;
            case Join:
                game.host = model.secondUser;
                game.joiner = model.currentUser.get();
                break;
        }
        for (SingleBoardViewModel smallBoard : model.boards)
        {
            game.smallBoards.add(mapSmallBoard(smallBoard));
        }
        game.nextMarker = model.currentMarker.get();
        game.nextBoardNumber = model.nextBoardNumber.get();

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
