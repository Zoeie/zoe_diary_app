package com.zoe.diary.net.base;

import com.zoe.diary.constant.WebConfig;
import com.zoe.diary.net.api.ApiService;
import com.zoe.diary.net.service.ApiManager;
import com.zoe.diary.net.service.OkHttpUtil;

/**
 * author zoe
 * created 2019/12/5 14:46
 */

public class DiaryBaseModel extends BaseModel<ApiService> {

    private ApiManager apiManager;

    @Override
    public ApiService doRxRequest() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager.retrofit(WebConfig.BASE_URL, OkHttpUtil.provideClient()).create(ApiService.class);
    }

    @Override
    public ApiService doRxRequest(String baseUrl) {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager.retrofit(baseUrl, OkHttpUtil.provideClient()).create(ApiService.class);
    }
}
