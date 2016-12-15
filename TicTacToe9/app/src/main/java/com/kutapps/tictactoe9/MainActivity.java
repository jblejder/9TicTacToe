package com.kutapps.tictactoe9;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kutapps.tictactoe9.board.fragments.BoardFragment;
import com.kutapps.tictactoe9.databinding.ActivityMainBinding;
import com.kutapps.tictactoe9.shared.TransactionOptions;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null)
        {
            loadFragment(BoardFragment.newInstance(), TransactionOptions.JustReplace);
        }
    }

    private void loadFragment(Fragment frag, TransactionOptions options)
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (options)
        {
            case JustReplace:
                ft.replace(binding.container.getId(), frag, frag.getClass().getSimpleName());
                break;
            case AddToBackStack:
                ft.addToBackStack(null);
                ft.add(binding.container.getId(), frag, frag.getClass().getSimpleName());
                break;
        }
        ft.commit();
    }
}
