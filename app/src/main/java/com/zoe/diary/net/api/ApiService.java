package com.zoe.diary.net.api;

import com.zoe.diary.net.response.DiaryListResponse;
import com.zoe.diary.net.response.DiarySaveResponse;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.net.response.base.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * author zoe
 * created 2019/12/5 14:03
 */

public interface ApiService {

    @GET("diary/list")
    Observable<DiaryListResponse> list();

    //文件上传和参数上传混合
    @POST("diary/save")
    Observable<DiarySaveResponse> save(@Body RequestBody body);

    @FormUrlEncoded
    @POST("user/reg_ordinary")
    Observable<UserInfoResponse> registerOrdinary(@Field("userName") String userName,
                                                  @Field("password") String password,
                                                  @Field("nickName") String nickName);

    @FormUrlEncoded
    @POST("user/login_ordinary")
    Observable<UserInfoResponse> login(@Field("userName") String userName,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("user/info")
    Observable<UserInfoResponse> getInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/login_third")
    Observable<UserInfoResponse> loginByThird(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("user/getCode")
    Observable<BaseResponse> sendCode(@Field("userName") String userName);

    @FormUrlEncoded
    @POST("user/isBind")
    Observable<UserInfoResponse> isBind(@Field("thirdKey") String thirdKey);

}
