package com.kutapps.tictactoe9.shared.fragments.callbacks;

import android.databinding.ViewDataBinding;

import com.kutapps.tictactoe9.shared.TransactionOptions;
import com.kutapps.tictactoe9.shared.fragments.BaseFragment;

public interface BaseFragmentCallback
{
    <TBinding extends ViewDataBinding> void openFragment(BaseFragment<TBinding> fragment, TransactionOptions justReplace);
}
