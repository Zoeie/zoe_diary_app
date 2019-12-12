package com.zoe.diary.ui.activity.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.SkinAppCompatDelegateImpl;

import com.zoe.diary.notify.DataObservable;

import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author zoe
 * created 2019/12/5 14:20
 */

public abstract class BaseActivity extends AppCompatActivity implements Observer {

    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(getLayoutResource());
        initialize();
    }

    private void initialize() {
        bind = ButterKnife.bind(this);
        DataObservable.getInstance().addObserver(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
    }

    protected abstract int getLayoutResource();

    @Override
    public void update(Observable o, Object arg) {}

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        return SkinAppCompatDelegateImpl.get(this, this);
    }
}
