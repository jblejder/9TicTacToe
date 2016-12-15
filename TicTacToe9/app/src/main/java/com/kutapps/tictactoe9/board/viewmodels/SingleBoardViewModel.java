package com.kutapps.tictactoe9.board.viewmodels;

import android.databinding.ObservableField;

import com.annimon.stream.Stream;
import com.kutapps.tictactoe9.board.consts.MarkerType;
import com.kutapps.tictactoe9.board.consts.WinnerType;


public class SingleBoardViewModel
{
    public final FieldViewModel[]            fields;
    public final ObservableField<WinnerType> winner;

    {
        fields = new FieldViewModel[9];
        winner = new ObservableField<>(WinnerType.NoneYet);
    }

    public SingleBoardViewModel()
    {
        for (int i = 0; i < fields.length; i++)
        {
            fields[i] = new FieldViewModel();
        }
    }

    public Void clear()
    {
        Stream.of(fields).forEach(FieldViewModel::clear);
        winner.set(findWinner());
        return null;
    }

    public Void randomize()
    {
        Stream.of(fields).forEach(FieldViewModel::randomize);
        winner.set(findWinner());
        return null;
    }

    public Void move(Integer fieldIndex, MarkerType nextMarker)
    {
        if (winner.get() != WinnerType.NoneYet)
        {
            throw new IllegalStateException("Already won");
        }
        FieldViewModel field = fields[fieldIndex];
        MarkerType markerType = field.marker.get();
        if (markerType != MarkerType.None)
        {
            throw new IllegalStateException("Wrong state");
        }
        field.marker.set(nextMarker);
        winner.set(findWinner());

        return null;
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
            MarkerType horizontalWinner = getLineWinner(fields[i], fields[i + 1], fields[i + 2]);
            if (horizontalWinner != MarkerType.None)
            {
                return horizontalWinner.getAsWinner();
            }
        }
        for (int i = 0; i < 3; i++)
        {
            MarkerType verticalWinner = getLineWinner(fields[i], fields[i + 3], fields[i + 6]);
            if (verticalWinner != MarkerType.None)
            {
                return verticalWinner.getAsWinner();
            }
        }

        MarkerType diagonalWinner = getLineWinner(fields[0], fields[4], fields[8]);
        if (diagonalWinner != MarkerType.None)
        {
            return diagonalWinner.getAsWinner();
        }
        MarkerType oppositeDiagonalWinner = getLineWinner(fields[2], fields[4], fields[6]);
        if (oppositeDiagonalWinner != MarkerType.None)
        {
            return oppositeDiagonalWinner.getAsWinner();
        }

        if (Stream.of(fields).noneMatch(value -> value.marker.get() == MarkerType.None))
        {
            return WinnerType.Tie;
        }

        return WinnerType.NoneYet;
    }

    private MarkerType getLineWinner(FieldViewModel... fields)
    {
        MarkerType lineWinner = fields[0].marker.get();
        if (Stream.of(fields).allMatch(value -> value.marker.get() == lineWinner))
        {
            return lineWinner;
        }
        else
        {
            return MarkerType.None;
        }
    }
}
