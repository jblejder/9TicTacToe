package com.kutapps.tictactoe9.board.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Pair;

import com.annimon.stream.Stream;
import com.google.firebase.auth.FirebaseUser;
import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.board.consts.WinnerType;
import com.kutapps.tictactoe9.board.models.database.DatabaseGameModel;
import com.kutapps.tictactoe9.gameSetup.models.GameSetupModel;
import com.kutapps.tictactoe9.shared.commands.Command;

public class BoardViewModel
{
    public static final int ALL_BOARDS = Integer.MIN_VALUE;

    public final SingleBoardViewModel[]      boards;
    public final ObservableField<MarkerType> currentMarker;
    public final ObservableInt               nextBoardNumber;

    public final ObservableField<WinnerType> winner;

    public GameSetupModel gameSetup;
    public String         secondUserId;

    public Command<Pair<Integer, Integer>, Void> moveCommand;
    public Command<Void, Void>                   clearCommand;
    public Command<Void, Void>                   randomizeCommand;
    public ObservableField<FirebaseUser>         currentUser;
    public Command<DatabaseGameModel, Void>      loadStateCommand;

    {
        boards = new SingleBoardViewModel[9];
        currentMarker = new ObservableField<>();
        winner = new ObservableField<>(WinnerType.NoneYet);
        nextBoardNumber = new ObservableInt(ALL_BOARDS);
        currentUser = new ObservableField<>();
    }

    public BoardViewModel(GameSetupModel setup)
    {
        gameSetup = setup;
        for (int i = 0; i < boards.length; i++)
        {
            boards[i] = new SingleBoardViewModel();
        }

        MarkerType selected = setup.marker.get();
        if (selected == MarkerType.None)
        {
            selected = Math.random() > 0.5 ? MarkerType.Cross : MarkerType.Nought;
        }
        currentMarker.set(selected);

        initCommands();
    }

    private void initCommands()
    {
        moveCommand = new Command<>(this::move);
        clearCommand = new Command<>(this::clear);
        randomizeCommand = new Command<>(this::randomize);
        loadStateCommand = new Command<>(this::loadState);
    }

    private Void move(Pair<Integer, Integer> param)
    {
        if (winner.get() != WinnerType.NoneYet)
        {
            throw new IllegalStateException("Already won");
        }
        if (nextBoardNumber.get() != ALL_BOARDS && nextBoardNumber.get() != param.first)
        {
            throw new IllegalStateException("Wrong board");
        }

        SingleBoardViewModel board = boards[param.first];
        Integer field = param.second;

        MarkerType currentMarker = this.currentMarker.get();
        board.move(field, currentMarker);
        this.currentMarker.set(getNextMarker(currentMarker));
        winner.set(findWinner());
        nextBoardNumber.set(findNextMiniBoard(field));

        return null;
    }

    private Void clear(Void o)
    {
        Stream.of(boards).forEach(SingleBoardViewModel::clear);
        winner.set(findWinner());
        nextBoardNumber.set(ALL_BOARDS);

        return null;
    }

    private Void randomize(Void aVoid)
    {
        Stream.of(boards).forEach(SingleBoardViewModel::randomize);
        winner.set(findWinner());
        nextBoardNumber.set(ALL_BOARDS);
        return null;
    }

    private MarkerType getNextMarker(MarkerType currentMarker)
    {
        switch (currentMarker)
        {
            case None:
                return MarkerType.Cross;
            case Nought:
                return MarkerType.Cross;
            case Cross:
            default:
                return MarkerType.Nought;
        }
    }

    /**
     * Magic
     *
     * @return winner or MarkerType.NoneYet
     */
    private WinnerType findWinner()
    {
        for (int i = 0; i < 9; i += 3)
        {
            WinnerType horizontalWinner = getLineWinner(boards[i], boards[i + 1], boards[i + 2]);
            if (horizontalWinner != WinnerType.NoneYet)
            {
                return horizontalWinner;
            }
        }
        for (int i = 0; i < 3; i++)
        {
            WinnerType verticalWinner = getLineWinner(boards[i], boards[i + 3], boards[i + 6]);
            if (verticalWinner != WinnerType.NoneYet)
            {
                return verticalWinner;
            }
        }

        WinnerType diagonalWinner = getLineWinner(boards[0], boards[4], boards[8]);
        if (diagonalWinner != WinnerType.NoneYet)
        {
            return diagonalWinner;
        }
        WinnerType oppositeDiagonalWinner = getLineWinner(boards[2], boards[4], boards[6]);
        if (oppositeDiagonalWinner != WinnerType.NoneYet)
        {
            return oppositeDiagonalWinner;
        }

        if (Stream.of(boards).noneMatch(value -> value.winner.get() == WinnerType.NoneYet))
        {
            return WinnerType.Tie;
        }

        return WinnerType.NoneYet;
    }

    private WinnerType getLineWinner(SingleBoardViewModel... fields)
    {
        WinnerType lineWinner = fields[0].winner.get();
        if (Stream.of(fields).allMatch(value -> value.winner.get() == lineWinner))
        {
            return lineWinner;
        }
        else
        {
            return WinnerType.NoneYet;
        }
    }

    private int findNextMiniBoard(Integer lastSelected)
    {
        SingleBoardViewModel board = boards[lastSelected];
        if (board.winner.get() == WinnerType.NoneYet)
        {
            return lastSelected;
        }
        else
        {
            return ALL_BOARDS;
        }
    }

    private Void loadState(DatabaseGameModel param)
    {
        String userId = currentUser.get().getUid();
        if (param.hostId.equals(userId))
        {
            secondUserId = param.userId;
        }
        else if (param.userId != null && param.userId.equals(userId))
        {
            param.hostId = param.userId;
        }
        else
        {
            throw new IllegalStateException("User should not play in this game");
        }
        for (int i = 0; i < boards.length; i++)
        {
            boards[i].loadState(param.smallBoards.get(i));
        }
        winner.set(findWinner());
        //nextBoardNumber.set(ALL_BOARDS);

        return null;
    }
}
