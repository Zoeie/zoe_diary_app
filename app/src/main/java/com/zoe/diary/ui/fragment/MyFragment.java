package com.zoe.diary.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.net.response.info.UserInfo;
import com.zoe.diary.ui.activity.DiaryLoginActivity;
import com.zoe.diary.ui.fragment.base.BaseFragment;
import com.zoe.diary.ui.activity.DiaryUserActivity;
import com.zoe.diary.utils.LogUtil;
import com.zoe.diary.utils.SharePreferencesUtil;

import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;
import skin.support.SkinCompatManager;

public class MyFragment extends BaseFragment {

    private static final String KEY_TAG = "KEY_TAG";
    private String tag = "";
    private boolean flag = true;

    @BindView(R.id.tv_login_subtitle)
    TextView tvLoginSubtitle;

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
        String userId = SharePreferencesUtil.getString(getActivity(), Constants.KEY.USER_ID, "");
        tvLoginSubtitle.setText(TextUtils.isEmpty(userId) ? "未登录":"已登录");
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_my;
    }

    @OnClick(R.id.iv_user_icon)
    public void login() {
        String userId = SharePreferencesUtil.getString(getActivity(), Constants.KEY.USER_ID, "");
        LogUtil.d("login userId："+userId);
        if(TextUtils.isEmpty(userId)) {
            startActivity(new Intent(getActivity(), DiaryLoginActivity.class));
        } else {
            Intent intent = new Intent(getContext(), DiaryUserActivity.class);
            intent.putExtra(DiaryUserActivity.KEY_ID, userId);
            startActivity(intent);
        }
    }

    @OnClick(R.id.user_theme)
    public void switchTheme() {
        if(flag) {
            Toast.makeText(getActivity(), "夜间模式", Toast.LENGTH_SHORT).show();
            SkinCompatManager.getInstance().loadSkin("night.skin", SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS);
        } else {
            Toast.makeText(getActivity(), "日间模式", Toast.LENGTH_SHORT).show();
            SkinCompatManager.getInstance().restoreDefaultTheme();
        }
        flag = !flag;
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (data instanceof Integer) {
            int msg = (int) data;
            if (msg == Constants.MSG.NOTIFY_REGISTER_SUCCESS
                    || msg == Constants.MSG.NOTIFY_LOGIN_SUCCESS) {
                tvLoginSubtitle.setText("已登录");
            }
        }
    }

    @OnClick(R.id.btn_logout)
    public void logOut() {
        SharePreferencesUtil.putString(getActivity(), Constants.KEY.USER_ID,"");
        tvLoginSubtitle.setText("未登录");
    }
}
