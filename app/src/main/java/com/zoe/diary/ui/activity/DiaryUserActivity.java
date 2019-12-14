package com.zoe.diary.ui.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.ui.activity.base.BaseActivity;

public class DiaryUserActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_user;
    }
}
