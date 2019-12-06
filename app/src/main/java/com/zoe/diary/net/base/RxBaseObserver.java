package com.zoe.diary.net.base;

import com.google.gson.JsonParseException;
import com.zoe.diary.R;
import com.zoe.diary.app.DiaryApplication;
import com.zoe.diary.utils.LogUtil;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Objects;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Observer基类
 * 注：用于解析后台返回数据"data"字段
 * 1.通过泛型，解析data数据。
 * 2.处理公共异常。
 * 3.显示或隐藏请求loading。
 */

public abstract class RxBaseObserver<T> extends DisposableObserver<T> {

    //----------------http类错误----------------------------------------
    private static final int CONNECT_ERROR = 1001;//连接错误,网络异常
    private static final int CONNECT_TIMEOUT = 1002;//连接超时
    private static final int PARSE_ERROR = 1003;//解析错误
    private static final int UNKNOWN_ERROR = 1004;//未知错误
    private static final int REQUEST_TIMEOUT = 1005;//请求超时

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onError(Throwable e) {
        dealException(e);
    }

    @Override
    public void onComplete() {
    }

    protected abstract void onError(int errorCode, String errorMsg);

    /**
     * 处理异常错误
     *
     * @param t
     */
    private void dealException(Throwable t) {
        if (t != null) {
            LogUtil.e("Request Error : " + t.getMessage());
        }
        if (t instanceof ConnectException || t instanceof UnknownHostException) {
            //连接错误
            onError(CONNECT_ERROR, DiaryApplication.getInstance().getString(R.string.common_connect_error));
        } else if (t instanceof InterruptedException) {
            //连接超时
            onError(CONNECT_TIMEOUT, DiaryApplication.getInstance().getString(R.string.common_connect_timeout));
        } else if (t instanceof JsonParseException || t instanceof JSONException
                || t instanceof ParseException) {
            //解析错误
            onError(PARSE_ERROR, DiaryApplication.getInstance().getString(R.string.common_parse_error));
        } else if (t instanceof SocketTimeoutException) {
            //请求超时
            onError(REQUEST_TIMEOUT, DiaryApplication.getInstance().getString(R.string.common_request_timeout));
        } else if (t instanceof HttpException) {
            onError(UNKNOWN_ERROR, DiaryApplication.getInstance().getString(R.string.common_net_error));
        } else if (t instanceof UnknownError) {
            //未知错误
            onError(UNKNOWN_ERROR, t.getMessage());
        } else {
            onError(UNKNOWN_ERROR, Objects.requireNonNull(t).getMessage());
        }
    }
}
