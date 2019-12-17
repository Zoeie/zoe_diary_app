package com.zoe.diary.net.request.user.register;

import com.zoe.diary.net.contract.IContract;
import com.zoe.diary.net.response.UserInfoResponse;

/**
 * author zoe
 * created 2019/6/20 9:22
 */

public interface RegisterContract {

    interface IPresenter extends IContract.IPresenter<RegisterContract.IView> {
        void register(String userName, String password,String nickName);
    }

    interface IView extends IContract.IView {
        void onRegisterSuccess(UserInfoResponse response);

        void onRegisterFailed();
    }
}
