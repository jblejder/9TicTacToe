package com.kutapps.tictactoe9.shared.commands;

import android.databinding.ObservableField;
import android.util.Log;

import org.jdeferred.Promise;
import org.jdeferred.android.DeferredAsyncTask;


public class Command<TParam, TResult>
{

    private CommandAction<TParam, TResult> command;
    public  ObservableField<CommandState>  isExecuting;

    {
        isExecuting = new ObservableField<>(CommandState.NotStarted);
    }


    public Command(CommandAction<TParam, TResult> command)
    {
        this.command = command;
    }

    public final Promise<TResult, Throwable, Integer> execute(TParam param)
    {
        isExecuting.set(CommandState.Executing);

        DeferredAsyncTask<TParam, Integer, TResult> task = new DeferredAsyncTask<TParam, Integer,
                TResult>()
        {
            @SafeVarargs
            @Override
            protected final TResult doInBackgroundSafe(TParam... params) throws Exception
            {
                return command.execute(params[0]);
            }
        };
        Promise<TResult, Throwable, Integer> promise = task.promise().done(result -> isExecuting
                .set(CommandState.Succeeded)).fail(result -> {
            Log.e("Command", result.getMessage(), result);
            isExecuting.set(CommandState.Error);
        });

        task.execute(param);

        return promise;
    }
}

