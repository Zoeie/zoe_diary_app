package com.zoe.diary.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.entity.ThirdPart;
import com.zoe.diary.net.request.user.third.ThirdContract;
import com.zoe.diary.net.request.user.third.ThirdPresenter;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.net.response.base.BaseResponse;
import com.zoe.diary.net.response.info.UserInfo;
import com.zoe.diary.notify.DataObservable;
import com.zoe.diary.ui.fragment.base.BaseMVPFragment;
import com.zoe.diary.utils.SharePreferencesUtil;
import com.zoe.diary.utils.ToastUtils;
import com.zoe.diary.utils.ValidateUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author zoe
 * created 2019/12/18 16:43
 */

public class BindAccountFragment extends BaseMVPFragment<ThirdPresenter, ThirdContract.IView> implements ThirdContract.IView {

    @BindView(R.id.et_input_account)
    EditText etAccount;

    @BindView(R.id.et_input_code)
    EditText etCode;

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

    @OnClick(R.id.tv_send_code)
    public void onSendCode() {
        String account = etAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showDebug("邮箱不能为空。");
            return;
        }
        if (!ValidateUtil.validateEmail(account)) {
            ToastUtils.showDebug("邮箱不合法");
            return;
        }
        //发送验证码
        mPresenter.sendCode(account);
    }

    @OnClick(R.id.btn_bind_account)
    public void bindAccount() {
        String account = etAccount.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showDebug("邮箱不能为空。");
            return;
        }
        if (!ValidateUtil.validateEmail(account)) {
            ToastUtils.showDebug("邮箱不合法");
            return;
        }
        String code = etCode.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showDebug("验证码不能为空");
            return;
        }
        if (!TextUtils.isDigitsOnly(code) || code.length() != 6) {
            ToastUtils.showDebug("验证码不合法");
            return;
        }
        if (part == null) {
            ToastUtils.showDebug("无第三方数据");
            return;
        }
        mPresenter.register(account, part.getUserId(), part.getType(), part.getUserName(), "", code);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_diary_bind_username;
    }

    public void setPart(ThirdPart part) {
        this.part = part;
    }

    @Override
    public void onSendSuccess(BaseResponse response) {
        ToastUtils.showDebug("发送验证码成功");
    }

    @Override
    public void onRegisterSuccess(UserInfoResponse response) {
        UserInfo data = response.data;
        if (data.getStatus() == 0) {
            ToastUtils.showDebug("绑定成功");
            DataObservable.getInstance().setData(Constants.MSG.NOTIFY_LOGIN_SUCCESS);
            if (getActivity() != null) {
                SharePreferencesUtil.putString(getActivity(), Constants.KEY.USER_ID, response.data.userId);
                getActivity().finish();
            }
        } else {
            ToastUtils.showDebug(data.getMsg());
        }
    }

    @Override
    public void onRegisterFailed() {

    }

    @Override
    protected ThirdPresenter createPresenter() {
        return new ThirdPresenter();
    }
}
