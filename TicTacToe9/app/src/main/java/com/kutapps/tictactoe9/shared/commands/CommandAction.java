package com.kutapps.tictactoe9.shared.commands;

public interface CommandAction<TParam, TResult>
{
    TResult execute(TParam param) throws Exception;
}
