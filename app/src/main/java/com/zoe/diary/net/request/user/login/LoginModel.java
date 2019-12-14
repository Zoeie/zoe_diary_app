package com.zoe.diary.net.request.user.login;

import com.zoe.diary.net.base.DiaryBaseModel;
import com.zoe.diary.net.response.UserInfoResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * author zoe
 * created 2019/11/11 10:44
 */

public class LoginModel extends DiaryBaseModel {

    public Observable<UserInfoResponse> login(String userName, String password) {
        return doRxRequest().login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
