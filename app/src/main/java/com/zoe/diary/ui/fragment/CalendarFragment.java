package com.zoe.diary.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.database.DbManager;
import com.zoe.diary.database.domain.DiaryColor;
import com.zoe.diary.database.domain.DiaryInfo;
import com.zoe.diary.ui.activity.DiaryEditActivity;
import com.zoe.diary.ui.activity.DiaryMonthActivity;
import com.zoe.diary.ui.adapter.DayAdapter;
import com.zoe.diary.ui.dialog.DiaryColorBottomDialog;
import com.zoe.diary.ui.fragment.base.BaseFragment;
import com.zoe.diary.utils.DateUtil;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author zoe
 * created 2019/12/6 16:43
 */

public class CalendarFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rv_date)
    RecyclerView rvDate;

    @BindView(R.id.tv_month_number)
    TextView tvMonthNumber;

    @BindView(R.id.tv_month_english)
    TextView tvMonthEnglish;

    @BindView(R.id.rl_solid_bg)
    RelativeLayout rlSolidBg;

    @BindView(R.id.rl_calendar)
    RelativeLayout rlCalendar;

    @BindView(R.id.card_view)
    CardView cardView;

    @BindView(R.id.tv_month_en_in_solid)
    TextView tvMonthEnInSolid;

    @BindView(R.id.tv_month_num_in_solid)
    TextView tvMonthNumInSolid;

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.tv_write_all_days_count)
    TextView tvWriteAllDaysCount;

    private boolean showCalendar = true;
    private boolean isExecuteAnim = false;
    private static final String KEY_YEAR = "YEAR";
    private static final String KEY_MONTH = "MONTH";
    private int year;
    private int month;
    private int width;
    private int height;
    private boolean doHalfAlready = false;
    private List<Integer> dayList = new ArrayList<>();
    private DayAdapter dayAdapter;

    public static CalendarFragment getInstance(int year, int month) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_YEAR, year);
        bundle.putInt(KEY_MONTH, month);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_calendar;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            year = arguments.getInt(KEY_YEAR);
            month = arguments.getInt(KEY_MONTH);
        }
        computerDayList();
        dayAdapter = new DayAdapter(dayList, getActivity(), year, month);
        dayAdapter.setOnItemChildClickListener(this);
        rvDate.setAdapter(dayAdapter);
    }

    private void computerDayList() {
        dayList.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int offsetDay = (dayOfWeek - 1) % Calendar.DAY_OF_WEEK;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        dayList.addAll(getDayData(offsetDay, daysInMonth));
    }

    private List<Integer> getDayData(int offsetDay, int daysInMonth) {
        List<Integer> dayList = new ArrayList<>();
        int count = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < Calendar.DAY_OF_WEEK; j++) {
                int day;
                int realNumber = count - offsetDay;
                if (realNumber < 0) {
                    day = 0;
                } else if (daysInMonth - realNumber < 0) {
                    day = 0;
                } else {
                    day = realNumber;
                }
                dayList.add(day);
                count++;
            }
        }
        return dayList;
    }

    private void initView() {
        resetUI();
        rlCalendar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = rlCalendar.getWidth();
                height = rlCalendar.getHeight();
                CardView.LayoutParams params = (CardView.LayoutParams) rlSolidBg.getLayoutParams();
                params.width = width;
                params.height = height;
                rlSolidBg.setLayoutParams(params);
                rlCalendar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                rlSolidBg.setRotationY(180); //默认先旋转180度
            }
        });
    }

    //设置纯色背景的数据
    private void setSolidBgData() {
        List<DiaryInfo> diaryByMonth = DbManager.getInstance().getDiaryByMonth(year, month);
        HashSet<DiaryInfo> hashSet = new HashSet<>(diaryByMonth);
        pb.setProgress(hashSet.size());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        pb.setMax(daysInMonth);
        tvWriteAllDaysCount.setText(String.format(Locale.getDefault(), "%d/%d", hashSet.size(), daysInMonth));
    }

    private void updateDayDiaryDate() {
        if(dayAdapter != null) {
            dayAdapter.notifyDayDiary(year, month);
        }
    }

    private void setMonthUI() {
        //月份从0开始的
        tvMonthNumber.setText(String.valueOf(month + 1));
        tvMonthEnglish.setText(DateUtil.convertNumberToEnDesc(month));
        tvMonthEnInSolid.setText(DateUtil.convertNumberToEnDesc(month));
        tvMonthNumInSolid.setText(String.valueOf(month + 1));
    }

    public void setYear(int year) {
        this.year = year;
    }

    //当日历年份切换时
    public void notifyUpdateUI() {
        resetUI();
        computerDayList();
        updateDayDiaryDate();
    }

    private void resetUI() {
        setMonthUI();
        setSolidBgData();
        updateSolidBg();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int day = (int) view.getTag();
        Intent intent = new Intent(getActivity(), DiaryEditActivity.class);
        intent.putExtra(DiaryEditActivity.KEY_YEAR, year);
        intent.putExtra(DiaryEditActivity.KEY_MONTH, month);
        intent.putExtra(DiaryEditActivity.KEY_DAY, day);
        startActivity(intent);
    }

    public void doAnim() {
        if (isExecuteAnim) return;
        int start = showCalendar ? 0 : 180;
        int end = showCalendar ? 180 : 0;
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(rootView, "rotationY", start, end);
        rotateAnimator.setInterpolator(new OvershootInterpolator());
        rootView.setPivotX(width / 2);
        rootView.setPivotY(height / 2);
        rotateAnimator.setDuration(1000);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (showCalendar && value > 90 && !doHalfAlready) {
                    rlCalendar.setVisibility(View.INVISIBLE);
                    rlSolidBg.setVisibility(View.VISIBLE);
                    doHalfAlready = true;
                }
                if (!showCalendar && value < 90 && !doHalfAlready) {
                    rlCalendar.setVisibility(View.VISIBLE);
                    rlSolidBg.setVisibility(View.INVISIBLE);
                    doHalfAlready = true;
                }
            }
        });

        rotateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                isExecuteAnim = true;
                doHalfAlready = false;
            }

            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                showCalendar = !showCalendar;
                isExecuteAnim = false;
            }
        });
        rotateAnimator.start();
    }

    @OnClick(R.id.rl_solid_bg)
    public void onSolidBgClick() {
        LogUtil.d("onSolidBgClick");
        Intent intent = new Intent(getActivity(), DiaryMonthActivity.class);
        intent.putExtra(DiaryMonthActivity.KEY_YEAR, year);
        intent.putExtra(DiaryMonthActivity.KEY_MONTH, month);
        startActivity(intent);
    }

    @OnClick(R.id.ll_show_color_dialog)
    public void showColorDialog() {
        LogUtil.d("showColorDialog");
        DiaryColorBottomDialog diaryColorBottomDialog = new DiaryColorBottomDialog(getActivity(), rlSolidBg.getWidth(), rlSolidBg.getHeight());
        diaryColorBottomDialog.setOnColorSelectedListener(new DiaryColorBottomDialog.OnColorSelectedListener() {
            @Override
            public void colorSelect(int pos, String color) {
                DiaryColor diaryColorByDate = DbManager.getInstance().getDiaryColorByDate(year, month);
                if (diaryColorByDate == null) {
                    DiaryColor diaryColor = new DiaryColor(year, month, Constants.COLOR_TYPE.COLOR_SOLID, "", color);
                    DbManager.getInstance().insertDiaryColor(diaryColor);
                    updateSolidBg();
                } else {
                    diaryColorByDate.setColor(color);
                    diaryColorByDate.setColorType(Constants.COLOR_TYPE.COLOR_SOLID);
                    DbManager.getInstance().insertDiaryColor(diaryColorByDate);
                    updateSolidBg();
                    LogUtil.d("diaryColor:" + diaryColorByDate.getId() + ",color:" + diaryColorByDate.getColor());
                }
            }
        });
        diaryColorBottomDialog.show();
    }

    public void updateSolidBg() {
        DiaryColor diaryColorByDate = DbManager.getInstance().getDiaryColorByDate(year, month);
        if (diaryColorByDate != null) {
            if (diaryColorByDate.colorType == Constants.COLOR_TYPE.COLOR_PIC) {
                Bitmap bitmap = BitmapFactory.decodeFile(diaryColorByDate.imgPath);
                rlSolidBg.setBackground(new BitmapDrawable(bitmap));
            } else if (diaryColorByDate.colorType == Constants.COLOR_TYPE.COLOR_SOLID) {
                rlSolidBg.setBackgroundColor(Color.parseColor(diaryColorByDate.getColor()));
            }
        } else {
            rlSolidBg.setBackgroundResource(R.color.colorAccent);
        }
    }

    //当添加新的日历时
    public void updateDiaryShowUI() {
        setSolidBgData();
        updateDayDiaryDate();
    }
}
