package com.kutapps.tictactoe9.board.consts;

import android.support.annotation.DrawableRes;

import com.kutapps.tictactoe9.R;

public enum MarkerType
{
    None(0),
    Nought(R.drawable.ic_nought),
    Cross(R.drawable.ic_cross);

    @DrawableRes
    private final int icon;

    MarkerType(@DrawableRes int icon)
    {
        this.icon = icon;
    }

    @DrawableRes
    public int getIcon()
    {
        return icon;
    }

    public WinnerType getAsWinner()
    {
        switch (this)
        {
            case Nought:
                return WinnerType.Nought;
            case Cross:
                return WinnerType.Cross;
            case None:
            default:
                return WinnerType.NoneYet;
        }
    }
}
