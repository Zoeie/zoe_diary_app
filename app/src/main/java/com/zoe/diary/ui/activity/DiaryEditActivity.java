package com.zoe.diary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.database.DbManager;
import com.zoe.diary.database.domain.DiaryInfo;
import com.zoe.diary.net.request.save.SaveContract;
import com.zoe.diary.net.request.save.SavePresenter;
import com.zoe.diary.net.response.DiarySaveResponse;
import com.zoe.diary.notify.DataObservable;
import com.zoe.diary.ui.activity.base.BaseMVPActivity;
import com.zoe.diary.ui.adapter.DiaryImgAdapter;
import com.zoe.diary.ui.dialog.DiaryEditBottomDialog;
import com.zoe.diary.ui.widget.GlideEngine;
import com.zoe.diary.utils.DateUtil;
import com.zoe.diary.utils.DisplayUtil;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DiaryEditActivity extends BaseMVPActivity<SavePresenter, SaveContract.IView>
        implements SaveContract.IView, ViewPager.OnPageChangeListener {

    @BindView(R.id.img_view_pager)
    ViewPager imgViewPager;

    @BindView(R.id.tv_local_date)
    TextView tvLocalDate;

    @BindView(R.id.et_input_title)
    EditText etInputTitle;

    @BindView(R.id.et_input_content)
    EditText etInputContent;

    @BindView(R.id.iv_smile)
    ImageView ivSmile;

    @BindView(R.id.iv_weather)
    ImageView ivWeather;

    @BindView(R.id.rl_delete_img)
    RelativeLayout rlDelete;

    @BindView(R.id.rl_modify_overlay)
    RelativeLayout rlOverlay;

    @BindView(R.id.ll_circle_indicator)
    LinearLayout llCircleIndicator;

    //emoji_line
    public static final int[] moodArr = new int[]{
            R.mipmap.emoji_1, R.mipmap.emoji_2, R.mipmap.emoji_3, R.mipmap.emoji_4,
            R.mipmap.emoji_5, R.mipmap.emoji_6, R.mipmap.emoji_7, R.mipmap.emoji_8,
            R.mipmap.emoji_9, R.mipmap.emoji_10, R.mipmap.emoji_11, R.mipmap.emoji_12,
    };

    //weather
    public static final int[] weatherArr = new int[]{
            R.mipmap.weather_1, R.mipmap.weather_2, R.mipmap.weather_3, R.mipmap.weather_4,
            R.mipmap.weather_5, R.mipmap.weather_6, R.mipmap.weather_7, R.mipmap.weather_8,
            R.mipmap.weather_9, R.mipmap.weather_10, R.mipmap.weather_11, R.mipmap.weather_12,
            R.mipmap.weather_13, R.mipmap.weather_14, R.mipmap.weather_15, R.mipmap.weather_16,
            R.mipmap.weather_17, R.mipmap.weather_18, R.mipmap.weather_19, R.mipmap.weather_20
    };

    DiaryImgAdapter diaryImgAdapter;
    private static final int MAX_NUM = 9;
    private List<String> imgList = new ArrayList<>();
    public static final String KEY_YEAR = "YEAR";
    public static final String KEY_MONTH = "MONTH";
    public static final String KEY_DAY = "DAY";
    public static final String KEY_ID = "DIARY_ID";
    private int year;
    private int month;
    private int day;
    private int mood = Constants.MOOD_TYPE.MOOD_3;
    private int weather = Constants.WEATHER_TYPE.WEATHER_3;
    private int dayOfWeek;
    private int diaryId;
    private int selectPicPos = 0;
    private static final int REQUEST_CODE = 88;

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
        diaryId = intent.getIntExtra(KEY_ID, 0);
        //表明，在数据库查找，进行更新
        if (diaryId > 0) {
            DiaryInfo diaryInfo = DbManager.getInstance().getDiaryById(diaryId);
            year = diaryInfo.getYear();
            month = diaryInfo.getMonth();
            day = diaryInfo.getDay();
            etInputTitle.setText(diaryInfo.getTitle());
            etInputContent.setText(diaryInfo.getContent());
            if (diaryInfo.getImg() != null && diaryInfo.getImg().size() > 0) {
                imgList.addAll(diaryInfo.getImg());
            }
            mood = diaryInfo.getMood();
            weather = diaryInfo.getWeather();
        }
    }

    private void initView() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String builder = DateUtil.convertNumberToWeek(dayOfWeek) +
                "." +
                (month + 1) +
                "月" +
                day +
                "/" +
                year;
        tvLocalDate.setText(builder);
        LogUtil.d("dayOfWeek:" + dayOfWeek);
        setIcon();
        notifyImg();
    }

    private void setIcon() {
        ivSmile.setImageResource(moodArr[mood]);
        ivWeather.setImageResource(weatherArr[weather]);
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
        String title = etInputTitle.getText().toString();
        long createTime = calendar.getTimeInMillis();
        int mood = this.mood;
        int weather = this.weather;
        List<String> tag = new ArrayList<>();
        tag.add("自定义标签1");
        List<String> img = this.imgList;
        String content = etInputContent.getText().toString();
        String location = "海岸城";
        int year = this.year;
        int month = this.month;
        int day = this.day;
        int dayOfWeek = this.dayOfWeek;
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        //保存
        DiaryInfo diaryInfo = new DiaryInfo();
        diaryInfo.setTitle(title);
        diaryInfo.setCreateTime(createTime);
        diaryInfo.setMood(mood);
        diaryInfo.setWeather(weather);
        diaryInfo.setTag(tag);
        diaryInfo.setImg(img);
        diaryInfo.setContent(content);
        diaryInfo.setLocation(location);
        diaryInfo.setYear(year);
        diaryInfo.setMonth(month);
        diaryInfo.setDay(day);
        diaryInfo.setHour(hour);
        diaryInfo.setMinute(minute);
        diaryInfo.setSecond(second);
        diaryInfo.setDayOfWeek(dayOfWeek);
        if(diaryId > 0) {
            diaryInfo.setId(diaryId);
            DbManager.getInstance().updateDiaryInfo(diaryInfo);
            setResult(RESULT_OK);
        } else {
            DbManager.getInstance().insertDiaryInfo(diaryInfo);
            DataObservable.getInstance().setData(Constants.MSG.NEW_DIARY_NOTIFY);
        }
        LogUtil.d(diaryInfo.toString());
        finish();
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

    @OnClick(R.id.rl_delete_img)
    public void deleteImg() {
        imgList.remove(selectPicPos);
        notifyImg();
    }

    @OnClick(R.id.rl_modify_overlay)
    public void modifyOverlay() {
        Intent intent = new Intent(this, DiaryImgSortActivity.class);
        intent.putStringArrayListExtra(DiaryImgSortActivity.KEY_IMG_LIST, (ArrayList<String>) imgList);
        startActivityForResult(intent, REQUEST_CODE);
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
                case REQUEST_CODE:
                    ArrayList<String> listExtra = data.getStringArrayListExtra(DiaryImgSortActivity.KEY_IMG_LIST);
                    if(listExtra != null) {
                        imgList.clear();
                        imgList.addAll(listExtra);
                        notifyImg();
                    }
                    break;
            }
        }
    }

    private void notifyImg() {
        addCircleIndicator();
        updateCircleIndicator();
        rlDelete.setVisibility(imgList.size() > 0 ? View.VISIBLE : View.GONE);
        rlOverlay.setVisibility(imgList.size() > 0 ? View.VISIBLE : View.GONE);
        if (diaryImgAdapter == null) {
            diaryImgAdapter = new DiaryImgAdapter(this, imgList);
            imgViewPager.setAdapter(diaryImgAdapter);
            imgViewPager.addOnPageChangeListener(this);
        } else {
            diaryImgAdapter.notifyDataSetChanged();
        }
    }

    private void addCircleIndicator() {
        llCircleIndicator.removeAllViews();
        llCircleIndicator.setVisibility(imgList.size() > 1 ? View.VISIBLE : View.GONE);
        for (int i = 0; i < imgList.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(this, 6),
                    DisplayUtil.dip2px(this, 6));
            params.setMarginStart(DisplayUtil.dip2px(this, 10));
            View circleView = View.inflate(this, R.layout.circle_view, null);
            llCircleIndicator.addView(circleView, params);
        }
    }

    private void updateCircleIndicator() {
        for (int i = 0; i < llCircleIndicator.getChildCount(); i++) {
            llCircleIndicator.getChildAt(i).setBackgroundResource(((i == selectPicPos) ? R.drawable.circle_selected_bg:
                    R.drawable.circle_normal_bg));
        }
    }

    @OnClick({R.id.iv_smile, R.id.iv_weather, R.id.iv_heart, R.id.iv_tag, R.id.iv_location})
    public void smileClick() {
        DiaryEditBottomDialog dialogView = new DiaryEditBottomDialog(this, weather, mood);
        dialogView.setOnDialogItemClickListener(new DiaryEditBottomDialog.OnDialogItemClickListener() {
            @Override
            public void onItemClick(int type, int pos) {
                if (type == Constants.TYPE.MOOD) {
                    mood = pos;
                } else if (type == Constants.TYPE.WEATHER) {
                    weather = pos;
                }
                setIcon();
            }
        });
        dialogView.show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPicPos = position;
        updateCircleIndicator();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
