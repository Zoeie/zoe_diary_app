package com.zoe.diary.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.zoe.diary.R;
import com.zoe.diary.constant.Constants;
import com.zoe.diary.ui.adapter.DiaryAdapter;
import com.zoe.diary.ui.dialog.DiaryDateBottomDialog;
import com.zoe.diary.ui.fragment.base.BaseFragment;
import com.zoe.diary.ui.transform.DiaryPageTransformer;
import com.zoe.diary.utils.LogUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author zoe
 * created 2019/12/5 17:30
 */

public class DiaryFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tv_date)
    TextView tvDate;

    private boolean showCalendar = true;
    private static final String KEY_TAG = "KEY_TAG";
    private String tag = "";
    private List<CalendarFragment> fragmentList;
    private int targetYear = Calendar.getInstance().get(Calendar.YEAR);
    private int targetMonth = Calendar.getInstance().get(Calendar.MONTH);
    private DiaryAdapter diaryAdapter;

    public static DiaryFragment getInstance(String tag) {
        DiaryFragment diaryFragment = new DiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TAG, tag);
        diaryFragment.setArguments(bundle);
        return diaryFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_diary;
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
            tag = arguments.getString(KEY_TAG);
        }
        genCalendarFragment();
    }

    private void genCalendarFragment() {
        fragmentList = new ArrayList<>();
        for (int month = 0; month < 12; month++) {
            fragmentList.add(CalendarFragment.getInstance(targetYear, month));
        }
    }

    private void initView() {
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setPageTransformer(false, new DiaryPageTransformer());
        diaryAdapter = new DiaryAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(diaryAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                targetMonth = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setYearTitle();
        viewPager.setCurrentItem(targetMonth);
    }

    private void setYearTitle() {
        tvDate.setText(String.valueOf(targetYear));
    }

    private void notifyDate() {
        for (int i = 0; i < fragmentList.size(); i++) {
            CalendarFragment fragment = fragmentList.get(i);
            fragment.setYear(targetYear);
            fragment.notifyUpdateUI();
        }
    }

    @OnClick(R.id.tv_rotate)
    public synchronized void onRotate() {
        for (CalendarFragment fragment : fragmentList) {
            fragment.doAnim();
        }
        showCalendar = !showCalendar;
    }

    @OnClick(R.id.tv_date)
    public void onTvDate() {
        DiaryDateBottomDialog diaryDateBottomDialog = new DiaryDateBottomDialog(getActivity(), targetYear, targetMonth);
        diaryDateBottomDialog.setOnDateListener(new DiaryDateBottomDialog.OnDateListener() {
            @Override
            public void onDate(int year, int month) {
                boolean yearChanged = (targetYear != year);
                //更新年份显示
                targetYear = year;
                targetMonth = month;
                if (yearChanged) {
                    setYearTitle();
                    notifyDate();
                }
                viewPager.setCurrentItem(targetMonth);
            }
        });
        diaryDateBottomDialog.show();
    }

    public int getTargetYear() {
        return targetYear;
    }

    public int getTargetMonth() {
        return targetMonth;
    }

    public void updateSolidBg() {
        int currentItem = viewPager.getCurrentItem();
        CalendarFragment fragment = fragmentList.get(currentItem);
        fragment.updateSolidBg();
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if(data instanceof Integer) {
            int msg = (int) data;
            if(msg == Constants.MSG.NEW_DIARY_NOTIFY) {
                updateDiaryShowUI();
            }
        }
    }

    private void updateDiaryShowUI() {
        int currentItem = viewPager.getCurrentItem();
        CalendarFragment fragment = fragmentList.get(currentItem);
        fragment.updateDiaryShowUI();
    }
}
