package com.kutapps.tictactoe9.shared.adapters.binding;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

public class BindableAdapter
{
    @BindingAdapter("android:src")
    public static void bindImage(ImageView view, @DrawableRes int drawable)
    {
        view.setImageResource(drawable);
    }

    @BindingAdapter(value = {"android:visibility", "bind:duration"}, requireAll = false)
    public static void bindVisibility(View view, boolean isVisible, Integer duration)
    {
        if (duration == null)
        {
            view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
        else
        {
            if (isVisible)
            {
                view.animate().alpha(1).setListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        view.setVisibility(View.VISIBLE);
                    }
                });
            }
            else
            {
                view.animate().alpha(0).setListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        view.setVisibility(View.GONE);
                    }
                });
            }
        }
    }
}
