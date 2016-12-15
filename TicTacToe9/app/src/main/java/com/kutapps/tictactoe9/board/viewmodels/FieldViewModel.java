package com.kutapps.tictactoe9.board.viewmodels;

import android.databinding.ObservableField;

import com.kutapps.tictactoe9.board.consts.MarkerType;

public class FieldViewModel
{
    public final ObservableField<MarkerType> marker;

    {
        marker = new ObservableField<>(MarkerType.None);
    }

    public void clear()
    {
        marker.set(MarkerType.None);
    }

    public void randomize()
    {
        marker.set(Math.random() > .5 ? MarkerType.None : Math.random() > .5 ? MarkerType.Cross :
                MarkerType.Nought);
    }
}
