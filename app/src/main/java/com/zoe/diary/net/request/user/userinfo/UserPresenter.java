package com.zoe.diary.net.request.user.userinfo;

import com.zoe.diary.net.base.BasePresenter;
import com.zoe.diary.net.base.RxObserver;
import com.zoe.diary.net.response.UserInfoResponse;

/**
 * author zoe
 * created 2019/6/19 10:18
 */

public class UserPresenter extends BasePresenter<UserContract.IView> implements UserContract.IPresenter {

    private UserModel mModel = new UserModel();

    @Override
    public void getUserInfo(String id) {
        RxObserver<UserInfoResponse> observer = new RxObserver<UserInfoResponse>() {
            @Override
            protected void onSuccess(UserInfoResponse response) {
                if(response.data == null) return;
                if (getView() != null) {
                    getView().onUserInfo(response);
                }
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {

            }
        };
        addDisposable(observer);
        mModel.getInfo(id).subscribe(observer);
    }
}
