package com.zoe.diary.net.request.user.login;

import com.zoe.diary.net.contract.IContract;
import com.zoe.diary.net.response.UserInfoResponse;

/**
 * author zoe
 * created 2019/6/20 9:22
 */

public interface LoginContract {

    interface IPresenter extends IContract.IPresenter<LoginContract.IView> {
        void login(String userName, String password);
    }

    interface IView extends IContract.IView {
        void onLoginSuccess(UserInfoResponse response);

        void onLoginFailed();
    }
}
