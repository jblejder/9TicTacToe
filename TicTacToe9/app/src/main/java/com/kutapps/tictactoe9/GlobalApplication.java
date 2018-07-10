package com.kutapps.tictactoe9;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class GlobalApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
