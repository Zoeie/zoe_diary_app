package com.zoe.diary.net.base;

import com.google.gson.Gson;
import com.zoe.diary.net.contract.IContract;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author zoe
 * created 2019/3/22 10:00
 */

public abstract class BaseModel<T> implements IContract.IModel<T> {

    public RequestBody getRequestBody(Object object) {
        String json = new Gson().toJson(object);
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }

    public RequestBody getRequestBody(String json) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
    }
}
