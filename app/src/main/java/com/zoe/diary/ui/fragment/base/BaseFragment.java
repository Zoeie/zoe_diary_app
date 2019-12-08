package com.zoe.diary.ui.fragment.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zoe.diary.notify.DataObservable;

import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;

/**
 * author zoe
 * created 2019/12/5 17:30
 */

public abstract class BaseFragment extends Fragment implements Observer {

    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DataObservable.getInstance().addObserver(this);
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
            ButterKnife.bind(this, rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected abstract int getLayoutResource();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataObservable.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
    }
}
