package com.kutapps.tictactoe9.board.viewmodels;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Pair;

import com.annimon.stream.Stream;
import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.board.consts.WinnerType;
import com.kutapps.tictactoe9.shared.commands.Command;

public class BoardViewModel
{
    public static final int ALL_BOARDS = Integer.MIN_VALUE;

    public final  SingleBoardViewModel[]      boards;
    private final ObservableField<MarkerType> currentMarker;
    public final  ObservableField<WinnerType> winner;
    public final  ObservableInt               nextBoardNumber;

    public Command<Pair<Integer, Integer>, Void> moveCommand;
    public Command<Void, Void>                   clearCommand;
    public Command<Void, Void>                   randomizeCommand;

    {
        boards = new SingleBoardViewModel[9];
        currentMarker = new ObservableField<>(MarkerType.Cross);
        winner = new ObservableField<>(WinnerType.NoneYet);
        nextBoardNumber = new ObservableInt(ALL_BOARDS);
    }


    public BoardViewModel()
    {
        for (int i = 0; i < boards.length; i++)
        {
            boards[i] = new SingleBoardViewModel();
        }
        initCommands();
    }

    private void initCommands()
    {
        moveCommand = new Command<>(this::move);
        clearCommand = new Command<>(this::clear);
        randomizeCommand = new Command<>(this::randomize);
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
}
