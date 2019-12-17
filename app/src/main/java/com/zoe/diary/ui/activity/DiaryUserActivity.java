package com.zoe.diary.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.ui.activity.base.BaseActivity;

import butterknife.BindView;

public class DiaryUserActivity extends BaseActivity {

    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;

    @BindView(R.id.tv_user_id)
    TextView tvUserId;

    @BindView(R.id.tv_user_nick)
    TextView tvUserNick;

    @BindView(R.id.tv_bind_qq)
    TextView tvBindQQ;

    @BindView(R.id.tv_bind_wechat)
    TextView tvBindWeChat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {

    }

    private void initView() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_user;
    }
}
