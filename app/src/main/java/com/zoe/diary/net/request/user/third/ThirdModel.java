package com.zoe.diary.net.request.user.third;

import com.zoe.diary.net.base.DiaryBaseModel;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.net.response.base.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * author zoe
 * created 2019/11/11 10:44
 */

public class ThirdModel extends DiaryBaseModel {

    public Observable<BaseResponse> sendCode(String userName) {
        return doRxRequest().sendCode(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<UserInfoResponse> loginByThird(String userName, String thirdKey, int thirdType,
                                                     String nickName, String headPortrait,String code) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("thirdKey", thirdKey);
        map.put("thirdType", String.valueOf(thirdType));
        map.put("nickName", nickName);
        map.put("headPortrait", headPortrait);
        map.put("code", code);
        return doRxRequest().loginByThird(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
