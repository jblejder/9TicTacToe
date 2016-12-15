package com.kutapps.tictactoe9.shared.fragments;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<TBinding extends ViewDataBinding> extends Fragment
{
    protected TBinding binding;

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
