package com.zoe.diary.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.ui.activity.DiaryLoginActivity;
import com.zoe.diary.ui.fragment.base.BaseFragment;
import com.zoe.diary.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseFragment {

    private static final String KEY_TAG = "KEY_TAG";
    private String tag = "";

    public static MyFragment getInstance(String tag) {
        LogUtil.d("tag:" + tag);
        MyFragment myFragment = new MyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TAG, tag);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            tag = arguments.getString(KEY_TAG);
        }
    }

    private void initView() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.iv_user_icon)
    public void login() {
        startActivity(new Intent(getActivity(), DiaryLoginActivity.class));
    }
}
