package com.zoe.diary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.net.request.save.SaveContract;
import com.zoe.diary.net.request.save.SavePresenter;
import com.zoe.diary.net.response.DiarySaveResponse;
import com.zoe.diary.ui.activity.base.BaseMVPActivity;
import com.zoe.diary.ui.adapter.DiaryImgAdapter;
import com.zoe.diary.ui.dialog.BottomDialogView;
import com.zoe.diary.ui.widget.GlideEngine;
import com.zoe.diary.utils.DateUtil;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DiaryEditActivity extends BaseMVPActivity<SavePresenter, SaveContract.IView>
        implements SaveContract.IView {

    @BindView(R.id.img_view_pager)
    ViewPager imgViewPager;

    @BindView(R.id.tv_local_date)
    TextView tvLocalDate;

    @BindView(R.id.et_input_title)
    EditText etInputTitle;

    @BindView(R.id.et_input_content)
    EditText etInputContent;

    DiaryImgAdapter diaryImgAdapter;
    private static final int MAX_NUM = 9;
    private List<String> imgList = new ArrayList<>();
    private static final String KEY_YEAR = "YEAR";
    private static final String KEY_MONTH = "MONTH";
    private static final String KEY_DAY = "DAY";
    private int year;
    private int month;
    private int day;
    private int mood = Constants.MOOD_TYPE.MOOD_3;
    private int weather = Constants.WEATHER_TYPE.WEATHER_3;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_diary_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        year = intent.getIntExtra(KEY_YEAR, Calendar.getInstance().get(Calendar.YEAR));
        month = intent.getIntExtra(KEY_MONTH, Calendar.getInstance().get(Calendar.MONTH));
        day = intent.getIntExtra(KEY_DAY, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
    }

    private void initView() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        StringBuilder builder = new StringBuilder();
        builder.append(DateUtil.convertNumberToWeek(dayOfWeek))
                .append(month + 1)
                .append("月")
                .append(day)
                .append("/")
                .append(year);
        tvLocalDate.setText(builder.toString());
        LogUtil.d("dayOfWeek:" + dayOfWeek);
    }

    @Override
    public void onSaveDiarySuccess(DiarySaveResponse response) {
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        finish();
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = new Date(calendar.getTimeInMillis());
        String title = etInputTitle.getText().toString();
        String content = etInputContent.getText().toString();
        String location = "海岸城";
        List<String> tagList = new ArrayList<>();
        tagList.add("自定义标签1");
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

    @OnClick({R.id.tv_smile, R.id.tv_weather, R.id.tv_heart, R.id.tv_tag, R.id.tv_location})
    public void smileClick() {
        BottomDialogView dialogView = new BottomDialogView(this);
        dialogView.setOnDialogItemClickListener(new BottomDialogView.OnDialogItemClickListener() {
            @Override
            public void onItemClick(int type, int pos) {
                if (type == Constants.TYPE.MOOD) {
                    mood = pos + 1;
                } else if (type == Constants.TYPE.WEATHER) {
                    weather = pos + 1;
                }
            }
        });
        dialogView.show();
    }
}
