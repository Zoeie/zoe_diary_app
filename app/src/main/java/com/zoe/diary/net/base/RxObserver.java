package com.zoe.diary.net.base;

import com.zoe.diary.net.response.base.BaseResponse;
import com.zoe.diary.utils.LogUtil;

/**
 * author zoe
 * created 2019/3/22 10:00
 */

public abstract class RxObserver<E extends BaseResponse> extends RxBaseObserver<BaseResponse> {

    //----------------后台约定错误----------------//
    public static final int REQUEST_SUCCESS = 1; //请求成功

    public RxObserver() {
        super();
    }

    @Override
    public void onNext(BaseResponse baseResponse) {
        if (baseResponse.status == REQUEST_SUCCESS) {
            onSuccess((E) baseResponse);
        } else {
            onError(baseResponse.status, baseResponse.msg);
        }
    }

    @Override
    protected void onError(int errorCode, String errorMsg) {
        LogUtil.e("errorCode:" + errorCode + ",errorMsg:" + errorMsg);
        onFail(errorCode, errorMsg);
    }

    protected abstract void onSuccess(E response);

    protected abstract void onFail(int errorCode, String errorMsg);

}
