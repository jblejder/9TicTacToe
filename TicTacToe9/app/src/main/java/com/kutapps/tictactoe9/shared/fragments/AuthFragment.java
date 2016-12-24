package com.kutapps.tictactoe9.shared.fragments;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class AuthFragment<TBinding extends ViewDataBinding> extends BaseFragment<TBinding>
{
    private static final String TAG = AuthFragment.class.getSimpleName();

    private FirebaseAuth                   mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null)
            {
                onUserLogged(user);
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
            }
            else
            {
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
        };
        mAuth.signInAnonymously();
        super.onCreate(savedInstanceState);
    }

    protected abstract void onUserLogged(FirebaseUser user);

    @Override
    public void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mAuthListener != null)
        {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
