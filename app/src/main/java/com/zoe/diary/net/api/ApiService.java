package com.zoe.diary.net.api;

import com.zoe.diary.net.response.DiaryListResponse;
import com.zoe.diary.net.response.DiarySaveResponse;
import com.zoe.diary.net.response.UserInfoResponse;
import com.zoe.diary.net.response.base.BaseResponse;
import com.zoe.diary.net.response.info.UserInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("user/ref_ordinary")
    Observable<UserInfoResponse> registerOrdinary(@Field("userName") String userName,
                                                  @Field("password") String password,
                                                  @Field("nickName") String nickName);

    @FormUrlEncoded
    @POST("user/login_ordinary")
    Observable<UserInfoResponse> login(@Field("userName") String userName,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("user/login_third")
    Observable<UserInfoResponse> loginByThird(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("user/getCode")
    Observable<BaseResponse> sendCode(@Field("userName") String userName);

}
