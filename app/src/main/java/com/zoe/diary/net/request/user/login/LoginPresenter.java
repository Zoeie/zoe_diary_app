package com.zoe.diary.net.request.user.login;

import com.zoe.diary.net.base.BasePresenter;
import com.zoe.diary.net.base.RxObserver;
import com.zoe.diary.net.request.user.register.RegisterContract;
import com.zoe.diary.net.request.user.register.RegisterModel;
import com.zoe.diary.net.response.UserInfoResponse;

/**
 * author zoe
 * created 2019/6/19 10:18
 */

public class LoginPresenter extends BasePresenter<LoginContract.IView> implements LoginContract.IPresenter {

    private LoginModel mModel = new LoginModel();

    @Override
    public void login(String userName, String password) {
        RxObserver<UserInfoResponse> observer = new RxObserver<UserInfoResponse>() {
            @Override
            protected void onSuccess(UserInfoResponse response) {
                if (getView() != null) {
                    getView().onLoginSuccess(response);
                }
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {

            }
        };
        addDisposable(observer);
        mModel.login(userName, password).subscribe(observer);
    }
}
