package com.kutapps.tictactoe9.shared.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kutapps.tictactoe9.shared.fragments.callbacks.BaseFragmentCallback;

public abstract class BaseFragment<TBinding extends ViewDataBinding> extends Fragment
{
    protected TBinding             binding;
    protected BaseFragmentCallback callback;

    @Override
    public void onAttach(Activity activity)
    {
        callback = (BaseFragmentCallback) activity;
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context)
    {
        callback = (BaseFragmentCallback) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

        onCreateView(savedInstanceState);

        return binding.getRoot();
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void onCreateView(Bundle savedInstanceState);
}
