package com.zoe.diary.net.request.diary;

import com.zoe.diary.net.base.DiaryBaseModel;
import com.zoe.diary.net.response.DiaryResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * author zoe
 * created 2019/11/11 10:44
 */

public class DiaryModel extends DiaryBaseModel {

    public Observable<DiaryResponse> getAllDiary() {
        return doRxRequest().list()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
