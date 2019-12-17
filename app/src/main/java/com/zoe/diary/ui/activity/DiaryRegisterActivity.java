package com.zoe.diary.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.net.request.diary.DiaryContract;
import com.zoe.diary.net.request.diary.DiaryPresenter;
import com.zoe.diary.net.request.user.register.RegisterContract;
import com.zoe.diary.net.request.user.register.RegisterPresenter;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.notify.DataObservable;
import com.zoe.diary.ui.activity.base.BaseActivity;
import com.zoe.diary.ui.activity.base.BaseMVPActivity;
import com.zoe.diary.utils.SharePreferencesUtil;
import com.zoe.diary.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class DiaryRegisterActivity extends BaseMVPActivity<RegisterPresenter, RegisterContract.IView> implements RegisterContract.IView {

    @BindView(R.id.et_input_account)
    EditText etAccount;

    @BindView(R.id.et_input_password)
    EditText etPassword;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

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

    @OnClick(R.id.btn_register)
    public void register() {
        String account = etAccount.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
            ToastUtils.showDebug("用户名和密码不能为空");
            return;
        }
        mPresenter.register(account, password, account);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_register;
    }

    @Override
    public void onRegisterSuccess(UserInfoResponse response) {
        if(response.data != null) {
            ToastUtils.showDebug("注册成功:" + response.data.userName);
            SharePreferencesUtil.putString(this, Constants.KEY.USER_ID, response.data.userId);
            DataObservable.getInstance().setData(Constants.MSG.NOTIFY_REGISTER_SUCCESS);
            finish();
        }
    }

    @Override
    public void onRegisterFailed() {

    }
}
