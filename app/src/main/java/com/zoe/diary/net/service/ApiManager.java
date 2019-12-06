package com.zoe.diary.net.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private Retrofit retrofit;

    public ApiManager() {}

    /**
     * 创建唯一的Retrofit对象。
     *
     * @return retrofit
     */
    public Retrofit retrofit(String baseUrl) {
        if (retrofit == null) {
            retrofit = provideRetrofit(baseUrl);
        }
        return retrofit;
    }

    public Retrofit retrofit(String baseUrl, OkHttpClient client) {
        if (retrofit == null) {
            retrofit = provideRetrofit(baseUrl, client);
        }
        return retrofit;
    }

    /**
     * 创建一个新的Retrofit对象。
     *
     * @param baseUrl baseUrl
     * @return retrofit
     */
    public Retrofit provideRetrofit(String baseUrl) {
        return provideRetrofit(baseUrl, provideClient());
    }

    /**
     * 创建自定义OkHttpClient和BaseUrl的Retrofit对象。
     *
     * @param client  OkHttpClient
     * @param baseUrl baseUrl
     * @return retrofit
     */
    public Retrofit provideRetrofit(String baseUrl, OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient provideClient() {
        return new OkHttpClient.Builder().build();
    }

}
