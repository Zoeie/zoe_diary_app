package com.zoe.diary.net.api;

import com.zoe.diary.net.response.DiaryResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * author zoe
 * created 2019/12/5 14:03
 */

public interface ApiService {

    @GET("diary/list")
    Observable<DiaryResponse> list();

}
