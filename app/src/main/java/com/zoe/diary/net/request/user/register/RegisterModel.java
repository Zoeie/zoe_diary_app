package com.zoe.diary.net.request.user.register;

import com.zoe.diary.net.base.DiaryBaseModel;
import com.zoe.diary.net.response.UserInfoResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * author zoe
 * created 2019/11/11 10:44
 */

public class RegisterModel extends DiaryBaseModel {

    public Observable<UserInfoResponse> register(String userName,
                                                 String password,
                                                 String nickName) {
        return doRxRequest().registerOrdinary(userName, password,nickName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
