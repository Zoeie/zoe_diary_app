package com.zoe.diary.net.request.user.third;

import com.zoe.diary.net.base.BasePresenter;
import com.zoe.diary.net.base.RxObserver;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.net.response.base.BaseResponse;
import com.zoe.diary.utils.LogUtil;

/**
 * author zoe
 * created 2019/6/19 10:18
 */

public class ThirdPresenter extends BasePresenter<ThirdContract.IView> implements ThirdContract.IPresenter {

    private ThirdModel mModel = new ThirdModel();

    @Override
    public void sendCode(String userName) {
        RxObserver<BaseResponse> observer = new RxObserver<BaseResponse>() {
            @Override
            protected void onSuccess(BaseResponse response) {
                if (getView() != null) {
                    getView().onSendSuccess(response);
                }
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                LogUtil.e("send code failed!");
            }
        };
        addDisposable(observer);
        mModel.sendCode(userName).subscribe(observer);
    }

    @Override
    public void register(String userName, String thirdKey, int thirdType, String nickName, String headPortrait, String code) {
        RxObserver<UserInfoResponse> observer = new RxObserver<UserInfoResponse>() {
            @Override
            protected void onSuccess(UserInfoResponse response) {
                if (getView() != null) {
                    getView().onRegisterSuccess(response);
                }
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                getView().onRegisterFailed();
            }
        };
        addDisposable(observer);
        mModel.loginByThird(userName, thirdKey, thirdType, nickName, headPortrait, code).subscribe(observer);
    }
}
