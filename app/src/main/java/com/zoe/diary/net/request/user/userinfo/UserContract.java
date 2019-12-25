package com.zoe.diary.net.request.user.userinfo;

import com.zoe.diary.net.contract.IContract;
import com.zoe.diary.net.response.UserInfoResponse;

/**
 * author zoe
 * created 2019/6/20 9:22
 */

public interface UserContract {

    interface IPresenter extends IContract.IPresenter<UserContract.IView> {
        void getUserInfo(String id);
    }

    interface IView extends IContract.IView {
        void onUserInfo(UserInfoResponse response);
    }
}
