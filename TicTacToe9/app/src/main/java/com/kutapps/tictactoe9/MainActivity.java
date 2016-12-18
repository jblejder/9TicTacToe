package com.kutapps.tictactoe9;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kutapps.tictactoe9.databinding.ActivityMainBinding;
import com.kutapps.tictactoe9.gameSetup.fragments.GameSetupFragment;
import com.kutapps.tictactoe9.shared.TransactionOptions;
import com.kutapps.tictactoe9.shared.fragments.BaseFragment;
import com.kutapps.tictactoe9.shared.fragments.callbacks.BaseFragmentCallback;
import com.kutapps.tictactoe9.shared.interfaces.OnBackPressedListener;

public class MainActivity extends AppCompatActivity implements BaseFragmentCallback
{
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null)
        {
            openFragment(GameSetupFragment.newInstance(), TransactionOptions.JustReplace);
        }
    }

    @Override
    public <TBinding extends ViewDataBinding> void openFragment(BaseFragment<TBinding> frag,
                                                                TransactionOptions options)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        switch (options)
        {
            case JustReplace:
                ft.replace(binding.container.getId(), frag, frag.getClass().getSimpleName());
                break;
            case AddToBackStack:
                ft.addToBackStack(null);
                ft.replace(binding.container.getId(), frag, frag.getClass().getSimpleName());
                break;
        }
        ft.commit();
    }

    @Override
    public void onBackPressed()
    {
        Fragment currentFragment = getCurrentFragment();
        boolean handled = false;
        if (currentFragment != null && currentFragment instanceof OnBackPressedListener)
        {
            handled = ((OnBackPressedListener) currentFragment).onBackPressed();
        }

        if (!handled)
        {
            super.onBackPressed();
        }
    }

    private Fragment getCurrentFragment()
    {
        return getFragmentManager().findFragmentById(binding.container.getId());
    }
}
