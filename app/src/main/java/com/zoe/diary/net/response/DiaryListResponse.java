package com.zoe.diary.net.response;

import com.zoe.diary.net.response.base.BaseResponse;
import com.zoe.diary.net.response.info.DiaryInfo;

import java.util.List;

/**
 * author zoe
 * created 2019/12/5 14:39
 */

public class DiaryListResponse extends BaseResponse {
    public List<DiaryInfo> data;
}
