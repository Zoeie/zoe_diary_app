package com.zoe.diary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.zoe.diary.R;
import com.zoe.diary.net.request.user.userinfo.UserContract;
import com.zoe.diary.net.request.user.userinfo.UserPresenter;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.net.response.info.UserInfo;
import com.zoe.diary.ui.activity.base.BaseActivity;
import com.zoe.diary.ui.activity.base.BaseMVPActivity;
import com.zoe.diary.utils.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class DiaryUserActivity extends BaseMVPActivity<UserPresenter, UserContract.IView> implements UserContract.IView {

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

    public static final String KEY_ID = "USER_ID";
    private String id;

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra(KEY_ID);
        LogUtil.d("id=" + id);
    }

    private void initView() {
        mPresenter.getUserInfo(id);
    }

    @OnClick(R.id.iv_back)
    public void onBack() {
        finish();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_user;
    }

    @Override
    public void onUserInfo(UserInfoResponse response) {
        UserInfo data = response.data;
        if (!TextUtils.isEmpty(data.headPortrait)) {
            Glide.with(this).load(data.headPortrait).into(ivUserIcon);
        }
        tvUserId.setText(data.userName);
        tvUserNick.setText(data.nickName);
        tvBindQQ.setText(data.bindQQ ? "已绑定" : "未绑定");
        tvBindWeChat.setText(data.bindWeChat ? "已绑定" : "未绑定");
    }
}
