package com.zoe.diary.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.entity.ThirdPart;
import com.zoe.diary.ui.fragment.base.BaseFragment;

/**
 * author zoe
 * created 2019/12/18 16:43
 */

public class BindAccountFragment extends BaseFragment {

    private ThirdPart part;

    public static BindAccountFragment getInstance() {
        BindAccountFragment fragment = new BindAccountFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_diary_bind_username;
    }

    public void setPart(ThirdPart part) {
        this.part = part;
    }
}
