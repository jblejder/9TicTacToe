package com.kutapps.tictactoe9.board.consts;

import android.support.annotation.DrawableRes;

import com.kutapps.tictactoe9.R;

public enum WinnerType
{
    NoneYet(0),
    Nought(R.drawable.ic_nought),
    Cross(R.drawable.ic_cross),
    Tie(0);

    @DrawableRes
    private final int icon;

    WinnerType(@DrawableRes int icon)
    {
        this.icon = icon;
    }

    @DrawableRes
    public int getIcon()
    {
        return icon;
    }
}
