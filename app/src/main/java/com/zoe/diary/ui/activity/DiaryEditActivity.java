package com.zoe.diary.ui.activity;

import android.os.Bundle;

import com.zoe.diary.R;
import com.zoe.diary.net.request.save.SaveContract;
import com.zoe.diary.net.request.save.SavePresenter;
import com.zoe.diary.net.response.DiarySaveResponse;
import com.zoe.diary.ui.activity.base.BaseMVPActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryEditActivity  extends BaseMVPActivity<SavePresenter, SaveContract.IView>
    implements SaveContract.IView {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        List<String> imgList = new ArrayList<>();
        imgList.add("/sdcard/1512718516224.jpg");
        String title = "日记title11";
        String content = "日记Content11";
        int weather = 1;
        int mood = 1;
        String location = "海岸城";
        Date date = new Date();
        List<String> tagList = new ArrayList<>();
        tagList.add("tag1");
        tagList.add("tag2");
        mPresenter.saveDiary(imgList,title,content,weather,mood,location,date,tagList);
    }

    @Override
    public void onSaveDiarySuccess(DiarySaveResponse response) {

    }

    @Override
    public void onSaveDiaryFailed() {

    }

    @Override
    protected SavePresenter createPresenter() {
        return new SavePresenter();
    }
}
