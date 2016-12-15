package com.kutapps.tictactoe9.shared.commands;

public interface CommandHandler<TParam, TResult>
{
    TResult execute(TParam param);
}
