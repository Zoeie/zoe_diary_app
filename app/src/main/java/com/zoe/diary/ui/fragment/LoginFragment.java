package com.zoe.diary.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.entity.ThirdPart;
import com.zoe.diary.net.request.user.login.LoginContract;
import com.zoe.diary.net.request.user.login.LoginPresenter;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.notify.DataObservable;
import com.zoe.diary.ui.activity.DiaryRegisterActivity;
import com.zoe.diary.ui.fragment.base.BaseMVPFragment;
import com.zoe.diary.utils.LogUtil;
import com.zoe.diary.utils.SharePreferencesUtil;
import com.zoe.diary.utils.ToastUtils;

import java.util.HashMap;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * author zoe
 * created 2019/12/18 16:17
 */

public class LoginFragment extends BaseMVPFragment<LoginPresenter, LoginContract.IView> implements LoginContract.IView {

    @BindView(R.id.et_input_account)
    EditText etAccount;

    @BindView(R.id.et_input_password)
    EditText etPassword;

    private OnGoToBindAccountListener onGoToBindAccountListener;

    public static LoginFragment getInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {}

    private void initView() {}

    @OnClick(R.id.tv_register)
    public void register() {
        startActivity(new Intent(getActivity(), DiaryRegisterActivity.class));
    }

    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg);
        if (arg instanceof Integer) {
            int value = (int) arg;
            if (value == Constants.MSG.NOTIFY_REGISTER_SUCCESS) {
                if(getActivity() != null) {
                    getActivity().finish();
                }
            }
        }
    }

    @OnClick(R.id.iv_qq)
    public void onQQClick() {
        loginWithThirdPart(QQ.NAME);
    }

    @OnClick(R.id.iv_we_chat)
    public void onWeChatClick() {
        loginWithThirdPart(Wechat.NAME);
    }

    private void loginWithThirdPart(String name) {
        Platform plat = ShareSDK.getPlatform(name);
        //移除授权状态和本地缓存，下次授权会重新授权
        plat.removeAccount(true);
        //SSO授权，传false默认是客户端授权
        plat.SSOSetting(false);
        //授权回调监听，监听oncomplete，onerror，oncancel三种状态
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String userId = platform.getDb().getUserId();
                String userIcon = platform.getDb().getUserIcon();
                long expiresTime = platform.getDb().getExpiresTime();
                String userName = platform.getDb().getUserName();
                String token = platform.getDb().getToken();
                LogUtil.d("xxx userId:" + userId);
                LogUtil.d("xxx userIcon:" + userIcon);
                LogUtil.d("xxx expiresTime:" + expiresTime);
                LogUtil.d("xxx userName:" + userName);
                LogUtil.d("xxx token:" + token);
                ThirdPart part = new ThirdPart(userId, userIcon, userName);
                if (onGoToBindAccountListener != null) {
                    onGoToBindAccountListener.gotoBindAccount(part);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                throwable.printStackTrace();
                LogUtil.d("onError i = " + i);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.d("onCancel i = " + i);
            }
        });
        //抖音登录适配安卓9.0
        //ShareSDK.setActivity(this);
        //要数据不要功能，主要体现在不会重复出现授权界面
        plat.showUser(null);
    }

    @OnClick(R.id.btn_login)
    public void login() {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            ToastUtils.showDebug("用户名和密码不能为空");
            return;
        }
        mPresenter.login(account, password);
    }

    @Override
    public void onLoginSuccess(UserInfoResponse response) {
        if (response.data != null) {
            DataObservable.getInstance().setData(Constants.MSG.NOTIFY_LOGIN_SUCCESS);
            if(getActivity() != null) {
                SharePreferencesUtil.putString(getActivity(), Constants.KEY.USER_ID, response.data.userId);
                getActivity().finish();
            }
        }
    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onLoginByThirdSuccess(UserInfoResponse response) {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dairy_login;
    }

    public void setOnGoToBindAccountListener(OnGoToBindAccountListener listener) {
        onGoToBindAccountListener = listener;
    }

    public interface OnGoToBindAccountListener {
        void gotoBindAccount(ThirdPart part);
    }
}
