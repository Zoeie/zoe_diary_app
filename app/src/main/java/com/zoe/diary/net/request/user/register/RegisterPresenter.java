package com.zoe.diary.net.request.user.register;

import com.zoe.diary.net.base.BasePresenter;
import com.zoe.diary.net.base.RxObserver;
import com.zoe.diary.net.request.diary.DiaryContract;
import com.zoe.diary.net.request.diary.DiaryModel;
import com.zoe.diary.net.response.DiaryListResponse;
import com.zoe.diary.net.response.UserInfoResponse;

/**
 * author zoe
 * created 2019/6/19 10:18
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.IView> implements RegisterContract.IPresenter {

    private RegisterModel mModel = new RegisterModel();

    @Override
    public void register(String userName, String password,String nickName) {
        RxObserver<UserInfoResponse> observer = new RxObserver<UserInfoResponse>() {
            @Override
            protected void onSuccess(UserInfoResponse response) {
                if (getView() != null) {
                    getView().onRegisterSuccess(response);
                }
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {

            }
        };
        addDisposable(observer);
        mModel.register(userName, password, nickName).subscribe(observer);
    }
}
