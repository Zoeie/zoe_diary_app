package com.zoe.diary.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zoe.diary.R;
import com.zoe.diary.net.request.save.SaveContract;
import com.zoe.diary.net.request.save.SavePresenter;
import com.zoe.diary.net.response.DiarySaveResponse;
import com.zoe.diary.ui.activity.base.BaseMVPActivity;
import com.zoe.diary.ui.adapter.DiaryImgAdapter;
import com.zoe.diary.ui.widget.GlideEngine;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DiaryEditActivity extends BaseMVPActivity<SavePresenter, SaveContract.IView>
        implements SaveContract.IView {

    @BindView(R.id.img_view_pager)
    ViewPager imgViewPager;

    DiaryImgAdapter diaryImgAdapter;
    private static final int MAX_NUM = 9;
    private List<String> imgList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {}

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

    @OnClick(R.id.iv_save_diary)
    public void saveDiary() {
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
        mPresenter.saveDiary(imgList, title, content, weather, mood, location, date, tagList);
    }

    @OnClick(R.id.rl_add_img)
    public void addImg() {
        PictureSelector.create(DiaryEditActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(MAX_NUM - imgList.size())
                .imageSpanCount(4)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectList) {
                        imgList.add(media.getPath());
                    }
                    notifyImg();
                    break;
            }
        }
    }

    private void notifyImg() {
        if (diaryImgAdapter == null) {
            diaryImgAdapter = new DiaryImgAdapter(this, imgList);
            imgViewPager.setAdapter(diaryImgAdapter);
        } else {
            diaryImgAdapter.notifyDataSetChanged();
        }
    }
}
