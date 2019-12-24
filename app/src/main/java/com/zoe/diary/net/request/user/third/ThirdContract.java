package com.zoe.diary.net.request.user.third;

import com.zoe.diary.net.contract.IContract;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.net.response.base.BaseResponse;

/**
 * author zoe
 * created 2019/6/20 9:22
 */

public interface ThirdContract {

    interface IPresenter extends IContract.IPresenter<ThirdContract.IView> {
        void sendCode(String userName);
        void register(String userName, String thirdKey, int thirdType, String nickName, String headPortrait, String code);
    }

    interface IView extends IContract.IView {
        void onSendSuccess(BaseResponse response);
        void onRegisterSuccess(UserInfoResponse response);
        void onRegisterFailed();
    }
}
