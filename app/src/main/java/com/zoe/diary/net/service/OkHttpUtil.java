package com.zoe.diary.net.service;

import com.zoe.diary.net.interceptor.HeadInterceptor;
import com.zoe.diary.net.log.HttpLogger;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * author zoe
 * created 2019/11/7 10:24
 */

public class OkHttpUtil {

    private static OkHttpClient httpClient;

    public static OkHttpClient provideClient() {
        if (httpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLogger());
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(new HeadInterceptor()) //默认添加Head
                    .connectTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                    .readTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                    .build();
        }
        return httpClient;
    }
}
