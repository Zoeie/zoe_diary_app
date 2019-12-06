package com.zoe.diary.net.request.save;

import com.zoe.diary.net.base.DiaryBaseModel;
import com.zoe.diary.net.response.DiaryListResponse;
import com.zoe.diary.net.response.DiarySaveResponse;
import com.zoe.diary.utils.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * author zoe
 * created 2019/11/11 10:44
 */

public class SaveModel extends DiaryBaseModel {

    /**
     * "title": "日记标题222",
     * "content": "我是一个大橘猫",
     * "weather": 1,
     * "mood": 1,
     * "location": "海岸城",
     * "date": "2019-12-06 20:46:30",
     * "tag": "111,222,3333",
     * "img": "http://localhost:8080/imgs/1e62d71c11774f6fbd4c58e97b9f210a_00003.jpg"
     */
    public Observable<DiarySaveResponse> saveDiary(List<String> imgPaths, String title, String content,
                                                   int weather, int mood, String location, Date date, List<String> tags) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String fileName : imgPaths) {
            builder.addFormDataPart("imgFile",fileName,getRequestBody(new File(fileName)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0;i < tags.size();i++) {
            stringBuilder.append(tags.get(i));
            if(i < tags.size() -1) {
                stringBuilder.append(",");
            }
        }
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
        LogUtil.d("dateStr:"+dateStr+",stringBuilder:"+stringBuilder.toString());
        RequestBody body = builder
                .addFormDataPart("title", title)
                .addFormDataPart("content", content)
                .addFormDataPart("weather", String.valueOf(weather))
                .addFormDataPart("mood", String.valueOf(mood))
                .addFormDataPart("location", location)
                .addFormDataPart("date", dateStr)
                .addFormDataPart("tag", stringBuilder.toString())
                .build();
        return doRxRequest().save(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
